package com.scaperoom.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.controlador.ControladorJuego;
import com.scaperoom.game.game.AssetsJuego;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.Inventario;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

import javax.naming.ldap.Control;

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

    private StringBuilder sbuffer;
    private BitmapFont bitMapFont;


    public RendererJuego(Mundo miMundo) {
        this.miMundo = miMundo;
        batch = new SpriteBatch();
        shaperender = new ShapeRenderer();
        camara2d = new OrthographicCamera();

        bernard = miMundo.getBernard();
        lechuck = miMundo.getLechuck();
        crono = 0;
        Gdx.input.setInputProcessor(this);

        //Libgdx by default, creates a BitmapFont using the default 15pt Arial font included in the libgdx JAR file.
        //Using FreeTypeFont, it is possible so create fonts with a desired size on the fly.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/DS-DIGIT.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (15 * Mundo.PROPORCION_REAL_MUNDO_ANCHO);
        this.bitMapFont = generator.generateFont(parameter); // font size in pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        sbuffer = new StringBuilder();
        this.bitMapFont.setColor(Color.WHITE);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        dibujarFondo();
        dibujarSombras();
        dibujarMapa();

        if (bernard.getRectangulo().y > lechuck.getRectangulo().y) {
            dibujarBernard();
            dibujarLeChuck();
        } else {
            dibujarLeChuck();
            dibujarBernard();
        }
        dibujarParedes();
        dibujarPuertas();
        dibujarNieblas();
        dibujarLlaveBaño();
        dibujarLlaveEstudio();
        dibujarMuñecoVudu();
        dibujarLlaveFinal();
        dibujarTiempo();

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
        crono += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) AssetsJuego.bernardAnimacion.getKeyFrame(crono, true);
        if (!bernard.direccion.isZero()) {
            if (bernard.direccion.x > 0) {
                batch
                        .draw(currentFrame, bernard.getPosicion().x,
                                bernard.getPosicion().y, bernard.getTamaño().x + 20,
                                bernard.getTamaño().y);
            } else {
                batch
                        .draw(currentFrame, (bernard.getPosicion().x + 40),
                                bernard.getPosicion().y, (-bernard.getTamaño().x - 20),
                                bernard.getTamaño().y);
            }
        } else
            batch.draw(AssetsJuego.textureCharacterBernard, bernard.getPosicion().x, bernard.getPosicion().y, bernard.getTamaño().x, bernard.getTamaño().y);
    }

    private void dibujarLeChuck() {
        LeChuck lechuck = miMundo.getLechuck();
        crono += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) AssetsJuego.lechuckAnimacion.getKeyFrame(crono, true);
        if (ControladorJuego.controlLeChuck) {
            batch.draw(currentFrame, lechuck.getPosicion().x,
                    lechuck.getPosicion().y, lechuck.getTamaño().x,
                    lechuck.getTamaño().y);
        } else {
            batch.draw(currentFrame, (lechuck.getPosicion().x + 60),
                    lechuck.getPosicion().y, -lechuck.getTamaño().x,
                    lechuck.getTamaño().y);
        }

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

    private void dibujarLlaveBaño(){
        if(!miMundo.getInventario().tengo_llavebaño)
            batch.draw(AssetsJuego.textureLlaveBaño_inactivo, Mundo.LLAVE_BAÑO_INACTIVO.x, Mundo.LLAVE_BAÑO_INACTIVO.y, Mundo.LLAVE_BAÑO_INACTIVO.width, Mundo.LLAVE_BAÑO_INACTIVO.height);
        else
            batch.draw(AssetsJuego.textureLlaveBaño, Mundo.LLAVE_BAÑO_ACTIVO.x, Mundo.LLAVE_BAÑO_ACTIVO.y, Mundo.LLAVE_BAÑO_ACTIVO.width, Mundo.LLAVE_BAÑO_ACTIVO.height);
    }
    private void dibujarLlaveEstudio(){
        if(!miMundo.getInventario().tengo_llaveestudio)
            batch.draw(AssetsJuego.textureLlaveEstudio_inactivo,Mundo.LLAVE_ESTUDIO_INACTIVO.x, Mundo.LLAVE_ESTUDIO_INACTIVO.y, Mundo.LLAVE_ESTUDIO_INACTIVO.width, Mundo.LLAVE_ESTUDIO_INACTIVO.height);
        else
            batch.draw(AssetsJuego.textureLlaveEstudio,Mundo.LLAVE_ESTUDIO_ACTIVO.x, Mundo.LLAVE_ESTUDIO_ACTIVO.y, Mundo.LLAVE_ESTUDIO_ACTIVO.width, Mundo.LLAVE_ESTUDIO_ACTIVO.height);
    }
    private void dibujarMuñecoVudu(){
        if(!miMundo.getInventario().tengo_muñecovudu)
            batch.draw(AssetsJuego.textureMuñecoVuduInactivo, Mundo.MUÑECO_VUDU_INACTIVO.x, Mundo.MUÑECO_VUDU_INACTIVO.y, Mundo.MUÑECO_VUDU_INACTIVO.width, Mundo.MUÑECO_VUDU_INACTIVO.height);
        else
            batch.draw(AssetsJuego.textureMuñecoVudu, Mundo.MUÑECO_VUDU_ACTIVO.x, Mundo.MUÑECO_VUDU_ACTIVO.y, Mundo.MUÑECO_VUDU_ACTIVO.width, Mundo.MUÑECO_VUDU_ACTIVO.height);
    }
    private void dibujarLlaveFinal(){
        if(!miMundo.getInventario().tengo_llavefinal)
            batch.draw(AssetsJuego.textureLlaveFinalInactivo, lechuck.getRectangulo().getCenter(lechuck.centro).x, Mundo.lechuck.getRectangulo().getCenter(lechuck.centro).y, 16, 16);
        else
        batch.draw(AssetsJuego.textureLlaveFinal, Mundo.LLAVE_FINAL_ACTIVO.x, Mundo.LLAVE_FINAL_ACTIVO.y, Mundo.LLAVE_FINAL_ACTIVO.width, Mundo.LLAVE_FINAL_ACTIVO.height);
    }

    private void dibujarTiempo() {
        // Borrar texto
        sbuffer.setLength(0);
        sbuffer.append(miMundo.getCronometro());
        // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().scale(1 / Mundo.PROPORCION_REAL_MUNDO_ANCHO, 1 / Mundo.PROPORCION_REAL_MUNDO_ALTO, 1));
        this.bitMapFont.draw(batch, sbuffer, 0, this.bitMapFont.getXHeight());
        batch.setProjectionMatrix(originalMatrix); //revert projection
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
        shaperender.rect(lechuck.getRectangulo().x, lechuck.getRectangulo().y, lechuck.getRectangulo().width, lechuck.getRectangulo().height);
        shaperender.rect(lechuck.getPosicion().x, lechuck.getPosicion().y, lechuck.getTamaño().x, lechuck.getTamaño().y, Color.RED, Color.RED, Color.RED, Color.RED);
        shaperender.end();

        shaperender.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < Mundo.SUELOS.size(); i++) {
            Rectangle r = Mundo.SUELOS.get(i);
            shaperender.setColor(new Color().fromHsv(360 * i / Mundo.SUELOS.size(), 1, 1));
            shaperender.rect(r.x, r.y, r.width, r.height);
        }
        for (int i = 0; i < Mundo.PUNTOS_DESPLAZAMIENTO.length; i++) {
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
