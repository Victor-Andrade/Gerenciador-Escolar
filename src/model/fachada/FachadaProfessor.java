package model.fachada;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.pessoas.Professor;
import model.cruds.CRUDAlunos;
import model.cruds.CRUDTurma;
import model.negocios.NegocioProfessor;
import model.negocios.NegocioTurma;
import Classes.turmas.Turma;

import java.io.IOException;
import java.util.List;

public class FachadaProfessor {
    private final NegocioTurma negocioTurma;
    private final NegocioProfessor negocioProfessor;

    public FachadaProfessor() {
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException {
        this.negocioProfessor.gerarBoletim(aluno);
    }

    public void recuperarTurmasProfessor(Professor professor) throws IOException, ClassNotFoundException {
        this.negocioProfessor.recuperarTurmasProfessor(professor);
    }

    public void recuperarAlunosTurma(Turma turma) throws IOException, ClassNotFoundException {
        this.negocioTurma.recuperarAlunosTurma(turma);
    }

    void adicionarTurma(double id, String apelido, List<Aluno> alunos)
            throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
        this.negocioTurma.adicionarTurma(id, apelido, alunos);
    }
    Turma getTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        return this.negocioTurma.pegarTurma(id);
    }
    void excluirTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        this.negocioTurma.removerTurma(id);
    }
    void atualizarTurma(double id, String apelido, List<Aluno> alunos) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        this.negocioTurma.atualizarTurma(id, apelido, alunos);
    }
}
