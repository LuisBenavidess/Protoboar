package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
//Clase que maneja lo que sea presionar un elemento
public class Click {
    private static Pane pane;
    private static boolean eliminarProximaImagen = false;
    private static ArrayList<conection> cables;
    private static ArrayList<Switch> switches;
    private final ManejarCirculos manejarCirculos;

    //Construcctor
    public Click(Pane pane, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        Click.pane = pane;
        cables = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCirculos = new ManejarCirculos(pane, alimentacion, ledClicked, cableClicked, bateria);
    }

    //Funciones get y set
    public ArrayList<conection> getCables() {
        return manejarCirculos.getCables();
    }

    public ArrayList<conection> getCables_led() {
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

    public void setCables(ArrayList<conection> cables) {
        this.cables = cables;
    }
    ///////

    //Metodo para cuando se preciona algun circlu(bus)
    public void presionarCirculo(MouseEvent event) {
        manejarCirculos.presionarCirculo(event);
    }

    //Metodo para cuando se preciona el basurero(Borrar)
    public void ClickEnBasurero() {
        System.out.println("Modo borrar");
        eliminarProximaImagen = true;
    }
    //Metodo que llama a eliminar elemento de otra clase
    public static void eliminarElemento(MouseEvent event) {
        if (eliminarProximaImagen) {
            Object basura = event.getSource();
            pane.getChildren().remove(basura);
            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");
            int i = 0;
            while (i < cables.size()) {
                System.out.println("busca cable");
                if (basura.equals(cables.get(i))) {
                    System.out.println("se elimino cable");
                    cables.remove(i);
                }
                i++;
            }
            while (i < switches.size()) {
                if (basura.equals(switches.get(i))) {
                    switches.remove(i);
                }
                i++;
            }
        }
    }
    //Vuelve a neutro cada bus
    public void revovinar() {
        manejarCirculos.revovinar();
    }
    // verifica el intercambio de cargas atravez de los cables
    public void verificar_cables() {
        setCables(manejarCirculos.getCables());
        manejarCirculos.verificar_cables();
    }
    // prender led atravez de las cargas
    public void prender_led() {
        manejarCirculos.prender_led();
    }
    // verifica los switch para el intercambio de cargas
    public void verificar_switch() {
        manejarCirculos.verificar_switch();
    }
}
