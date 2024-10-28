package com.example.protoboar;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Switch3x3 extends Group {

    private final ArrayList<Pata> Patas;
    private ArrayList<Protoboard> protos;
    private ImageView Imagen;
    public double initX;
    public double initY;
    public int pos_proto;
    public boolean terminado;
    private boolean encendido = false;
    public boolean agregado=false;

    Switch3x3() {
        // Creación del switch y donde almacenar las patas a futuro junto con
        // instanciar la carga que verifica que este prendido o apagado
        Patas = new ArrayList<>();
        protos = new ArrayList<>();
        terminado = false;
    }

    //Seters
    public void setPatas(Pata patas) {
        Patas.add(patas);
    }

    public void setImageView(ImageView imageView) {
        Imagen = imageView;
    }

    public void setProtos(ArrayList<Protoboard> protos) {
        this.protos = protos;
    }

    //Geters
    public ImageView getImageView() {
        return Imagen;
    }

    public ArrayList<Pata> getPatas() {
        return Patas;
    }

    public Pata getPats(int x){
        return Patas.get(x);
    }

    public ArrayList<Protoboard> getProtos(){
        return protos;
    }

    public boolean getencendido(){
        return encendido;
    }

    //Metodo que recibe el evento de apretar el switch y este cambia la carga y la imagen
    public void cambiarCarga(MouseEvent event) {
        Image image;
        if(!encendido) {
            image = new Image("/Switch3x3.png");
            encendido=true;
        }else{
            image = new Image("/Switch3x3On.png");
            encendido=false;
        }
        Imagen.setImage(image);
        Imagen.toFront();
        Click.iniciar();
    }
}