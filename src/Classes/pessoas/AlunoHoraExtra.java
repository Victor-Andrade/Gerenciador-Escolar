package Classes.pessoas;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.materia.Curso;

/**
 * Extensão da classe de aluno, guarda um curso que o aluno pode fazer
 * @author
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
