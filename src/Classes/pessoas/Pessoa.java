package Classes.pessoas;

import Classes.datas.Data;

import java.io.Serializable;

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

    //Falta definir uma regra
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public Data getDataDeNascimento() {
        return this.dataDeNascimento;
    }

    //Falta idade?
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

    @Override
    public boolean equals(Object obj){
        if(obj instanceof String){
            String nomeOuCpf = (String) obj;
            return nomeOuCpf.toLowerCase().equals(this.nome) || nomeOuCpf.replace("-", "").replace(".", "").replace(" ", "").equals(this.cpf);
        }else{
            return false;
        }
    }
}
