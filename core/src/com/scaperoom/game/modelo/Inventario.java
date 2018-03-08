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

//    public Inventario(Mundo mundo) {
//        this.mundo = mundo;
//   }

    public Inventario() {}

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

    public void usarLlaveBaño() {
        use_llavebaño = true;
        Mundo.SUELOS.add(Mundo.PASILLO_BAÑO);
    }

    public void usarLlaveEstudio() {
        use_llaveestudio = true;
        Mundo.SUELOS.add(Mundo.PASILLO_ESTUDIO);
    }

    public void usarMuñecoVudu() {
        use_muñecovudu = true;
        Mundo.lechuck.morir();
    }

    public void usarLlaveFinal() {
        use_llavefinal = true;
        // WIN??
    }
}
