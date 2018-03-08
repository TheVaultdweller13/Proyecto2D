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

    public static Texture texturePresentacion;

    public static Texture textureBackground;
    public static Texture textureMap;
    public static Texture textureParedes;
    public static Texture texturePuertas;
    public static Texture textureCharacterBernard;
    public static Texture textureCharacterLeChuck;
    public static Texture textureNiebla;
    public static Texture textureAntiniebla;
    public static Texture textureSombraHombre;
    public static Texture textureSombraMujer;

    public static Texture textureLlaveBaño;
    public static Texture textureLlaveBaño_inactivo;
    public static Texture textureLlaveEstudio;
    public static Texture textureLlaveEstudio_inactivo;
    public static Texture textureMuñecoVudu;
    public static Texture textureMuñecoVuduInactivo;
    public static Texture textureLlaveFinal;
    public static Texture textureLlaveFinalInactivo;

    public static Animation bernardAnimacion;
    public static Animation lechuckAnimacion;

    public static void cargarTexturas() {

        FileHandle imageFileHandle;

        /* Fondo del juego */
        imageFileHandle = Gdx.files.internal("graficos/fondo.png");
        textureBackground = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/map_room.png");
        textureMap = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/paredes.png");
        textureParedes = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/puerta.png");
        texturePuertas = new Texture(imageFileHandle);

        imageFileHandle = Gdx.files.internal("graficos/niebla.png");
        textureNiebla = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/antiniebla.png");
        textureAntiniebla = new Texture(imageFileHandle);

        /* Pantallas */
        imageFileHandle = Gdx.files.internal("graficos/inicio.png");
        texturePresentacion = new Texture(imageFileHandle);

        /* Personajes */
        imageFileHandle = Gdx.files.internal("graficos/simple_bernard_character.png");
        textureCharacterBernard = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/bernard_character.png");
        animacionBernard(imageFileHandle);

        imageFileHandle = Gdx.files.internal("graficos/simple_lechuck_character.png");
        textureCharacterLeChuck = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/lechuck_character.png");
        animacionLeChuck(imageFileHandle);


        imageFileHandle = Gdx.files.internal("graficos/silueta_mujer.png");
        textureSombraMujer = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/silueta_hombre.png");
        textureSombraHombre = new Texture(imageFileHandle);

        /* Inventario */

        imageFileHandle = Gdx.files.internal("graficos/llave_baño.png");
        textureLlaveBaño = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/llave_baño_inactivo.png");
        textureLlaveBaño_inactivo = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/llave_estudio.png");
        textureLlaveEstudio = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/llave_estudio_inactivo.png");
        textureLlaveEstudio_inactivo = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/muñeco_vudu.png");
        textureMuñecoVudu = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/muñeco_vudu_inactivo.png");
        textureMuñecoVuduInactivo = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/llave_final.png");
        textureLlaveFinal = new Texture(imageFileHandle);
        imageFileHandle = Gdx.files.internal("graficos/llave_final_inactivo.png");
        textureLlaveFinalInactivo = new Texture(imageFileHandle);

    }

    public static void liberarTexturas() {

        texturePresentacion.dispose();
        textureBackground.dispose();
        textureMap.dispose();
        textureParedes.dispose();
        texturePuertas.dispose();
        textureCharacterBernard.dispose();
        textureCharacterLeChuck.dispose();
        textureNiebla.dispose();
        textureAntiniebla.dispose();
        textureSombraHombre.dispose();
        textureSombraMujer.dispose();
        textureLlaveBaño.dispose();
        textureLlaveBaño_inactivo.dispose();
        textureLlaveEstudio.dispose();
        textureLlaveEstudio_inactivo.dispose();
        textureMuñecoVudu.dispose();
        textureMuñecoVuduInactivo.dispose();
        textureLlaveFinal.dispose();
        textureLlaveFinalInactivo.dispose();
    }

    private static void animacionBernard(FileHandle imageFileHandle) {
        Texture textureAnimBernard = new Texture(imageFileHandle);
        TextureRegion[][] tmp = TextureRegion.split(textureAnimBernard, 112, 154);

        int num_columnas = tmp[0].length;
        int num_filas = tmp.length;
        TextureRegion[] framesanimacion = new TextureRegion[num_columnas * num_filas];
        int cont = 0;
        for (int fila = 0; fila < num_filas; fila++) {
            for (int col = 0; col < num_columnas; col++) {
                framesanimacion[cont] = tmp[fila][col];
                cont++;
            }
        }
        bernardAnimacion = new Animation(0.25f, framesanimacion);
    }

    private static void animacionLeChuck(FileHandle imageFileHandle) {
        Texture textureAnimBernard = new Texture(imageFileHandle);
        TextureRegion[][] tmp = TextureRegion.split(textureAnimBernard, 47, 60);

        int num_columnas = tmp[0].length;
        int num_filas = tmp.length;
        TextureRegion[] framesanimacion = new TextureRegion[num_columnas * num_filas];
        int cont = 0;
        for (int fila = 0; fila < num_filas; fila++) {
            for (int col = 0; col < num_columnas; col++) {
                framesanimacion[cont] = tmp[fila][col];
                cont++;
            }
        }
        lechuckAnimacion = new Animation(0.35f, framesanimacion);
    }
}
