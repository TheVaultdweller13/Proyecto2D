package com.scaperoom.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.controlador.ControladorJuego;
import com.scaperoom.game.game.Juego;
import com.scaperoom.game.modelo.Controles;
import com.scaperoom.game.modelo.Mundo;
import com.scaperoom.game.renderer.RendererJuego;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class PantallaJuego implements Screen, InputProcessor {

    private Mundo miMundo;
    private Juego juego;
    private ControladorJuego c_juego;
    private RendererJuego r_juego;

    public PantallaJuego(Juego juego) {
        this.juego = juego;
        miMundo = new Mundo();
        r_juego = new RendererJuego(miMundo);
        c_juego = new ControladorJuego(miMundo);
    }

    @Override
    public void render(float delta) {
        r_juego.render(delta);
        c_juego.update(delta);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        r_juego.resize(width, height);
    }

    @Override
    public void pause() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(this);
        r_juego.dispose();
    }


    @Override
    public boolean keyDown(int keycode) {
// Liberamos las teclas para que se arrastramos o dedo a outro control sen soltar o anterior non xunte o efecto
        c_juego.liberarTecla(ControladorJuego.Keys.ABAJO);
        c_juego.liberarTecla(ControladorJuego.Keys.ARRIBA);
        c_juego.liberarTecla(ControladorJuego.Keys.IZQUIERDA);
        c_juego.liberarTecla(ControladorJuego.Keys.DERECHA);

        switch (keycode) {
            case Input.Keys.UP:
                c_juego.pulsarTecla(ControladorJuego.Keys.ARRIBA);
                break;
            case Input.Keys.DOWN:
                c_juego.pulsarTecla(ControladorJuego.Keys.ABAJO);
                break;
            case Input.Keys.LEFT:
                c_juego.pulsarTecla(ControladorJuego.Keys.IZQUIERDA);
                break;
            case Input.Keys.RIGHT:
                c_juego.pulsarTecla(ControladorJuego.Keys.DERECHA);
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                c_juego.liberarTecla(ControladorJuego.Keys.ARRIBA);
                break;
            case Input.Keys.DOWN:
                c_juego.liberarTecla(ControladorJuego.Keys.ABAJO);
                break;
            case Input.Keys.LEFT:
                c_juego.liberarTecla(ControladorJuego.Keys.IZQUIERDA);
                break;
            case Input.Keys.RIGHT:
                c_juego.liberarTecla(ControladorJuego.Keys.DERECHA);
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 temporal = new Vector3(screenX, screenY, 0);

        this.r_juego.getCamara2d().unproject(temporal);
//
//        if (Intersector.overlaps(new Circle(temporal.x, temporal.y, 2), Controles.FLECHA_IZQUIERDA)) {
//            c_juego.pulsarTecla(ControladorJuego.Keys.IZQUIERDA);
//        } else if (Intersector.overlaps(new Circle(temporal.x, temporal.y, 2), Controles.FLECHA_DERECHA)) {
//            c_juego.pulsarTecla(ControladorJuego.Keys.DERECHA);
//        } else if (Intersector.overlaps(new Circle(temporal.x, temporal.y, 2), Controles.FLECHA_ABAJO)) {
//            c_juego.pulsarTecla(ControladorJuego.Keys.ABAJO);
//        } else if (Intersector.overlaps(new Circle(temporal.x, temporal.y, 2), Controles.FLECHA_ARRIBA)) {
//            c_juego.pulsarTecla(ControladorJuego.Keys.ARRIBA);
//        }

        Circle dedo = new Circle(temporal.x, temporal.y, 2);
        Vector2 posicionDedo = new Vector2(dedo.x, dedo.y);
        miMundo.getBernard().puntoDestino.set(posicionDedo, posicionDedo.y);
        Vector2 direccion = posicionDedo.sub(miMundo.getBernard().getPosicion());
        miMundo.getBernard().direccion.set(direccion.nor());

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
