package com.example.protoboar;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Switch3x3 extends Group {

    private ArrayList<Pata> Patas;
    private ArrayList<Protoboard> protos;
    private ImageView Imagen;
    public double initX;
    public double initY;
    public int pos_proto;
    public boolean terminado;
    private String Carga;
    public boolean agregado=false;

    Switch3x3() {
        // Creación del switch y donde almacenar las patas a futuro junto con
        // instanciar la carga que verifica que este prendido o apagado
        Patas = new ArrayList<>();
        protos = new ArrayList<>();
        terminado = false;
        Carga = "-";
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

    public String getCarga(){
        return Carga;
    }

    //Metodo que recibe el evento de apretar el switch y este cambia la carga y la imagen
    public void cambiarCarga(MouseEvent event) {
        Image image;
        if(this.Carga.equals("+")) {
            image = new Image("/Switch3x3.png");
            this.Carga = "-";

            System.out.println("apagado");
        }else{
            image = new Image("/Switch3x3On.png");
            this.Carga = "+";

            System.out.println("prendido");
        }
        Imagen.setImage(image);
        Imagen.toFront();

        Click.iniciar();

    }

}
