package Classes.pessoas;

import Classes.datas.Data;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private Data dataDeNascimento;
    private String email;
    private String numeroParaContato;

    public Pessoa(String nome, String cpf, Data dataDeNascimento, String email, String numeroParaContato){
        this.nome = nome;
        setCpf(cpf);
        setDataDeNascimento(dataDeNascimento);
        setEmail(email);
        setNumeroParaContato(numeroParaContato);
    }

    public String getNome() {
        return nome;
    }

    //Falta definir uma regra
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public Data getDataDeNascimento() {
        return dataDeNascimento;
    }

    //Falta idade?
    public void setDataDeNascimento(Data dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNumeroParaContato() {
        return numeroParaContato;
    }

    public void setNumeroParaContato(String numeroParaContato){
        this.numeroParaContato = numeroParaContato;
    }

}
