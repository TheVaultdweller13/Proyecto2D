package com.scaperoom.game.controlador;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Intersector;
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


    //Control ejes X: Lado derecho de la mansión
        if(bernard.getPosicion().x>=miMundo.BORDES[0].x-bernard.getTamaño().x){
            bernard.setPosicion(miMundo.BORDES[0].x-bernard.getTamaño().x, bernard.getPosicion().y);
        }
        else if (bernard.getPosicion().x>=Mundo.ROOM_SALON[0].x-bernard.getTamaño().x
                && !Intersector.overlaps(bernard.getRectangulo(), Mundo.ESPACIO_MOVIL[0])){
            if(bernard.getPosicion().x<= miMundo.ESPACIO_MOVIL[0].x){
                    bernard.setPosicion(Mundo.ROOM_SALON[0].x-bernard.getTamaño().x, bernard.getPosicion().y);
            }
        }
        if(bernard.getPosicion().x<=Mundo.ROOM_ESTUDIO.x
                && Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_ESTUDIO)
                && !Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_SALON[0])){
                bernard.setPosicion(Mundo.ROOM_ESTUDIO.x, bernard.getPosicion().y);
        }

     //Control ejes X: Lado izquierdo de la mansión
        if(bernard.getPosicion().x<=20){
            bernard.setPosicion(20, bernard.getPosicion().y);
        }
        else if (bernard.getPosicion().x<=Mundo.BORDES[1].x+20
                && !Intersector.overlaps(bernard.getRectangulo(), Mundo.ESPACIO_MOVIL[1])){
            if(bernard.getPosicion().x>= miMundo.ESPACIO_MOVIL[1].x){
                bernard.setPosicion(Mundo.BORDES[1].x+20, bernard.getPosicion().y);
            }
        }
        if(bernard.getPosicion().x>=Mundo.BORDES[1].x-bernard.getTamaño().x &&
                !Intersector.overlaps(bernard.getRectangulo(), Mundo.ESPACIO_MOVIL[1])){
            if(bernard.getPosicion().x<= miMundo.ESPACIO_MOVIL[1].x){
                bernard.setPosicion(Mundo.BORDES[1].x-bernard.getTamaño().x, bernard.getPosicion().y);
            }
        }
        for(int i=0; i<Mundo.PASILLOS.length; i++){
            if(Intersector.overlaps(bernard.getRectangulo(), Mundo.PASILLOS[i])){
                bernard.setPosicion( Mundo.PASILLOS[i].x-5,bernard.getPosicion().y);
            }
        }
    //Control Ejes Y
        if(bernard.getPosicion().y>=miMundo.ROOM_SALON[0].y+bernard.getTamaño().y+30){ //Los márgenes superiores de las tres habitaciones están igualados
            bernard.setPosicion(bernard.getPosicion().x, miMundo.ROOM_SALON[0].y+bernard.getTamaño().y+30);
        }
        //Salón
        else if(bernard.getPosicion().y<=Mundo.ROOM_SALON[0].y &&
                Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_SALON[0]) &&
                !Intersector.overlaps(bernard.getRectangulo(), Mundo.PASILLOS[1])                ){
            bernard.setPosicion(bernard.getPosicion().x, Mundo.ROOM_SALON[0].y);
        }
        else if(bernard.getPosicion().y<=Mundo.ROOM_SALON[1].y &&
                !Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_SALON[2]) &&
                Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_SALON[1])){
            bernard.setPosicion(bernard.getPosicion().x, Mundo.ROOM_SALON[1].y);
        }
        else if(bernard.getPosicion().y<=Mundo.ROOM_SALON[2].y &&
                Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_SALON[2])){
            bernard.setPosicion(bernard.getPosicion().x, Mundo.ROOM_SALON[2].y);
        }
        //Estudio
        if(bernard.getPosicion().y<=Mundo.ROOM_ESTUDIO.y &&
                Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_ESTUDIO)){
            bernard.setPosicion(bernard.getPosicion().x, Mundo.ROOM_ESTUDIO.y);
        }
        else if(bernard.getPosicion().y>=Mundo.ROOM_ESTUDIO.y+bernard.getTamaño().y &&
                Intersector.overlaps(bernard.getRectangulo(), Mundo.ROOM_ESTUDIO) &&
                !Intersector.overlaps(bernard.getRectangulo(),Mundo.PASILLOS[1])){
            bernard.setPosicion(bernard.getPosicion().x, Mundo.ROOM_ESTUDIO.y+bernard.getTamaño().y);
        }

    }

    private void controlarNiebla(float delta){

        for(ElementoMovil niebla: miMundo.getNiebla()){
            niebla.update(delta);
            if (niebla.getVelocidad()>0){   // Izquierda a derecha
                if (niebla.getPosicion().x>=Mundo.TAMAÑO_NIEBLA.x){
                    niebla.setPosicion(-Mundo.TAMAÑO_NIEBLA.x, niebla.getPosicion().y);
                }
            }
            else{   // Derecha a izquierda
                if (niebla.getPosicion().x<=-niebla.getTamaño().x){
                        niebla.setPosicion(Mundo.TAMAÑO_MUNDO_ANCHO, niebla.getPosicion().y);
                }
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
        controlarNiebla(delta);
        procesarEntradas();
    }
}
