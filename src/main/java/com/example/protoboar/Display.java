package com.example.protoboar;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Display extends Group {

    public double initX;
    public double initY;
    private Rectangle base;
    private ArrayList<Protoboard> protos;
    private ArrayList<Pata> patas;
    private ArrayList<Rectangle> leds;
    private Circle punto;
    public int pos_proto;
    public boolean terminado;
    public boolean agregado=false;

    public Display() {
        protos = new ArrayList<>();
        patas = new ArrayList<>();
        leds = new ArrayList<>();
        terminado = false;
    }

    //Metodos gets y sets
    public void addProto(Protoboard proto){
        protos.add(proto);
    }
    public void addPat(Pata pat){
        patas.add(pat);
    }
    public void setProtos(ArrayList<Protoboard> protos){
        this.protos = protos;
    }
    public void setPats(ArrayList<Pata> pats){
        this.patas = pats;
    }
    public void removeProto(Protoboard proto){
        protos.remove(proto);
    }
    public void removePat(Rectangle pat){
        patas.remove(pat);
    }
    public ArrayList<Protoboard> getProtos(){
        return protos;
    }

    public ArrayList<Pata> getPatas(){
        return patas;
    }
    public Pata getPats(int x){
        return patas.get(x);
    }
    public void setBase(Rectangle base){
        this.base = base;
    }
    public Rectangle getBase(){
        return base;
    }

    public ArrayList<Rectangle> getLeds(){
        return leds;
    }

    public ArrayList<Rectangle> getleds(){
        return leds;
    }

    public void setLeds(Rectangle led){
        leds.add(led);
    }
    public Rectangle getLeds(int x){
        return leds.get(x);
    }

    public void setPunto(Circle punto){
        this.punto=punto;
    }
    public Circle getPunto(){
        return punto;
    }

}
