package com.scaperoom.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.Arrays;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class HighScores {

    public static String[] highscores = {"0", "0", "0"};
    public static String archivoHighscores = "highscores.dat";


    public static void load() {
        FileHandle archivo = Gdx.files.external(HighScores.archivoHighscores);

        if (!archivo.exists())
            HighScores.save();
        else {
            String texto = archivo.readString();
            highscores = texto.split(",");
        }

    }

    public static void añadirPuntuacion(int puntuacion) {
        boolean encontrado = false;
        int i = 0;
        String[] aux = (String[]) Arrays.copyOf(highscores, highscores.length);

        while ((i < HighScores.highscores.length) && (!encontrado)) {
            if (puntuacion > Integer.parseInt(HighScores.highscores[i])) {
                highscores[i] = String.valueOf(puntuacion);
                encontrado = true;
                ++i;
            } else
                ++i;
            if (encontrado) {
                --i;

                while (i + 1 < highscores.length) {
                    highscores[i + 1] = aux[i];
                    ++i;
                }
                save();
            }
        }
    }

    private static void save() {
        FileHandle arquivo = Gdx.files.external(HighScores.archivoHighscores);
        arquivo.writeString(HighScores.highscores[0] + ",", false);
        arquivo.writeString(HighScores.highscores[1] + ",", true);
        arquivo.writeString(HighScores.highscores[2], true);
    }
}
