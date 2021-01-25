package Classes.pessoas;

import Classes.pessoas.interfaces.ILogin;

public class Administrador extends Pessoa implements ILogin {
    private String senha;

    public Administrador(String nome, String cpf, int[] data, String email, String contato, String senha) {
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
