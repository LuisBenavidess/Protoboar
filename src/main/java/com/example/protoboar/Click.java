package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

//Clase que maneja lo que sea presionar un elemento
public class Click {
    private static Pane pane;
    public static boolean eliminarProximaImagen = false;
    private static ArrayList<conection> cables;
    private static ArrayList<Switch> switches;
    private static ArrayList<Resistencia> resistencias;
    private static ManejarCirculos manejarCirculos;
    private static ArrayList<Protoboard> protos;
    private static ArrayList<Led> leds;
    private static ArrayList<Chip> chips;
    private static ArrayList<Switch3x3> switch3x3;

    //Construcctor
    public Click(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria, Motor motor) {
        Click.pane = pane;
        cables = new ArrayList<>();
        switches = new ArrayList<>();
        resistencias = new ArrayList<>();
        leds = new ArrayList<>();
        manejarCirculos = new ManejarCirculos(pane, protos, ledClicked, cableClicked, bateria, motor);
        // Configura el Timeline para ejecutar iniciar() cada segundo
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> iniciar()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        Click.protos = protos;
        manejarCirculos = new ManejarCirculos(pane, protos, ledClicked, cableClicked, bateria,motor);
    }

    //Funciones get y set
    public static ArrayList<conection> getCables() {
        return manejarCirculos.getCables();
    }

    public static ArrayList<Led> getCables_led() {
        return manejarCirculos.get_leds();
    }

    public void setLedClicked(boolean ledClicked) {
        manejarCirculos.setLedClicked(ledClicked);
    }

    public void setCableClicked(boolean cableClicked) {
        manejarCirculos.setCableClicked(cableClicked);
    }

    public void setSwitchClicked(boolean switchClicked){
        manejarCirculos.setSwitchClicked(switchClicked);
    }

    public void setResistencias(boolean resistClicked){
        manejarCirculos.setResistClicked(resistClicked);
    }

    public static void setCables(ArrayList<conection> cable) {
        cables = cable;
    }

    public static void setSwitches(ArrayList<Switch> switche) {
        switches = switche;
    }

    public static void setResistencias(ArrayList<Resistencia> resistencia) {
        resistencias = resistencia;
    }

    public static ArrayList<Protoboard> getprotos() {
        return protos;
    }

    public static void  setSwitch3x3(ArrayList<Switch3x3> switch3x3s) {
        switch3x3 = switch3x3s;
    }

    public static ArrayList<Switch3x3> getSwitch3x3s() {
        return switch3x3;
    }

    public static void  setChips(ArrayList<Chip> chip) {
        chips=chip;
    }

    public static ArrayList<Chip> getChips() {
        return chips;
    }

    public void setEliminarProximaImagen(boolean eliminar){
        eliminarProximaImagen=eliminar;
    }


    ////////////////////////////////////////////////////////////////////////

    //Metodo para cuando se preciona algun circlu(bus)
    public void presionarCirculo(MouseEvent event) {
        manejarCirculos.presionarCirculo(event);
    }

    public static void presiona(MouseEvent event) {
    }

    //Vuelve a neutro cada bus
    public static void revovinar() {
        manejarCirculos.revovinar();
    }

    // verifica el intercambio de cargas atravez de los cables
    public static void verificar_cables() {
        setCables(manejarCirculos.getCables());
        manejarCirculos.verificar_cables();
    }

    // prender led atravez de las cargas
    public static void prender_led() {
        manejarCirculos.prender_led();
    }

    // verifica los switch para el intercambio de cargas
    public static void verificar_switch() {
        setSwitches(manejarCirculos.getswitches());
        manejarCirculos.verificar_switch();
    }

    // verifica los switch para el intercambio de cargas
    public static void verificar_resistencia() {
        setResistencias(manejarCirculos.getResistencias());
        manejarCirculos.verificar_resistencia();
    }

    // verifica los switch3x3 para el intercambio de carga
    public static void verificar_Switch3x3s() {
        manejarCirculos.verificar_sw3x3();
    }
    public static void verificar_chips() {
        manejarCirculos.verificar_chip();
    }
    @FXML
    //Metodo que inicia el proceso de verificar cargas atravez de los buses, cables y swich, esto se genera atravez del evento de presionar el boton
    public static void iniciar() {
        //Revovina todos los circulos a neutro(negro) para verificar de forma correcta
        revovinar();
        int i=0;
        setChips(manejarCirculos.getChips());
        setSwitch3x3(manejarCirculos.getswitches3x3());
        // Verifica los cables y switch para trasladar la carga
        while(i<getCables().size()){
            verificar_cables();
            verificar_switch();
            verificar_resistencia();
            int j=0;
            while(j<getSwitch3x3s().size()){
                verificar_Switch3x3s();
                j++;
            }
            j=0;
            while(j<getChips().size()){
                verificar_chips();
                j++;
            }
            i++;
        }
        i=0;
        //Verifica los leds
        while(i<getCables_led().size()){
            prender_led();
            i++;
        }
        i=0;
        //Verifica los chips
        //manejarCirculos.verificar_chip();
        if(switch3x3!=null){
            manejarCirculos.verificar_sw3x3();
        }
        if(chips!=null){
            manejarCirculos.verificar_chip();
        }
    }

    //Metodo para cuando se preciona el basurero (Borrar)
    public void ClickEnBasurero() {
        eliminarProximaImagen = !eliminarProximaImagen;
    }

    public void CrearChip(String tipo){
        manejarCirculos.crearChip(tipo);
    }

    public void CrearSwitch3x3(){
        manejarCirculos.crearSwitch3x3();
    }

    //Metodo que llama a eliminar elemento de otra clase
    public static void eliminarElemento(MouseEvent event) {
        //Condcion para saber si se desea eliminar un objeto
        if (eliminarProximaImagen) {
            //Obtiene el objeto presionado y lo borra
            Object basura = event.getSource();
            pane.getChildren().remove(basura);
            System.out.println("Se eliminó algo");
            //Para poder borrar un cable o un switch este tambie debe de borrar el ArrayList de cada uno y
            // verificar si el objeto obtenido es el mismo que alguno de los array
            int i = 0; //bucle cables
            while (i < cables.size()) {
                if (basura.equals(cables.get(i))) {
                    cables.get(i).getInicio().componenteCreado = false;
                    cables.get(i).getFin().componenteCreado = false;
                    int x=0;
                    while(x<getprotos().size()){
                        protos.get(x).getChildren().remove(cables.get(i));
                        if(protos.get(x).getChildren().contains(cables.get(i))){
                            System.out.println("se borro el cable");
                            protos.get(x).getChildren().remove(cables.get(i));
                        }
                        x++;
                    }
                    cables.remove(i);
                }
                i++;
            }
            i=0;
            //bucles switch
            switches=manejarCirculos.getswitches();
            System.out.println(switches.size());
            while (i < switches.size()) {
                if (basura.equals(switches.get(i).getImageView())) {
                    System.out.println("se elimino switch");
                    switches.get(i).getCable().getInicio().componenteCreado = false;
                    switches.get(i).getCable().getFin().componenteCreado = false;
                    // Remover el switch de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(switches.get(i).getImageView());
                        proto.getChildren().remove(switches.get(i).getCable());
                    }
                    // Remover el switch del array
                    switches.remove(i);
                }
            }
            i=0;
            //bucles Leds
            leds=manejarCirculos.get_leds();
            System.out.println(leds.size());
            while (i < leds.size()) {
                if (basura.equals(leds.get(i).getImageView())) {
                    System.out.println("se elimino led");
                    leds.get(i).getCable_rojo().getInicio().componenteCreado = false;
                    leds.get(i).getCable_azul().getFin().componenteCreado = false;
                    // Remover el switch de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(leds.get(i).getImageView());
                        proto.getChildren().remove(leds.get(i).getCable_rojo());
                        proto.getChildren().remove(leds.get(i).getCable_azul());
                    }
                    // Remover el switch del array
                    leds.remove(i);
                }
                i++;
            }
            i=0;
            //bucles switch
            resistencias=manejarCirculos.getResistencias();
            System.out.println(switches.size());
            while (i < resistencias.size()) {
                if (basura.equals(resistencias.get(i).getImageView())) {
                    System.out.println("se elimino resistencia");
                    // Remover el switch de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(resistencias.get(i).getImageView());
                        proto.getChildren().remove(resistencias.get(i).getCable());
                    }
                    // Remover el switch del array
                    resistencias.remove(i);
                }
                i++;
            }
            i=0;
            //bucle chips
            chips=manejarCirculos.getChips();
            while (i < chips.size()) {
                if (basura.equals(chips.get(i))) {
                    System.out.println("se elimino Chip");
                    // Actualiza buses conectados al chip eliminado
                    for (Pata pata : chips.get(i).getPatas()) {
                        if (pata.getBus_conectado() != null) {
                            pata.getBus_conectado().componenteCreado = false; // Actualiza el bus
                        }
                    }
                    // Remover el chip de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(chips.get(i));
                    }
                    chips.remove(i);
                }
                i++;
            }
            i=0;
            //bucle Switch3x3
            switch3x3=manejarCirculos.getswitches3x3();
            while(i < switch3x3.size()){
                if (basura.equals(switch3x3.get(i))) {
                    System.out.println("se elimino Switch3x3");
                    for (Pata pata : switch3x3.get(i).getPatas()) {
                        if (pata.getBus_conectado() != null) {
                            pata.getBus_conectado().componenteCreado = false; // Actualiza el bus
                        }
                    }
                    // Remover el chip de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(switch3x3.get(i));
                    }
                    switch3x3.remove(i);
                }
                i++;
            }
        }
    }
}