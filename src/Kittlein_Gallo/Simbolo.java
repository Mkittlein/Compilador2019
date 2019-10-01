package Kittlein_Gallo;

public class Simbolo {
    private int tipoVariable;
    private Object o;

    public Simbolo( int tipoVariable, Object o) {
        this.o=o;
        this.tipoVariable = tipoVariable;
    }

    public String toString(){
        String aux=(" Tipo: \""+tipoVariable+"\" Valor: \""+o+"\"\n");
        return aux;
    }

    public Simbolo( int tipoVariable) {
        this.tipoVariable = tipoVariable;
        this.o=0;
    }

    public void setTipoVariable(int tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public int getTipoVariable() {
        return tipoVariable;
    }

    public Object getValor() {
        return o;
    }

    public void setValor(Object o) {
        this.o = o;
    }
}
