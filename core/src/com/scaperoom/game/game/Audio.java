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
            audioPresentacion,
            audioPuntuacion;


    public static void iniciarAudio() {
        FileHandle audioFileHandle;

        audioFileHandle = Gdx.files.internal("audio/title.mp3");
        audioPresentacion = Gdx.audio.newMusic(audioFileHandle);
        audioPresentacion.setLooping(true);

        audioFileHandle = Gdx.files.internal("audio/game.mp3");
        audioJuego = Gdx.audio.newMusic(audioFileHandle);
        audioJuego.setLooping(true);

        audioFileHandle = Gdx.files.internal("audio/puntuacion.mp3");
        audioPuntuacion = Gdx.audio.newMusic(audioFileHandle);
        audioPuntuacion.setLooping(true);
    }

    public static void liberarAudio(){
        audioJuego.dispose();
        audioPresentacion.dispose();
        audioPuntuacion.dispose();
    }
    public static void silenciarAudio(){
        audioJuego.setVolume(0f);
        audioPresentacion.setVolume(0f);
        audioPuntuacion.setVolume(0f);
    }
    public static void activarAudio(){
        audioJuego.setVolume(0.5f);
        audioPresentacion.setVolume(0.5f);
        audioPuntuacion.setVolume(0.5f);
    }
}
