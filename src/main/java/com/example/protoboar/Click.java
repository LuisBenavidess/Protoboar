package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), _ -> iniciar())); // ejecuta iniciar() cada segundo
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

    public int getCantidad() {
        return protos.size();
    }

    public conection getlinea(){
        return manejarCirculos.getLinea();
    }
    public ManejarCirculos get_manejarciruclo(){
        return manejarCirculos;
    }

    public void setEliminarProximaImagen(boolean eliminar){
        eliminarProximaImagen=eliminar;
    }


    ////////////////////////////////////////////////////////////////////////
    public void presionarCirculo(MouseEvent event) { //Metodo para cuando se preciona algun circlu(bus)
        manejarCirculos.presionarCirculo(event);
    }

    public static void presiona(MouseEvent event) {
    }

    public static void revovinar() {  //Vuelve a neutro cada bus
        manejarCirculos.revovinar();
    }

    public static void verificar_cables() { // verifica el intercambio de cargas atravez de los cables
        setCables(manejarCirculos.getCables());
        manejarCirculos.verificar_cables();
    }


    public static void prender_led() { // prender led atravez de las cargas
        manejarCirculos.prender_led();
    }

    public static void verificar_switch() { // verifica los switch para el intercambio de cargas
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
    public static void iniciar() { //Metodo que inicia el proceso de verificar cargas atravez de los buses, cables y switch
        revovinar(); //Revovina todos los circulos a neutro
        int i=0;
        setChips(manejarCirculos.getChips());
        setSwitch3x3(manejarCirculos.getswitches3x3());
        while(i<getCables().size()){ // Verifica los cables y switch para trasladar la carga
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
        while(i<getCables_led().size()){ //Verifica los leds
            prender_led();
            i++;
        }
        if(switch3x3!=null){
            manejarCirculos.verificar_sw3x3();
        }
        if(chips!=null){
            manejarCirculos.verificar_chip();
        }
        verificar_cables();
    }

    public void ClickEnBasurero() {  //Metodo para cuando se preciona el basurero (Borrar)
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
        if (eliminarProximaImagen) {   //Condcion para saber si se desea eliminar un objeto
            Object basura = event.getSource(); //Obtiene el objeto presionado y lo borra
            pane.getChildren().remove(basura);
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
                            protos.get(x).getChildren().remove(cables.get(i));
                        }
                        x++;
                    }
                    cables.remove(i);
                }
                i++;
            }
            i=0;
            switches=manejarCirculos.getswitches(); //bucles switch
            System.out.println(switches.size());
            while (i < switches.size()) {
                if (basura.equals(switches.get(i).getImageView())) {
                    switches.get(i).getCable().getInicio().componenteCreado = false;
                    switches.get(i).getCable().getFin().componenteCreado = false;
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(switches.get(i).getImageView()); // Remover el switch de los protoboards
                        proto.getChildren().remove(switches.get(i).getCable());
                    }
                    switches.remove(i); // Remover el switch del array
                }
            }
            i=0;
            leds=manejarCirculos.get_leds();  //bucles Leds
            System.out.println(leds.size());
            while (i < leds.size()) {
                if (basura.equals(leds.get(i).getImageView())) {
                    leds.get(i).getCable_rojo().getInicio().componenteCreado = false;
                    leds.get(i).getCable_azul().getFin().componenteCreado = false;
                    for (Protoboard proto : getprotos()) {  // Remover el switch de los protoboards
                        proto.getChildren().remove(leds.get(i).getImageView());
                        proto.getChildren().remove(leds.get(i).getCable_rojo());
                        proto.getChildren().remove(leds.get(i).getCable_azul());
                    }
                    leds.remove(i); // Remover el switch del array
                }
                i++;
            }
            i=0;
            resistencias=manejarCirculos.getResistencias();    //bucle resistencias
            System.out.println(switches.size());
            while (i < resistencias.size()) {
                if (basura.equals(resistencias.get(i).getImageView())) {
                    for (Protoboard proto : getprotos()) {  // Remover resistencias  de los protoboards
                        proto.getChildren().remove(resistencias.get(i).getImageView());
                        proto.getChildren().remove(resistencias.get(i).getCable());
                    }
                    resistencias.remove(i);
                }
                i++;
            }
            i=0;
            chips=manejarCirculos.getChips();  //bucle chips
            while (i < chips.size()) {
                if (basura.equals(chips.get(i))) {
                    for (Pata pata : chips.get(i).getPatas()) {  // Actualiza buses conectados al chip eliminado
                        if (pata.getBus_conectado() != null) {
                            pata.getBus_conectado().componenteCreado = false; // Actualiza el bus
                        }
                    }
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(chips.get(i));   // Remover el chip de los protoboards
                    }
                    chips.remove(i);
                }
                i++;
            }
            i=0;
            switch3x3=manejarCirculos.getswitches3x3();
            while(i < switch3x3.size()){  //bucle Switch3x3
                if (basura.equals(switch3x3.get(i))) {
                    for (Pata pata : switch3x3.get(i).getPatas()) {
                        if (pata.getBus_conectado() != null) {
                            pata.getBus_conectado().componenteCreado = false; // Actualiza el bus
                        }
                    }
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(switch3x3.get(i)); // Remover el chip de los protoboards
                    }
                    switch3x3.remove(i);
                }
                i++;
            }
            if((!(basura instanceof Rectangle) && !(basura instanceof Chip) && !(basura instanceof Switch3x3))
                    || (basura instanceof Text || basura instanceof  Label)){
                i=0;
                while(i< protos.size()){
                    if(protos.get(i).getChildren().contains(basura)){

                        if(alertaeliminar()){

                            while (!protos.get(i).getConections().isEmpty()) {
                                //System.out.println("entra");
                                protos.get(i).getConections().get(0).getInicio().componenteCreado = false;
                                protos.get(i).getConections().get(0).getFin().componenteCreado = false;
                                pane.getChildren().remove(protos.get(i).getConections().get(0));
                                protos.get(i).getConections().remove(0);
                            }
                            pane.getChildren().remove(protos.get(i));
                            protos.remove(i);


                        }


                    }
                    i++;
                }

            }

        }
    }

    private static boolean alertaeliminar(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea Eliminar el protoboard?");
        alert.setContentText("Seleccione una opción:");

        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");

        alert.getButtonTypes().setAll(botonSi, botonNo);

        Optional<ButtonType> resultado = alert.showAndWait();
        if (resultado.isPresent() && resultado.get() == botonSi) {
            return true;
            // Coloca aquí el código para continuar
        } else {
            return false;
            // Coloca aquí el código para cancelar
        }
    }
}