package com.example.protoboar;

import javafx.scene.Group;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;
public class Chip extends Group {

    public double initX;
    public double initY;
    private ArrayList<Protoboard> protos;
    private ArrayList<Pata> patas;
    public int pos_proto;
    public boolean terminado;

    Chip(){
        protos = new ArrayList<>();
        patas = new ArrayList<>();
        terminado = false;
    }
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

}
