package Kittlein_Gallo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AccionSemantica {
    AnalizadorLexico AL;
    Parser p;
   public AccionSemantica(AnalizadorLexico AL,Parser p){
        this.AL = AL;
        this.p=p;
   }
    public void run(StringBuilder S, char c){};

    public class AccionPalabraReservada extends AccionSemantica{

        public AccionPalabraReservada( AnalizadorLexico al,Parser p) {
                super(al,p);
        	}


        public void run(StringBuilder parcial, char last){
            boolean esPalabra=false;
            String pr=parcial.toString();
            switch (pr){
                case "then" : esPalabra=true;
                case "if" : esPalabra=true;
                case "else" : esPalabra=true;
                case "end_if" : esPalabra=true;
                case "print" : esPalabra=true;
                case "usinteger" : esPalabra=true;
                case "double" : esPalabra=true;
                case "for" : esPalabra=true;
                case "write" :esPalabra=true;
                case "readonly" : esPalabra=true;
                case "pass" :  esPalabra=true;
                case "return" : esPalabra=true;
            }
             AL.setRollback(last);
           // System.out.println("ROLLBACK SETEADO CON :"+last);
         //   System.out.println(this.getClass());
            AL.setTipoToken(parcial.toString());
            if (!esPalabra){
                AL.setError();
                AL.warning("\"" + parcial.toString() + "\"" + " no es una palabra reservada");
            }
        }

    }

    public class AccionConcatenarGuion extends AccionSemantica{

        public AccionConcatenarGuion(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        }

        public void run(StringBuilder parcial, char last){
         //   System.out.println(this.getClass());
            parcial.append("_").append(last);
            AL.setTipoToken(parcial.toString());
        }
    }

    public class AccionConcatenar extends AccionSemantica{

        public AccionConcatenar(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        }

        public void run(StringBuilder parcial, char last){
           // System.out.println(this.getClass());
            parcial.append(last);
            AL.setTipoToken(parcial.toString());
        }
    }
    
    public class AccionNumero extends AccionSemantica{

        public AccionNumero(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        }

        public void run(StringBuilder parcial, char last){
           // System.out.println(this.getClass());
            AL.setTipoToken("constante");
            if(last!='\n' && last!=' ' && last!='\t'){
                AL.setRollback(last);
            }
            BigInteger valor = new BigInteger(parcial.toString());
            if (valor.compareTo(BigInteger.valueOf(32768)) == 1){
                AL.warning("Valor de Entero fuera de rango");
                valor = BigInteger.valueOf(32768);
            }
            parcial.setLength(0);
            parcial.append(valor.toString());
        }
    }

    public class AccionNumeroUnsigned extends AccionSemantica{
        BigInteger maxVal;

        public AccionNumeroUnsigned(AnalizadorLexico AL,Parser p) {

            super(AL,p);
            maxVal=BigInteger.valueOf(Long.valueOf("65535"));
        }


        public void run(StringBuilder parcial, char last) {
            //System.out.println(this.getClass());
            AL.setTipoToken("constanteUsinteger");
            BigInteger valor = BigInteger.valueOf(Long.valueOf(parcial.toString()));
            if (valor.compareTo(BigInteger.valueOf(0))<=0){
                AL.warning("Valor de Unsigned fuera de rango");
                 valor=BigInteger.valueOf(Long.valueOf("0"));
            }
            if (valor.compareTo(maxVal)>=0){
                AL.warning("Valor de Unsigned fuera de rango");
                valor=maxVal;
            }
            parcial.setLength(0);
            parcial.append(valor.toString());
            if (!AL.getTablaDeSimbolos().contains(parcial.toString()+"_ui")){
                AL.getTablaDeSimbolos().add(parcial.toString()+"_ui",new Simbolo(262,parcial));}

        }
    }
    
    public class AccionNumeroDouble extends AccionSemantica{
        BigDecimal maxMant;
        BigDecimal maxExp;
        BigDecimal maxVal;
        BigDecimal minMant;
        BigDecimal minExp ;
        BigDecimal minVal;
        boolean positivo;

        public AccionNumeroDouble(AnalizadorLexico AL,Parser p) {
            super(AL,p);
            // System.out.println(this.getClass());
            maxMant= new BigDecimal("1.7976931348623157");
            maxExp = new BigDecimal(Math.pow(10,308));
            maxVal = maxExp.multiply(maxMant);
            minMant= new BigDecimal("-2.2250738585072014");
            minExp = new BigDecimal(Math.pow(10,-308));
            minVal = minExp.multiply(minMant);
        }

        public void run(StringBuilder parcial, char last){
            AL.setTipoToken("constanteDouble");
            positivo=true;
            if(last!='\n'&&last!=' '&&last!='\t'){
                AL.setRollback(last);
            }
            if (parcial.indexOf("D")!=-1){
                String[] output = parcial.toString().split("D");
                if (Math.abs(Double.valueOf(output[0])) < Double.valueOf(0) ){positivo=false;}
                Double mantiza = Math.abs(Double.valueOf(output[0]));//Hacer los calculos de MANTIZA
                Long exponente = Long.valueOf(output[1]);//Hacer los calculos del Exponente

                BigDecimal bigmantiza = new BigDecimal(mantiza);
                BigDecimal bigexponente = new BigDecimal(Math.pow(10, exponente));
                BigDecimal bigvalor = (bigexponente.multiply(bigmantiza));
                if (bigvalor.compareTo(maxVal) == 1){
                    AL.warning("Valor de Double fuera de rango");
                    bigvalor = maxVal;}
                if ((bigvalor.compareTo(minVal) == -1) && (bigvalor.compareTo(BigDecimal.valueOf(0)))!=0){
                    bigvalor = minVal;
                AL.warning("Valor de Double fuera de rango");}
                parcial.setLength(0);
                if (!positivo){bigvalor=bigvalor.multiply(BigDecimal.valueOf(-1));}
                double auxD = Double.valueOf(bigvalor.toString());
                parcial.append(auxD);
            }else{
                Double valor= Double.valueOf(parcial.toString());
                parcial.setLength(0);
                parcial.append(valor.toString());
            }
            if (!AL.getTablaDeSimbolos().contains(parcial.toString()+"_D")){
                AL.getTablaDeSimbolos().add(parcial.toString()+"_D",new Simbolo(263,parcial));
            }

        }
    }
    
    public class AccionConsumeChar extends AccionSemantica{

        public AccionConsumeChar(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        }

        public void run(StringBuilder parcial, char last){
           // System.out.println(this.getClass());

        }
    }

    public class AccionString extends AccionSemantica{
        public AccionString(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        }

        public void run(StringBuilder parcial, char last){
          //  System.out.println(this.getClass());
            AL.setTipoToken("string");
            if (!AL.getTablaDeSimbolos().contains("STR_"+parcial.toString().replace(" ","_"))){
                AL.getTablaDeSimbolos().add("STR_"+parcial.toString().replace(" ","_"),new Simbolo(264,parcial.toString()));}
            }
        }


    public class AccionFinalTS extends AccionSemantica{
        public AccionFinalTS(AnalizadorLexico AL,Parser p) {
            super(AL,p);
        } //Esta mete token en tabla de simbolos

        public void run(StringBuilder parcial, char last){ //Ver que el ID sea menor a 25 y agregarlo a tabla simbolos
           // System.out.println(this.getClass());
            AL.setTipoToken("id");
            if(last!='\n'&&last!=' '&&last!='\t'){
                AL.setRollback(last);
            }
        }
    }
    
}
