package Kittlein_Gallo;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    Map<String, Simbolo> TS;


    public TablaSimbolos() {
        TS = new HashMap<>();
    }


    public void setNeg(String s){
        if (TS.containsKey(s)){
            TS.put("-"+s,TS.get(s));
        }
    }

    public Map<String,Simbolo> getTablaDeSimbolos(){
        return TS;}

    public void add(String key, Simbolo value){
        if (!TS.containsKey(key)){
        TS.put(key,value);}
    }

    public boolean contains(String S){
        return TS.containsKey(S);
    }

    public Simbolo getSimbolo(String k){
        return TS.get(k);
    }

    public void setSize(String Key, int newsize){
        TS.get(Key).setSize(newsize);
    }

    public String toString() {

        Simbolo s;
        StringBuilder aux = new StringBuilder("Tabla de Simbolos: \n");
        for (String k:TS.keySet()) {
        aux.append("["+k+" \t| "+TS.get(k).toString()+"]\n");
        }

        return aux.toString();
    }
}