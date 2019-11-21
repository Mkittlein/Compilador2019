package Kittlein_Gallo;

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
    private char T;

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
            if (t=="+"||t=="-"||t=="*"||t=="/"||t=="[]"){
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
            writer.write("@TITULO"+" DB \"ERROR \",0");
            writer.newLine();
            writer.write("@LIMITE_ARREGLO"+" DB \"Se quiso acceder a una celda fuera del rango del arreglo\",0");
            writer.newLine();
            writer.write("@OVERFLOW_MULT"+" DB \"Hubo un overflow en una multiplicacion\",0");
            writer.newLine();
            writer.write("@DIVISOR_CERO"+" DB \"Se quizo hacer una division por 0\",0");
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
                if (aux.getTipo() == 'F') {
                    if(k.contains("@"))
                        writer.write( k + " dd  ");
                    else {
                        if (aux.getUso()=='C')
                            writer.write("@" + k.replace('.','p') + " dd  ");
                        else
                            writer.write("_" + k + " dd  ");
                    }
                    writer.flush();
                    writer.write(aux.getStringASM());
                    writer.newLine();
                }
                if (aux.getTipo() =='S') {
                    writer.write("@" + k.replace(' ' ,'_') + " db \"" + k + "\",0");
                    writer.flush();
                    writer.newLine();
                }

                if (aux.getUso()=='A'){
                    writer.write("@"+k+"_MAX dw "+aux.getSize());
                    writer.newLine();
                }
                writer.flush();
            }
            writer.write(".code");
            writer.newLine();
            writer.write("_start:");
            writer.newLine();
            List<String> saltos = new ArrayList<>();
            for (int h = 0; h < polaca.size();h++) {
                if ((polaca.get(h).equals("BI")) || (polaca.get(h).equals("BF"))) {
                    saltos.add((polaca.get(h - 1)));
                }
            }
            System.out.println("ESTOS SON LOS SALTOS: "+saltos);
            AuxFloat=1;
            AuxInt=1;
            int j=0;
            for (String s : polaca) {
                System.out.println("PILA: "+pila+" TIPO: "+T);
                if (saltos.contains(Integer.valueOf(j).toString())){
                    writer.newLine();
                    writer.write("Label"+ j + ":");
                    writer.newLine();
                }
                if (TS.containsKey(s)){

                    if (pila.empty())
                        T=TS.get(s).getTipo();
                    if (TS.get(s).getUso()=='V')
                        pila.push("_"+s);
                    else if (TS.get(s).getUso()=='C' &&TS.get(s).getTipo()=='F')
                        pila.push("@"+s.replace('.','p'));
                    else  pila.push(s);
                }else{
                    if (T == 'I') {
                        switch (s) {
                            case "*":
                                this.writeMulI(pila, writer);
                                System.out.println("MUL");
                                break;
                            case "+":
                                this.writeSumI(pila, writer);
                                System.out.println("SUM");
                                break;
                            case "-":
                                this.writeSubI(pila, writer);
                                System.out.println("SUB");
                                break;
                            case "/":
                                this.writeDivI(pila, writer);
                                System.out.println("DIV");
                                break;
                            case ":=":
                                this.writeAsigI(pila, writer);
                                System.out.println("ASIG");
                                break;
                        }
                    }else {
                        switch (s) {
                            case "*":
                                this.writeMulF(pila, writer);
                                System.out.println("MUL");
                                break;
                            case "+":
                                this.writeSumF(pila, writer);
                                System.out.println("SUM");
                                break;
                            case "-":
                                this.writeSubF(pila, writer);
                                System.out.println("SUB");
                                break;
                            case "/":
                                this.writeDivF(pila, writer);
                                System.out.println("DIV");
                                break;
                            case ":=":
                                this.writeAsigF(pila, writer);
                                System.out.println("ASIG");
                                break;
                        }
                    }
                    if (s.equals("print")){
                        writer.write("invoke MessageBox, NULL, addr @"+pila.peek().replace(' ' ,'_')+", addr @"+pila.pop().replace(' ' ,'_')+", MB_OK");
                    }
                    String reg="";
                    if (T=='F'){
                        reg="e";
                    }
                    if (s.equals("[]")){
                        writer.write("MOV cx ,"+pila.pop());
                        writer.newLine();
                        writer.write("MOV dx ,@"+pila.pop()+"_MAX");
                        writer.newLine();
                        writer.write("CMP cx , dx");
                        writer.newLine();
                        writer.write("JG LabelArr");
                        if (T=='I')
                            pila.push("@AUXI"+AuxInt);
                        else
                            pila.push("@AUXF"+AuxFloat);

                    }

                    if (s.equals(">") || s.equals("<") || s.equals(">=") || s.equals("<=") || s.equals("<>") || s.equals("==") ) {
                        writer.write("MOV " + reg + "cx ," + pila.pop());
                        writer.newLine();
                        writer.write("MOV " + reg + "dx ," + pila.pop());
                        writer.newLine();
                        writer.write("CMP "+reg+"dx , "+reg + "cx");
                        writer.newLine();
                        if (s.equals(">")) {
                            writer.write("JLE Label" + polaca.get(j + 1));

                        }
                        if (s.equals("<")) {
                            writer.write("JGE Label" + polaca.get(j + 1));

                        }
                        if (s.equals("==")) {
                            writer.write("JNE Label" + polaca.get(j + 1));

                        }
                        if (s.equals(">=")) {
                            writer.write("JG Label" + polaca.get(j + 1));

                        }
                        if (s.equals("<=")) {
                            writer.write("JL Label" + polaca.get(j + 1));

                        }

                        if (s.equals("<>")) {
                            writer.write("JE Label" + polaca.get(j + 1));

                        }
                    }
                    if (s.equals("BI")){
                        writer.write("JMP Label" + polaca.get(j-1));

                    }
                    writer.newLine();

                }

                j++;
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
            writer.write("invoke MessageBox, NULL, addr @DIVISOR_CERO, addr @TITULO , MB_OK ");
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("LabelArr:");
            writer.newLine();
            writer.write("invoke MessageBox, NULL, addr @LIMITE_ARREGLO, addr @TITULO , MB_OK " );
            writer.newLine();
            writer.write("JMP EXIT");
            writer.newLine();
            writer.write("LabelOF:");
            writer.newLine();
            writer.write("invoke MessageBox, NULL, addr @OVERFLOW_MULT, addr @TITULO , MB_OK ");
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
        try {
            System.out.println("C:\\masm32\\bin\\ml.exe /c /coff "+codigoASM.getName());
            System.out.println("C:\\masm32\\bin\\link.exe /subsystem:windows "+codigoASM.getName().substring(0,codigoASM.getName().length()-4)+".obj");
            Process p = Runtime.getRuntime().exec("C:\\masm32\\bin\\ml.exe /c /coff "+codigoASM.getName()+ " && C:\\masm32\\bin\\link.exe /subsystem:windows "+codigoASM.getName().substring(0,codigoASM.getName().length()-4)+".obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMulI(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MUL "+pila.pop());
        writer.newLine();
        writer.write("JO LabelOF");
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeDivI(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV bx, "+aux);
        writer.newLine();
        writer.write("ADD bx, " + 0);
        writer.newLine();
        writer.write("JZ "+ "LabelDiv0");
        writer.newLine();
        writer.write("DIV bx");
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeAsigI(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV "+aux+", ax");
        AuxInt=1;
    }

    private void writeSumI(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("ADD ax, "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeSubI(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV ax, "+pila.pop());
        writer.newLine();
        writer.write("SUB ax, "+aux);
        writer.newLine();
        writer.write("MOV @AUXI"+AuxInt+", ax");
        pila.push("@AUXI"+AuxInt);
        AuxInt++;
    }

    private void writeAsigF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("MOV "+aux+", eax");
        AuxFloat=1;
    }

    private void writeSumF(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("FADD  "+pila.pop());
        writer.newLine();
        writer.write("MOV @AUXF"+AuxFloat+", eax");
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeSubF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("FSUB  "+aux);
        writer.newLine();
        writer.write("MOV @AUXF"+AuxFloat+", eax");
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeMulF(Stack<String> pila, BufferedWriter writer) throws IOException {
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("FMUL  "+pila.pop());
        writer.newLine();
        writer.write("JO LabelOF");
        writer.newLine();
        writer.write("MOV @AUXF"+AuxFloat+", eax");
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeDivF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("MOV ebx, "+aux);
        writer.newLine();
        writer.write("CMP ebx, " + 0);
        writer.newLine();
        writer.write("JE "+ "LabelDiv0");
        writer.newLine();
        writer.write("FDIV "+aux);
        writer.newLine();
        writer.write("MOV @AUXF"+AuxFloat+", eax");
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

}
