package Kittlein_Gallo;

public class Simbolo {
    private char Tipo;
    private Object Valor;
    private int size;
    private char Uso;

    Simbolo(char tipo, char uso) {
        // Tipo es Float = F, Int = I , String = S, D para IDs no determinadas. Usar D para definir si no es instanciada esta bien
        // Uso es A para Arreglos/Colecciones, V para variable, C para constante
        this.Tipo = tipo;
        this.size=1;
        this.Uso=uso;
    }

    public String toString(){
        if (size > 1){
            return (" Tipo: \""+Tipo+ "\" Uso: " + Uso + "\" Tamaño: " + size );
        }
        return (" Tipo: \""+Tipo+ "\" Uso: " + Uso + "\"");
    }

    public void setTipo(char tipo) {
        this.Tipo = tipo;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setUso(char uso) {
        this.Uso = uso;
    }

    public int getUso() {
        return Uso;
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
