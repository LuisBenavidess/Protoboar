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
    private static ManejarCirculos manejarCirculos;
    private static ArrayList<Protoboard> protos;
    private static ArrayList<Led> leds;

    //Construcctor
    public Click(Pane pane, ArrayList<Protoboard> protos, boolean ledClicked, boolean cableClicked, Bateria bateria,Motor motor) {
        Click.pane = pane;
        cables = new ArrayList<>();
        switches = new ArrayList<>();
        leds = new ArrayList<>();
        manejarCirculos = new ManejarCirculos(pane, protos, ledClicked, cableClicked, bateria);
        // Configura el Timeline para ejecutar iniciar() cada segundo
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> iniciar()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        Click.protos = protos;
        manejarCirculos = new ManejarCirculos(pane, protos, ledClicked, cableClicked, bateria);
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

    public static void setCables(ArrayList<conection> cable) {
        cables = cable;
    }

    public static void setSwitches(ArrayList<Switch> switche) {
        switches = switche;
    }

    public static ArrayList<Protoboard> getprotos() {
        return protos;
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

    @FXML
    //Metodo que inicia el proceso de verificar cargas atravez de los buses, cables y swich, esto se genera atravez del evento de presionar el boton
    public static void iniciar() {
        //Revovina todos los circulos a neutro(negro) para verificar de forma correcta
        revovinar();
        int i=0;
        // Verifica los cables y switch para trasladar la carga
        while(i<getCables().size()){
            verificar_cables();
            verificar_switch();
            i++;
        }
        i=0;
        //Verifica los leds
        while(i<getCables_led().size()){
            prender_led();
            i++;
        }
    }

    //Metodo para cuando se preciona el basurero (Borrar)
    public void ClickEnBasurero() {
        eliminarProximaImagen = !eliminarProximaImagen;
    }

    //Metodo que llama a eliminar elemento de otra clase
    public static void eliminarElemento(MouseEvent event) {
        //Condcion para saber si se desea eliminar un objeto
        if (eliminarProximaImagen) {
            //Obtiene el objeto presionado y lo borra
            Object basura = event.getSource();
            pane.getChildren().remove(basura);
            //Para poder borrar un cable o un switch este tambie debe de borrar el ArrayList de cada uno y
            // verificar si el objeto obtenido es el mismo que alguno de los array
            int i = 0;
            //bucle cables
            while (i < cables.size()) {
                if (basura.equals(cables.get(i))) {
                    int x=0;
                    while(x<getprotos().size()){
                        protos.get(x).getChildren().remove(cables.get(i));
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
                    // Remover el switch de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(switches.get(i).getImageView());
                        proto.getChildren().remove(switches.get(i).getCable());
                    }
                    // Remover el switch del array
                    switches.remove(i);
                } else {
                    i++;
                }
            }
            i=0;
            //bucles Leds
            leds=manejarCirculos.get_leds();
            System.out.println(leds.size());
            while (i < leds.size()) {
                if (basura.equals(leds.get(i).getImageView())) {
                    // Remover el switch de los protoboards
                    for (Protoboard proto : getprotos()) {
                        proto.getChildren().remove(leds.get(i).getImageView());
                        proto.getChildren().remove(leds.get(i).getCable_rojo());
                        proto.getChildren().remove(leds.get(i).getCable_azul());
                    }
                    // Remover el switch del array
                    leds.remove(i);
                } else {
                    i++;
                }
            }
        }
    }
}
