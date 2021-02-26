package model.classes.faltas;

import model.classes.Data;

import java.io.Serializable;

/**
 * Classe responsável por armazenar os dados de uma falta justificada de um aluno
 * @author Pedro Vinícius e Victor Hugo
 */

public class FaltaJustificada extends Falta implements Serializable {
    private String caminho;
    private boolean confirmada;

    public FaltaJustificada(Data data, String mensagem, String caminho, int id) {
        super(data, mensagem, id);
        this.caminho = caminho;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    @Override
    public boolean isConfirmada() {
        return confirmada;
    }

    @Override
    public void alterarStatus(){
        this.confirmada = !confirmada;
    }
}
