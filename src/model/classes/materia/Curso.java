package model.classes.materia;

import java.io.Serializable;

/**
 * Classe responsável por armazenar as informações de um curso de um aluno
 * @author Victor Hugo
 */

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

    public void removerHoras(){
        this.horas -= 2;
    }
}
