package com.example.protoboar;

import javafx.scene.paint.Color;
import java.util.ArrayList;

public class ManejarCarga {
    private final bus[][] alimentacion;

    public ManejarCarga(bus[][] alimentacion) {
        this.alimentacion = alimentacion;
    }

    public void revovinar() {
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                if (i >= 0) {
                    alimentacion[i][j].setCarga(" ");  // Reiniciar la carga
                    alimentacion[i][j].setFill(Color.BLACK);  // Reiniciar el color
                }
                j++;
            }
            i++;
        }
    }

    public void corriente() {
        int i = 0;
        while (i < 14) {
            int j = 0;
            while (j < 30) {
                if (alimentacion[i][j].getCarga().equals("+")) {
                    marcar(i, j, "+");
                } else {
                    if (alimentacion[i][j].getCarga().equals("-")) {
                        marcar(i, j, "-");
                    }
                }
                j++;
            }
            i++;
        }
    }

    public void marcar(int i, int j, String carga) {
        if (i >= 0 && i <= 1) {
            // Filas 0 y 1
            int col = 0;
            while (col < 30) {
                if (carga.equals("+")) {
                    alimentacion[i][col].setFill(Color.RED);
                    alimentacion[i][col].setCarga("+");
                } else if (carga.equals("-")) {
                    alimentacion[i][col].setFill(Color.BLUE);
                    alimentacion[i][col].setCarga("-");
                }
                col++;
            }
        } else if (i >= 2 && i <= 6) {
            // Filas 2-6
            int fil = 2;
            while (fil <= 6) {
                if (carga.equals("+")) {
                    alimentacion[fil][j].setFill(Color.RED);
                    alimentacion[fil][j].setCarga("+");
                } else if (carga.equals("-")) {
                    alimentacion[fil][j].setFill(Color.BLUE);
                    alimentacion[fil][j].setCarga("-");
                }
                fil++;
            }
        } else if (i >= 7 && i <= 11) {
            // Filas 7-11
            int fil = 11;
            while (fil >= 7) {
                if (carga.equals("+")) {
                    alimentacion[fil][j].setFill(Color.RED);
                    alimentacion[fil][j].setCarga("+");
                } else if (carga.equals("-")) {
                    alimentacion[fil][j].setFill(Color.BLUE);
                    alimentacion[fil][j].setCarga("-");
                }
                fil--;
            }
        } else {
            int col = 0;
            while (col < 30) {
                if (carga.equals("+")) {
                    alimentacion[i][col].setFill(Color.RED);
                    alimentacion[i][col].setCarga("+");
                } else if (carga.equals("-")) {
                    alimentacion[i][col].setFill(Color.BLUE);
                    alimentacion[i][col].setCarga("-");
                }
                col++;
            }
        }
    }

    public void verificarCables(ArrayList<conection> cables) {
        int i = 0;
        while (i < cables.size()) {
            conection lin = cables.get(i);
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
            i++;
        }
    }

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
