package Classes.pessoas;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.pessoas.excecoes.InvalidFieldException;
import Classes.pessoas.interfaces.ILogin;

public class Professor extends Pessoa implements ILogin {
    private String senha;

    public Professor(String nome, String cpf, Data data, String email, String contato) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
    }

    @Override
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
