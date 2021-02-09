package Classes.turmas;

import Classes.pessoas.Aluno;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Turma implements Serializable {
    private double id;
    private List<String> nomesAlunos;
    private String apelido;
    private List<Aluno> alunos;

    public Turma(double id, String apelido, List<Aluno> alunos) {
        this.id = id;
        this.apelido = apelido;
        this.alunos = alunos;
        this.nomesAlunos = new ArrayList<>();
    }

    public Turma(double id, String apelido) {
        this.id = id;
        this.apelido = apelido;
        this.nomesAlunos = new ArrayList<>();
    }

    public double getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getApelido() {
        return this.apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public List<Aluno> getAlunos() {
        return this.alunos;
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

    public List<String> getNomesAlunos(){
        return this.nomesAlunos;
    }

    public void setAlunos(List<Aluno> alunos){
        this.alunos = alunos;
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
