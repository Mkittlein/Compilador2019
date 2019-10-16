package Kittlein_Gallo;

public class Simbolo {
    private char Tipo;
    private Object Valor;
    private int size;

    Simbolo(char tipo) {
        this.Tipo = tipo; //El tipo variable distingue entre IDs y constantes, ints, floats y strings siumltaneamente. Es decir que tendríamos 5 tipo
        this.size=1;// ID_INT= I, ID_FLOAT = F, CTE_INT = i, CTE_FLOAT = f, CTE_STRING = s y D para IDs no determinadas.

    }

    public String toString(){
        if (size > 1){
            return (" Tipo: \""+Tipo+   "\" Tamaño: " + size );
        }
        return (" Tipo: \""+Tipo+"\"");
    }

    public void setTipo(char tipo) {
        this.Tipo = tipo;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
