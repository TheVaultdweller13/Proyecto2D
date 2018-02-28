package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Mundo {

    public static final int TAMAÑO_MUNDO_ANCHO=576;
    public static final int TAMAÑO_MUNDO_ALTO=490;

    public final static Vector2 TAMAÑO_NIEBLA = new Vector2(Mundo.TAMAÑO_MUNDO_ANCHO,512);

    private Bernard bernard;
    private LeChuck lechuck;

    private Array<ElementoMovil> niebla;

    public final static Rectangle BORDES[] = {
            new Rectangle(Mundo.TAMAÑO_MUNDO_ANCHO-90,0,90,Mundo.TAMAÑO_MUNDO_ALTO),
            new Rectangle(175, 0, 29, TAMAÑO_MUNDO_ALTO)
    };

    public final static Rectangle ROOM_COCINA = new Rectangle(20, 200, 155, 200);


    public static final Rectangle ROOM_SALON[]={
            new Rectangle(320,260,169,150),
            new Rectangle(200,80,125,325),
            new Rectangle(230,45, 60,68)
    };

    public static final Rectangle ESPACIO_MOVIL[] = {
            new Rectangle(ROOM_SALON[0].x-20, ROOM_SALON[0].y, 44, 130),
            new Rectangle(ROOM_COCINA.width+18,200, 40,60),

    };

    public static final Rectangle PASILLOS[]={
            new Rectangle(115,140,30,95),
            new Rectangle(405,190,30,95),
    };

    public final static Rectangle ROOM_ESTUDIO= new Rectangle(
            375,75,100,125);

    public final static Rectangle ROOM_BAÑO = new Rectangle(
            35,15,140,135);

    public Mundo() {
        bernard = new Bernard(new Vector2(360, 320), new Vector2(42, 80),100);
        lechuck = new LeChuck(new Vector2(25, 310), new Vector2(60, 95),40);

        niebla = new Array<ElementoMovil>();

        //Carga Niebla Blanca
        niebla.add(new ElementoMovil(new Vector2(0,0),new Vector2(TAMAÑO_NIEBLA.cpy()),-18, ElementoMovil.TIPOS_ELEMENTOS.NIEBLA));
        niebla.add(new ElementoMovil(new Vector2(TAMAÑO_NIEBLA.x,0),new Vector2(TAMAÑO_NIEBLA.cpy()),-18, ElementoMovil.TIPOS_ELEMENTOS.NIEBLA));

        //Carga Niebla Negra
        niebla.add(new ElementoMovil(new Vector2(0, 0),new Vector2(TAMAÑO_NIEBLA.cpy()),18, ElementoMovil.TIPOS_ELEMENTOS.ANTI_NIEBLA));
        niebla.add(new ElementoMovil(new Vector2(TAMAÑO_NIEBLA.x, 0),new Vector2(TAMAÑO_NIEBLA.cpy()),18, ElementoMovil.TIPOS_ELEMENTOS.ANTI_NIEBLA));
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
