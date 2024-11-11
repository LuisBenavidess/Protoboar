package com.example.protoboar;

import java.util.ArrayList;
import java.util.Objects;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Switch3x3 extends Group {
    private final ArrayList<Pata> Patas;
    private ArrayList<Protoboard> protos;
    private ImageView Imagen;
    private String tipoCarga;
    public double initX;
    public double initY;
    public int pos_proto;
    public boolean terminado;
    private boolean encendido = false;
    public boolean agregado = false;

    Switch3x3() {
        Patas = new ArrayList<>();
        protos = new ArrayList<>();
        terminado = false;
        tipoCarga = null;
    }

    // Getters y Setters
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

    public Pata getPats(int x) {
        return Patas.get(x);
    }

    public ArrayList<Protoboard> getProtos() {
        return protos;
    }

    public boolean getEncendido() {
        return encendido;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        if (this.tipoCarga == null) {
            this.tipoCarga = tipoCarga;
        }
    }

    public void quitarPolaridad() {
        this.tipoCarga = null;  // Reiniciar tipo de carga a neutro
    }

    // Método que recibe el evento de apretar el switch y cambia la carga y la imagen
    public void cambiarCarga(MouseEvent event) {
        if (!Objects.equals(tipoCarga, "X")) {
            Image image;
            if (!encendido) {
                image = new Image("/Switch3x3.png");
                encendido = true;
            } else {
                image = new Image("/Switch3x3On.png");
                encendido = false;
                tipoCarga = null;
            }
            Imagen.setImage(image);
            Imagen.toFront();
            Click.iniciar();
        }
    }

    public void quemarSwitch() {
        Image image = new Image("/Switch3x3On(Quemado).png"); // imagen del switch quemado
        Imagen.setImage(image);
        Imagen.toFront();
        encendido = false;
        tipoCarga = "X"; // Marca el switch como quemado
    }
}