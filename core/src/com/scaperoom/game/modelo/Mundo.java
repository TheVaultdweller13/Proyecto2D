package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Mundo {

    public static final int TAMAÑO_MUNDO_ANCHO=576;
    public static final int TAMAÑO_MUNDO_ALTO=490;

    private Bernard bernard;
    private LeChuck lechuck;

    public final static Rectangle MARGEN_DERECHO = new Rectangle(
            Mundo.TAMAÑO_MUNDO_ANCHO-90,0,90,Mundo.TAMAÑO_MUNDO_ALTO);

    public final static Rectangle ROOM_COCINA = new Rectangle(20, 200, 155, 200);

    public static final Rectangle ROOM_SALON[]={
            new Rectangle(320,260,169,150),
            new Rectangle(200,80,125,325),
            new Rectangle(ROOM_COCINA.width+18,200, 30,60),
            new Rectangle(230,45, 60,68)
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
        bernard = new Bernard(new Vector2(360, 320), new Vector2(45, 80),100);
        lechuck = new LeChuck(new Vector2(25, 310), new Vector2(60, 95),40);
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
