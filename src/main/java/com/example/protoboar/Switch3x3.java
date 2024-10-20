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
    private static ImageView Imagen;
    public double initX;
    public double initY;
    public int pos_proto;
    public boolean terminado;
    public static String Carga;
    public boolean agregado=false;

    Switch3x3() {
        Patas = new ArrayList<>();
        protos = new ArrayList<>();
        terminado = false;
        Carga = "-";
    }

    public void setPatas(Pata patas) {
        Patas.add(patas);
    }

    public void setImageView(ImageView imageView) {
        Imagen = imageView;
    }

    public void setProtos(ArrayList<Protoboard> protos) {
        this.protos = protos;
    }

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

    //Metodo que recibe el evento de apretar el switch y este cambia la carga y la imagen
    public static void cambiarCarga(MouseEvent event) {

        if(Objects.equals(Carga, "+")) {
            Apagar();
            System.out.println("apagado");
        }else{
            Encender();
            System.out.println("prendido");
        }
        Click.iniciar();

    }

    //Metodo que cambia la carga a positivo y cambia la imagen a 1
    public static void Encender() {
        Image image = new Image("/Switch3x3On.png");
        Imagen.setImage(image);
        Imagen.toFront();
        Carga = "+";
    }

    //Metodo que cambia la carga a negativo y cambia la imagen a 2
    public static void Apagar() {
        Image image = new Image("/Switch3x3.png");
        Imagen.setImage(image);
        Imagen.toFront();
        Carga = "-";
    }

    public String getCarga(){
        return Carga;
    }
}
