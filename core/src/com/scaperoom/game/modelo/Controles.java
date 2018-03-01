package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Controles {

    public final static Rectangle CONTROL = new Rectangle(10, 40, 50, 70);


    //    Flecha izquierda:
    public final static Rectangle FLECHA_IZQUIERDA = new Rectangle(
            Controles.CONTROL.x, Controles.CONTROL.y + Controles.CONTROL.height / 3, Controles.CONTROL.width / 2, Controles.CONTROL.height / 3);
    //    Flecha derecha:
    public final static Rectangle FLECHA_DERECHA = new Rectangle(
            Controles.CONTROL.x + Controles.CONTROL.width / 2, Controles.CONTROL.y + Controles.CONTROL.height / 3, Controles.CONTROL.width / 2, Controles.CONTROL.height / 3);
    //    Flecha arriba:
    public final static Rectangle FLECHA_ARRIBA = new Rectangle(
            Controles.CONTROL.x, Controles.CONTROL.y + Controles.CONTROL.height * 2 / 3, Controles.CONTROL.width, Controles.CONTROL.height / 3);
    //    Flecha abajo:
    public final static Rectangle FLECHA_ABAJO = new Rectangle(
            Controles.CONTROL.x, Controles.CONTROL.y, Controles.CONTROL.width, Controles.CONTROL.height / 3);
}
