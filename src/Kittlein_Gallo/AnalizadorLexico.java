package Kittlein_Gallo;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class AnalizadorLexico {
    Parser p;
    TablaSimbolos TS;
    private FileInputStream fis;
    private boolean rollback;
    private  char rollchar;
    private Gui gui;
    private int estadoactual;
    private  int estadofuturo;
    private File code;
    private StringBuilder CurrentToken;
    private char NextChar;
    private int pos,linea;
    private int cantidadTokens;
    private String tipoToken;
    private List<String> polaca;
    private int [][] MTEstados;
    private AccionSemantica [][] MASemanticas;

    private void cargarMatrices(){
        MTEstados=new int[][]{
                { 0,  0, 13,  1,  1,  3,  2,  4,  8,  9, -1, 10, 12, 11, 13, 13, 13},
                {13, 13,  1,  1,  1,  1, -1, -1, 13, -1, -1, -1, -1, -1, 13, -1, 13},
                { 0,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  2},
                {13, 13, -1, -1, -1,  3, -1,  5, -1, -1, -1, -1, -1, 13, 13, -1, 13},
                {-1, -1, -1, -1, -1,  5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {13, 13, -1, -1,  6,  5, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, 13},
                {-1, -1, -1, -1, -1,  7, -1, -1, -1, -1, -1, -1, -1, -1, -1,  7, -1},
                {13, 13, -1, -1, -1,  7, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, 13},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1},
                { 9,  9,  9,  9,  9,  9,  9,  9,  9,  9, 13,  9,  9,  9,  9,  9,  9},
                {13, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, 13, -1, -1, -1},
                {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1},
                {13, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1}
        };

        AccionSemantica AS = new AccionSemantica(this,p);
        AccionSemantica.AccionConsumeChar ASCC =  AS.new AccionConsumeChar(this,p);
        AccionSemantica.AccionConcatenar ASC = AS.new AccionConcatenar(this,p);
        AccionSemantica.AccionFloat ASF = AS.new AccionFloat(this,p);
        AccionSemantica.AccionID ASID = AS.new AccionID(this,p);
        AccionSemantica.AccionInt ASI = AS.new AccionInt(this,p);
        AccionSemantica.AccionString ASS = AS.new AccionString(this,p);
        AccionSemantica.AccionError ERR= AS.new AccionError(this,p);

        MASemanticas= new AccionSemantica[][]{
                {ASCC, ASCC,  ASC,  ASC,  ASC,  ASC, ASCC,  ASC,  ASC, ASCC,  ERR,  ASC,  ASC,  ASC,  ASC,  ASC, ASC},
                {ASID, ASID,  ASC,  ASC,  ASC,  ASC,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASID,  ASID, ASID},
                {ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC, ASCC},
                { ASI,  ASI,  ERR,  ERR,  ERR,  ASC,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASI,  ERR,  ASI},
                { ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR},
                { ASF,  ASF,  ERR,  ERR,  ASC,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASF,  ERR,  ASF},
                { ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR},
                { ASF,  ASF,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASF,  ERR,  ASF},
                { ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR},
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC, ASS,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC},
                {ASCC, ASCC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ASC,  ERR,  ERR, ERR},
                { ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR, ERR},
                {ASCC, ASCC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR, ERR}
        };
    }

    public int getLinea(){
        return linea;
    }

    public void error(String m){
        gui.setColorError(3);
        gui.addMensaje("ERROR: "+m);
    }

    public void warning(String m){
        gui.setColorError(2);
        gui.addMensaje("WARNING: "+m);
    }

    public  Parser getParser(){return p;}

    public TablaSimbolos getTablaDeSimbolos(){
        return TS;}

    public AnalizadorLexico(File code, Gui gui, TablaSimbolos TS) throws IOException{
        this.TS=TS;
        fis = new FileInputStream(code);
        this.p=new Parser();//this,TS);
        this.gui=gui;
        cargarMatrices();
        this.pos=1;
        this.polaca = new ArrayList<String>();
        this.cantidadTokens=0;
        this.code=code;
        System.out.println("Codigo Cargado:");
        System.out.println(code.toString());
    }

    public void setTipoToken(String T){
        tipoToken=T;
    }

    public int getCodToken(String t){
        switch (t){
            case "begin" : return 257;
            case "end" : return 258;
            case "id" : return 259;
            case "int" : return 260;
            case "float" : return 261;
            case "print" : return 262;
            case "string" : return 263;
            case ":=" : return 264;
            case "foreach" : return 265;
            case "in" : return 266;
            case "if" : return 267;
            case "end_if" : return 268;
            case "else" : return 269;
            case ">=" : return 270;
            case "<=" : return 271;
            case "==" : return 272;
            case "cte_integer" : return 273;
            case "cte_float" : return 274;
            case "<>" : return  275;

        }
        if (t.length()==1){
            return (int) t.charAt(0);
        }
        return 0;
    }

    public int getCodChar(char ch) {
        int c=ch;

        if (c==13) //[CR]
            return 0;
        if(c==10)//[LN]
            return 0;
        if(c==32)//[ESPACIO]
            return 1;
        if(c==9)//[TAB]
            return 1;
        if(c==95)//_
            return 2;
        if(c==69)//E
            return 4;
        if(c==101)//e
            return 4;
        if(c==35)//#
            return 6;
        if(c==46)//.
            return 7;
        if(c==58)//:
            return 8;
        if(c==123)//{
            return 9;
        if(c==125)//}
            return 10;
        if(c==60)//<
            return 11;
        if(c==62)//>
            return 12;
        if(c==61)//=
            return 13;
        if (c==59)//;
            return 16;
        if (c==44)//,
            return 14;
        if(c==47)///
            return 14;
        if(c==42)//*
            return 14;
        if(c==40)//(
            return 14;
        if(c==41)//)
            return 14;
        if(c==91)//[
            return 14;
        if(c==93)//]
            return 14;
        if(c==43)//+
            return 15;
        if(c==45)//-
            return 15;
        if((c>=48)&&(c<=57))//numeros
            return 5;
        if((c>=65)&&(c<=90))//LETRAS
            return 3;
        if((c>=97)&&(c<=122))//letras
            return 3;
        gui.addMensaje("CARACTER INVALIDO \""+ch+"\""+"Codigo: "+c);
       return 0;
    }



    public void setRollback(char c){
        rollback=true;
        rollchar=c;
    }

    public Integer getPosPolaca(){return (Integer) polaca.size();}

    public void addPolaca(String s){
        polaca.add(s);
        this.gui.updatePolaca();
    }

    public void addPolaca(Integer pos, String s){
        System.out.println("Agrego a la polaca de tamaño "+getPosPolaca()+" el string "+s+" en el lugar "+pos);
        polaca.set(pos,s);
        this.gui.updatePolaca();
    }

    public void clearPolaca(){
        polaca.clear();
    }

    public void polacaIfSaver(){
        polaca.remove(polaca.size()-1);
        polaca.remove(polaca.size()-1);
        int lastJump = polaca.lastIndexOf("BF");
        polaca.set((lastJump-1),String.valueOf(polaca.size()-1));
        this.gui.updatePolaca();
    }

    public void borraLastPolaca(){
        polaca.remove(polaca.size()-1);
        this.gui.updatePolaca();
    }

    public  List<String> getPolaca(){
        return polaca;}


    public String getPolacaToString(){
        StringBuilder out= new StringBuilder();
        out.append("Polaca Inversa: \n\n");
        for (int i =0;i<polaca.size();i++){
            out.append(i+":\t"+polaca.get(i)+"\n");
        }

        return out.toString();
    }

    public Token getToken()throws FileNotFoundException, IOException {
        Token out=null;
        StringBuilder parcial= new StringBuilder();
        char aux =0;
        estadoactual=0;
        while (estadoactual!=13 && estadoactual!=-1){
            if (fis.available()<=0){
                fis.close();
                return null;}
            if (!rollback){
                if (fis.available()==0){aux = '\n';}
                else{
                aux= (char) fis.read();}
                System.out.println("CARACTER LEIDO: "+aux);
                pos++;
                if (aux == '\n'){linea++;}
            }else {
                aux=rollchar;
                rollback=false;}
            estadofuturo=MTEstados[estadoactual][getCodChar(aux)];
            MASemanticas[estadoactual][getCodChar(aux)].run(parcial,aux);
            System.out.println("Estado actual: "+estadoactual+", Caracter Leido: "+aux+"("+getCodChar(aux)+")"+"proximo estado: "+MTEstados[estadoactual][getCodChar(aux)]);
            System.out.println("Char "+aux+" Estado "+estadoactual +" Posicion :"+pos);

            if (estadoactual==-1){
                JOptionPane.showMessageDialog(null, "ERROR: Linea "+this.getLinea()+" on token \""+parcial+"\" ");
                while (fis.available()!=0 && aux!=';' ){
                    aux= (char) fis.read();
                    if (aux == '\n'){linea++;}
                }
                out=null;
            }else {
                out=new Token(parcial.toString(),getCodToken(tipoToken));
            }
            estadoactual= estadofuturo;
        }

        System.out.println("TOKEN CARGADO: "+parcial+" TIPO: "+tipoToken);
        gui.addToken(out.toString());

        return out;
    }
}
