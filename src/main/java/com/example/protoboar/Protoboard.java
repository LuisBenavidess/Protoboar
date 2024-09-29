package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

//Clase que genera el protoboard
public class Protoboard extends Group {

    //Atributos
    public bus[][] alimentacion;
    private Rectangle base;
    public int columnas;
    public int filas;
    public double x;
    public double y;
    public double offsetX;
    public double offsetY;
    //private Rectangle base;

    //Constructor
    public Protoboard() {

        alimentacion = new bus[14][30];
        x=37;
        y=52;
    }

    public void setAlimentacion(bus[][] alimentacion) {
        this.alimentacion = alimentacion;
    }

    public bus[][] getAlimentacion() {
        return alimentacion;
    }

    public void setBase(Rectangle base) {
        this.base = base;
    }

    public Rectangle getBase() {
        return base;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getFilas() {
        return filas;
    }


    //Metodos

}
