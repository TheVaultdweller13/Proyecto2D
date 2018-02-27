package com.scaperoom.game.controlador;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.scaperoom.game.modelo.Bernard;
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
    };

    private OrthographicCamera camara2d;

    public ControladorJuego(Mundo miMundo) {
        this.miMundo=miMundo;
        camara2d = new OrthographicCamera();
        bernard = miMundo.getBernard();
    }

    /**
     * Modifica el estado del mapa de teclas y pone a true
     * @param tecla: tecla pulsada
     */
    public void pulsarTecla(Keys tecla){
        keys.put(tecla, true);
    }
    /**
     * Modifica el estado del mapa de teclas y pone a false
     * @param tecla: tecla liberada
     */
    public void liberarTecla(Keys tecla){
        keys.put(tecla, false);
    }

    private void controlarBernard(float delta){
        // Actualiza Bernard
        bernard.update(delta);

        if(bernard.getPosicion().x>=miMundo.TAMAÑO_MUNDO_ANCHO || bernard.getPosicion().x<=0){
            bernard.setPosicion(0, bernard.getPosicion().y);
        }
        else {
            if (bernard.getPosicion().x >= Mundo.TAMAÑO_MUNDO_ANCHO-bernard.getTamaño().x){
                bernard.setPosicion(Mundo.TAMAÑO_MUNDO_ANCHO-bernard.getTamaño().x, bernard.getPosicion().y);
            }
        }
    }

    private void procesarEntradas(){

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

    public void update(float delta){
//        miMundo.updateCronometro(delta);
        controlarBernard(delta);
        procesarEntradas();
    }
}
