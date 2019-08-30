package Kittlein_Gallo;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, Simbolo> TS;


    public TablaSimbolos() {
        TS = new HashMap<>();
    }

    public void TS(String key, Simbolo value) {
        TS.put(key, value);
    }

    public Map<String,Simbolo> getTablaDeSimbolos(){
        return TS;}

    public void add(String key, Simbolo value){
        TS.put(key,value);
    }
    public boolean contains(String S){
        return TS.containsKey(S);
    }

    public String toString() {
        StringBuilder aux = new StringBuilder("Tabla de Simbolos: \n");
        aux.append(TS.toString());
        return aux.toString();
    }

}