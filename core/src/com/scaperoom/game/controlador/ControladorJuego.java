package com.scaperoom.game.controlador;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.Inventario;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class ControladorJuego {

    private Mundo miMundo;
    private Bernard bernard;
    private LeChuck lechuck;
    public static boolean controlLeChuck = true;

    private Inventario llave_baño;
    private Inventario llave_estudio;
    private Inventario muñeco_vudu;
    private Inventario llave_final;

    private int avanceLechuck = 0;

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
        lechuck = miMundo.getLechuck();
        avanceLechuck = 0;

        llave_baño = miMundo.getInventario();
        llave_estudio = miMundo.getInventario();
        muñeco_vudu = miMundo.getInventario();
        llave_final = miMundo.getInventario();

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

        boolean colisionx = true, colisiony = true;
        List<Rectangle> suelos = Mundo.SUELOS;

        // Calcula la próxima posición de Bernard tras moverse en horizontal
        Vector2 nuevapos = bernard.siguientePosicion(delta, false, true);

        // buscamos colisiones moviéndonos en x
        for (int i = 0; i < suelos.size(); i++) {
            Rectangle r = suelos.get(i);
            // si bernard está dentro de algún suelo
            if (r.contains(bernard.calcularRectangulo(nuevapos))) {
                colisionx = false; // no hay colision
                break; // y sale del bucle
            }
        }

        // posición de Bernard ahora teniendo en cuenta el movimiento vertical
        nuevapos = bernard.siguientePosicion(delta, colisionx, false);

        // buscamos colisiones moviéndonos en y
        for (int i = 0; i < suelos.size(); i++) {
            Rectangle r = suelos.get(i);
            // si bernard está dentro de algún suelo
            if (r.contains(bernard.calcularRectangulo(nuevapos))) {
                colisiony = false; // no hay colision
                break; // y sale del bucle
            }
        }

        // indicamos a bernard las colisiones que hayamos detectado
        bernard.colisionx = colisionx;
        bernard.colisiony = colisiony;

        // ahora se pone la posición correcta, ya sea la original o la nueva
        bernard.update(delta);

        if (Intersector.overlaps(bernard.puntoDestino, bernard.getRectangulo())) {
            bernard.direccion = new Vector2(0f, 0f);
        } else {
            Vector2 destino = new Vector2(bernard.puntoDestino.x, bernard.puntoDestino.y);
            destino.sub(bernard.getCentro());
            bernard.direccion.set(destino.nor());
        }
    }

    private void controlarLeChuck(float delta) {
        lechuck.update(delta);
        Vector3 posicionMapa = new Vector3(Mundo.PUNTOS_DESPLAZAMIENTO[avanceLechuck].x, Mundo.PUNTOS_DESPLAZAMIENTO[avanceLechuck].y, 0);
        lechuck.puntoDestino.set(new Vector2(posicionMapa.x, posicionMapa.y));
        Vector2 direccion = lechuck.puntoDestino.cpy().sub(lechuck.centro);
        lechuck.direccion.set(direccion.nor());

        if (Mundo.PUNTOS_DESPLAZAMIENTO[avanceLechuck].contains(lechuck.centro)) {
            if (avanceLechuck < 4) {
                avanceLechuck++;
                controlLeChuck = true;
            }

            if (Mundo.PUNTOS_DESPLAZAMIENTO[Mundo.PUNTOS_DESPLAZAMIENTO.length - 1].contains(lechuck.centro)) {
                direccion = new Vector2(lechuck.direccion.x * -1, lechuck.direccion.y * -1);

                lechuck.direccion.set(direccion.nor());
                avanceLechuck = 0;
                controlLeChuck = false;
                }
        }
    }

    private void controlarNiebla(float delta) {

        for (ElementoMovil niebla : miMundo.getNiebla()) {
            niebla.update(delta);
            if (niebla.getVelocidad() > 0) {   // Izquierda a derecha
                if (niebla.getPosicion().x >= Mundo.TAMAÑO_NIEBLA.x) {
                    niebla.setPosicion(-Mundo.TAMAÑO_NIEBLA.x, niebla.getPosicion().y);
                }
            } else {   // Derecha a izquierda
                if (niebla.getPosicion().x <= -Mundo.TAMAÑO_NIEBLA.x) {
                    niebla.setPosicion(Mundo.TAMAÑO_NIEBLA.x, niebla.getPosicion().y);
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

    /**
     * Controla la recogida de objetos por el mapa
     * @param delta
     */
    private void controlarRecogerObjetos(float delta){
        // Llave baño
        if(Intersector.overlaps(Mundo.LLAVE_BAÑO_INACTIVO, bernard.getRectangulo())){
            llave_baño.cogerLlaveBaño();
        }
        // Llave estudio
        if(Intersector.overlaps(Mundo.LLAVE_ESTUDIO_INACTIVO, bernard.getRectangulo())){
            llave_baño.cogerLlaveEstudio();
        }
        // Muñeco vudú
        if(Intersector.overlaps(Mundo.MUÑECO_VUDU_INACTIVO, bernard.getRectangulo())){
            llave_baño.cogerMuñecoVudu();
        }
        // Llave final
        if(miMundo.getLechuck().isMuerto()){
            miMundo.inicializarLlaveFinal();
            if(Intersector.overlaps(miMundo.LLAVE_FINAL_INACTIVO, bernard.getRectangulo())){
                llave_baño.cogerLlaveFinal();
            }
        }
    }

    /**
     * Controla que Bernard pueda usar los objetos que ha recogido
     * @param delta
     */
    private void controlarUsarObjetos(float delta){
        // Llave baño
        if(miMundo.getInventario().use_llavebaño && Intersector.overlaps(bernard.getRectangulo(), Mundo.PASILLO_BAÑO)){
            miMundo.getInventario().usarLlaveBaño();
        }
        // Llave estudio
        if(miMundo.getInventario().use_llaveestudio && Intersector.overlaps(bernard.getRectangulo(), Mundo.PASILLO_ESTUDIO)){
            miMundo.getInventario().usarLlaveEstudio();
        }
        // Muñeco vudu
        if(miMundo.getInventario().use_muñecovudu && Intersector.overlaps(bernard.getRectangulo(), lechuck.getRectangulo())){
            miMundo.getInventario().usarMuñecoVudu();
        }
        //Llave final
        if(miMundo.getInventario().use_llavefinal && Intersector.overlaps(bernard.getRectangulo(), Mundo.SUELOS.get(3))){
            miMundo.getInventario().usarLlaveFinal();
            System.out.println("HAS GANADO");
        }
    }

//    private void procesarEntradas() {
//
//        if (keys.get(Keys.DERECHA))
//            bernard.setVelocidadX(bernard.velocidad_max);
//        if (keys.get(Keys.IZQUIERDA))
//            bernard.setVelocidadX(-bernard.velocidad_max);
//        if (!(keys.get(Keys.IZQUIERDA)) && (!(keys.get(Keys.DERECHA))))
//            bernard.setVelocidadX(0);
//
//        if (keys.get(Keys.ARRIBA))
//            bernard.setVelocidadY(bernard.velocidad_max);
//        if (keys.get(Keys.ABAJO))
//            bernard.setVelocidadY(-bernard.velocidad_max);
//        if (!(keys.get(Keys.ARRIBA)) && (!(keys.get(Keys.ABAJO))))
//            bernard.setVelocidadY(0);
//    }

    public void update(float delta) {
        miMundo.updateCronometro(delta);
        controlarBernard(delta);
        controlarNiebla(delta);
        controlarSombra(delta);
        controlarLeChuck(delta);
        controlarRecogerObjetos(delta);
        controlarUsarObjetos(delta);
    }
}
