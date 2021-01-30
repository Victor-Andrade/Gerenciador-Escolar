package Classes.pessoas;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.pessoas.excecoes.InvalidFieldException;
import Classes.pessoas.interfaces.ILogin;

public class Administrador extends Pessoa implements ILogin {
    private String senha;

    public Administrador(String nome, String cpf, Data data, String email, String contato, String senha) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
