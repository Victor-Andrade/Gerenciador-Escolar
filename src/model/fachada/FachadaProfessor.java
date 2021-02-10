package model.fachada;

import model.excecoes.AlunoNotFoundException;
import model.excecoes.TurmaNaoExisteException;
import model.classes.pessoas.Aluno;
import model.classes.pessoas.Professor;
import model.cruds.CRUDAlunos;
import model.cruds.CRUDTurma;
import model.negocios.NegocioProfessor;
import model.negocios.NegocioTurma;
import model.classes.turmas.Turma;

import java.io.IOException;

/**
 * Fachada usada para lidar com as requisições do Professor,
 * retorna informações com base no negócios de Turma e professor
 */
public class FachadaProfessor {
    private final NegocioTurma negocioTurma;
    private final NegocioProfessor negocioProfessor;

    public FachadaProfessor() {
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.negocioProfessor.gerarBoletim(aluno);
    }

    public void recuperarTurmasProfessor(Professor professor) throws IOException, ClassNotFoundException {
        this.negocioProfessor.recuperarTurmasProfessor(professor);
    }

    public void recuperarAlunosTurma(Turma turma) throws IOException, ClassNotFoundException {
        this.negocioTurma.recuperarAlunosTurma(turma);
    }

    public void adicionarAlunoEmTurma(Turma turma, String nomeOuCpf) throws ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException, IOException {
        this.negocioTurma.adicionarAlunoEmTurma(turma, nomeOuCpf);
    }

    public void removerAlunoDaTurma(Turma turma, String nomeOuCpf) throws ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException, IOException {
        this.negocioTurma.removerAlunoDaTurma(turma, nomeOuCpf);
    }
}
