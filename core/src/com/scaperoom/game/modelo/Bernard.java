package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dam208 on 27/02/2018.
 */

public class Bernard extends Personaje {

    public Vector2
            temporal,
            direccion;
    public Circle  puntoDestino;

    public Bernard(Vector2 posicion, Vector2 tamaño, float velocidad) {
        super(posicion, tamaño, velocidad);
        temporal = new Vector2();
        direccion = new Vector2(0,0);
        puntoDestino = new Circle();

        getRectangulo().setSize(tamaño.x / 2);
    }

    @Override
    public void actualizarRectangulo() {
        getRectangulo().x = getPosicion().x + getTamaño().x / 4;
        getRectangulo().y = getPosicion().y + getTamaño().y / 4;
    }

    @Override
    public void update(float delta) {
        temporal.set(direccion);
        setPosicion(posicion.add(temporal.scl(velocidad * delta)));
    }
}
