package com.scaperoom.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class AssetsJuego {

    public static Texture textureMap;
    public static Texture textureParedes;
    public static Texture texturePuertas;
    public static Texture textureCharacterBernard;
    public static Texture textureCharacterLeChuck;
    public static Texture textureNiebla;
    public static Texture textureAntiniebla;

    public static Animation bernardArriba,
            bernardAbajo,
            bernardIzquierda,
            bernardDerecha;

    public static void cargarTexturas(){

        FileHandle imageFileHandle;

          /* Fondo del juego */
        imageFileHandle = Gdx.files.internal("graficos/map_room.jpg");
        textureMap = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/paredes.png");
        textureParedes = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/puerta.png");
        texturePuertas = new Texture(imageFileHandle);

        imageFileHandle = Gdx.files.internal("graficos/niebla.png");
        textureNiebla = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/antiniebla.png");
        textureAntiniebla = new Texture(imageFileHandle);

        /* Personajes */
        imageFileHandle = Gdx.files.internal("graficos/simple_bernard_character.png");
        textureCharacterBernard = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/simple_lechuck_character.png");
        textureCharacterLeChuck= new Texture(imageFileHandle);

    }

    public static void liberarTexturas(){

        textureMap.dispose();
        textureCharacterBernard.dispose();
        textureParedes.dispose();
        texturePuertas.dispose();
        textureNiebla.dispose();
        textureAntiniebla.dispose();
    }

    private static void animacionBernard(FileHandle imageFileHandle){
        Texture textureAnimBernard = new Texture(imageFileHandle);
        TextureRegion[][] tmp = TextureRegion.split(textureAnimBernard,60,69);
        int pasos=0;

        int num_filas = tmp[0].length;
        int num_columnas = tmp.length;

        TextureRegion[] txBerArriba= {tmp[3][0], tmp[3][1]};
        bernardArriba = new Animation(0.15f, txBerArriba);

        TextureRegion[] txBerAbajo= {tmp[0][0], tmp[0][1]};
        bernardAbajo = new Animation(0.15f, txBerAbajo);

        TextureRegion[] txBerlIzquierda= {tmp[2][0], tmp[2][1]};
        bernardIzquierda = new Animation(0.15f, txBerlIzquierda);

        TextureRegion[] txBerDerecha= {tmp[1][0], tmp[1][1]};
        bernardDerecha = new Animation(0.15f, txBerDerecha);
    }
}
