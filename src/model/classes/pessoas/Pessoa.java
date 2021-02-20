package model.classes.pessoas;

import model.classes.Data;

import java.io.Serializable;

/**
 * Classe principal da hierarquia de pessoas do projeto.
 * Escolhemos tal hierarquia pois foi observado que todos os objetos
 * pessoa, administrador e aluno detem de características semelhantes tais como nome
 * cpf, data de nascimento, entre outros.
 *
 * @author Victor Hugo e Pedro Vinícius
 */

public abstract class Pessoa implements Serializable {
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
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public Data getDataDeNascimento() {
        return this.dataDeNascimento;
    }

    public void setDataDeNascimento(Data dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getNumeroParaContato() {
        return this.numeroParaContato;
    }

    public void setNumeroParaContato(String numeroParaContato){
        this.numeroParaContato = numeroParaContato;
    }

    public String getCpf() {
        return this.cpf;
    }


    @Override
    public boolean equals(Object pessoa){
        if(pessoa instanceof Pessoa){
            Pessoa pessoaTemp = (Pessoa) pessoa;

            String nome = pessoaTemp.getNome().replace(" ", "");
            String cpf = pessoaTemp.getCpf().replace("-", "")
                    .replace(".", "").replace(" ", "").toLowerCase();

            String nomeAluno = this.nome.replace(" ", "");
            String cpfAluno = this.cpf.replace("-", "")
                    .replace(".", "").replace(" ", "").toLowerCase();
            return nome.equalsIgnoreCase(nomeAluno) || cpf.equals(cpfAluno);
        }
        return false;
    }

    @Override
    public String toString(){
        return "Nome: " + this.nome + "\nCPF: " + this.cpf + "\nData de Nascimento: "
                + this.dataDeNascimento.formatarData() + "\nE-mail: "
                + this.email + "\nNúmero para contato: " + this.numeroParaContato;
    }
}
