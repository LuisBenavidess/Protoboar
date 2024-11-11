package com.example.protoboar;

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

    // Metodo que se encarga de volver a neutro a todos los buses
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
        if (proto.alimentacion[i][j].getFill() == Color.YELLOW) {
            return;  // Si el bus ya está quemado, no hacer nada
        }
        if (i <= 1) { // Filas 0 y 1 - Forma Horizontal
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
        while (i < cables.size()) { // Viaja a través del ArrayList
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
        if (fila == 0 || fila == 1 || fila == 12 || fila == 13) {
            for (int col = 0; col < proto.alimentacion[fila].length; col++) {
                quemarBus(fila, col,proto);
            }
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
    public void verificarResistencias(ArrayList<Resistencia> resistencias) {
        int i = 0;
        while (i < resistencias.size() && !resistencias.get(i).quemado) {
            conection cable = resistencias.get(i).getCable();
            String ini = cable.getInicio().getCarga();
            String fin = cable.getFin().getCarga();
            if (fin.equals("+")){
                resistencias.get(i).quemado = true;
                System.out.println("Se quemó una resistencia");
            }
            if (ini.equals("-")){
                resistencias.get(i).quemado = true;
                System.out.println("Se quemó una resistencia");
            }

            if (ini.equals("+") && resistencias.get(i).quemado) {
                if (fin.equals(" ")) {
                    cable.getFin().setCarga(ini);
                    corriente();
                }
            } else {
                if (fin.equals("-") && resistencias.get(i).quemado) {

                    if (ini.equals(" ")) {
                        cable.getInicio().setCarga(fin);
                        corriente();
                    }

                }
            }
            i++;
        }
    }

    public void verificar_chips(ArrayList<Chip> chips) {
        //Agregar al protoboard
        int i=0;
        while (i < chips.size()) {
            if(chips.get(i).terminado){
                System.out.println("entra");
                protos.get(chips.get(i).pos_proto).getChildren().add(chips.get(i));
                chips.get(i).terminado=false;
                chips.get(i).agregado=true;
            }
            i++;
        }

        // Iniciar procedimiento

        i=0;
        while (i < chips.size()) {
            if(chips.get(i).agregado){
                //System.out.println("cual es el chip");
                Chip chip = chips.get(i);
                //System.out.println(chip.getPats(0).getBus_conectado().getCarga());
                if(chip.getPats(0).getBus_conectado().getCarga().equals("+") && chip.getPats(13).getBus_conectado().getCarga().equals("-")){
                    //System.out.println("entra");
                    if(chip.getTipo().equals("NOT")){

                        chip_not(chip);
                    }else{
                        if(chip.getTipo().equals("AND")){
                            chip_and(chip);
                        }else{
                            chip_or(chip);
                        }
                    }
                }
            }
            i++;
        }

    }

    // Este metodo realiza la simple logica del chip not
    public void chip_not(Chip chip){

        int i=1;
        // Verifica cada pata
        while(i<chip.getPatas().size()){
            // Con esta condicion verifica si la pata esta conectada con carga negativa para saber si pasar carga positiva al otro
            if(chip.getPats(i).getBus_conectado().getCarga().equals("-") && i!=13){
                chip.getPats(i+1).getBus_conectado().setCarga("+");
                corriente();
            }
            if(chip.getPats(i).getBus_conectado().getCarga().equals("+") && i!=13){
                chip.getPats(i+1).getBus_conectado().setCarga("-");
                corriente();
            }
            i=i+2;
        }

    }

    // Este metodo realiza la simple logica del chip and
    public void chip_and(Chip chip){
        int i=1;
        // Verifica cada pata
        while(i<chip.getPatas().size()){
            //Condicion para saber si es and quiere decir que ambas patas deben estar en positivo
            if(i!=13){
                if(chip.getPats(i).getBus_conectado().getCarga().equals("+") && chip.getPats(i+1).getBus_conectado().getCarga().equals("+")){
                    chip.getPats(i+2).getBus_conectado().setCarga("+");
                    corriente();
                }else{
                    if((!chip.getPats(i).getBus_conectado().getCarga().equals(" ") || chip.getPats(i+1).getBus_conectado().getCarga().equals(" ")) &&
                            (chip.getPats(i).getBus_conectado().getCarga().equals("-") || chip.getPats(i+1).getBus_conectado().getCarga().equals("-"))){
                        chip.getPats(i+2).getBus_conectado().setCarga("-");
                        corriente();
                    }
                }

            }


            i=i+3;
        }
    }

    //Este metodo realiza la simple logica del chip or
    public void chip_or(Chip chip){
        int i=1;

        while(i<chip.getPatas().size()){
            if(i!=13){
                if(!chip.getPats(i).getBus_conectado().getCarga().equals(" ") && !chip.getPats(i+1).getBus_conectado().getCarga().equals(" ")){

                    if(chip.getPats(i).getBus_conectado().getCarga().equals("+") || chip.getPats(i+1).getBus_conectado().getCarga().equals("+")){
                        chip.getPats(i+2).getBus_conectado().setCarga("+");
                    }else{
                        chip.getPats(i+2).getBus_conectado().setCarga("-");
                    }
                    corriente();
                }
            }

            i=i+2;
        }
    }

    public void verificar_sw3x3(ArrayList<Switch3x3> sw) {
        int i = 0;
        while (i < sw.size()) {
            Switch3x3 switchActual = sw.get(i);
            if (switchActual.terminado) {
                protos.get(switchActual.pos_proto).getChildren().add(switchActual);
                switchActual.terminado = false;
                switchActual.agregado = true;
            }
            i++;
        }
        i = 0;
        while (i < sw.size()) {  // recorre todos los switches
            Switch3x3 switchActual = sw.get(i);
            if (switchActual.getEncendido()) {
                int j = 0;
                while (j < switchActual.getPatas().size()) {
                    if (switchActual.agregado) {
                        bus Bus = switchActual.getPats(j).getBus_conectado();
                        String cargaActual = Bus.getCarga();
                        if (Bus.getFill() == Color.YELLOW) {
                            j++; // se salta el bus quemado
                            continue;
                        }
                        if (switchActual.getTipoCarga() == null && !cargaActual.equals(" ") && !cargaActual.equals("X")) {
                            switchActual.setTipoCarga(cargaActual);  // asignar polaridad
                        }
                        if (cargaActual.equals(switchActual.getTipoCarga()) || cargaActual.equals(" ")) {
                            switchActual.getPats(0).getBus_conectado().setCarga(cargaActual);
                            switchActual.getPats(1).getBus_conectado().setCarga(cargaActual);
                            switchActual.getPats(2).getBus_conectado().setCarga(cargaActual);
                            switchActual.getPats(3).getBus_conectado().setCarga(cargaActual);
                            corriente();
                        } else {
                            quemarColumna(Bus);  // si la carga no coincide se quema la columna
                        }
                    }
                    j++;
                }
            } else { // switch está apagado
                int j = 0;
                while (j < switchActual.getPatas().size()) {
                    if (switchActual.agregado) {
                        bus Bus = switchActual.getPats(j).getBus_conectado();
                        String cargaActual = Bus.getCarga();
                        if (Bus.getFill() == Color.YELLOW) {
                            j++; // se salta el bus quemado
                            continue;
                        }
                        if (cargaActual.equals(switchActual.getTipoCarga()) || !Bus.getCarga().equals(" ") && !Bus.getCarga().equals("X")) {
                            if(Bus.fila == sw.get(i).getPats(0).getBus_conectado().fila){
                                sw.get(i).getPats(0).getBus_conectado().setCarga(Bus.getCarga());
                            }
                            if(Bus.fila == sw.get(i).getPats(1).getBus_conectado().fila){
                                sw.get(i).getPats(1).getBus_conectado().setCarga(Bus.getCarga());
                            }
                            if(Bus.fila == sw.get(i).getPats(2).getBus_conectado().fila){
                                sw.get(i).getPats(2).getBus_conectado().setCarga(Bus.getCarga());
                            }
                            if(Bus.fila == sw.get(i).getPats(3).getBus_conectado().fila){
                                sw.get(i).getPats(3).getBus_conectado().setCarga(Bus.getCarga());
                            }
                            corriente();
                        }
                    }
                    j++;
                }
            }
            i++;
        }
    }

    public void verificar_sw8x3(ArrayList<Switch8x3> sw) {
        int i = 0;
        while (i < sw.size()) {
            Switch8x3 switchActual = sw.get(i);
            if (switchActual.terminado) {
                protos.get(switchActual.pos_proto).getChildren().add(switchActual);
                switchActual.terminado = false;
                switchActual.agregado = true;
            }
            i++;
        }
        //Verifica los interruptores y actualiza cargas
        for(i = 0; i < sw.size(); i++) {
            Switch8x3 switchActual = sw.get(i);
            int j = 0;
            int j2 = 8;

            while (j < switchActual.getInterruptores().size()) {
                Interruptor interruptor = switchActual.getInterruptores().get(j);

                if (interruptor.getEncendido() && interruptor.getQuemado()==false ) {
                    if (switchActual.agregado) {
                        bus Bus = switchActual.getPats(j).getBus_conectado();
                        bus Bus2 = switchActual.getPats(j2).getBus_conectado();

                        String cargaActual = Bus.getCarga();
                        String cargaActual2 = Bus2.getCarga();

                        if (Bus.getFill() == Color.YELLOW) {
                            j++; // se salta el bus quemado
                            continue;
                        }

                        if (!cargaActual.equals(" ") && cargaActual2.equals(" ")) {
                            sw.get(i).getPats(j2).getBus_conectado().setCarga(cargaActual);
                            corriente();
                        } else if (!cargaActual2.equals(" ") && cargaActual.equals(" ")) {
                            sw.get(i).getPats(j).getBus_conectado().setCarga(cargaActual2);
                            corriente();
                        }
                    }

                }
                j++;
                j2++;

            }
        }
    }

    // Metodo que quema una sección de la columna según la fila del bus
    public void quemarColumna(bus fin) {
        int x = 0;
        while (x < protos.size()) {
            if (protos.get(x).getChildren().contains(fin)) {
                int columnaDestino = getColumna(fin, protos.get(x));
                if (columnaDestino == -1) return; // Si no se encuentra la columna, salir
                int fila = getFila(fin, protos.get(x));
                if (fila == -1) return; // Si no se encuentra la fila, salir
                if (fila >= 2 && fila <= 6) {
                    for (int f = 2; f <= 6; f++) {
                        quemarBus(f, columnaDestino, protos.get(x));
                    }
                } else if (fila >= 7 && fila <= 11) {
                    for (int f = 7; f <= 11; f++) {
                        quemarBus(f, columnaDestino, protos.get(x));
                    }
                } else {
                    quemarFila(fila, protos.get(x)); // Si la fila es 0, 1, 13 o 14, quemar la fila completa
                }
            }
            x++;
        }
    }
}