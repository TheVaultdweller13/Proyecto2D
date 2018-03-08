package com.scaperoom.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class HighScores {

    public static int[] highscores = {999, 999, 999};
    public static String archivoHighscores = "highscores.dat";
    private static final FileHandle archivo = Gdx.files.external(HighScores.archivoHighscores);

    public static void load() {

        if (!archivo.exists())
            HighScores.save();
        else {
            String[] texto = archivo.readString().split(",");
            highscores[0] = Integer.parseInt(texto[0]);
            highscores[1] = Integer.parseInt(texto[1]);
            highscores[2] = Integer.parseInt(texto[2]);
        }
    }

    public static void añadirPuntuacion(int puntuacion) {
        int i = 0, j = 0;
        // Cargar las puntuaciones actuales
        load();
        // Actualizar las puntuaciones
        while (i < highscores.length) {
            if (puntuacion < highscores[i]) {
                j = i; // se empujan las puntuaciones hacia abajo para hacer hueco para la nueva
                while (j + 1 < highscores.length) {
                    highscores[j + 1] = highscores[j];
                    ++j;
                }
                highscores[i] = puntuacion;
                break;
            }
            ++i;
        }
        // Guardar las puntuaciones actualizadas
        save();
    }

    private static void save() {
        archivo.writeString(Integer.toString(HighScores.highscores[0]) + ",", false);
        archivo.writeString(Integer.toString(HighScores.highscores[1]) + ",", true);
        archivo.writeString(Integer.toString(HighScores.highscores[2]), true);
    }
}
