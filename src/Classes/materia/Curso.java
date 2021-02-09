package Classes.materia;

import java.io.Serializable;

public class Curso implements Serializable {
    private String nome;
    private int horas;

    public Curso(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHoras() {
        return this.horas;
    }

    public void adicionarHoras() {
        this.horas += 2;
    }
}
