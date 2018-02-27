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

    public final static Rectangle ROOM_COCINA = new Rectangle(20, 232, 155, 160);

    public static final Rectangle ROOM_SALON[]={
            new Rectangle(0,40,238,116),
            new Rectangle(0,220,300,120),
            new Rectangle(0,420,300,80)};

    public final static Rectangle ROOM_ESTUDIO= new Rectangle(
            375,118,100,85);

    public final static Rectangle ROOM_BAÑO = new Rectangle(
            35,55,140,95);

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
