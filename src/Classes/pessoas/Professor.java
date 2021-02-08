package Classes.pessoas;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.excecoes.InvalidFieldException;
import Classes.interfaces.ILogin;
import Classes.turmas.Turma;

import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa implements ILogin {
    private List<String> turmas;
    private ArrayList<Turma> turmasArrayList;
    private String senha;

    public Professor(String nome, String cpf, Data data, String email, String contato, String senha) {
        super(nome, cpf, data, email, contato);
        this.senha = senha;
    }

    public List<String> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<String> turmas) {
        this.turmas = turmas;
    }

    public void adicionarTurma(String id){
        this.turmas.add(id);
    }

    public void removerTurmas(String id){
        this.turmas.remove(id);
    }

    public ArrayList<Turma> getTurmasArrayList() {
        return turmasArrayList;
    }

    public void setTurmasArrayList(ArrayList<Turma> turmaArrayList) {
        this.turmasArrayList = turmaArrayList;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
