package Classes.turmas;

import Classes.pessoas.Aluno;

import java.util.ArrayList;
import java.util.List;

public class Turma {
    private double id;
    private ArrayList<String> nomesAlunos;
    private String apelido;
    private List<Aluno> alunos;

    public Turma(double id, String apelido, List<Aluno> alunos) {
        this.id = id;
        this.apelido = apelido;
        this.alunos = alunos;
    }

    public double getId() {
        return id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void adicionarAluno(String nome){
        this.nomesAlunos.add(nome);
    }

    public void removerAluno(String nome) {
        this.nomesAlunos.remove(nome);
    }

    public void setNomesAlunos(ArrayList<String> alunos){
        this.nomesAlunos = alunos;
    }

    public ArrayList<String> getNomesAlunos(){
        return this.nomesAlunos;
    }

    public void setAlunos(List<Aluno> alunos){
        this.alunos = alunos;
    }

    public void adicionarAluno(Aluno novo){
        this.alunos.add(novo);
    }

    public Aluno selecionarAluno(int index){
        return alunos.get(index);
    }

    public void removerAluno(int index){
        this.alunos.remove(index);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Turma){
            Turma t = (Turma) obj;
            return this.id == t.getId();
        }
        return false;
    }
}
