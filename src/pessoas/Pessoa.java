package pessoas;

public class Pessoa {
    String nome;
    String cpf;
    String dataDeNascimento;
    String email;
    String numeroParaContato;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String nascimento) {
        this.dataDeNascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroParaContato() {
        return numeroParaContato;
    }

    public void setNumeroParaContato(String numeroParaContato) {
        this.numeroParaContato = numeroParaContato;
    }
}
