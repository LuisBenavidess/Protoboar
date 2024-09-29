package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

//Clase que genera el protoboard
public class Protoboard extends Group {

    //Atributos
    public bus[][] alimentacion;
    private Rectangle base;
    public int columnas;
    public int filas;
    public double x;
    public double y;
    public double initialX;
    public double initialY;
    public double movimientoX;
    public double movimientoY;

    private ArrayList<conection> conections;
    //private Rectangle base;

    //Constructor
    public Protoboard() {
        conections = new ArrayList<>();
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

    public void setConections(conection conection) {
        conections.add(conection);
    }
    public ArrayList<conection> getConections() {
        return conections;
    }

    //Metodos

}
