package Classes.pessoas;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.excecoes.InvalidFieldException;
import Classes.interfaces.ILogin;

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
