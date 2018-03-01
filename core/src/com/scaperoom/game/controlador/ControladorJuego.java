package com.scaperoom.game.controlador;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

import java.util.HashMap;

/**
 * Created by dam208 on 27/02/2018.
 */

public class ControladorJuego {

    private Mundo miMundo;
    private Bernard bernard;
    private LeChuck lechuck;

    public enum Keys {
        IZQUIERDA,
        DERECHA,
        ARRIBA,
        ABAJO
    }

    HashMap<Keys, Boolean> keys = new HashMap<ControladorJuego.Keys, Boolean>();

    {
        keys.put(Keys.IZQUIERDA, false);
        keys.put(Keys.DERECHA, false);
        keys.put(Keys.ARRIBA, false);
        keys.put(Keys.ABAJO, false);
    }

    private OrthographicCamera camara2d;

    public ControladorJuego(Mundo miMundo) {
        this.miMundo = miMundo;
        camara2d = new OrthographicCamera();
        bernard = miMundo.getBernard();

    }

    /**
     * Modifica el estado del mapa de teclas y pone a true
     *
     * @param tecla: tecla pulsada
     */
    public void pulsarTecla(Keys tecla) {
        keys.put(tecla, true);
    }

    /**
     * Modifica el estado del mapa de teclas y pone a false
     *
     * @param tecla: tecla liberada
     */
    public void liberarTecla(Keys tecla) {
        keys.put(tecla, false);
    }

    private void controlarBernard(float delta) {
        float ox = bernard.getPosicion().x, oy = bernard.getPosicion().y; // posicion original

        float x = ox, y = oy; // próxima posición, empieza siendo la antigua
        // Actualiza Bernard
        bernard.update(delta);

        Rectangle[] suelos = miMundo.SUELOS;
        for (int i = 0; i < suelos.length; i++) {
            // si bernard está dentro de algún suelo
            if (suelos[i].contains(bernard.getRectangulo())) {
                // su próxima posición es la que consiguió en la update
                x = bernard.getPosicion().x;
                y = bernard.getPosicion().y;
                break; // y sal del bucle
            }
        }

        // ahora se pone la posición correcta, ya sea la original o la nueva
        bernard.setPosicion(x, y);
    }

    private void controlarNiebla(float delta) {

        for (ElementoMovil niebla : miMundo.getNiebla()) {
            niebla.update(delta);
            if (niebla.getVelocidad() > 0) {   // Izquierda a derecha
                if (niebla.getPosicion().x >= Mundo.TAMAÑO_NIEBLA.x) {
                    niebla.setPosicion(-Mundo.TAMAÑO_NIEBLA.x, niebla.getPosicion().y);
                }
            } else {   // Derecha a izquierda
                if (niebla.getPosicion().x <= -niebla.getTamaño().x) {
                    niebla.setPosicion(Mundo.TAMAÑO_MUNDO_ANCHO, niebla.getPosicion().y);
                }
            }
        }
    }

    private void controlarSombra(float delta) {

        for (ElementoMovil sombras : miMundo.getSombra()) {
            sombras.update(delta);
            if (sombras.getVelocidad() > 0) {   // Izquierda a derecha
                if (sombras.getPosicion().x >= Mundo.TAMAÑO_MUNDO_ANCHO) {
                    sombras.setPosicion(-Mundo.TAMAÑO_MUNDO_ANCHO, sombras.getPosicion().y);
                }
            } else {   // Derecha a izquierda
                if (sombras.getPosicion().x <= -sombras.getTamaño().x) {
                    sombras.setPosicion(Mundo.TAMAÑO_MUNDO_ANCHO, sombras.getPosicion().y);
                }
            }
        }
    }

    private void procesarEntradas() {

        if (keys.get(Keys.DERECHA))
            bernard.setVelocidadX(bernard.velocidad_max);
        if (keys.get(Keys.IZQUIERDA))
            bernard.setVelocidadX(-bernard.velocidad_max);
        if (!(keys.get(Keys.IZQUIERDA)) && (!(keys.get(Keys.DERECHA))))
            bernard.setVelocidadX(0);

        if (keys.get(Keys.ARRIBA))
            bernard.setVelocidadY(bernard.velocidad_max);
        if (keys.get(Keys.ABAJO))
            bernard.setVelocidadY(-bernard.velocidad_max);
        if (!(keys.get(Keys.ARRIBA)) && (!(keys.get(Keys.ABAJO))))
            bernard.setVelocidadY(0);
    }

    public void update(float delta) {
//      miMundo.updateCronometro(delta);
        controlarBernard(delta);
        controlarNiebla(delta);
        controlarSombra(delta);
        procesarEntradas();
    }
}
