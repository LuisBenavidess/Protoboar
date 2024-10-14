package com.example.protoboar;

import javafx.scene.shape.Rectangle;

public class Pata extends Rectangle {
    private int pata;
    private bus bus_conectado;

    public Pata(double x, double y, int width, int height ) {

        super(x, y, width, height);
    }
    public void setPata(int pata) {
        this.pata = pata;
    }
    public void setBus_conectado(bus bus_conectado) {
        this.bus_conectado = bus_conectado;
    }

    public int getPata() {
        return pata;
    }
    public bus getBus_conectado() {
        return bus_conectado;
    }
}
