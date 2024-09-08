package com.example.protoboar;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;

public class Click {
    private static Pane pane;
    private static boolean eliminarProximaImagen = false;
    private static ArrayList<conection> cables;
    private static ArrayList<Switch> switches;
    private final ManejarCirculos manejarCirculos;

    public Click(Pane pane, bus[][] alimentacion, boolean ledClicked, boolean cableClicked, Bateria bateria) {
        Click.pane = pane;
        cables = new ArrayList<>();
        switches = new ArrayList<>();
        this.manejarCirculos = new ManejarCirculos(pane, alimentacion, ledClicked, cableClicked, bateria);
    }

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

    public void presionarCirculo(MouseEvent event) {
        manejarCirculos.presionarCirculo(event);
    }

    public void ClickEnBasurero() {
        System.out.println("Modo borrar");
        eliminarProximaImagen = true;
    }

    public static void eliminarElemento(MouseEvent event) {
        if (eliminarProximaImagen) {
            Object basura = event.getSource();
            pane.getChildren().remove(basura);
            eliminarProximaImagen = false;
            System.out.println("Se eliminó algo");
            int i = 0;
            while (i < cables.size()) {
                if (basura.equals(cables.get(i))) {
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

    public void revovinar() {
        manejarCirculos.revovinar();
    }

    public void verificar_cables() {
        manejarCirculos.verificar_cables();
    }

    public void prender_led() {
        manejarCirculos.prender_led();
    }

    public void verificar_switch() {
        manejarCirculos.verificar_switch();
    }
}
