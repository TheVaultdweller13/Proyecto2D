package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by dam208 on 27/02/2018.
 */

public class Bernard extends Personaje {

    private Vector2 velocidad;
    public Vector2
            puntoDestino,
            temporal,
            direccion;

    public Bernard(Vector2 posicion, Vector2 tamaño, float velocidad_max) {
        super(posicion, tamaño, velocidad_max);
        velocidad = new Vector2(0, 0);

        temporal = new Vector2();
        direccion = new Vector2(0,0);
        puntoDestino = new Vector2();

        getRectangulo().setSize(tamaño.x / 2);
    }

    public float getVelocidadX() {
        return velocidad.x;
    }

    public float getVelocidadY() {
        return velocidad.y;
    }

    public void setVelocidadX(float x) {
        velocidad.x = x;

    }

    public void setVelocidadY(float y) {
        velocidad.y = y;
    }

    @Override
    public void actualizarRectangulo() {
        getRectangulo().x = getPosicion().x + getTamaño().x / 4;
        getRectangulo().y = getPosicion().y + getTamaño().y / 4;
    }


    @Override
    public void update(float delta) {
        temporal.set(direccion);
        setPosicion(posicion.add(temporal.scl(velocidad_max*delta)));
//        setPosicion(getPosicion().x + (velocidad.x) * delta,
//                getPosicion().y + velocidad.y * delta);
    }
}
