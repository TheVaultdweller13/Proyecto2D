package com.scaperoom.game.modelo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public abstract class Personaje {
    private Rectangle rectangulo;
    /**
     * Constructor por defecto
     */
    public Personaje(){
        rectangulo = new Rectangle();
    }
    /**
     * Instancia unha personaxe
     *
     * @param posicion
     * @param tamaño
     * @param velocidad_max
     */
    public Personaje(Vector2 posicion, Vector2 tamaño, float velocidad_max) {
        this.posicion = posicion;
        this.tamaño = tamaño;
        this.velocidad_max = velocidad_max;

        rectangulo = new Rectangle(posicion.x,posicion.y,tamaño.x,tamaño.y);
    }

    public void setTamañoRectangulo(float width,float height){
        rectangulo.setWidth(width);
        rectangulo.setHeight(height);
    }

    /**
     * Actualiza la posición del rectángulo asociado a la forma del gráfico
     *
     */
    public void actualizarRectangulo(){
        rectangulo.x=posicion.x;
        rectangulo.y=posicion.y;
    }

    /**
     * Devolve o rectángulo asociado
     * @return rectangulo
     */
    public Rectangle getRectangulo(){
        return rectangulo;
    }

    /**
     * Velocidad que toma cuando se move.
     */
    public float velocidad_max;
    /**
     * Velocidad actual
     */
    protected float velocidad = 0;
    /**
     * Posición
     */
    protected Vector2 posicion;
    /**
     * Tamaño
     */
    protected Vector2 tamaño;

    /**
     * Devuelve la posicion
     * @return posición
     */
    public Vector2 getPosicion() {
        return posicion;
    }

    /**
     * Modifica la posición
     * @param posicion: la nueva posicin
     */
    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
        actualizarRectangulo();
    }

    /**
     * Modifica ´la posición
     *
     * @param x: nueva posición x
     * @param y: nueva posición y
     */
    public void setPosicion(float x, float y) {
        posicion.x = x;
        posicion.y = y;

        actualizarRectangulo();
    }

    /**
     * Modifica la velocidad
     * @param velocidad: la nueva velocidad
     */
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Devuelve a velocidad
     * @return velocidad
     */
    public float getVelocidad() {
        return velocidad;
    }

    /**
     * Devuelve el tamaño
     * @return tamaño
     */
    public Vector2 getTamaño() {

        return tamaño;
    }

    /**
     * Asigna un nuevo tamaño
     * @param tamaño: el nuevo tamaño.
     */
    public void setTamaño(Vector2 tamaño) {
        this.tamaño=tamaño;
        setTamañoRectangulo(tamaño.x,tamaño.y);
    }


    /**
     * Modifica el tamaño
     *
     * @param width: nuevo tamaño de ancho
     * @param height: nuevo tamaño de alto
     */
    public void setTamaño(float width, float height) {
        this.tamaño.set(width,height);
        setTamañoRectangulo(width, height);

    }

    /**
     * Actualiza la posición en función de la velocidad
     * @param delta: tiempo entre una llamada y la siguiente
     */
    public abstract void update(float delta);
}
