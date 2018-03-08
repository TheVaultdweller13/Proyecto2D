package com.scaperoom.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scaperoom.game.game.HighScores;
import com.scaperoom.game.game.Juego;
import com.scaperoom.game.modelo.Mundo;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class PantallaPuntuacion implements Screen, InputProcessor {

    private Juego juego;
    private StringBuilder sbuffer;
    private BitmapFont bitMapFont;
    private SpriteBatch batch;
    PantallaPresentacion pantallapresentacion;

    public PantallaPuntuacion(Juego juego) {
        this.juego = juego;
        batch = new SpriteBatch();
      //  Audio.liberarAudio();
        pantallapresentacion = new PantallaPresentacion(juego);
    }
        @Override
        public void resize(int width, int height) {
            // TODO Auto-generated method stub

        }

        /**
         * Called when the screen should render itself.
         *
         * @param delta The time in seconds since the last render.
         */
        @Override
        public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            bitMapFont.setColor(Color.YELLOW);
            //bitMapFont.setScale(0.5f, 2);
            bitMapFont.draw(batch, sbuffer, 30 * Mundo.PROPORCION_REAL_MUNDO_ANCHO, 450 * Mundo.PROPORCION_REAL_MUNDO_ALTO);
            batch.end();
        }

        @Override
        public void show() {
            Gdx.input.setInputProcessor(this);
            HighScores.load();
            String[] puesto = {"Primer", "Segundo", "Tercer"};
            for (int x = 0; x < HighScores.highscores.length; x++) {
                sbuffer.append("\n\n\n" + puesto[x] + " puesto: " + HighScores.highscores[x]);
            }
        }

        @Override
        public void pause() {
        }

        @Override
        public void resume() {
        }

        @Override
        public void hide() {
        }

        @Override
        public void dispose() {
            batch.dispose();
            bitMapFont.dispose();
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
            juego.setScreen(pantallapresentacion);
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
