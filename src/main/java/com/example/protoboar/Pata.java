package com.example.protoboar;

import javafx.scene.shape.Rectangle;
// clase de las patas del chip y el switch
public class Pata extends Rectangle {
    // atributos
    private int pata;
    private bus bus_conectado; // a que pata este se conectó

    public Pata(double x, double y, int width, int height ) {
        super(x, y, width, height);
    }

    // metodos gets y sets
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