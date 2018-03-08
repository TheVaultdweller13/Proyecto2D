package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Equipo on 08/03/2018.
 */

public class Controles {

    public final static Rectangle CONTROL_SALIR = new Rectangle(510, 12, 64, 64);
    public final static Rectangle CONTROL_AUDIO = new Rectangle(10, 10, 50, 50);

    /**
     * Booleano que controla si el audio está activado o desactivado
     */
    private static boolean audio_activado;

    public static boolean isAudio_activado() {
        return audio_activado;
    }

    public static void setAudio_activado(boolean audio_activado) {
        Controles.audio_activado = audio_activado;
    }

}
