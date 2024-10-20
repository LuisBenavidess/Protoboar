package com.example.protoboar;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.ArrayList;

// Clase que se encarga de manejar las cargas
public class ManejarCarga {

    //Atributo
    private final ArrayList<Protoboard> protos;

    //Constructor
    public ManejarCarga(ArrayList<Protoboard> protos) {
       this.protos = protos;
    }

    // Metodo que se encarga de volver a neutro (Negro) a todos los buses
    public void revovinar() {
        //Viaja por toda la matriz de los buses convirtiendolos en neutro(negro)
        int x=0;
        while (x<protos.size()) {
            int i = 0;
            while (i < 14) {
                int j = 0;
                while (j < 30) {
                    if (i >= 0) {
                        if (protos.get(x).alimentacion[i][j].getFill() != Color.YELLOW) {
                            protos.get(x).alimentacion[i][j].setCarga(" ");  // Reiniciar la carga
                            protos.get(x).alimentacion[i][j].setFill(Color.BLACK);  // Reiniciar el color
                            protos.get(x).alimentacion[i][j].setVoltaje(null);
                        }
                    }
                    j++;
                }
                i++;
            }
            x++;
        }
    }

    // Metodo que verifica si tiene corriente y lo dispersa
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

    // Metodo que se encarga de distribuir la carga en el sector correspondiente
    public void marcar(int i, int j, String carga,Protoboard proto) {
        // Si el bus ya está quemado, no hacer nada
        if (proto.alimentacion[i][j].getFill() == Color.YELLOW) {
            return;
        }
        if (i <= 1) {
            // Filas 0 y 1 - Forma Horizontal
            int col = 0;
            while (col < 30) {
                if (carga.equals("+")) {
                    proto.alimentacion[i][col].setFill(Color.RED);
                    proto.alimentacion[i][col].setCarga("+");
                    proto.alimentacion[i][col].setVoltaje(9.0);
                } else if (carga.equals("-")) {
                    proto.alimentacion[i][col].setFill(Color.BLUE);
                    proto.alimentacion[i][col].setCarga("-");
                    proto.alimentacion[i][col].setVoltaje(0.0);
                }
                col++;
            }
        } else if (i <= 6) {
            int fil = 2;
            while (fil <= 6) {
                if (carga.equals("+")) {
                    proto.alimentacion[fil][j].setFill(Color.RED);
                    proto.alimentacion[fil][j].setCarga("+");
                    proto.alimentacion[fil][j].setVoltaje(9.0);
                } else if (carga.equals("-")) {
                    proto.alimentacion[fil][j].setFill(Color.BLUE);
                    proto.alimentacion[fil][j].setCarga("-");
                    proto.alimentacion[fil][j].setVoltaje(0.0);
                }
                fil++;
            }
        } else if (i <= 11) {
            // Filas 7-11 - Forma vertical
            int fil = 11;
            while (fil >= 7) {
                if (carga.equals("+")) {
                    proto.alimentacion[fil][j].setFill(Color.RED);
                    proto.alimentacion[fil][j].setCarga("+");
                    proto.alimentacion[fil][j].setVoltaje(9.0);
                } else if (carga.equals("-")) {
                    proto.alimentacion[fil][j].setFill(Color.BLUE);
                    proto.alimentacion[fil][j].setCarga("-");
                    proto.alimentacion[fil][j].setVoltaje(0.0);
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
                    proto.alimentacion[i][col].setVoltaje(9.0);
                } else if (carga.equals("-")) {
                    proto.alimentacion[i][col].setFill(Color.BLUE);
                    proto.alimentacion[i][col].setCarga("-");
                    proto.alimentacion[i][col].setVoltaje(0.0);
                }
                col++;
            }
        }
    }

    // Metodo que verifica el ArrayList de los cables
    public void verificarCables(ArrayList<conection> cables) {
        int i = 0;
        //Viaja atraves del ArrayList
        while (i < cables.size()) {
            conection lin = cables.get(i);
            lin.toFront();
            String ini = lin.getInicio().getCarga();
            String fin = lin.getFin().getCarga();
            if (lin.getInicio().getFill() == Color.YELLOW || lin.getFin().getFill() == Color.YELLOW) {
                i++;
                continue;
            }
            if (!ini.equals(" ")) {
                if (fin.equals(" ")) {
                    lin.getFin().setCarga(ini);
                    corriente();
                } else {
                    if (!fin.equals(ini)) {
                        quemarColumna(lin.getFin());
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

    // Metodo que quema una fila completa según la fila del bus
    public void quemarFila(int fila,Protoboard proto) {
        // Verifica si la fila está en el rango que debe ser quemado
        if (fila == 0 || fila == 1 || fila == 13 || fila == 14) {
            for (int col = 0; col < proto.alimentacion[fila].length; col++) {
                quemarBus(fila, col,proto);

            }
        }
    }

    // Metodo que quema una sección de la columna según la fila del bus
    public void quemarColumna(bus fin) {
        int x=0;
        while(x<protos.size()) {
            if(protos.get(x).getChildren().contains(fin)) {

                int columnaAQuemar = getColumna(fin,protos.get(x));
                if (columnaAQuemar == -1) return; // Si no se encuentra la columna, salir

                int fila = getFila(fin,protos.get(x));
                if (fila == -1) return; // Si no se encuentra la fila, salir

                // Quemar la columna específica en las secciones adecuadas
                if (fila >= 2 && fila <= 6) {
                    for (int f = 2; f <= 6; f++) {
                        quemarBus(f, columnaAQuemar,protos.get(x));
                    }
                } else if (fila >= 7 && fila <= 11) {
                    for (int f = 7; f <= 11; f++) {
                        quemarBus(f, columnaAQuemar,protos.get(x));
                    }
                } else {
                    // Si la fila es 0, 1, 13 o 14, quemar la fila completa
                    quemarFila(fila,protos.get(x));
                }

            }
            x++;
        }

    }

    // Metodo que obtiene la fila de un bus
    private int getFila(bus circulo,Protoboard proto) {
        for (int i = 0; i < proto.alimentacion.length; i++) {
            for (int j = 0; j < proto.alimentacion[i].length; j++) {
                if (proto.alimentacion[i][j] == circulo) {
                    return i; // Devuelve la fila si coincide con el bus
                }
            }
        }
        return -1; // No se encontró la fila
    }

    // Metodo que obtiene la columna de un bus
    private int getColumna(bus circulo,Protoboard proto) {
        for (int i = 0; i < proto.alimentacion.length; i++) {
            for (int j = 0; j < proto.alimentacion[i].length; j++) {
                if (proto.alimentacion[i][j] == circulo) {
                    return j; // Devuelve la columna si coincide con el bus
                }
            }
        }
        return -1; // No se encontró la columna
    }

    // Metodo que quema un bus específico
    private void quemarBus(int fila, int columna,Protoboard proto) {
        bus circulo = proto.alimentacion[fila][columna];
        if (circulo != null && circulo.getFill() != Color.YELLOW) {
            circulo.setFill(Color.YELLOW); // Cambia el color a amarillo para indicar que está quemado
            circulo.setCarga("X"); // Indica que el bus está quemado e inutilizable
            circulo.setVoltaje(null);
        }
    }

    // Metodo para prender el led
    public void prenderLed(ArrayList<Led> Leds) {
        int i = 0;
        while (i < Leds.size()) {
            if (Leds.get(i).getCable_rojo().getInicio().getCarga().equals("+") && Leds.get(i).getCable_azul().getFin().getCarga().equals("-")) {
                Leds.get(i).prender();
            } else if (Leds.get(i).getCable_azul().getFin().getCarga().equals("+") || Leds.get(i).getCable_rojo().getInicio().getCarga().equals("-")) {
                Leds.get(i).setQuemado(true);
            } else {
                Leds.get(i).apagar();
            }
            i++;
        }
    }

    // Metodo para verificar los switches
    public void verificarSwitches(ArrayList<Switch> switches) {
        int i = 0;
        while (i < switches.size()) {
            if (switches.get(i).getCarga().equals("+")) {
                conection lin = switches.get(i).getCable();
                String ini = lin.getInicio().getCarga();
                String fin = lin.getFin().getCarga();
                if (!ini.equals(" ")) {
                    if (fin.equals(" ")) {
                        lin.getFin().setCarga(ini);
                        corriente();
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

    // Metodo para verificar los switches
    public void verificarResistencias(ArrayList<Resistencia> resistencias) {
        int i = 0;
        while (i < resistencias.size()) {
            conection lin = resistencias.get(i).getCable();
            String ini = lin.getInicio().getCarga();
            String fin = lin.getFin().getCarga();
            if (!ini.equals(" ")) {
                if (fin.equals(" ")) {
                    lin.getFin().setCarga(ini);
                    corriente();
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

    public void verificar_chips(ArrayList<Chip> chips) {
        int i=0;
        while (i < chips.size()) {
            if(chips.get(i).terminado){
                protos.get(chips.get(i).pos_proto).getChildren().add(chips.get(i));
                chips.get(i).terminado=false;
            }
            i++;
        }

    }

    public void verificar_sw3x3(ArrayList<Switch3x3> sw) {

        int i=0;
        while (i < sw.size()) {
            if(sw.get(i).terminado){
                protos.get(sw.get(i).pos_proto).getChildren().add(sw.get(i));
                sw.get(i).terminado=false;
            }
            i++;
        }

        i=0;
        // Condicion para que recorra todos los switches
        while (i < sw.size()) {

            //Condicion de prendido
            if(sw.get(i).getCarga().equals("+")){

                int j = 0;
                while(j < sw.get(i).getPatas().size()){
                    bus Bus = sw.get(i).getPats(j).getBus_conectado();
                    if (!Bus.getCarga().equals(" ")) {

                        sw.get(i).getPats(0).getBus_conectado().setCarga(Bus.getCarga());
                        sw.get(i).getPats(1).getBus_conectado().setCarga(Bus.getCarga());
                        sw.get(i).getPats(2).getBus_conectado().setCarga(Bus.getCarga());
                        sw.get(i).getPats(3).getBus_conectado().setCarga(Bus.getCarga());


                        corriente();

                    }

                    j++;
                }

            }
            i++;

        }


    }


}
