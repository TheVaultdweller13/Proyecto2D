package com.scaperoom.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.game.AssetsJuego;
import com.scaperoom.game.game.Audio;
import com.scaperoom.game.game.Juego;
import com.scaperoom.game.modelo.Controles;
import com.scaperoom.game.modelo.Mundo;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class PantallaPresentacion implements Screen, InputProcessor {


    private Juego juego;
    private SpriteBatch batch;
    private OrthographicCamera camara2d;

    private Rectangle botones[] = {
            new Rectangle(200, 230, 170, 60),
            new Rectangle(200, 150, 170, 70),
            new Rectangle(200, 75, 170, 70)
    };
    Preferences prefs = Gdx.app.getPreferences("controlmusica");

    public PantallaPresentacion(Juego juego) {
        this.juego = juego;
        batch = new SpriteBatch();
        camara2d = new OrthographicCamera();
        Controles.setAudio_activado(prefs.getBoolean("controlmusica", true));
    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link com.badlogic.gdx.Input.Buttons#LEFT} on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button   @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 temp = new Vector3(screenX, screenY, 0);
        camara2d.unproject(temp);
        Circle dedo = new Circle(temp.x, temp.y, 2);


        if (Intersector.overlaps(dedo, botones[0])) {    // Pulsar Juego nuevo
            dispose();
            juego.setScreen(new PantallaJuego(juego));
        }

        if (Intersector.overlaps(dedo, botones[1])) {    // Pulsar Marcadores
            dispose();
            juego.setScreen(new PantallaPuntuacion(juego));
        }
        if (Intersector.overlaps(dedo, botones[2])) { //Pulsar salir
            Gdx.app.exit();
        }
        if (Intersector.overlaps(dedo, Controles.CONTROL_AUDIO)) {
            if (Controles.isAudio_activado()) {
                Controles.setAudio_activado(false);
            } else {
                Controles.setAudio_activado(true);
            }
            prefs.putBoolean("controlmusica", Controles.isAudio_activado());
            prefs.flush();
        }
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.  @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * Called when this screen becomes the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void show() {
        Audio.audioPresentacion.play();
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(AssetsJuego.texturePresentacion, 0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
        if (Controles.isAudio_activado()) {
            batch.draw(AssetsJuego.textureMusicOn,
                    Controles.CONTROL_AUDIO.x,
                    Controles.CONTROL_AUDIO.y,
                    Controles.CONTROL_AUDIO.width,
                    Controles.CONTROL_AUDIO.height);
            Audio.activarAudio();
        } else {
            batch.draw(AssetsJuego.textureMusicOff,
                    Controles.CONTROL_AUDIO.x,
                    Controles.CONTROL_AUDIO.y,
                    Controles.CONTROL_AUDIO.width,
                    Controles.CONTROL_AUDIO.height);
            Audio.silenciarAudio();
        }
        batch.end();
    }

    /**
     * @param width
     * @param height
     * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camara2d.setToOrtho(false, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
        camara2d.update();

        batch.setProjectionMatrix(camara2d.combined);
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link com.badlogic.gdx.Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
        juego.setScreen(new PantallaJuego(juego));
    }
}
