package com.example.protoboar;

import javafx.scene.paint.Color;
import java.util.ArrayList;
// Clase que se encarga de manejar las cargas
public class ManejarCarga {

    //Atributo
    private ArrayList<Protoboard> protos;
    //private  bus[][] alimentacion;

    //Constructor
    public ManejarCarga(ArrayList<Protoboard> protos) {
       this.protos = protos;
    }

    //Metodo que se encarga de volver a neutro(Negro) a todos los buses
    public void revovinar() {
        //Viaja por toda la matriz de los buses convirtiendolos en neutro(negro)
        int x=0;
        while (x<protos.size()) {

            int i = 0;
            while (i < 14) {
                int j = 0;
                while (j < 30) {
                    if (i >= 0) {
                        // System.out.println("Entroooooooo");
                        protos.get(x).alimentacion[i][j].setCarga(" ");  // Reiniciar la carga
                        protos.get(x).alimentacion[i][j].setFill(Color.BLACK);  // Reiniciar el color
                    }
                    j++;
                }
                i++;
            }

            x++;
        }

    }

    //Metodo que verifica si tiene corriente y lo dispersa
    public void corriente() {
        //Viajo por la matriz y verifico si este bus esta en positivo o negatico
        int x=0;
        while (x<protos.size()) {

            int i = 0;
            while (i < 14) {
                int j = 0;
                while (j < 30) {
                    //si se encuentra un bus con una carga este llamara a la funcion para distribuir en el sector correspondiente
                    // Horizontal o Vertical
                    if (protos.get(x).alimentacion[i][j].getCarga().equals("+")) {
                        marcar(i, j, "+",protos.get(x));
                    } else {
                        if (protos.get(x).alimentacion[i][j].getCarga().equals("-")) {
                            marcar(i, j, "-",protos.get(x));
                        }
                    }
                    j++;
                }
                i++;
            }

            x++;
        }

    }

    //Metodo que se encarga de distribuir la carga en el sector correspondiente
    public void marcar(int i, int j, String carga, Protoboard proto) {
        //Dependiendo donde se encutre el bus este se dispersara de forma difernete }

        if (i >= 0 && i <= 1) {
            // Filas 0 y 1
            // Forma Horizontal
            int col = 0;
            while (col < 30) {
                if (carga.equals("+")) {
                    proto.alimentacion[i][col].setFill(Color.RED);
                    proto.alimentacion[i][col].setCarga("+");
                } else if (carga.equals("-")) {
                    proto.alimentacion[i][col].setFill(Color.BLUE);
                    proto.alimentacion[i][col].setCarga("-");
                }
                col++;
            }
        } else if (i >= 2 && i <= 6) {
            // Filas 2-6
            //Forma vertical
            int fil = 2;
            while (fil <= 6) {
                if (carga.equals("+")) {
                    proto.alimentacion[fil][j].setFill(Color.RED);
                    proto.alimentacion[fil][j].setCarga("+");
                } else if (carga.equals("-")) {
                    proto.alimentacion[fil][j].setFill(Color.BLUE);
                    proto.alimentacion[fil][j].setCarga("-");
                }
                fil++;
            }
        } else if (i >= 7 && i <= 11) {
            // Filas 7-11
            //Forma vertical
            int fil = 11;
            while (fil >= 7) {
                if (carga.equals("+")) {
                    proto.alimentacion[fil][j].setFill(Color.RED);
                    proto.alimentacion[fil][j].setCarga("+");
                } else if (carga.equals("-")) {
                    proto.alimentacion[fil][j].setFill(Color.BLUE);
                    proto.alimentacion[fil][j].setCarga("-");
                }
                fil--;
            }
        } else {
            int col = 0;
            //Forma horizontal
            while (col < 30) {
                if (carga.equals("+")) {
                    proto.alimentacion[i][col].setFill(Color.RED);
                    proto.alimentacion[i][col].setCarga("+");
                } else if (carga.equals("-")) {
                    proto.alimentacion[i][col].setFill(Color.BLUE);
                    proto.alimentacion[i][col].setCarga("-");
                }
                col++;
            }
        }
    }

    //Metodos que verifica el ArrayList de los cables
    public void verificarCables(ArrayList<conection> cables) {
        int i = 0;
        //Viaja atraves del ArrayList
        while (i < cables.size()) {
            conection lin = cables.get(i);
            String ini = lin.getInicio().getCarga();
            String fin = lin.getFin().getCarga();
            //Si algun bus se encuentra con alguna carga este se encarga de verificar si el otro al que esta conectado
            // tiene carga o no, si este no tiene carga obtendra la carga del primero y si tiene alguno se verifica
            // si es el mismo o no
            if (!ini.equals(" ")) {
                if (fin.equals(" ")) {
                    lin.getFin().setCarga(ini);
                    corriente();
                } else {
                    if (!fin.equals(ini)) {
                        System.out.println("Exploto");
                    }
                }
            } else {
                if (!fin.equals(" ")) {
                    lin.getInicio().setCarga(fin);
                    corriente();
                }
            }
            i++;
        }
    }

    // Metodo para prender el led
    public void prenderLed(ArrayList<conection> cablesLed) {
        int i = 0;
        //viaja por los leds si los buses que estan conectados a este tienen cargas diferentes positivo y negativo
        // este se prender
        while (i < cablesLed.size()) {
            if (cablesLed.get(i).getInicio().getCarga().equals("+") && cablesLed.get(i).getFin().getCarga().equals("-") ||
                    cablesLed.get(i).getInicio().getCarga().equals("-") && cablesLed.get(i).getFin().getCarga().equals("+")) {
                cablesLed.get(i).get_foto().prender();
            } else {
                cablesLed.get(i).get_foto().apagar();
            }
            i++;
        }
    }

    // Metodo para verificar los switches
    public void verificarSwitches(ArrayList<Switch> switches) {
        int i = 0;
        //viaja atraves del ArrayList
        while (i < switches.size()) {
            if (switches.get(i).getCarga().equals("+")) {
                conection lin = switches.get(i).getCable();
                String ini = lin.getInicio().getCarga();
                String fin = lin.getFin().getCarga();
                //Si algun bus se encuentra con alguna carga este se encarga de verificar si el otro al que esta conectado
                // tiene carga o no, si este no tiene carga obtendra la carga del primero y si tiene alguno se verifica
                // si es el mismo o no
                if (!ini.equals(" ")) {
                    if (fin.equals(" ")) {
                        lin.getFin().setCarga(ini);
                        corriente();
                    } else {
                        if (!fin.equals(ini)) {
                            System.out.println("Exploto");
                        }
                    }
                } else {
                    if (!fin.equals(" ")) {
                        lin.getInicio().setCarga(fin);
                        corriente();
                    }
                }
            }
            i++;
        }
    }
}
