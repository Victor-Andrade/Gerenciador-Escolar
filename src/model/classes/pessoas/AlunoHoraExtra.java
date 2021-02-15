package model.classes.pessoas;

import model.classes.datas.Data;
import model.classes.excecoes.InvalidDateException;
import model.classes.materia.Curso;

/**
 * Extensão da classe de aluno, guarda um curso que o aluno pode fazer
 * @author Pedro Vinícius
 */

public class AlunoHoraExtra extends Aluno{
    private Curso curso;

    public AlunoHoraExtra(String nome, String cpf, Data data, String email, String contato, String emailPais, Curso curso) throws InvalidDateException {
        super(nome, cpf, data, email, contato, emailPais);
        this.curso = curso;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
