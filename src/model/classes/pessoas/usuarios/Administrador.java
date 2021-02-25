package model.classes.pessoas.usuarios;

import model.classes.Data;

/**
 * Classe representado o administrador do sistema
 * Ela herda de Professor
 * @author Victor Hugo
 */

public class Administrador extends Usuario {

    public Administrador(String nome, String cpf, Data data, String email, String contato, String senha){
        super(nome, cpf, data, email, contato , senha);
    }

    @Override
    public String toString(){
        return super.toString() + "\nSenha: " + this.getSenha();
    }

}
