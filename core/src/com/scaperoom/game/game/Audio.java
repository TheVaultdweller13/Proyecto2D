package com.scaperoom.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Audio {

    public static Music
            audioJuego,
            audioPresentacion;


    public static void iniciarAudio() {
        FileHandle audioFileHandle;

        audioFileHandle = Gdx.files.internal("audio/title.mp3");
        audioPresentacion = Gdx.audio.newMusic(audioFileHandle);
        // audioPresentacion.setVolume(0.5f);
        audioPresentacion.setLooping(true);

        audioFileHandle = Gdx.files.internal("audio/game.mp3");
        audioJuego = Gdx.audio.newMusic(audioFileHandle);
        // audioPresentacion.setVolume(0.5f);
        audioJuego.setLooping(true);
    }

    public static void liberarAudio(){
        audioJuego.dispose();
        audioPresentacion.dispose();
    }
    public static void silenciarAudio(){
        audioJuego.setVolume(0f);
        audioPresentacion.setVolume(0f);
    }
    public static void activarAudio(){
        audioJuego.setVolume(0.5f);
        audioPresentacion.setVolume(0.5f);
    }
}
