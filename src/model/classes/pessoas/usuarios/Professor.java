package model.classes.pessoas.usuarios;

import model.classes.datas.Data;
import model.classes.interfaces.ILogin;
import model.classes.pessoas.Pessoa;
import model.classes.turmas.Turma;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Classe representando o professor do sistema, guarda slista das turmas e a senha para autenticação
 * @author Victor Hugo e Pedro Vinícius
 */

public class Professor extends Usuario {
    private List<Double> turmas;
    private List<Turma> turmasArrayList;

    public Professor(String nome, String cpf, Data data, String email, String contato, String senha) {
        super(nome, cpf, data, email, contato, senha);
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
    public String toString(){
        return super.toString() + "\nSenha: " + this.getSenha();
    }
}
