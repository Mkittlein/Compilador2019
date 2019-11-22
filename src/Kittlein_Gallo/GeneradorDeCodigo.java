package Kittlein_Gallo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
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
        int contForeach=0;
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
            if(t==":="||t=="<"||t=="<="||t==">"||t==">="||t=="<>"||t=="=="||t=="IN"){
                nuevaSentencia=true;
                contI=0;
                contF=0;
            }
            if (t=="IN"){
                contForeach++;
                TS.put("@AuxFOREACH"+contForeach,new Simbolo('I','V'));
                TS.get("@AuxFOREACH"+contForeach).setValor(Integer.valueOf(0));}
        }
        while(AuxInt!=0){
            TS.put("@AUXI"+AuxInt,new Simbolo('I','V'));
            AuxInt--;
        }
        while(AuxFloat!=0){
            TS.put("@AUXF"+AuxFloat,new Simbolo('F','V'));
            AuxFloat--;
        }
    }

    public void generarCodigo(List<String> polaca, String nombre, Map<String, Simbolo> TS) {
        nombre = nombre.replace(".txt", "");
        File codigoASM = new File( Paths.get("").toAbsolutePath().toString()+"\\"+nombre+".asm");
        int countForEach=1;
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
                if ((polaca.get(h).equals("BI")) || (polaca.get(h).equals("BF"))|| (polaca.get(h).equals("BIF"))) {
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
                                System.out.println("MULF");
                                break;
                            case "+":
                                this.writeSumF(pila, writer);
                                System.out.println("SUMF");
                                break;
                            case "-":
                                this.writeSubF(pila, writer);
                                System.out.println("SUBF");
                                break;
                            case "/":
                                this.writeDivF(pila, writer);
                                System.out.println("DIVF");
                                break;
                            case ":=":
                                this.writeAsigF(pila, writer);
                                System.out.println("ASIGF");
                                break;
                        }
                    }
                    if (s.equals("print")){
                        System.out.println("PRINT :"+pila.peek());
                        writer.write("invoke MessageBox, NULL, addr @"+pila.peek().replace(' ' ,'_')+", addr @"+pila.pop().replace(' ' ,'_')+", MB_OK");
                    }

                    if (s.equals("[]")){
                        String direccion=pila.pop();
                        String ArrID=pila.pop();
                        writer.write("MOV cx ,"+direccion);
                        writer.newLine();
                        writer.write("MOV dx ,@"+ArrID+"_MAX");
                        writer.newLine();
                        writer.write("CMP cx , dx");
                        writer.newLine();
                        writer.write("JG LabelArr");
                        writer.newLine();
                        writer.write("XOR ebx,ebx");
                        writer.newLine();
                        writer.write("XOR eax,eax");
                        writer.newLine();
                        writer.write("MOV ax, "+direccion);
                        writer.newLine();
                        if (T=='I'){
                            writer.write("IMUL ax, 2");
                            writer.newLine();
                            writer.write("ADD eax, offset _"+ArrID);
                            writer.newLine();
                            writer.write("MOV bx, word ptr [eax]");
                            writer.newLine();
                            writer.write("MOV @AUXI"+AuxInt+", bx");
                            pila.push("@AUXI"+AuxInt);
                        }
                        else {
                            writer.write("IMUL ax, 4");
                            writer.newLine();
                            writer.write("ADD eax, offset _"+ArrID);
                            writer.newLine();
                            writer.write("MOV ebx, dword ptr [eax]");
                            writer.newLine();
                            writer.write("MOV @AUXF"+AuxFloat+", ebx");
                            pila.push("@AUXF"+AuxFloat);

                            pila.push("@AUXF"+AuxFloat);}
                    } else if(s.contains("[") && s.contains("]")){
                        pila.push(s);
                    }

                    if (s.equals(">") || s.equals("<") || s.equals(">=") || s.equals("<=") || s.equals("<>") || s.equals("==") ) {
                        writer.write("XOR ecx, ecx");
                        writer.newLine();
                        writer.write("XOR edx, edx");
                        writer.newLine();
                        if (T=='I'){
                        writer.write("MOV  cx ," + pila.pop());
                        writer.newLine();
                        writer.write("MOV dx ," + pila.pop());
                        writer.newLine();
                        writer.write("CMP dx , cx");}
                        else{
                            writer.write("MOV ecx ," + pila.pop());
                            writer.newLine();
                            writer.write("MOV edx ," + pila.pop());
                            writer.newLine();
                            writer.write("CMP edx , ecx");
                        }
                        writer.newLine();
                        if (s.equals(">")) {
                            writer.write("JLE Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }
                        if (s.equals("<")) {
                            writer.write("JGE Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }
                        if (s.equals("==")) {
                            writer.write("JNE Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }
                        if (s.equals(">=")) {
                            writer.write("JG Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }
                        if (s.equals("<=")) {
                            writer.write("JL Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }

                        if (s.equals("<>")) {
                            writer.write("JE Label" + polaca.get(j + 1)+";Se salta por el contrario para no tener que poner las sentencias del else antes");

                        }
                    }
                    if (s.equals("BI")){
                        writer.write("JMP Label" + polaca.get(j-1));
                    }
                    if(s.equals("BIF")){
                        writer.write("ADD @AuxFOREACH"+countForEach+", 1");
                        writer.newLine();
                        writer.write("JMP Label" + polaca.get(j-1));
                        writer.newLine();
                        writer.write("LabelForeach"+countForEach+":");

                        countForEach++;
                    }

                    if (s.equals("IN")){
                        String ArrID=pila.pop();
                        String VAR=pila.pop();
                        writer.write("MOV cx, @AuxFOREACH"+countForEach);
                        writer.newLine();
                        writer.write("MOV dx, @"+ArrID+"_MAX");
                        writer.newLine();
                        writer.write("CMP cx, dx");
                        writer.newLine();
                        writer.write("JGE LabelForeach"+countForEach);
                        writer.newLine();
                        writer.write("XOR ebx,ebx");
                        writer.newLine();
                        writer.write("XOR eax,eax");
                        writer.newLine();
                        writer.write("MOV ax, @AuxFOREACH"+countForEach);
                        writer.newLine();
                        if(T=='I'){
                            writer.write("IMUL ax, 2");
                            writer.newLine();
                            writer.write("ADD eax, offset _"+ArrID);
                            writer.newLine();
                            writer.write("MOV bx, word ptr [eax]");
                            writer.newLine();
                            writer.write("MOV  "+VAR+", bx");
                        }
                        else{
                            writer.write("IMUL ax, 4");
                            writer.newLine();
                            writer.write("ADD eax, offset _"+ArrID);
                            writer.newLine();
                            writer.write("MOV ebx, dword ptr [eax]");
                            writer.newLine();
                            writer.write("MOV "+VAR+", ebx");

                        }
                    }


                    if (s.equals("End")){
                        writer.write("JMP EXIT");
                    }
                    writer.newLine();

                }

                j++;
                writer.flush();
            }
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
        //No logramos hacer que el linker genere el exe

        try {
            ProcessBuilder PB = new ProcessBuilder();
            String path=Paths.get("").toAbsolutePath().toFile().toString();
            String c="dir "+path+" && "+Paths.get("").toAbsolutePath().toFile().toString()+"\\bin\\ml.exe /c /coff "+codigoASM.getName()+" && "+Paths.get("").toAbsolutePath().toFile().toString()+"\\bin\\link.exe /subsystem:windows "+Paths.get("").toAbsolutePath().toFile()+"\\"+nombre+".obj";
            System.out.println("COMANDO EJECUTADO: "+c);
            PB.command("cmd.exe","/c",c);
            //PB.directory(Paths.get("").toAbsolutePath().toFile());
            PB.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //=================================================================OPERACIONES CON INTS==============================================================================

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
        if(aux.contains("[")&&aux.contains("]")){
            int i = aux.indexOf('[');
            String ArrID=aux.substring(0,i);
            String direccionAux=aux.substring(i+1,aux.length()-1);
            String direccion=direccionAux;
            if(TS.get(direccionAux).getUso()=='V')
                direccion="_"+direccionAux;
            writer.write("XOR ebx,ebx");
            writer.newLine();
            writer.write("XOR eax,eax");
            writer.newLine();
            writer.write("MOV cx ,"+direccion);
            writer.newLine();
            writer.write("MOV dx ,@"+ArrID+"_MAX");
            writer.newLine();
            writer.write("CMP cx , dx");
            writer.newLine();
            writer.write("JG LabelArr");
            writer.newLine();
            writer.write("MOV bx , "+pila.pop());
            writer.newLine();
            writer.write("MOV ax, "+direccion);
            writer.newLine();
            writer.write("IMUL ax, 2");
            writer.newLine();
            writer.write("ADD eax, offset _"+ArrID);
            writer.newLine();
            writer.write("MOV word ptr [eax], bx");
        } else {
            writer.write("MOV ax, "+pila.pop());
            writer.newLine();
            writer.write("MOV "+aux+", ax");
        }
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

    //=================================================================OPERACIONES CON FLOATS==============================================================================


    private void writeAsigF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        if(aux.contains("[")&&aux.contains("]")){
            int i = aux.indexOf('[');
            String ArrID=aux.substring(0,i);
            String direccionAux=aux.substring(i+1,aux.length()-1);
            String direccion=direccionAux;
            if(TS.get(direccionAux).getUso()=='V')
                direccion="_"+direccionAux;
            writer.write("MOV cx ,"+direccion);
            writer.newLine();
            writer.write("MOV dx ,@"+ArrID+"_MAX");
            writer.newLine();
            writer.write("CMP cx , dx");
            writer.newLine();
            writer.write("JG LabelArr");
            writer.newLine();
            writer.write("XOR ebx,ebx");
            writer.newLine();
            writer.write("XOR eax,eax");
            writer.newLine();
            writer.write("MOV ebx , "+pila.pop());
            writer.newLine();
            writer.write("MOV ax, "+direccion);
            writer.newLine();
            writer.write("IMUL eax, 4");
            writer.newLine();
            writer.write("ADD eax, offset _"+ArrID);
            writer.newLine();
            writer.write("MOV dword ptr [eax], ebx");
        }else{
        writer.write("MOV eax, "+pila.pop());
        writer.newLine();
        writer.write("MOV "+aux+", eax");}
        AuxFloat=1;
    }

    private void writeSumF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("FLD "+pila.pop());
        writer.newLine();
        writer.write("FADD  "+aux);
        writer.newLine();
        writer.write("FST @AUXF"+AuxFloat);
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeSubF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("FLD "+pila.pop());
        writer.newLine();
        writer.write("FSUB  "+aux);
        writer.newLine();
        writer.write("FST @AUXF"+AuxFloat);
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeMulF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("FLD "+pila.pop());
        writer.newLine();
        writer.write("FMUL  "+aux);
        writer.newLine();
        writer.write("JO LabelOF");
        writer.newLine();
        writer.write("FST @AUXF"+AuxFloat);
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

    private void writeDivF(Stack<String> pila, BufferedWriter writer) throws IOException {
        String aux=pila.pop();
        writer.write("MOV ebx, "+aux);
        writer.newLine();
        writer.write("XOR ecx, ecx");
        writer.newLine();
        writer.write("CMP ebx, ecx");
        writer.newLine();
        writer.write("JE "+ "LabelDiv0");
        writer.newLine();
        writer.write("FLD "+pila.pop());
        writer.newLine();
        writer.write("FDIV "+aux);
        writer.newLine();
        writer.write("FST @AUXF"+AuxFloat);
        pila.push("@AUXF"+AuxFloat);
        AuxFloat++;
    }

}
