package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class Bernard extends Personaje {

    public Vector2
            direccion;
    public Circle puntoDestino;

    public boolean colisionx = false, colisiony = false;

    public Bernard(Vector2 posicion, Vector2 tamaño, float velocidad) {
        super(posicion, tamaño, velocidad);
        direccion = new Vector2(0, 0);
        detenerBernard();

        getRectangulo().setSize(tamaño.x / 2);
    }

    public void detenerBernard() {
        Vector2 centro = getCentro();
        puntoDestino = new Circle(centro.x, centro.y, 20);
    }

    public Vector2 getCentro() {
        Vector2 centro = new Vector2();
        getRectangulo().getCenter(centro);
        return centro;
    }

    @Override
    public void actualizarRectangulo() {
        Rectangle r = calcularRectangulo(getPosicion());
        getRectangulo().x = r.x;
        getRectangulo().y = r.y;
    }

    public Rectangle calcularRectangulo(Vector2 posicion) {
        Rectangle r = new Rectangle(getRectangulo());
        r.x = posicion.x + getTamaño().x / 4;
        r.y = posicion.y;
        return r;
    }

    /**
     * Método para predecir la posición que tendrá Bernard tras la siguiente update
     *
     * @param delta      delta
     * @param horizontal colision horizontal
     * @param vertical   colision vertical
     * @return
     */
    public Vector2 siguientePosicion(float delta, boolean horizontal, boolean vertical) {
        Vector2 temporal = new Vector2(), pos = getPosicion().cpy();
        temporal.x = horizontal ? 0f : direccion.x;
        temporal.y = vertical ? 0f : direccion.y;
        return pos.add(temporal.scl(velocidad * delta));
    }

    @Override
    public void update(float delta) {
        if (colisionx && colisiony) {
            detenerBernard();
        } else {
            setPosicion(siguientePosicion(delta, colisionx, colisiony));
        }
    }
}
