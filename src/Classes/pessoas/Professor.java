package Classes.pessoas;

import Classes.pessoas.interfaces.ILogin;

public class Professor extends Pessoa implements ILogin {
    private String senha;

    public Professor(String nome, String cpf, int[] data, String email, String contato) {
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
