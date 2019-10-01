package Kittlein_Gallo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class AccionSemantica {
    AnalizadorLexico AL;
    Parser p;

    public AccionSemantica(AnalizadorLexico AL, Parser p) {
        this.AL = AL;
        this.p = p;
    }

    public void run(StringBuilder S, char c) {
    }



    public class AccionID extends AccionSemantica {

        public AccionID(AnalizadorLexico al, Parser p) {
            super(al, p);
        }


        public void run(StringBuilder parcial, char last) {
            boolean esPalabra = false;
            String pr = parcial.toString();
            switch (pr) {
                case "if":
                    esPalabra = true;
                case "else":
                    esPalabra = true;
                case "end_if":
                    esPalabra = true;
                case "print":
                    esPalabra = true;
                case "int":
                    esPalabra = true;
                case "float":
                    esPalabra = true;
                case "begin":
                    esPalabra = true;
                case "end":
                    esPalabra = true;
                case "foreach":
                    esPalabra = true;
                case "in":
                    esPalabra = true;
            }
            AL.setRollback(last);//Creo que esto no hace falta, porque sólo llamamos a AccionID con caracteres de espacio o salto de linea
            AL.setTipoToken(parcial.toString());
            if (!esPalabra) {
                AL.setTipoToken("id");
                if (parcial.length() > 24){
                    parcial.delete(25,parcial.length());
                    AL.warning("Id con mas de 25 caracteres recortado");
                }
                TablaSimbolos Ts = AL.getTablaDeSimbolos();
                //Cambiar tipo var cuando tengamos valores
                Simbolo S = new Simbolo(0,parcial);
                Ts.add(parcial.toString(),S);
            }
        }
    }

    public class AccionConcatenar extends AccionSemantica {

        public AccionConcatenar(AnalizadorLexico AL, Parser p) {
            super(AL, p);
        }

        public void run(StringBuilder parcial, char last) {
            // System.out.println(this.getClass());
            parcial.append(last);
            AL.setTipoToken(parcial.toString());
        }
    }

    public class AccionInt extends AccionSemantica {

        public AccionInt(AnalizadorLexico AL, Parser p) {
            super(AL, p);
        }

        public void run(StringBuilder parcial, char last) {
            // System.out.println(this.getClass());
            AL.setTipoToken("integer");
            if (last != '\n' && last != ' ' && last != '\t') {
                AL.setRollback(last);
            }
            BigInteger valor = new BigInteger(parcial.toString());
            if (valor.compareTo(BigInteger.valueOf(32768)) == 1) {

                //TO DO: si se va de rango pasa a ser float
                AL.warning("Integer mayor al rango convertido a float");
                Float valorF = Float.valueOf(parcial.toString());
                parcial.setLength(0);
                parcial.append(valorF.toString());
            }else{
            parcial.setLength(0);
            parcial.append(valor.toString());}

            TablaSimbolos Ts = AL.getTablaDeSimbolos();
            //Cambiar tipo var cuando tengamos valores
            Simbolo S = new Simbolo(0,parcial);
            Ts.add(parcial.toString(),S);
        }
    }


    public class AccionFloat extends AccionSemantica {

        // TO DO: transformar en float - Cambiar d por e y los rangos

        BigDecimal maxMant;
        BigDecimal maxExp;
        BigDecimal maxVal;
        BigDecimal minMant;
        BigDecimal minExp;
        BigDecimal minVal;

        public AccionFloat(AnalizadorLexico AL, Parser p) {
            super(AL, p);
            // System.out.println(this.getClass());
            maxMant = new BigDecimal("3.40282347");
            maxExp = new BigDecimal(Math.pow(10, 38));
            maxVal = maxExp.multiply(maxMant);
            minMant = new BigDecimal("1.17549435");
            minExp = new BigDecimal(Math.pow(10, -38));
            minVal = minExp.multiply(minMant);
        }

        public void run(StringBuilder parcial, char last) {
            AL.setTipoToken("float");
            if (last != '\n' && last != ' ' && last != '\t') {
                AL.setRollback(last);
            }
            if ((parcial.indexOf("E") != -1) || (parcial.indexOf("e") != -1) ) {
                String delimitador;
                if (parcial.indexOf("E") != -1){
                    delimitador="E";
                } else {
                    delimitador="e";
                }
                String[] output = parcial.toString().split(delimitador);

                Float mantiza = Math.abs(Float.valueOf(output[0]));//Hacer los calculos de Mantisa
                Long exponente = Long.valueOf(output[1]);//Hacer los calculos del Exponente

                BigDecimal bigmantiza = new BigDecimal(mantiza);
                BigDecimal bigexponente = new BigDecimal(Math.pow(10, exponente));
                BigDecimal bigvalor = (bigexponente.multiply(bigmantiza));
                if (bigvalor.compareTo(maxVal) == 1) {
                    AL.warning("Valor de float fuera de rango");
                    bigvalor = maxVal;
                }
                if ((bigvalor.compareTo(minVal) == -1) && (bigvalor.compareTo(BigDecimal.valueOf(0))) != 0) {
                    bigvalor = minVal;
                    AL.warning("Valor de float fuera de rango");
                }
                parcial.setLength(0);
                float auxF = Float.valueOf(bigvalor.toString());
                parcial.append(auxF);
            } else {
                Float valor = Float.valueOf(parcial.toString());
                parcial.setLength(0);
                parcial.append(valor.toString());
            }
            TablaSimbolos Ts = AL.getTablaDeSimbolos();
            //Cambiar tipo var cuando tengamos valores
            Simbolo S = new Simbolo(0,parcial);
            Ts.add(parcial.toString(),S);

        }
    }

    public class AccionConsumeChar extends AccionSemantica {

        public AccionConsumeChar(AnalizadorLexico AL, Parser p) {
            super(AL, p);
        }

        public void run(StringBuilder parcial, char last) {
            // System.out.println(this.getClass());

        }
    }

    public class AccionString extends AccionSemantica {
        public AccionString(AnalizadorLexico AL, Parser p) {
            super(AL, p);
        }

        public void run(StringBuilder parcial, char last) {
            //  System.out.println(this.getClass());
            AL.setTipoToken("string");
            TablaSimbolos Ts = AL.getTablaDeSimbolos();
            //Cambiar tipo var cuando tengamos valores
            Simbolo S = new Simbolo(0,parcial);
            Ts.add(parcial.toString(),S);
            }
        }
    }
