package model.classes.faltas;

import model.classes.Data;
import model.interfaces.IFalta;

import java.io.Serializable;

/**
 * Classe responsável por armazenar os dados de uma falta de um aluno
 * @author Pedro Vinícius
 */

public class Falta implements Serializable, IFalta {
    private Data data;
    private String cpf;

    public Falta(Data data, String cpf) {
        this.data = data;
        this.cpf = cpf;
    }

    public String getCpf(){
        return this.cpf;
    }

    public Data getData(){
        return this.data;
    }

    public void setData(Data data){
        this.data = data;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Data){
            Data comp = (Data) obj;
            return comp.equals(this.data);
        }
        return false;
    }

    @Override
    public boolean isConfirmada() {
        return false;
    }
}
