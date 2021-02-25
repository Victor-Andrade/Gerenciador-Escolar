package model.classes;

import java.io.Serializable;

public class Situacao implements Serializable {
    private int id;
    private String mensagem;
    private Data data;

    public Situacao(String mensagem, Data data, int id){
        this.data = data;
        this.mensagem = mensagem;
        this.id = id;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
