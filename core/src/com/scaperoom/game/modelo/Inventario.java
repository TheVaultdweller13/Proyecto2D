package com.scaperoom.game.modelo;

public class Inventario {


    //private Mundo mundo;

    public boolean tengo_llavebaño = false,
            tengo_llaveestudio = false,
            tengo_muñecovudu = false,
            tengo_llavefinal = false;

    public boolean use_llavebaño = false,
            use_llaveestudio = false,
            use_muñecovudu = false,
            use_llavefinal = false;

    public boolean usada_llavebaño = false,
            usada_llaveestudio = false,
            usado_muñecovudu = false,
            usada_llavefinal = false;

//    public Inventario(Mundo mundo) {
//        this.mundo = mundo;
//   }

    Inventario() {
    }

    public void cogerLlaveBaño() {
        tengo_llavebaño = true;
    }

    public void cogerLlaveEstudio() {
        tengo_llaveestudio = true;
    }

    public void cogerMuñecoVudu() {
        tengo_muñecovudu = true;
    }

    public void cogerLlaveFinal() {
        tengo_llavefinal = true;
    }
}
