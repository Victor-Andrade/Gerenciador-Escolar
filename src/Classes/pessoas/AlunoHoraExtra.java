package Classes.pessoas;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.materia.Curso;
import Classes.excecoes.InvalidFieldException;

public class AlunoHoraExtra extends Aluno{
    private Curso curso;

    public AlunoHoraExtra(String nome, String cpf, Data data, String email, String contato) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
        this.curso = new Curso("Inglês");
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
