package com.example.protoboar;

import javafx.scene.shape.Circle;
//Esta clase es la que utilizamos para cada circulo que se encuentra en el protoboard
public class bus extends Circle {
    //Atributos
    String carga;
    int columna;
    int fila;
    double x;
    double y;

    //Contructor
    public bus() {
    }
    //Ingresar columan
    public void setColumna(int columna){
        this.columna=columna;
    }
    //Ingresar fila
    public void setFila(int fila){
        this.fila=fila;
    }
    //Ingresar carga
    public void setCarga(String carga){
        this.carga=carga;
    }
    //Devolver cagra
    public String getCarga() {
        return carga;
    }

}

