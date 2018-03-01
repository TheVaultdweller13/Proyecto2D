package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class ElementoMovil extends Personaje {

    public static enum TIPOS_ELEMENTOS {NIEBLA, ANTI_NIEBLA, SILUETA_HOMBRE, SILUETA_MUJER}

    ;
    private TIPOS_ELEMENTOS tipo;

    public ElementoMovil(Vector2 posicion, Vector2 tamano, float velocidad_max, TIPOS_ELEMENTOS tipo) {
        super(posicion, tamano, velocidad_max);
        setVelocidad(velocidad_max);
        this.tipo = tipo;
    }

    public TIPOS_ELEMENTOS getTipo() {
        return tipo;
    }

    @Override
    public void update(float delta) {
        setPosicion((getPosicion().x + velocidad * delta), getPosicion().y);

    }
}
