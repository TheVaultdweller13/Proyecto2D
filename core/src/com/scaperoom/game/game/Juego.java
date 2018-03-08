package com.scaperoom.game.game;

import com.badlogic.gdx.Game;
import com.scaperoom.game.pantallas.PantallaJuego;
import com.scaperoom.game.pantallas.PantallaPresentacion;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Juego extends Game {

    @Override
    public void create() {
        AssetsJuego.cargarTexturas();
        Audio.iniciarAudio();
        setScreen(new PantallaPresentacion(this));
    }

    @Override
    public void dispose() {
        super.dispose();

        AssetsJuego.liberarTexturas();
        Audio.liberarAudio();
    }
}
