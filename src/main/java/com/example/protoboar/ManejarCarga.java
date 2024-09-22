package com.example.protoboar;

import javafx.scene.paint.Color;
import java.util.ArrayList;

// Clase que se encarga de manejar las cargas
public class ManejarCarga {

    // Atributo
    private final bus[][] alimentacion;

    // Constructor
    public ManejarCarga(bus[][] alimentacion) {
        this.alimentacion = alimentacion;
    }

    // Método que se encarga de volver a neutro (Negro) a todos los buses
    public void revovinar() {
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                if (alimentacion[i][j].getFill() != Color.YELLOW) {
                    alimentacion[i][j].setCarga(" ");  // Reiniciar la carga
                    alimentacion[i][j].setFill(Color.BLACK);  // Reiniciar el color
                }
                j++;
            }
            i++;
        }
    }

    // Método que verifica si tiene corriente y lo dispersa
    public void corriente() {
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                // Si se encuentra un bus con una carga, llama a la función para distribuir en el sector correspondiente
                if (alimentacion[i][j].getCarga().equals("+")) {
                    marcar(i, j, "+");
                } else if (alimentacion[i][j].getCarga().equals("-")) {
                    marcar(i, j, "-");
                }
                j++;
            }
            i++;
        }
    }

    // Método que se encarga de distribuir la carga en el sector correspondiente
    public void marcar(int i, int j, String carga) {
        // Si el bus ya está quemado, no hacer nada
        if (alimentacion[i][j].getFill() == Color.YELLOW) {
            return;
        }

        // Lógica para marcar la carga según la posición
        if (i <= 1) {
            int col = 0;
            while (col < 30) {
                if (alimentacion[i][col].getFill() != Color.YELLOW) {
                    if (carga.equals("+")) {
                        alimentacion[i][col].setFill(Color.RED);
                        alimentacion[i][col].setCarga("+");
                    } else if (carga.equals("-")) {
                        alimentacion[i][col].setFill(Color.BLUE);
                        alimentacion[i][col].setCarga("-");
                    }
                }
                col++;
            }
        } else if (i <= 6) {
            int fil = 2;
            while (fil <= 6) {
                if (alimentacion[fil][j].getFill() != Color.YELLOW) {
                    if (carga.equals("+")) {
                        alimentacion[fil][j].setFill(Color.RED);
                        alimentacion[fil][j].setCarga("+");
                    } else if (carga.equals("-")) {
                        alimentacion[fil][j].setFill(Color.BLUE);
                        alimentacion[fil][j].setCarga("-");
                    }
                }
                fil++;
            }
        } else if (i <= 11) {
            int fil = 11;
            while (fil >= 7) {
                if (alimentacion[fil][j].getFill() != Color.YELLOW) {
                    if (carga.equals("+")) {
                        alimentacion[fil][j].setFill(Color.RED);
                        alimentacion[fil][j].setCarga("+");
                    } else if (carga.equals("-")) {
                        alimentacion[fil][j].setFill(Color.BLUE);
                        alimentacion[fil][j].setCarga("-");
                    }
                }
                fil--;
            }
        } else {
            int col = 0;
            while (col < 30) {
                if (alimentacion[i][col].getFill() != Color.YELLOW) {
                    if (carga.equals("+")) {
                        alimentacion[i][col].setFill(Color.RED);
                        alimentacion[i][col].setCarga("+");
                    } else if (carga.equals("-")) {
                        alimentacion[i][col].setFill(Color.BLUE);
                        alimentacion[i][col].setCarga("-");
                    }
                }
                col++;
            }
        }
    }

    // Método que verifica el ArrayList de los cables
    public void verificarCables(ArrayList<conection> cables) {
        int i = 0;
        while (i < cables.size()) {
            conection lin = cables.get(i);
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

    // Método que quema una fila completa según la fila del bus
    public void quemarFila(int fila) {
        // Verifica si la fila está en el rango que debe ser quemado
        if (fila == 0 || fila == 1 || fila == 13 || fila == 14) {
            for (int col = 0; col < alimentacion[fila].length; col++) {
                quemarBus(fila, col);
            }
        }
    }

    // Método que quema una sección de la columna según la fila del bus
    public void quemarColumna(bus fin) {
        int columnaAQuemar = getColumna(fin);
        if (columnaAQuemar == -1) return; // Si no se encuentra la columna, salir

        int fila = getFila(fin);
        if (fila == -1) return; // Si no se encuentra la fila, salir

        // Quemar la columna específica en las secciones adecuadas
        if (fila >= 2 && fila <= 6) {
            for (int f = 2; f <= 6; f++) {
                quemarBus(f, columnaAQuemar);
            }
        } else if (fila >= 7 && fila <= 11) {
            for (int f = 7; f <= 11; f++) {
                quemarBus(f, columnaAQuemar);
            }
        } else {
            // Si la fila es 0, 1, 13 o 14, quemar la fila completa
            quemarFila(fila);
        }
    }

    // Método que obtiene la fila de un bus
    private int getFila(bus circulo) {
        for (int i = 0; i < alimentacion.length; i++) {
            for (int j = 0; j < alimentacion[i].length; j++) {
                if (alimentacion[i][j] == circulo) {
                    return i; // Devuelve la fila si coincide con el bus
                }
            }
        }
        return -1; // No se encontró la fila
    }

    // Método que obtiene la columna de un bus
    private int getColumna(bus circulo) {
        for (int i = 0; i < alimentacion.length; i++) {
            for (int j = 0; j < alimentacion[i].length; j++) {
                if (alimentacion[i][j] == circulo) {
                    return j; // Devuelve la columna si coincide con el bus
                }
            }
        }
        return -1; // No se encontró la columna
    }

    // Método que quema un bus específico
    private void quemarBus(int fila, int columna) {
        bus circulo = alimentacion[fila][columna];
        if (circulo != null && circulo.getFill() != Color.YELLOW) {
            circulo.setFill(Color.YELLOW); // Cambia el color a amarillo para indicar que está quemado
            circulo.setCarga("X"); // Indica que el bus está quemado e inutilizable
        }
    }

    // Método para prender el led
    public void prenderLed(ArrayList<conection> cablesLed) {
        int i = 0;
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

    // Método para verificar los switches
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
