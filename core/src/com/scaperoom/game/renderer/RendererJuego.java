package com.scaperoom.game.renderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.StringBuilder;
import com.scaperoom.game.controlador.ControladorJuego;
import com.scaperoom.game.game.AssetsJuego;
import com.scaperoom.game.modelo.Bernard;
import com.scaperoom.game.modelo.Controles;
import com.scaperoom.game.modelo.ElementoMovil;
import com.scaperoom.game.modelo.LeChuck;
import com.scaperoom.game.modelo.Mundo;

/**
 * Created by Héctor Fernández on 27/02/2018.
 */

public class RendererJuego implements InputProcessor {

    private Mundo miMundo;

    private SpriteBatch batch;
    private ShapeRenderer shaperender;

    private float crono;

    private final boolean DEBUGGER = false;
    private Bernard bernard;
    private LeChuck lechuck;

    private OrthographicCamera camara2d;

    private StringBuilder sbuffer;
    private StringBuilder textosLeChuck;
    private StringBuilder textosBernard;
    private BitmapFont bitMapFont;


    public RendererJuego(Mundo miMundo) {
        this.miMundo = miMundo;
        batch = new SpriteBatch();
        shaperender = new ShapeRenderer();
        camara2d = new OrthographicCamera();

        bernard = miMundo.getBernard();
        lechuck = miMundo.getLechuck();
        crono = 0;
        Gdx.input.setInputProcessor(this);

        //Libgdx by default, creates a BitmapFont using the default 15pt Arial font included in the libgdx JAR file.
        //Using FreeTypeFont, it is possible so create fonts with a desired size on the fly.
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/One Size.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (12 * Mundo.PROPORCION_REAL_MUNDO_ANCHO);
        this.bitMapFont = generator.generateFont(parameter); // font size in pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        sbuffer = new StringBuilder();
        textosLeChuck = new StringBuilder();
        textosBernard = new StringBuilder();
        this.bitMapFont.setColor(Color.GOLDENROD);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        dibujarFondo();
        dibujarSombras();
        dibujarMapa();

        if (bernard.getRectangulo().y > lechuck.getRectangulo().y) {
            dibujarBernard();
            dibujarLeChuck();
        } else {
            dibujarLeChuck();
            dibujarBernard();
        }
        dibujarParedes();
        dibujarPuertas();
        dibujarLlaveBaño();
        dibujarLlaveEstudio();
        dibujarMuñecoVudu();
        dibujarLlaveFinal();
        dibujarNieblas();
        dibujarTiempo();
        dibujarDialogos();
        dibujarControles();

        batch.end();

        if (DEBUGGER) {
            debugger();
        }

    }

    private void dibujarFondo() {
        batch.draw(AssetsJuego.textureBackground,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarMapa() {
        batch.draw(AssetsJuego.textureMap,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarParedes() {
        batch.draw(AssetsJuego.textureParedes,
                0, 0, Mundo.TAMAÑO_MUNDO_ANCHO, Mundo.TAMAÑO_MUNDO_ALTO);
    }

    private void dibujarPuertas() {
        if(!miMundo.getInventario().usada_llavebaño){
            batch.draw(AssetsJuego.texturePuertas,
                    115, 144, 29, 48);
        }
        if(!miMundo.getInventario().usada_llaveestudio){
            batch.draw(AssetsJuego.texturePuertas,
                    405, 202, 29, 48);
        }
    }

    private void dibujarBernard() {
        Bernard bernard = miMundo.getBernard();
        crono += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) AssetsJuego.bernardAnimacion.getKeyFrame(crono, true);
        if (!bernard.direccion.isZero()) {
            if (bernard.direccion.x > 0) {
                batch
                        .draw(currentFrame, bernard.getPosicion().x,
                                bernard.getPosicion().y, bernard.getTamaño().x + 20,
                                bernard.getTamaño().y);
            } else {
                batch
                        .draw(currentFrame, (bernard.getPosicion().x + 40),
                                bernard.getPosicion().y, (-bernard.getTamaño().x - 20),
                                bernard.getTamaño().y);
            }
        } else
            batch.draw(AssetsJuego.textureCharacterBernard, bernard.getPosicion().x, bernard.getPosicion().y, bernard.getTamaño().x, bernard.getTamaño().y);
    }

    private void dibujarLeChuck() {
        LeChuck lechuck = miMundo.getLechuck();
        crono += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = (TextureRegion) AssetsJuego.lechuckAnimacion.getKeyFrame(crono, true);
        if(!lechuck.isMuerto()){
            if (ControladorJuego.controlLeChuck) {
                batch.draw(currentFrame, lechuck.getPosicion().x,
                        lechuck.getPosicion().y, lechuck.getTamaño().x,
                        lechuck.getTamaño().y);
            } else {
                batch.draw(currentFrame, (lechuck.getPosicion().x + 60),
                        lechuck.getPosicion().y, -lechuck.getTamaño().x,
                        lechuck.getTamaño().y);
            }
        }
        else
            batch.draw(AssetsJuego.textureLeChuckMuerto, lechuck.getPosicion().x, lechuck.getPosicion().y, lechuck.getTamaño().x, lechuck.getTamaño().y);

    }

    private void dibujarSombras() {
        Texture textura = null;
        for (ElementoMovil sombras : miMundo.getSombra()) {

            switch (sombras.getTipo()) {
                case SILUETA_MUJER:
                    textura = AssetsJuego.textureSombraMujer;
                    break;
                case SILUETA_HOMBRE:
                    textura = AssetsJuego.textureSombraHombre;
                    break;
            }
            if (sombras.getVelocidad() > 0) {
                batch.draw(textura, sombras.getPosicion().x + sombras.getTamaño().x, sombras.getPosicion().y, -sombras.getTamaño().x, sombras.getTamaño().y);
            } else {
                batch.draw(textura, sombras.getPosicion().x, sombras.getPosicion().y, sombras.getTamaño().x, sombras.getTamaño().y);
            }
        }
    }

    private void dibujarNieblas() {
        Texture textura = null;
        for (ElementoMovil niebla : miMundo.getNiebla()) {

            switch (niebla.getTipo()) {
                case NIEBLA:
                    textura = AssetsJuego.textureNiebla;
                    break;
                case ANTI_NIEBLA:
                    textura = AssetsJuego.textureAntiniebla;
                    break;
            }
            batch.draw(
                    textura,
                    niebla.getPosicion().x,
                    niebla.getPosicion().y,
                    niebla.getTamaño().x,
                    niebla.getTamaño().y
            );
        }
    }

    private void dibujarLlaveBaño(){
        if(!miMundo.getInventario().tengo_llavebaño)
            batch.draw(AssetsJuego.textureLlaveBaño, Mundo.LLAVE_BAÑO_SUELO.x, Mundo.LLAVE_BAÑO_SUELO.y, Mundo.LLAVE_BAÑO_SUELO.width, Mundo.LLAVE_BAÑO_SUELO.height);
        else if(miMundo.getInventario().usada_llavebaño){
            batch.draw(AssetsJuego.textureLlaveBaño_inactivo, Mundo.LLAVE_BAÑO_INVENTARIO.x, Mundo.LLAVE_BAÑO_INVENTARIO.y, Mundo.LLAVE_BAÑO_INVENTARIO.width, Mundo.LLAVE_BAÑO_INVENTARIO.height);
        }
        else if(miMundo.getInventario().use_llavebaño){
            batch.draw(AssetsJuego.textureLlaveBaño_activo, Mundo.LLAVE_BAÑO_INVENTARIO.x, Mundo.LLAVE_BAÑO_INVENTARIO.y, Mundo.LLAVE_BAÑO_INVENTARIO.width, Mundo.LLAVE_BAÑO_INVENTARIO.height);
        }
        else
            batch.draw(AssetsJuego.textureLlaveBaño, Mundo.LLAVE_BAÑO_INVENTARIO.x, Mundo.LLAVE_BAÑO_INVENTARIO.y, Mundo.LLAVE_BAÑO_INVENTARIO.width, Mundo.LLAVE_BAÑO_INVENTARIO.height);
    }
    private void dibujarLlaveEstudio(){
        if(!miMundo.getInventario().tengo_llaveestudio)
            batch.draw(AssetsJuego.textureLlaveEstudio,Mundo.LLAVE_ESTUDIO_SUELO.x, Mundo.LLAVE_ESTUDIO_SUELO.y, Mundo.LLAVE_ESTUDIO_SUELO.width, Mundo.LLAVE_ESTUDIO_SUELO.height);
        else if(miMundo.getInventario().usada_llaveestudio){
            batch.draw(AssetsJuego.textureLlaveEstudio_inactivo, Mundo.LLAVE_ESTUDIO_INVENTARIO.x, Mundo.LLAVE_ESTUDIO_INVENTARIO.y, Mundo.LLAVE_ESTUDIO_INVENTARIO.width, Mundo.LLAVE_ESTUDIO_INVENTARIO.height);
        }
        else if(miMundo.getInventario().use_llaveestudio){
            batch.draw(AssetsJuego.textureLlaveEstudio_activo, Mundo.LLAVE_ESTUDIO_INVENTARIO.x, Mundo.LLAVE_ESTUDIO_INVENTARIO.y, Mundo.LLAVE_ESTUDIO_INVENTARIO.width, Mundo.LLAVE_ESTUDIO_INVENTARIO.height);
        }
        else
            batch.draw(AssetsJuego.textureLlaveEstudio,Mundo.LLAVE_ESTUDIO_INVENTARIO.x, Mundo.LLAVE_ESTUDIO_INVENTARIO.y, Mundo.LLAVE_ESTUDIO_INVENTARIO.width, Mundo.LLAVE_ESTUDIO_INVENTARIO.height);
    }
    private void dibujarMuñecoVudu(){
        if(!miMundo.getInventario().tengo_muñecovudu)
            batch.draw(AssetsJuego.textureMuñecoVudu, Mundo.MUÑECO_VUDU_SUELO.x, Mundo.MUÑECO_VUDU_SUELO.y, Mundo.MUÑECO_VUDU_SUELO.width, Mundo.MUÑECO_VUDU_SUELO.height);
        else if(miMundo.getInventario().usado_muñecovudu){
            batch.draw(AssetsJuego.textureMuñecoVuduInactivo, Mundo.MUÑECO_VUDU_INVENTARIO.x, Mundo.MUÑECO_VUDU_INVENTARIO.y, Mundo.MUÑECO_VUDU_INVENTARIO.width, Mundo.MUÑECO_VUDU_INVENTARIO.height);
        }
        else if(miMundo.getInventario().use_muñecovudu){
            batch.draw(AssetsJuego.textureMuñecoVudu_activo, Mundo.MUÑECO_VUDU_INVENTARIO.x, Mundo.MUÑECO_VUDU_INVENTARIO.y, Mundo.MUÑECO_VUDU_INVENTARIO.width, Mundo.MUÑECO_VUDU_INVENTARIO.height);
        }
        else
            batch.draw(AssetsJuego.textureMuñecoVudu, Mundo.MUÑECO_VUDU_INVENTARIO.x, Mundo.MUÑECO_VUDU_INVENTARIO.y, Mundo.MUÑECO_VUDU_INVENTARIO.width, Mundo.MUÑECO_VUDU_INVENTARIO.height);
    }
    private void dibujarLlaveFinal(){
        if(!miMundo.getInventario().tengo_llavefinal && lechuck.isMuerto())
            batch.draw(AssetsJuego.textureLlaveFinal, Mundo.LLAVE_FINAL_SUELO.x, Mundo.LLAVE_FINAL_SUELO.y, Mundo.LLAVE_FINAL_SUELO.width, Mundo.LLAVE_FINAL_SUELO.height);
        else if(miMundo.getInventario().usada_llavefinal){
            batch.draw(AssetsJuego.textureLlaveFinalInactivo, Mundo.LLAVE_FINAL_INVENTARIO.x, Mundo.LLAVE_FINAL_INVENTARIO.y, Mundo.LLAVE_FINAL_INVENTARIO.width, Mundo.LLAVE_FINAL_INVENTARIO.height);
        }
        else if(miMundo.getInventario().use_llavefinal){
            batch.draw(AssetsJuego.textureLlaveFinal_activo, Mundo.LLAVE_FINAL_INVENTARIO.x, Mundo.LLAVE_FINAL_INVENTARIO.y, Mundo.LLAVE_FINAL_INVENTARIO.width, Mundo.LLAVE_FINAL_INVENTARIO.height);
        }
        else if (miMundo.getInventario().tengo_llavefinal)
            batch.draw(AssetsJuego.textureLlaveFinal, Mundo.LLAVE_FINAL_INVENTARIO.x, Mundo.LLAVE_FINAL_INVENTARIO.y, Mundo.LLAVE_FINAL_INVENTARIO.width, Mundo.LLAVE_FINAL_INVENTARIO.height);
    }

    private void dibujarTiempo() {
        // Borrar texto
        sbuffer.setLength(0);
        sbuffer.append("Tiempo: ");
        sbuffer.append(miMundo.getCronometro());
        // cpy() needed to properly set afterwards because calling set() seems to modify kept matrix, not replaces it
        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().translate(20, 20, 0).scale(1, 1 , 1));
        this.bitMapFont.draw(batch, sbuffer, 0, this.bitMapFont.getXHeight());
        batch.setProjectionMatrix(originalMatrix); //revert projection
    }
    private void dibujarControles(){
        batch.draw(
                AssetsJuego.textureStop,
                Controles.CONTROL_SALIR.x,
                Controles.CONTROL_SALIR.y,
                Controles.CONTROL_SALIR.width,
                Controles.CONTROL_SALIR.height);
    }

    private void dibujarDialogos(){
        textosLeChuck.setLength(0);
        if(miMundo.getCronometro() > 5 && miMundo.getCronometro() < 15){
            textosLeChuck.append("LECHUCK: TRANQUILO, LOS INVITADOS LLEGARAN\nPRONTO");
        }
        else if(miMundo.getCronometro() > 25 && miMundo.getCronometro() < 30){
            textosLeChuck.append("LECHUCK: ME ESTAS PONIENDO NERVIOSO");
        }
        else if(miMundo.getCronometro() > 45 && miMundo.getCronometro() < 55){
            textosLeChuck.append("LECHUCK: NUNCA SALDRAS DE AQUI.\n AL MENOS HASTA QUE TERMINE MI FIESTA");
        }
        else if(miMundo.getCronometro() > 65 && miMundo.getCronometro() < 70){
            textosLeChuck.append("LECHUCK: AQUI LLEGAN LOS DEMAS");
        }
        if (Intersector.overlaps(bernard.getRectangulo(), miMundo.PASILLO_ESTUDIO) && !miMundo.getInventario().tengo_llaveestudio) {
            textosLeChuck.setLength(0);
            textosLeChuck.append("LECHUCK: EH, PARA QUIETO");
        }
        if (Intersector.overlaps(bernard.getRectangulo(), miMundo.PASILLO_BAÑO) && !miMundo.getInventario().tengo_llavebaño) {
            textosLeChuck.setLength(0);
            textosLeChuck.append("LECHUCK: DEJA DE HACER EL TONTO");
        }
        if(miMundo.getLechuck().isMuerto()){
            textosLeChuck.setLength(0);
            textosLeChuck.append("LECHUCK: AAAAAHHGGGGG!!!");
        }

        controlDialogos(batch, textosLeChuck, 120, 40);


        textosBernard.setLength(0);
        if (Intersector.overlaps(bernard.getRectangulo(), miMundo.PASILLO_ESTUDIO) && !miMundo.getInventario().tengo_llaveestudio) {
            textosBernard.append("NECESITO LA LLAVE DEL\n ESTUDIO");
        }
        if (Intersector.overlaps(bernard.getRectangulo(), miMundo.PASILLO_BAÑO) && !miMundo.getInventario().tengo_llavebaño) {
            textosBernard.append("NECESITO LA LLAVE DEL\n SERVICIO");
        }


        controlDialogos(batch, textosBernard,miMundo.getBernard().getPosicion().x-miMundo.getBernard().getTamaño().x*2,
                miMundo.getBernard().getPosicion().y + miMundo.getBernard().getTamaño().y);
    }

    private void controlDialogos(SpriteBatch batch, StringBuilder builder, float x, float y) {

        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().translate(x, y, 0).scale(1, 1, 1));
        this.bitMapFont.draw(batch, builder, 0, this.bitMapFont.getXHeight());
        batch.setProjectionMatrix(originalMatrix); //revert projection
    }


    public OrthographicCamera getCamara2d() {
        return this.camara2d;
    }

    public void resize(int width, int height) {

        camara2d.setToOrtho(false, Mundo.TAMAÑO_MUNDO_ANCHO,
                Mundo.TAMAÑO_MUNDO_ALTO);
        camara2d.update();

        batch.setProjectionMatrix(camara2d.combined);
        shaperender.setProjectionMatrix(camara2d.combined);

    }

    public void dispose() {

        Gdx.input.setInputProcessor(null);
        batch.dispose();
    }

    /**
     * Dibuja los gráficos en forma de figuras geométricas
     */
    private void debugger() {

        shaperender.begin(ShapeRenderer.ShapeType.Line);
        shaperender.setColor(Color.BLACK);
        shaperender.rect(bernard.getRectangulo().x, bernard.getRectangulo().y, bernard.getRectangulo().width, bernard.getRectangulo().height);
        shaperender.rect(bernard.getPosicion().x, bernard.getPosicion().y, bernard.getTamaño().x, bernard.getTamaño().y, Color.RED, Color.RED, Color.RED, Color.RED);
        shaperender.rect(lechuck.getRectangulo().x, lechuck.getRectangulo().y, lechuck.getRectangulo().width, lechuck.getRectangulo().height);
        shaperender.rect(lechuck.getPosicion().x, lechuck.getPosicion().y, lechuck.getTamaño().x, lechuck.getTamaño().y, Color.RED, Color.RED, Color.RED, Color.RED);
        shaperender.end();

        shaperender.begin(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < miMundo.SUELOS.size(); i++) {
            Rectangle r = miMundo.SUELOS.get(i);
            shaperender.setColor(new Color().fromHsv(360 * i / miMundo.SUELOS.size(), 1, 1));
            shaperender.rect(r.x, r.y, r.width, r.height);
        }
        for (int i = 0; i < miMundo.PUNTOS_DESPLAZAMIENTO.length; i++) {
            shaperender.circle(miMundo.PUNTOS_DESPLAZAMIENTO[i].x, miMundo.PUNTOS_DESPLAZAMIENTO[i].y, miMundo.PUNTOS_DESPLAZAMIENTO[i].radius);
        }
        shaperender.end();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 temporal = new Vector3();
        temporal.set(screenX, screenY, 0f);
        camara2d.unproject(temporal);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
