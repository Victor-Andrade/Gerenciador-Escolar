package Classes.pessoas;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.pessoas.excecoes.InvalidFieldException;

import java.time.LocalDateTime;

public abstract class Pessoa {
    private String nome;
    private String cpf;
    private Data dataDeNascimento;
    private String email;
    private String numeroParaContato;

    public Pessoa(String nome, String cpf, Data dataDeNascimento, String email, String numeroParaContato) throws InvalidFieldException, InvalidDateException {
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

    public void setCpf(String cpf) throws InvalidFieldException{
        if(cpf != null){
            if(verificarCampos(cpf)){
                this.cpf = removerCaracteres(cpf);
            }else{
                throw new InvalidFieldException("Cpf");
            }
        }
    }

    public Data getDataDeNascimento() {
        return dataDeNascimento;
    }

    //Falta idade?
    public void setDataDeNascimento(Data dataDeNascimento) throws InvalidFieldException, InvalidDateException {
        if(dataDeNascimento != null){
            LocalDateTime hoje = LocalDateTime.now();
            if(dataDeNascimento.vemAntes(new Data(hoje.getYear(), hoje.getMonthValue(), hoje.getDayOfMonth()))){
                this.dataDeNascimento = dataDeNascimento;
            }else{
                throw new InvalidFieldException("Data de nascimento");
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidFieldException{
        if(email != null){
            if(email.contains("@") && email.contains(".com")){
                this.email = email;
            }else{
                throw new InvalidFieldException("Email");
            }
        }
    }

    public String getNumeroParaContato() {
        return numeroParaContato;
    }

    public void setNumeroParaContato(String numeroParaContato) throws InvalidFieldException{
        if(numeroParaContato != null){
            if(verificarCampos(numeroParaContato)){
                this.numeroParaContato = removerCaracteres(numeroParaContato);
            }else{
                throw new InvalidFieldException("Número");
            }
        }
    }

    //Remove caracteres especiais e de espaço
    private String removerCaracteres(String cpf){
        return cpf.replace(".", "").replace("-", "").replace("(", "").replace(")", "").trim();
    }

    //Verifica se uma string é um inteiro
    private boolean eInteiro( String s ) {
        char[] c = s.toCharArray();

        for (char value : c)
            if (!Character.isDigit(value)) {
                return false;
            }
        return true;
    }

    //Verifica se o cpf ou número é válido
    private boolean verificarCampos(String dado){
        dado = removerCaracteres(dado);

        return dado.length() == 11  && eInteiro(dado);
    }
}
