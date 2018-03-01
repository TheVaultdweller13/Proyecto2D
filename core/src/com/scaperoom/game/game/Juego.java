package com.scaperoom.game.game;

import com.badlogic.gdx.Game;
import com.scaperoom.game.pantallas.PantallaJuego;

/**
 * Created by dam208 on 27/02/2018.
 */

public class Juego extends Game {

    //opción A
    private PantallaJuego pantallajuego;

    @Override
    public void create() {
        // TODO Auto-generated method stub

        //     opción A
        AssetsJuego.cargarTexturas();
        pantallajuego = new PantallaJuego(this);
        setScreen(pantallajuego);

//        AssetsJuego.cargarTexturas();
//        Audio.iniciarAudio();
//        setScreen(new PantallaPresentacion(this));
    }

    @Override
    public void dispose() {
        super.dispose();

//        AssetsJuego.liberarTexturas();
//        Audio.liberarAudio();
        //opción A
        AssetsJuego.liberarTexturas();
    }
}
