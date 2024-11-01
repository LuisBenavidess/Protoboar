package com.example.protoboar;

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
    private final ArrayList<conection> conections;
    private ArrayList<Led> leds;

    //Constructor
    public Protoboard() {
        conections = new ArrayList<>();
        leds=new ArrayList<>();
        alimentacion = new bus[14][30];
        x=37;
        y=52;
    }

    public void setBase(Rectangle base) {
        this.base = base;
    }

    public Rectangle getBase() {
        return base;
    }

    public void setConections(conection conection) {
        conections.add(conection);
    }
    public ArrayList<conection> getConections() {
        return conections;
    }

    public void setLeds(Led led){
        leds.add(led);
    }
    public ArrayList<Led> getLeds(){
        return leds;
    }
    public Led get_led_exacto(int x){
        return leds.get(x);
    }
}
