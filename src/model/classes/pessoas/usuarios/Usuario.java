package model.classes.pessoas.usuarios;

import model.classes.datas.Data;
import model.classes.interfaces.ILogin;
import model.classes.pessoas.Pessoa;

public class Usuario extends Pessoa implements ILogin {
    private String senha;

    public Usuario(String nome, String cpf, Data dataDeNascimento, String email, String numeroParaContato, String senha) {
        super(nome, cpf, dataDeNascimento, email, numeroParaContato);
        this.senha = senha;
    }

    private void setSenha(String senha){
        this.senha = senha;
    }

    @Override
    public String getSenha() {
        return this.senha;
    }


}
