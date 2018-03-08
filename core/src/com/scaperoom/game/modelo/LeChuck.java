package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Vector2;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class LeChuck extends Personaje {

    public Vector2
            temporal,
            centro,
            direccion,
            puntoDestino;

    public LeChuck(Vector2 posicion, Vector2 tamaño, float velocidad) {
        super(posicion, tamaño, velocidad);

        temporal = new Vector2();
        centro = new Vector2(0, 0);
        direccion = new Vector2(0, 0);
        puntoDestino = new Vector2(0, 0);
        // puntoDestino = new Circle();

        getRectangulo().setSize(tamaño.x / 2);
    }

    public void morir() {
        throw new NotImplementedException();
    }

    @Override
    public void actualizarRectangulo() {

        getRectangulo().x = getPosicion().x + getTamaño().x / 4;
        getRectangulo().y = getPosicion().y + getTamaño().y / 4;
        getRectangulo().getCenter(centro);

    }

    @Override
    public void update(float delta) {
        temporal.set(direccion);
        setPosicion(posicion.add(temporal.scl(velocidad * delta)));
    }
}
