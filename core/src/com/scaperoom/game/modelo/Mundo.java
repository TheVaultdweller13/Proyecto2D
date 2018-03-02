package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Mundo {

    public static final int TAMAÑO_MUNDO_ANCHO = 576;
    public static final int TAMAÑO_MUNDO_ALTO = 490;

    public final static Vector2 TAMAÑO_NIEBLA = new Vector2(576, 576);

    private Bernard bernard;
    private LeChuck lechuck;

    private Array<ElementoMovil> niebla;
    private Array<ElementoMovil> sombra;

    public final static Rectangle BORDES[] = {
            new Rectangle(Mundo.TAMAÑO_MUNDO_ANCHO - 90, 0, 90, Mundo.TAMAÑO_MUNDO_ALTO),
            new Rectangle(175, 0, 29, TAMAÑO_MUNDO_ALTO)
    };

    public final static Rectangle SUELOS[] = {
            new Rectangle(20, 200, 155, 240),   // COCINA
            new Rectangle(200, 260, 289, 180),  // SALÓN DERECHA
            new Rectangle(200, 80, 125, 360),   // SALÓN IZQUIERDA
            new Rectangle(230, 45, 60, 68),     // RECIBIDOR
            new Rectangle(125, 200, 100, 80),   // UNIÓN COCINA-SALÓN
            new Rectangle(115, 120, 30, 100),   // PASILLO BAÑO
            new Rectangle(405, 190, 30, 90),    // PASILLO ESTUDIO
            new Rectangle(375, 75, 100, 165),   // ESTUDIO
            new Rectangle(35, 15, 140, 135)     // BAÑO
    };

    public Mundo() {
        bernard = new Bernard(new Vector2(360, 320), new Vector2(40, 75), 100);
        lechuck = new LeChuck(new Vector2(25, 310), new Vector2(60, 95), 40);

        niebla = new Array<ElementoMovil>();
        sombra = new Array<ElementoMovil>();

        //Carga Niebla Blanca
        niebla.add(
                new ElementoMovil(
                        new Vector2(0, 0),
                        new Vector2(TAMAÑO_NIEBLA.cpy()),
                        -18,
                        ElementoMovil.TIPOS_ELEMENTOS.NIEBLA
                )
        );
        niebla.add(
                new ElementoMovil(
                        new Vector2(TAMAÑO_NIEBLA.x, 0),
                        new Vector2(TAMAÑO_NIEBLA.cpy()),
                        -18,
                        ElementoMovil.TIPOS_ELEMENTOS.NIEBLA
                )
        );

        //Carga Niebla Negra
        niebla.add(new ElementoMovil(new Vector2(0, 0), new Vector2(TAMAÑO_NIEBLA.cpy()), 18, ElementoMovil.TIPOS_ELEMENTOS.ANTI_NIEBLA));
        niebla.add(new ElementoMovil(new Vector2(TAMAÑO_NIEBLA.x, 0), new Vector2(TAMAÑO_NIEBLA.cpy()), 18, ElementoMovil.TIPOS_ELEMENTOS.ANTI_NIEBLA));

        //Carga sombra de mujer
        sombra.add(new ElementoMovil(new Vector2(40, 412), new Vector2(40, 40), MathUtils.random(10, 30), ElementoMovil.TIPOS_ELEMENTOS.SILUETA_MUJER));
        sombra.add(new ElementoMovil(new Vector2(240, 412), new Vector2(40, 40), MathUtils.random(10, 30) - 40, ElementoMovil.TIPOS_ELEMENTOS.SILUETA_MUJER));

        //Carga sombra de hombre
        sombra.add(new ElementoMovil(new Vector2(120, 400), new Vector2(50, 50), MathUtils.random(10, 30), ElementoMovil.TIPOS_ELEMENTOS.SILUETA_HOMBRE));
        sombra.add(new ElementoMovil(new Vector2(380, 400), new Vector2(50, 50), MathUtils.random(10, 30) - 40, ElementoMovil.TIPOS_ELEMENTOS.SILUETA_HOMBRE));
    }

    public Array<ElementoMovil> getSombra() {
        return sombra;
    }

    public Array<ElementoMovil> getNiebla() {
        return niebla;
    }

    public Bernard getBernard() {
        return bernard;
    }

    public void setBernard(Bernard bernard) {
        this.bernard = bernard;
    }

    public LeChuck getLechuck() {
        return lechuck;
    }

    public void setLechuck(LeChuck lechuck) {
        this.lechuck = lechuck;
    }
}
