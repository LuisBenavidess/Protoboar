package com.example.protoboar;

import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

// Esta clase es la que utilizamos para cada círculo que se encuentra en el protoboard
public class bus extends Circle {
    // Atributos
    String carga;
    int columna;
    int fila;
    double x;
    double y;
    boolean componenteCreado = false; // Indica si se ha creado un componente en este círculo
    Double voltaje;
    Text voltajeDisplay; // Para mostrar el voltaje en pantalla

    // Constructor
    public bus() {
        this.voltaje = null; // Inicializamos el voltaje como null
        this.voltajeDisplay = new Text();
        this.voltajeDisplay.setText("");
    }

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

    // Establecer voltaje
    public void setVoltaje(Double voltaje) {
        this.voltaje = voltaje;
        updateVoltajeDisplay(); // Actualiza el texto que muestra el voltaje
    }

    public void crearComponente() {
        componenteCreado = true;
    }

    // Metodo para comprobar si se puede crear un componente
    public boolean puedeCrearComponente() {
        return !componenteCreado;
    }

    // Metodo para actualizar la visualización del voltaje
    private void updateVoltajeDisplay() {
        if (voltaje != null) {
            voltajeDisplay.setText(String.valueOf(voltaje));
        } else {
            voltajeDisplay.setText(""); // No mostrar nada si voltaje es null
        }
    }

    // Metodo para obtener el nodo que muestra el voltaje
    public Text getVoltajeDisplay() {
        return voltajeDisplay;
    }

    public void setVoltajeDisplay(String voltaje) {
        this.voltajeDisplay.setText(voltaje);
    }
}