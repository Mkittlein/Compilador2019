package Kittlein_Gallo;

public class Simbolo {
    private int tipoVariable;

    public Simbolo( int tipoVariable) {
        this.tipoVariable = tipoVariable; //El tipo variable distingue entre IDs y constantes, ints, floats y strings siumltaneamente. Es decir que tendríamos 5 tipos
        //ID_INT, ID_FLOAT, CTE_INT, CTE_FLOAT y CTE_STRING

    }

    public String toString(){
        String aux=(" Tipo: \""+tipoVariable+"\n");
        return aux;
    }

    public void setTipoVariable(int tipoVariable) {
        this.tipoVariable = tipoVariable;
    }

    public int getTipoVariable() {
        return tipoVariable;
    }

}
