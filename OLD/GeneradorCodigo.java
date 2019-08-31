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

public class GeneradorCodigo {
    BufferedWriter writer = null;
    private Stack<String> pila;
    Map<String, Simbolo> TS;


    public void generarCodigo(List<String> polaca, String nombre, Map<String, Simbolo> TS) throws IOException {
        nombre = nombre.replace(".txt", "");
        File codigoASM = new File("./" + nombre + ".asm");
        int i = 0;
        this.TS = TS;
        BufferedWriter writer = new BufferedWriter(new FileWriter(codigoASM));
        pila = new Stack<String>();
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
        writer.write("TITULO"+" DB \"Error de Compilacion \",0");
        writer.newLine();
        writer.write("RESTA_NEGATIVA"+" DB \"El resultado de una resta de USINTEGER da negativo\",0");
        writer.newLine();
        writer.write("OVERFLOW_MULT"+" DB \"Hubo un overflow en una multiplicacion\",0");
        writer.newLine();
        writer.write("DIVISOR_CERO"+" DB \"Se quizo hacer una division por 0\",0");
        writer.newLine();
        for (String k : TS.keySet()) {
            Simbolo aux = TS.get(k);
            if (aux.getTipoVariable() == 262) {
                writer.write(k + " DW ," + aux.getValor() + ",0");
            }
            if (aux.getTipoVariable() == 263) {
                writer.write(k + " DQ ," + aux.getValor() + ",0");
            }
            if (aux.getTipoVariable() == 264) {
                writer.write(k + " DB ,\"" + aux.getValor() + "\",0");
            }
            writer.newLine();
        }

        writer.write(".code");
        writer.newLine();
        writer.write("start:");
        writer.newLine();



        List<Integer> marcas = new ArrayList<>();
        for (int h = 0; h < polaca.size();h++) {
            if ((polaca.get(h).equals("BI"))||(polaca.get(h).equals("BF"))){
                marcas.add(Integer.valueOf(polaca.get(h-1)));
            }
        }
        String registro = "";
        int j = 0;
        for (String s : polaca) {
            if (marcas.contains(j)){
                writer.write("Label"+ j + ":");
                writer.newLine();
            }
            if (s.startsWith("_") || s.endsWith("_D") || s.endsWith("_ui") || s.startsWith("STR_")) {
                pila.push(s);
            }



            if (s.equals("print")){
                String aux = pila.pop();
                writer.write("invoke MessageBox, NULL, addr "+aux+", addr "+aux+", MB_OK");
                writer.newLine();
            }


                 if (s.equals(">")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JG Label" + polaca.get(j+1));
                    writer.newLine();
                }

                if (s.equals("<")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JL Label" + polaca.get(j+1));
                    writer.newLine();
                }

                if (s.equals("=")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JE Label" + polaca.get(j+1));
                    writer.newLine();
                }

                if (s.equals(">=")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JLE Label" + polaca.get(j+1));
                    writer.newLine();
                }

                if (s.equals("<=")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JGE Label" + polaca.get(j+1));
                    writer.newLine();
                }

                if (s.equals("!=")){
                    String aux = pila.pop();
                    String aux2 = pila.pop();
                    writer.write("CMP "+aux+","+aux2);
                    writer.newLine();
                    writer.write("JNE Label" + polaca.get(j+1));
                    writer.newLine();
                }


            if (s.equals("BI")){
                writer.write("JMP Label" + polaca.get(j-1));
                writer.newLine();
            }



            if (s == ":=") {
                String aux = pila.pop();
                String aux2 = pila.pop();

                registro = this.CualReg(aux2);

                writer.write("MOV "+ registro + "," + aux2);
                writer.newLine();
                writer.write("MOV " + aux + ", " + registro );
                writer.newLine();
            }
            if (s == "+") {
                String aux = pila.pop();
                if (TS.get(aux).getTipoVariable() == 262) {
                    String aux2 = pila.pop();
                    registro = "DW1";
                    writer.write("MOV " + registro +"," + aux2);
                    writer.newLine();
                    writer.write("ADD " + registro +"," + aux);
                    writer.newLine();
                    writer.write("MOV @aux" + i + "," + registro);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(262));
                    i++;
                }
                if (TS.get(aux).getTipoVariable() == 263) {
                    String aux2 = pila.pop();
                    writer.write("FLD " + aux2);
                    writer.newLine();
                    writer.write("FLD " + aux);
                    writer.newLine();
                    writer.write("FADD");
                    writer.newLine();
                    writer.write("FSTP @aux" + i);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(263));
                    i++;
                }

            }
            if (s == "-") {
                String aux = pila.pop();
                if (TS.get(aux).getTipoVariable() == 262) {
                    String aux2 = pila.pop();
                    registro = CualReg(aux2);
                    writer.write("MOV " + registro +"," + aux2);
                    writer.newLine();
                    writer.write("SUB " + registro +"," + aux);
                    writer.newLine();
                    writer.write("MOV @aux" + i + "," + registro);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(262));
                    i++;
                    writer.write(";Chequeo numero negativo");
                    writer.newLine();
                    writer.write("CMP "+ registro +", " + 0 );
                    writer.newLine();
                    writer.write("JS LabelNeg ");
                    writer.newLine();
                }
                if (TS.get(aux).getTipoVariable() == 263) {
                    String aux2 = pila.pop();
                    writer.write("FLD " + aux2);
                    writer.newLine();
                    writer.write("FLD " + aux);
                    writer.newLine();
                    writer.write("FSUB");
                    writer.newLine();
                    writer.write("FSTP @aux" + i);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(263));
                    i++;
                }
            }
            if (s == "*") {
                String aux = pila.pop();
                if (TS.get(aux).getTipoVariable() == 262) {
                    String aux2 = pila.pop();
                    registro = CualReg(aux2);
                    writer.write("MOV " + registro +"," + aux2);
                    writer.newLine();
                    writer.write("MUL  " + registro +"," + aux);
                    writer.newLine();
                    writer.write("MOV @aux" + i + "," + registro);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(262));
                    i++;
                }
                if (TS.get(aux).getTipoVariable() == 263) {
                    String aux2 = pila.pop();
                    writer.write("FLD " + aux2);
                    writer.newLine();
                    writer.write("FLD " + aux);
                    writer.newLine();
                    writer.write("FMUL");
                    writer.newLine();
                    writer.write("FSTP @aux" + i);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(263));
                    i++;
                }
                writer.write(";Chequeo OVERFLOW");
                writer.newLine();
                writer.write("JO LabelOF");
                writer.newLine();
            }
            if (s == "/") {
                String aux = pila.pop();
                if (TS.get(aux).getTipoVariable() == 262) {
                    String aux2 = pila.pop();
                    registro = CualReg(aux2);
                    writer.write("MOV  " + registro +"," + aux2);
                    writer.newLine();
                    writer.write("DIV  " + registro +"," + aux);
                    writer.newLine();
                    writer.write("MOV @aux" + i + "," + registro);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(262));
                    writer.write("CMP "+ registro +", " + 0);
                    writer.newLine();
                    writer.write("JE "+ "LabelDiv0");
                    writer.newLine();
                    i++;
                }
                if (TS.get(aux).getTipoVariable() == 263) {
                    String aux2 = pila.pop();
                    registro = CualReg(aux2);
                    writer.write("FLD " + aux2);
                    writer.newLine();
                    writer.write("FLD " + aux);
                    writer.newLine();
                    writer.write("FDIV");
                    writer.newLine();
                    writer.write("FSTP @aux" + i);
                    writer.newLine();
                    pila.push("@aux" + i);
                    TS.put("@aux" + i, new Simbolo(263));
                    writer.write("CMP "+ registro +", " + 0);
                    writer.newLine();
                    writer.write("JE "+ "LabelDiv0");
                    writer.newLine();
                    i++;
                }
                writer.flush();

            }
            j++;}

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
        writer.write("LabelNeg:");
        writer.newLine();
        writer.write("invoke MessageBox, NULL, addr RESTA_NEGATIVA, addr TITULO , MB_OK " );
        writer.newLine();
        writer.write("JMP EXIT");
        writer.newLine();
        writer.write("LabelOF:");
        writer.newLine();
        writer.write("invoke MessageBox, NULL, addr OVERFLOW_MULT, addr TITULO , MB_OK ");
        writer.newLine();
        writer.write("JMP EXIT");
        writer.newLine();
     writer.write("end start");
    writer.close();
        JOptionPane.showMessageDialog(null, "Codigo generado con exito en "+nombre+".asm");
    }

    public String CualReg(String S){
        int comp = TS.get(S).getTipoVariable();
        String registro = "";
        System.out.println(comp);
        if (comp == 262){
            registro = "DW1";
        }
        if (comp == 263){
            registro = "DQ1";
        }
        return registro;
    }

}

