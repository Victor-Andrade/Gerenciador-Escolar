package Classes.pessoas;

import Classes.datas.Data;
import Classes.interfaces.ILogin;
import Classes.turmas.Turma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Classe representando o professor do sistema, guarda somente o nome dos alunos
 */

public class Professor extends Pessoa implements ILogin {
    private List<Double> turmas;
    private List<Turma> turmasArrayList;
    private String senha;

    public Professor(String nome, String cpf, Data data, String email, String contato, String senha) {
        super(nome, cpf, data, email, contato);
        this.senha = senha;
        this.turmas = new ArrayList<>();
        this.turmasArrayList = new ArrayList<>();
    }

    public List<Double> getTurmas() {
        return this.turmas;
    }

    public void setTurmas(List<Double> turmas) {
        this.turmas = turmas;
    }

    public void adicionarTurma(double id){
        this.turmas.add(id);
    }

    public void removerTurmas(double id){
        this.turmas.remove(id);
    }

    public List<Turma> getTurmasArrayList() {
        return this.turmasArrayList;
    }

    public void setTurmasArrayList(ArrayList<Turma> turmaArrayList) {
        this.turmasArrayList = turmaArrayList;
    }

    @Override
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
