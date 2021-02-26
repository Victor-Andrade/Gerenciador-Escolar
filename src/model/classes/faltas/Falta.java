package model.classes.faltas;

import model.classes.Data;
import model.interfaces.IFalta;

import java.io.Serializable;

/**
 * Classe responsável por armazenar os dados de uma falta de um aluno
 * @author Pedro Vinícius e Victor Hugo
 */

public class Falta implements Serializable, IFalta {
    private int id;
    private Data data;
    private String mensagem;

    public Falta(Data data, String mensagem, int id) {
        this.data = data;
        this.mensagem = mensagem;
        this.id = id;
    }

    public Data getData(){
        return this.data;
    }

    public void setData(Data data){
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public void alterarStatus(){

    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Falta){
            return ((Falta) obj).getId() == this.id;
        }
        return false;
    }

    @Override
    public boolean isConfirmada() {
        return false;
    }
}
