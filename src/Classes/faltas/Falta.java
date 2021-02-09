package Classes.faltas;

import Classes.datas.Data;

import java.io.Serializable;

public class Falta implements Serializable {
    private final Data data;
    private boolean justificada;
    private String documentoDaJustificativa;

    public Falta(Data data, boolean justificada, String documentoDaJustificativa) {
        this.data = data;
        this.justificada = justificada;
        this.documentoDaJustificativa = documentoDaJustificativa;
    }

    public void justificar(String documentoDaJustificativa){
        this.justificada = true;
        this.documentoDaJustificativa = documentoDaJustificativa;
    }

    public void retirarJustificativa(){
        this.justificada = false;
    }

    public Data getData(){
        return this.data;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Data){
            Data comp = (Data) obj;
            return comp.equals(this.data);
        }
        return false;
    }
}
