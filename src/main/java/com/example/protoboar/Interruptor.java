package com.example.protoboar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Interruptor extends ImageView {
    private boolean encendido = true;
    public bus bus1;
    public bus bus2;
    private boolean quemado = false;

    Interruptor() {
        super("/Interruptor1.png");
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    public void setQuemado(boolean quemado) {
        this.quemado = quemado;
    }

    public void setBus1(bus bus1) {
        this.bus1 = bus1;
    }

    public void setBus2(bus bus2) {
        this.bus2 = bus2;
    }

    public boolean getEncendido() {
        return encendido;
    }

    public boolean getQuemado() {
        return quemado;
    }

    public bus getBus1() {
        return bus1;
    }

    public bus getBus2() {
        return bus2;
    }

}
