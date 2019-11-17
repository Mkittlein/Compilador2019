package Kittlein_Gallo;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class GeneradorDeCodigo {
    BufferedWriter writer = null;
    private Stack<String> pila;
    Map<String, Simbolo> TS;




    public void generarCodigo(List<String> polaca, String nombre, Map<String, Simbolo> TS) {
        nombre = nombre.replace(".txt", "");
        File codigoASM = new File("./" + nombre + ".asm");
        int i = 0;
        this.TS = TS;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(codigoASM));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pila = new Stack<String>();
        try {
            writer.write(".386");

        writer.newLine();
        writer.write(".model flat, stdcall");
        writer.newLine();
        writer.write("option casemap :none");
        writer.newLine();
        writer.write("include \\masm32\\include\\windows.inc");
        writer.newLine();
        writer.write("include \\masm32\\include\\kernel32.inc");
        writer.newLine();
        writer.write("include \\masm32\\include\\user32.inc");
        writer.newLine();
        writer.write("includelib \\masm32\\lib\\kernel32.lib");
        writer.newLine();
        writer.write("includelib \\masm32\\lib\\user32.lib");
        writer.newLine();
        writer.write(".data");
        writer.newLine();
        writer.write("TITULO"+" DB \"ERROR \",0");
        writer.newLine();
        writer.write("LIMITE_ARREGLO"+" DB \"Se quiso acceder a una celda fuera del rango del arreglo\",0");
        writer.newLine();
        writer.write("OVERFLOW_MULT"+" DB \"Hubo un overflow en una multiplicacion\",0");
        writer.newLine();
        writer.write("DIVISOR_CERO"+" DB \"Se quizo hacer una division por 0\",0");
        writer.newLine();
            for (String k : TS.keySet()) { //DECLARA LAS VARIABLES DE LA TABLA DE SIMBOLOS
                Simbolo aux = TS.get(k);
                if (aux.getTipo() == 'I' && aux.getUso() != 'C') {
                    writer.write("_" + k + " dw ");
                    writer.flush();
                    writer.write(aux.getStringASM());
                    writer.newLine();
                }
                if (aux.getTipo() == 'F' && aux.getUso() != 'C') {
                    writer.write("_" + k + " dd  ");
                    writer.flush();
                    writer.write(aux.getStringASM());
                    writer.newLine();
                }
                if (aux.getTipo() =='S') {
                    writer.write("STR_" + k + " db ,\"" + k + "\",0");
                    writer.flush();
                    writer.newLine();
                }
                if (aux.getUso()=='A'){
                    writer.write("_"+k+"_MAX dw "+aux.getSize());
                    writer.newLine();
                }
                writer.flush();
            }
            writer.write(".code");
            writer.newLine();
            writer.write("_start:");
            writer.newLine();





            //ACA VA EL CODIGO





            writer.write("JMP LabelArr");
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("EXIT:");
            writer.newLine();
            writer.write("invoke ExitProcess, 0");
            writer.newLine();
            writer.write("LabelDiv0:");
            writer.newLine();
            writer.write("invoke MessageBox, NULL, addr DIVISOR_CERO, addr TITULO , MB_OK ");
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("LabelArr:");
            writer.newLine();
            writer.write("invoke MessageBox, NULL, addr LIMITE_ARREGLO, addr TITULO , MB_OK " );
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("LabelOF:");
            writer.newLine();
            writer.write("invoke MessageBox, NULL, addr OVERFLOW_MULT, addr TITULO , MB_OK ");
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("end _start");



        } catch (IOException e) {}
        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
