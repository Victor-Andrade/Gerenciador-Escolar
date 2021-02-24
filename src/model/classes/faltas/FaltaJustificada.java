package model.classes.faltas;

import model.classes.Data;
import model.interfaces.IFalta;

import java.io.Serializable;

public class FaltaJustificada extends Falta implements Serializable, IFalta {
    private String caminho;
    private boolean confirmada;

    public FaltaJustificada(Data data, String cpf, String caminho) {
        super(data, cpf);
        this.caminho = caminho;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    @Override
    public boolean isConfirmada() {
        return confirmada;
    }
}
