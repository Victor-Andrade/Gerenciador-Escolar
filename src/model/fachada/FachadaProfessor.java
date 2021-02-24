package model.fachada;

import model.excecoes.*;
import model.classes.pessoas.alunos.Aluno;
import model.cruds.CRUDAlunos;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
import model.negocios.NegocioAdministrador;
import model.negocios.NegocioProfessor;
import model.negocios.NegocioTurma;
import model.classes.Turma;

import java.io.IOException;

/**
 * Fachada usada para lidar com as requisições do Professor,
 * retorna informações com base no negócios de Turma e professor
 * @author Victor Hugo
 */
public class FachadaProfessor {
    private final NegocioTurma negocioTurma;
    private final NegocioProfessor negocioProfessor;
    private final NegocioAdministrador negocioAdministrador;

    public FachadaProfessor() {
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos(), new CRUDUsuarios());
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDUsuarios());

    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.negocioProfessor.gerarBoletim(aluno);
    }

    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno)
            throws ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException,
            IOException, InvalidDateException, AlunoAlredyRegisteredException {
        this.negocioTurma.adicionarAlunoEmTurma(turma, aluno);
    }

    public void removerAlunoDaTurma(Turma turma, Aluno aluno)
            throws ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException,
            IOException, InvalidDateException {
        this.negocioTurma.removerAlunoDaTurma(turma, aluno);
    }

    public Turma buscarTurma(double id) throws ClassNotFoundException, IOException, TurmaNaoExisteException {
        return this.negocioTurma.pegarTurma(id);
    }

    public Aluno buscarAluno(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        return this.negocioProfessor.buscarAluno(aluno);
    }

    public void atualizarNotasAluno(Aluno aluno) throws ClassNotFoundException, AlunoNotFoundException, NotasInvalidasException, IOException {
        this.negocioAdministrador.atualizarNotasAluno(aluno);
    }
}
