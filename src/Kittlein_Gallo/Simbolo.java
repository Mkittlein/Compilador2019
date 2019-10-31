package Kittlein_Gallo;

import java.util.ArrayList;

public class Simbolo {
    private char Tipo;
    private Object Valor;
    private int Size;
    private char Uso;
    private ArrayList<String> Valores;

    Simbolo(char tipo, char uso) {
        // Tipo es Float = F, Int = I , String = S, D para IDs no determinadas. Usar D para definir si no es instanciada esta bien
        // Uso es A para Arreglos/Colecciones, V para variable, C para constante
        this.Tipo = tipo;
        this.Size=1;
        this.Uso=uso;
    }

    public String toString(){
        if (Size > 1){
            return (" Tipo: \""+Tipo+ "\" Uso: " + Uso + "\" Tamaño: " + Size);
        }
        return (" Tipo: \""+Tipo+ "\" Uso: " + Uso + "\"");
    }

    public void setTipo(char tipo) {
        this.Tipo = tipo;
    }

    public char getTipo() {
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
        return Size;
    }

    public void setSize(int size) {
        this.Size = size;
        Valores = new ArrayList<>(size);
    }

    public void asignarValor(ArrayList<String> entrada){
       this.Valores = entrada;
    }
}
