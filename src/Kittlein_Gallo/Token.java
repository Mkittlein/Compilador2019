package Kittlein_Gallo;

public class Token {
    private String T;
    private int cod;

    public Token(String t, int cod) {
        T = t;
        this.cod = cod;

    }

    public String toString(){
        String aux= new String("Codigo :\""+cod+"\" Token: \""+T+"\"");
        return aux;
    }


    public String getT(){
        return T;
    }

    public int getCod() {
        return cod;
    }
}
