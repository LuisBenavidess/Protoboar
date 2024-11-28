package com.example.protoboar;

import javafx.scene.shape.Circle;

// Esta clase es la que utilizamos para cada círculo que se encuentra en el protoboard
public class bus extends Circle {
    // Atributos
    String carga;
    int columna;
    int fila;
    double x;
    double y;
    boolean componenteCreado = false; // Indica si se ha creado un componente en este círculo

    // Ingresar columna
    public void setColumna(int columna) {
        this.columna = columna;
    }

    // Ingresar fila
    public void setFila(int fila) {
        this.fila = fila;
    }

    // Ingresar carga
    public void setCarga(String carga) {
        this.carga = carga;
    }

    public void setExtremo() {
    }

    // Devolver carga
    public String getCarga() {
        return carga;
    }

    public void crearComponente() {
        componenteCreado = true;
    }

    // Metodo para comprobar si se puede crear un componente
    public boolean puedeCrearComponente() {
        return !(componenteCreado);
    }
}