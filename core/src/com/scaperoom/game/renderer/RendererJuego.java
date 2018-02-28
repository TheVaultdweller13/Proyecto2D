package com.scaperoom.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.game.AssetsJuego;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

/**
 * Created by dam208 on 27/02/2018.
 */

public class RendererJuego implements InputProcessor{

    private Mundo miMundo;

    private SpriteBatch batch;
    private Vector3 temporal;
    private ShapeRenderer shaperender;


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
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
            dibujarMapa();
            dibujarBernard();
            dibujarLeChuck();
            dibujarParedes();
            dibujarPuertas();
            dibujarNieblas();
        batch.end();

        if(debugger){
            debugger();
        }

    }


    private void dibujarMapa(){
        batch.draw(AssetsJuego.textureMap,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarParedes(){
        batch.draw(AssetsJuego.textureParedes,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }
    private void dibujarPuertas(){
        batch.draw(AssetsJuego.texturePuertas,
                115,144,29,48);
        batch.draw(AssetsJuego.texturePuertas,
                405,202,29,48);
    }

    private void dibujarBernard(){
        Bernard bernard = miMundo.getBernard();
        batch.draw(AssetsJuego.textureCharacterBernard,
                bernard.getPosicion().x,
                bernard.getPosicion().y,
                bernard.getTamaño().x,
                bernard.getTamaño().y);
    }

    private void dibujarLeChuck(){
        LeChuck lechuck = miMundo.getLechuck();
        batch.draw(AssetsJuego.textureCharacterLeChuck,
                lechuck.getPosicion().x,
                lechuck.getPosicion().y,
                lechuck.getTamaño().x,
                lechuck.getTamaño().y);
    }

    private void dibujarNieblas() {
        Texture textura=null;
        for (ElementoMovil niebla : miMundo.getNiebla()){

            niebla.setPosicion(niebla.getPosicion().x+(1*delta), niebla.getPosicion().y);

            switch(niebla.getTipo()){
                case NIEBLA:
                    textura = AssetsJuego.textureNiebla;
                    break;
                case ANTI_NIEBLA:
                    textura = AssetsJuego.textureAntiniebla;
                    break;
            }
            if (niebla.getVelocidad()<0){
                batch.draw(textura,niebla.getPosicion().x+niebla.getTamaño().x,niebla.getPosicion().y,-niebla.getTamaño().x,niebla.getTamaño().y);
            }
            else{
                batch.draw(textura,niebla.getPosicion().x,niebla.getPosicion().y,niebla.getTamaño().x,niebla.getTamaño().y);
            }
        }
    }

    public OrthographicCamera getCamara2d(){
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
    private void debugger(){

        shaperender.begin(ShapeRenderer.ShapeType.Line);
            shaperender.setColor(Color.BLACK);
            shaperender.rect(bernard.getPosicion().x,bernard.getPosicion().y,bernard.getTamaño().x,bernard.getTamaño().y);
            shaperender.rect(lechuck.getPosicion().x,lechuck.getPosicion().y,lechuck.getTamaño().x,lechuck.getTamaño().y);
        shaperender.end();

        shaperender.begin(ShapeRenderer.ShapeType.Line);
            shaperender.setColor(Color.RED);
            shaperender.rect(0,0,miMundo.TAMAÑO_MUNDO_ANCHO-5,miMundo.TAMAÑO_MUNDO_ALTO-5);
            shaperender.rect(miMundo.BORDES[0].x, miMundo.BORDES[0].y, miMundo.BORDES[0].width, miMundo.BORDES[0].height, Color.GOLD, Color.GOLD, Color.GOLD, Color.GOLD);
            shaperender.rect(miMundo.BORDES[1].x, miMundo.BORDES[1].y, miMundo.BORDES[1].width, miMundo.BORDES[1].height, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW);
            shaperender.rect(miMundo.ROOM_COCINA.x, miMundo.ROOM_COCINA.y, miMundo.ROOM_COCINA.width, miMundo.ROOM_COCINA.height, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
            shaperender.rect(miMundo.ROOM_BAÑO.x, miMundo.ROOM_BAÑO.y, miMundo.ROOM_BAÑO.width, miMundo.ROOM_BAÑO.height, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN);
            shaperender.rect(miMundo.ROOM_ESTUDIO.x, miMundo.ROOM_ESTUDIO.y, miMundo.ROOM_ESTUDIO.width, miMundo.ROOM_ESTUDIO.height, Color.ORANGE, Color.ORANGE, Color.ORANGE, Color.ORANGE);
            shaperender.rect(miMundo.ROOM_SALON[0].x, miMundo.ROOM_SALON[0].y, miMundo.ROOM_SALON[0].width, miMundo.ROOM_SALON[0].height, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA);
            shaperender.rect(miMundo.ROOM_SALON[1].x, miMundo.ROOM_SALON[1].y, miMundo.ROOM_SALON[1].width, miMundo.ROOM_SALON[1].height, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA);
            shaperender.rect(miMundo.ROOM_SALON[2].x, miMundo.ROOM_SALON[2].y, miMundo.ROOM_SALON[2].width, miMundo.ROOM_SALON[2].height, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
            shaperender.rect(miMundo.PASILLOS[0].x, miMundo.PASILLOS[0].y, miMundo.PASILLOS[0].width, miMundo.PASILLOS[0].height, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
            shaperender.rect(miMundo.PASILLOS[1].x, miMundo.PASILLOS[1].y, miMundo.PASILLOS[1].width, miMundo.PASILLOS[1].height, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
            shaperender.rect(miMundo.ESPACIO_MOVIL[0].x, miMundo.ESPACIO_MOVIL[0].y, miMundo.ESPACIO_MOVIL[0].width, miMundo.ESPACIO_MOVIL[0].height, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
            shaperender.rect(miMundo.ESPACIO_MOVIL[1].x, miMundo.ESPACIO_MOVIL[1].y, miMundo.ESPACIO_MOVIL[1].width, miMundo.ESPACIO_MOVIL[1].height, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN);
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
        temporal.set(screenX,screenY,0);
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
