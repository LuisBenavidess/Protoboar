package com.example.protoboar;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
//Clase que maneja lo que sea presionar un elemento
public class Click {
    private static Pane pane;
    private static boolean eliminarProximaImagen = false;
    private static ArrayList<conection> cables;
    private static ArrayList<Switch> switches;
    private static ManejarCirculos manejarCirculos;

    //Construcctor
    public Click(Pane pane, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        Click.pane = pane;
        cables = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCirculos = new ManejarCirculos(pane, alimentacion, ledClicked, cableClicked, bateria);
    }

    //Funciones get y set
    public static ArrayList<conection> getCables() {
        return manejarCirculos.getCables();
    }

    public static ArrayList<Switch> getswitch() {
        return manejarCirculos.getswitches();
    }

    public static ArrayList<conection> getCables_led() {
        return manejarCirculos.getCables_led();
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
    ////////////////////////////////////////////////////////////////////////

    //Metodo para cuando se preciona algun circlu(bus)
    public void presionarCirculo(MouseEvent event) {
        manejarCirculos.presionarCirculo(event);
    }

    //Metodo para cuando se preciona el basurero(Borrar)
    public void ClickEnBasurero() {
        System.out.println("Modo borrar");
        eliminarProximaImagen = true;
    }

    public static void presiona(TouchEvent event) {
        iniciar();
    }

    //Metodo que llama a eliminar elemento de otra clase
    public static void eliminarElemento(MouseEvent event) {
        //Condcion para saber si se desea eliminar un objeto
        if (eliminarProximaImagen) {
            //Obtiene el objeto presionado y lo borra
            Object basura = event.getSource();
            pane.getChildren().remove(basura);

            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");

            //Para poder borrar un cable o un switch este tambie debe de borrar el ArrayList de cada uno y
            // verificar si el objeto obtenido es el mismo que alguno de los array
            int i = 0;
            //bucle cables
            while (i < cables.size()) {
                System.out.println("busca cable");
                if (basura.equals(cables.get(i))) {
                    System.out.println("se elimino cable");
                    cables.remove(i);
                }
                i++;
            }
            i=0;
            //bucles switch
            System.out.println(switches.size());
            while (i < switches.size()) {
                System.out.println("busca switch");
                if (basura.equals(switches.get(i).getImageView())) {
                    System.out.println("se elimino switch");
                    pane.getChildren().remove(switches.get(i).getCable());
                    switches.remove(i);
                }
                i++;
            }

            //iniciar();
        }
       // iniciar();
    }
    //Vuelve a neutro cada bus
    public static void revovinar() {
        manejarCirculos.revovinar();
    }
    // verifica el intercambio de cargas atravez de los cables
    public static void verificar_cables() {
        System.out.println("verifica cable");
        setCables(manejarCirculos.getCables());
        manejarCirculos.verificar_cables();
      //  iniciar();
    }
    // prender led atravez de las cargas
    public static void prender_led() {
        manejarCirculos.prender_led();
    }
    // verifica los switch para el intercambio de cargas
    public static void verificar_switch() {
        System.out.println("verifica switch");
        setSwitches(manejarCirculos.getswitches());
        manejarCirculos.verificar_switch();
    }

    @FXML
    //Metodo que inicia el proceso de verificar cargas atravez de los buses, cables y swich, esto se genera atravez del evento de presionar el boton
    public static void iniciar() {
        System.out.println("paso");
        //Revovina todos los circulos a neutro(negro) para verificar de forma correcta
        revovinar();
        int i=0;
        // Verifica los cables y switch para trasladar la carga
        while(i<getCables().size()){
            //System.out.println("morrrriirirr");
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
}
