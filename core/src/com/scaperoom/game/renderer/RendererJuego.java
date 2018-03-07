package com.scaperoom.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.controlador.ControladorJuego;
import com.scaperoom.game.game.AssetsJuego;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class RendererJuego implements InputProcessor {

    private Mundo miMundo;

    private SpriteBatch batch;
    private Vector3 temporal;
    private ShapeRenderer shaperender;

    private float crono;

    private float delta = Gdx.graphics.getDeltaTime();

    private boolean debugger = true;
    private Bernard bernard;
    private LeChuck lechuck;

    private OrthographicCamera camara2d;


    public RendererJuego(Mundo miMundo) {
        this.miMundo = miMundo;
        batch = new SpriteBatch();
        shaperender = new ShapeRenderer();
        camara2d = new OrthographicCamera();

        bernard = miMundo.getBernard();
        lechuck = miMundo.getLechuck();
        crono = 0;
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            dibujarFondo();
            dibujarSombras();
            dibujarMapa();
            if(bernard.getRectangulo().y>lechuck.getRectangulo().y){
                dibujarBernard();
                dibujarLeChuck();
            }
            else{
                dibujarLeChuck();
                dibujarBernard();
            }

            dibujarLeChuck();
            dibujarParedes();
            dibujarPuertas();
            dibujarNieblas();
        batch.end();

        if (debugger) {
            debugger();
        }

    }

    private void dibujarFondo() {
        batch.draw(AssetsJuego.textureBackground,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarMapa() {
        batch.draw(AssetsJuego.textureMap,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarParedes() {
        batch.draw(AssetsJuego.textureParedes,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarPuertas() {
        batch.draw(AssetsJuego.texturePuertas,
                115, 144, 29, 48);
        batch.draw(AssetsJuego.texturePuertas,
                405, 202, 29, 48);
    }

    private void dibujarBernard() {
        Bernard bernard = miMundo.getBernard();
        crono+=Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) AssetsJuego.bernardAnimacion.getKeyFrame(crono, true);
        if(!bernard.direccion.isZero()){
            if(bernard.direccion.x>0){
                batch
                        .draw(currentFrame, bernard.getPosicion().x,
                                bernard.getPosicion().y, bernard.getTamaño().x+20,
                                bernard.getTamaño().y);
            }
            else{
                batch
                        .draw(currentFrame, (bernard.getPosicion().x+40),
                                bernard.getPosicion().y, (-bernard.getTamaño().x-20),
                                bernard.getTamaño().y);
            }
        }
        else
            batch.draw(AssetsJuego.textureCharacterBernard, bernard.getPosicion().x, bernard.getPosicion().y, bernard.getTamaño().x, bernard.getTamaño().y);
    }

    private void dibujarLeChuck() {
        LeChuck lechuck = miMundo.getLechuck();

        if(ControladorJuego.controlLeChuck)
            batch.draw(AssetsJuego.textureCharacterLeChuck, lechuck.getPosicion().x, lechuck.getPosicion().y, lechuck.getTamaño().x, lechuck.getTamaño().y);

        else
            batch.draw(AssetsJuego.textureCharacterLeChuck, lechuck.getPosicion().x + lechuck.getTamaño().x, lechuck.getPosicion().y, -lechuck.getTamaño().x, lechuck.getTamaño().y);
    }

    private void dibujarSombras() {
        Texture textura = null;
        for (ElementoMovil sombras : miMundo.getSombra()) {

            switch (sombras.getTipo()) {
                case SILUETA_MUJER:
                    textura = AssetsJuego.textureSombraMujer;
                    break;
                case SILUETA_HOMBRE:
                    textura = AssetsJuego.textureSombraHombre;
                    break;
            }
            if (sombras.getVelocidad() > 0) {
                batch.draw(textura, sombras.getPosicion().x + sombras.getTamaño().x, sombras.getPosicion().y, -sombras.getTamaño().x, sombras.getTamaño().y);
            } else {
                batch.draw(textura, sombras.getPosicion().x, sombras.getPosicion().y, sombras.getTamaño().x, sombras.getTamaño().y);
            }
        }
    }

    private void dibujarNieblas() {
        Texture textura = null;
        for (ElementoMovil niebla : miMundo.getNiebla()) {

            switch (niebla.getTipo()) {
                case NIEBLA:
                    textura = AssetsJuego.textureNiebla;
                    break;
                case ANTI_NIEBLA:
                    textura = AssetsJuego.textureAntiniebla;
                    break;
            }
            if (niebla.getVelocidad() < 0) {
                batch.draw(textura, niebla.getPosicion().x + niebla.getTamaño().x, niebla.getPosicion().y, -niebla.getTamaño().x, niebla.getTamaño().y);
            } else {
                batch.draw(textura, niebla.getPosicion().x, niebla.getPosicion().y, niebla.getTamaño().x, niebla.getTamaño().y);
            }
        }
    }

    public OrthographicCamera getCamara2d() {
        return this.camara2d;
    }

    public void resize(int width, int height) {

        camara2d.setToOrtho(false, Mundo.TAMAÑO_MUNDO_ANCHO,
                Mundo.TAMAÑO_MUNDO_ALTO);
        camara2d.update();

        batch.setProjectionMatrix(camara2d.combined);
        shaperender.setProjectionMatrix(camara2d.combined);

    }

    public void dispose() {

        Gdx.input.setInputProcessor(null);
        batch.dispose();
    }

    /**
     * Dibuja los gráficos en forma de figuras geométricas
     */
    private void debugger() {

        shaperender.begin(ShapeRenderer.ShapeType.Line);
        shaperender.setColor(Color.BLACK);
        shaperender.rect(bernard.getRectangulo().x, bernard.getRectangulo().y, bernard.getRectangulo().width, bernard.getRectangulo().height);
        shaperender.rect(bernard.getPosicion().x, bernard.getPosicion().y, bernard.getTamaño().x, bernard.getTamaño().y, Color.RED, Color.RED, Color.RED, Color.RED);
        shaperender.rect(lechuck.getPosicion().x, lechuck.getPosicion().y, lechuck.getTamaño().x, lechuck.getTamaño().y);
        shaperender.end();

        shaperender.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < miMundo.SUELOS.length; i++) {
            Rectangle r = miMundo.SUELOS[i];
            shaperender.setColor(new Color().fromHsv(360 * i / miMundo.SUELOS.length, 1, 1));
            shaperender.rect(r.x, r.y, r.width, r.height);
        }
        for (int i = 0; i < miMundo.PUNTOS_DESPLAZAMIENTO.length; i++){
            shaperender.circle(Mundo.PUNTOS_DESPLAZAMIENTO[i].x, Mundo.PUNTOS_DESPLAZAMIENTO[i].y, Mundo.PUNTOS_DESPLAZAMIENTO[i].radius);
        }
        shaperender.end();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        temporal.set(screenX, screenY, 0);
        camara2d.unproject(temporal);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
