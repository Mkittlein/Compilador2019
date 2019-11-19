package Kittlein_Gallo;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneradorDeCodigo {
    BufferedWriter writer = null;
    private Stack<String> pila;
    Map<String, Simbolo> TS;
    private int AuxFloat=0;
    private int AuxInt=0;

    public void addVarAux(List<String> polaca,  Map<String, Simbolo> TS){ //FALTA AGREGAR A LOS ACCESOS A ARREGLOS COMO TERMINOS
        int contF=0,contI=0;
        char Tipo='A';
        String t ="",p="";
        boolean nuevaSentencia=true;
        for(int i=0; i<polaca.size();i++){
            p=t;
            t=polaca.get(i);
            if(TS.containsKey(t)&&nuevaSentencia){
                Tipo=TS.get(t).getTipo();
                nuevaSentencia=false;
            }
            if (t=="+"||t=="-"||t=="*"||t=="/"){
                if(Tipo=='I')
                    contI++;
                else
                    contF++;
            }
            AuxFloat=Math.max(AuxFloat,contF);
            AuxInt=Math.max(AuxInt,contI);
            if(t==":="){
                nuevaSentencia=true;
                contI=0;
                contF=0;
            }
        }
        while(AuxInt!=0){
            TS.put("@AUXI"+AuxInt,new Simbolo('I','V'));
            AuxInt--;
        }
        while(AuxFloat!=0){
            TS.put("@AUXF"+AuxFloat,new Simbolo('F','V'));
            AuxFloat--;
        }
        TS.put("@AuxARRI",new Simbolo('I','V'));
        TS.put("@AuxARRF",new Simbolo('F','V'));
        if (polaca.contains("foreach"))
            TS.put("@AuxFOREACH",new Simbolo('I','V'));
    }

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
            addVarAux(polaca,TS);
            for (String k : TS.keySet()) { //DECLARA LAS VARIABLES DE LA TABLA DE SIMBOLOS
                Simbolo aux = TS.get(k);
                if (aux.getTipo() == 'I' && aux.getUso() != 'C') {
                    if(k.contains("@"))
                        writer.write( k + " dw ");
                    else
                        writer.write("_" + k + " dw ");
                    writer.flush();
                    writer.write(aux.getStringASM());
                    writer.newLine();
                }
                if (aux.getTipo() == 'F' && aux.getUso() != 'C') {
                    if(k.contains("@"))
                        writer.write( k + " dd  ");
                    else
                        writer.write("_" + k + " dd  ");
                    writer.flush();
                    writer.write(aux.getStringASM());
                    writer.newLine();
                }
                if (aux.getTipo() =='S') {
                    writer.write("_" + k + " db ,\"" + k + "\",0");
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
            List<Integer> saltos = new ArrayList<>();
            for (int h = 0; h < polaca.size();h++) {
                if ((polaca.get(h).equals("BI")) || (polaca.get(h).equals("BF"))) {
                    saltos.add(Integer.valueOf(polaca.get(h - 1)));
                }
            }
            AuxFloat=1;
            AuxInt=1;
            for (String s : polaca) {
                if (TS.containsKey(s)){
                    if (TS.get(s).getUso()=='V')
                        pila.push("_"+s);
                    else  pila.push(s);
                }else{
                    switch(s){
                        case "*" :
                            this.writeMult(pila,writer);
                            System.out.println("MUL");
                            break;
                        case "+" :
                            this.writeSum(pila,writer);
                            System.out.println("SUM");
                            break;
                        case "-" :
                            this.writeSub(pila,writer);
                            System.out.println("SUB");
                            break;
                        case "/" :
                            this.writeDiv(pila,writer);
                            System.out.println("DIV");
                            break;
                        case ":=" :
                            this.writeAsig(pila,writer);
                            System.out.println("ASIG");
                            break;
                    }
                    writer.newLine();
                }
                System.out.println(pila);
                writer.flush();



            }


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

    private void writeMult(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MUL eax, "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeDiv(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV bx, "+pila.peek());
        writer.newLine();
        writer.write("CMP bx, " + 0);
        writer.newLine();
        writer.write("JE "+ "LabelDiv0");
        writer.newLine();
        writer.write("DIV ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeAsig(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV "+aux+", ax");
        writer.newLine();
        AuxInt=1;
        AuxFloat=1;
    }

    private void writeSum(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("ADD ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeSub(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("SUB ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }
}
