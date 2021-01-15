package Aplicacao.pessoas;

public class Login {
    private final String senha;
    private final String cpf;

    public boolean equals(Login comparar){
        return this.cpf.equals(comparar.getCpf()) && this.senha.equals(comparar.getSenha());
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenha() {
        return senha;
    }

    public Login(String cpf, String senha) {

        this.senha = senha;
        this.cpf = cpf;
    }
}
