package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

//Clase que genera el protoboard
public class Protoboard extends Group {

    private Rectangle base;
    //private Rectangle base;

    //Constructor
    public Protoboard() {

    }

    public void setBase(Rectangle base) {
        this.base = base;
    }

    public Rectangle getBase() {
        return base;
    }

}