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
    int [][] MTEstados;
    AccionSemantica [][] MASemanticas;

    private void cargarMatrices(){
        MTEstados=new int[][]{
                { 0,  1,  4,  5,  4,  4, 17,  8,  4, 12,  0, 14, 15, 16, 17, 17, 17, 17, 17, 17, 17},
                {-1,  3,  2,  2,  2,  2, -1, -1,  2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {17, 17,  2,  2,  2,  2, 17, 17,  2, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17},
                { 0,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3,  3},
                {17,  4,  4, -1,  4,  4, -1, -1,  4, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, 17, 17},
                {17,  6, -1,  5, -1, -1, -1,  9, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17},
                {17, -1, -1, -1,  7, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17},
                {17, -1, -1, -1, -1, 17, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17},
                {-1, -1, -1,  9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                {17, -1, -1,  9, -1, -1, -1, -1, 10, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17},
                {-1, -1, -1, 11, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 11, 11, -1, -1, -1, -1},
                {17, -1, -1, 11, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17},
                {-1, 12, 12, 12, 12, 12, 12, 12, 12, 17, 12, 12, 12, 12, 12, 12, 13, 12, 12, 12, 12},
                {12, 12, 12, 12, 12, 12, 12, 12, 12, 17, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
                {17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1},
                {17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17},
                {17, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, 17, -1, -1, -1, -1, -1, 17}
        };

        AccionSemantica AS = new AccionSemantica(this,p);

        AccionSemantica.AccionConcatenar ASC= AS.new AccionConcatenar(this,p);
        AccionSemantica.AccionPalabraReservada ASPR= AS.new AccionPalabraReservada(this,p);
        AccionSemantica.AccionConcatenarGuion ASCG= AS.new AccionConcatenarGuion(this,p);
        AccionSemantica.AccionNumero ASN= AS.new AccionNumero(this,p);
        AccionSemantica.AccionFinalTS ASFTS= AS.new AccionFinalTS(this,p);
        AccionSemantica.AccionNumeroUnsigned ASNU= AS.new AccionNumeroUnsigned(this,p);
        AccionSemantica.AccionNumeroDouble ASND= AS.new AccionNumeroDouble(this,p);
        AccionSemantica.AccionConsumeChar ASCC= AS.new AccionConsumeChar(this,p);
        AccionSemantica.AccionConsumeChar ERR= AS.new AccionConsumeChar(this,p);
        AccionSemantica.AccionString ASFS= AS.new AccionString(this,p);

        MASemanticas= new AccionSemantica[][]{
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASCC,  ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC, ASC},
                {ASCC,  ASCC,  ASC,  ASC,  ASC,  ASC,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ERR},
                {ASFTS,  ASFTS,  ASC,  ASC,  ASC,  ASC,  ASFTS,  ASFTS,  ASC,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS,  ASFTS, ASFTS},
                {ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC, ASCC},
                {ASPR,  ASC,  ASC,  ERR,  ASC,  ASC,  ERR,  ERR,  ASC,  ERR,  ASPR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASPR, ASPR},
                {ASN,  ASCC,  ERR,  ASC,  ERR,  ERR,  ERR,  ASC,  ERR,  ERR,  ASN,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ASN},
                {ASNU,  ERR,  ERR,  ERR,  ASCC,  ERR,  ERR,  ERR,  ERR,  ERR,  ASNU,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ASNU},
                {ASNU,  ERR,  ERR,  ERR,  ERR,  ASNU,  ERR,  ERR,  ERR,  ERR,  ASNU,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ASNU},
                {ASCC,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ERR},
                {ASND,  ASCC,  ASCC,  ASC,  ASCC,  ASCC,  ASCC,  ASCC,  ASC,  ASCC,  ASND,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC, ASND},
                {ASCC,  ASCC,  ASCC,  ASC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASCC,  ASC,  ASC,  ASCC,  ASCC,  ASCC, ERR},
                {ASND,  ERR,  ERR,  ASC,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ASND,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR,  ERR, ASND},
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASFS,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASCC,  ASC,  ASC,  ASC, ASC},
                {ASCC,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASFS,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG,  ASCG, ASC},
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC, ERR},
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC, ASFTS},
                {ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASCC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC,  ASC, ERR}
        };

    }

    public int getLinea(){
        return linea;
    }

    public void warning(String m){
        gui.addMensaje("WARNING: "+m);
    }

    public void error(String m){
        gui.addMensaje("ERROR: "+m);
    }

    public  Parser getParser(){return p;}

    public TablaSimbolos getTablaDeSimbolos(){
        return TS;}

    public AnalizadorLexico(File code, Gui gui, TablaSimbolos TS) throws IOException{
        this.TS=TS;
        fis = new FileInputStream(code);
        this.p=new Parser(this,TS);
        this.gui=gui;
        cargarMatrices();
        this.pos=0;
        this.polaca = new ArrayList<String>();
        this.cantidadTokens=0;
        this.code=code;
        System.out.println("Codigo Cargado:");
        System.out.println(code.toString());
    }

    public void addPolaca(String s){
        polaca.add(s);
        this.gui.updatePolaca();
    }

    public void setTipoToken(String T){
        tipoToken=T;
    }



    public int getCodToken(String t){
        switch (t){
            case "readonly" : return 257;
            case "write" : return 258;
            case "pass" : return 259;
            case "return" : return 276;
            case "constanteDouble" : return 264;
            case "constanteUsinteger" : return 265;
            case "id" : return 260;
            case "integer" : return 261;
            case "usinteger" : return 262;
            case "double" : return 263;
            case "string":return  266;
            case "if" : return 267;
            case "else" : return 268;
            case "end_if" : return 269;
            case "print" : return 271;
            case "for" : return 270;
            case "!" : return (int) '!';
            case "!=" : return 272;
            case "," : return (int) ',';
            case ">" : return (int) '>';
            case ">=" : return 273;
            case "<" : return (int) '<';
            case ":" : return (int) ':';
            case "<=" : return 274;
            case ";" : return (int) ';';
            case "=" : return (int) '=';
            case "+" : return (int) '+';
            case "-" : return (int) '-';
            case "/" : return (int) '/';
            case "*" : return (int) '*';
            case "(" : return (int) '(';
            case ")" : return (int) ')';
            case "[" : return (int) '[';
            case "]" : return (int) ']';
            case "{" : return (int) '{';
            case "}" : return (int) '}';
            case ":=":return 275;
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
        if (c==59)
            return 6;
        if (c==44)
            return 20;
        if(c==117)//u
            return 4;
        if(c==105)//i
            return 5;
        if(c==68)//D
            return 8;
        if(c==32)//[ESPACIO]
            return 10;
        if(c==9)//[TAB]
            return 10;
        if(c==10)//[LN]
            return 0;
        if(c==39)//'
            return 9;
        if(c==95)//_
            return 1;
        if(c==33)//!
            return 11;
        if(c==60)//<
            return 12;
        if(c==62)//>
            return 12;
        if(c==58)//:
            return 13;
        if(c==61)//=
            return 14;
        if(c==43)//+
            return 15;
        if(c==45)//-
            return 16;
        if(c==46)//.
            return 7;
        if(c==47)///
            return 17;
        if(c==42)//*
            return 18;
        if(c==40)//(
            return 19;
        if(c==41)//)
            return 19;
        if(c==91)//[
            return 19;
        if(c==93)//]
            return 19;
        if(c==123)//{
            return 19;
        if(c==125)//}
            return 19;
        if(c==117)
            return 4;
        if((c>=48)&&(c<=57))//numeros
            return 3;
        if((c>=65)&&(c<=90))//LETRAS
            return 2;
        if((c>=97)&&(c<=122))//letras
            return 2;
        gui.addMensaje("CARACTER INVALIDO \""+ch+"\""+"Codigo: "+c);
       return 0;
    }

    public void setError(){
        estadofuturo=-1;
        gui.error=true;
    }

    public void setRollback(char c){
        rollback=true;
        rollchar=c;
    }

    public Integer getPosPolaca(){return (Integer) polaca.size();}

    public void addPolaca(Integer pos, String s){
        System.out.println("Agrego a la polaca de tamaño "+getPosPolaca()+" el string "+s+"en el lugar "+pos);
        polaca.set(pos,s);
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
        Token out;
        StringBuilder parcial= new StringBuilder();
        char aux;
        estadoactual=0;
        while (estadoactual!=17 && estadoactual!=-1){
            if (fis.available()<=2){
                fis.close();
                return null;}
            if (!rollback){
                aux= (char) fis.read();
                //System.out.println("CARACTER LEIDO: "+aux);
                pos++;
                if (aux == '\n'){linea++;}
            }else {
                aux=rollchar;
                rollback=false;}
            estadofuturo=MTEstados[estadoactual][getCodChar(aux)];
            MASemanticas[estadoactual][getCodChar(aux)].run(parcial,aux);
            //System.out.println("Estado actual: "+estadoactual+", Caracter Leido: "+aux+"("+getCodChar(aux)+")"+"proximo estado: "+MTEstados[estadoactual][getCodChar(aux)]);
            //System.out.println("Char "+aux+" Estado "+estadoactual +" Posicion :"+pos);
            estadoactual= estadofuturo;
        }
        if (estadoactual==-1){
            JOptionPane.showMessageDialog(null, "ERROR: Linea "+this.getLinea()+" on token \""+parcial+"\" ");
            return null;
        }else {
            out=new Token(parcial.toString(),getCodToken(tipoToken));
        }
        System.out.println("TOKEN CARGADO: "+parcial+" TIPO: "+tipoToken);
        gui.addMensaje(out.toString());
        return out;
    }
}
