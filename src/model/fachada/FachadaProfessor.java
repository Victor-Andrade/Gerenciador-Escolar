package model.fachada;

import model.classes.Data;
import model.excecoes.*;
import model.classes.pessoas.alunos.Aluno;
import model.cruds.CRUDAlunos;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
import model.negocios.NegocioAluno;
import model.negocios.NegocioUsuario;
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
    private final NegocioAluno negocioAluno;
    private final NegocioUsuario negocioUsuario;

    public FachadaProfessor() {
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos(), new CRUDUsuarios());
        this.negocioAluno = new NegocioAluno(new CRUDAlunos());
        this.negocioUsuario = new NegocioUsuario(new CRUDAlunos(), new CRUDUsuarios());

    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.negocioAluno.gerarBoletim(aluno);
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
        return this.negocioAluno.buscarAluno(aluno);
    }

    public void atualizarNotasAluno(Aluno aluno) throws ClassNotFoundException, AlunoNotFoundException, NotasInvalidasException, IOException {
        this.negocioAluno.atualizarNotasAluno(aluno);
    }

    public void adicionarFalta(Aluno aluno, String mensagem, Data data) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioAluno.adicionarFalta(aluno, mensagem, data);
    }

    public void adicionarFaltaJustificada(Aluno aluno, String mensagem, Data data, String caminho) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioAluno.adicionarFaltaJustificada(aluno, mensagem, data, caminho);
    }

    public void reportarSituacao(Aluno aluno, String mensagem, Data data) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioAluno.adicionarSituacao(aluno, data, mensagem);
    }
}
