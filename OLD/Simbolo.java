package Kittlein_Gallo;

public class Simbolo {
    private int tipoVariable;
    private int uso;
    private Object o;


    public Simbolo( int tipoVariable, Object o) {
        this.o=o;
        this.tipoVariable = tipoVariable;
        this.uso=0;
    }



    public Simbolo( int tipoVariable, int uso) {
        this.tipoVariable = tipoVariable;
        this.uso=uso;
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
