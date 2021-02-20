package model.classes.pessoas.usuarios;

import model.classes.Data;

/**
 * Classe representado o administrador do sistema
 * Ela herda de Professor
 * @author Victor Hugo
 */

public class Administrador extends Usuario {
    private String contrato;

    public Administrador(String nome, String cpf, Data data, String email, String contato, String senha){
        super(nome, cpf, data, email, contato , senha);
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString(){
        return super.toString() + "\nSenha: " + this.getSenha();
    }

}
