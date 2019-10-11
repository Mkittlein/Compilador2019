package Kittlein_Gallo;

public class Simbolo {
    private char Tipo;
    private Object Valor;

    Simbolo(char tipo) {
        this.Tipo = tipo; //El tipo variable distingue entre IDs y constantes, ints, floats y strings siumltaneamente. Es decir que tendríamos 5 tipos
                                          //ID_INT= I, ID_FLOAT = F, CTE_INT = i, CTE_FLOAT = f, CTE_STRING = s y D para IDs no determinadas.

    }

    public String toString(){
        return (" Tipo: \""+Tipo+"\n");
    }

    public void setTipo(char tipoVariable) {
        this.Tipo = tipoVariable;
    }

    public int getTipo() {
        return Tipo;
    }

    public Object getValor() {
        return Valor;
    }

    void setValor(Object valor) {
        Valor = valor;
    }
}
