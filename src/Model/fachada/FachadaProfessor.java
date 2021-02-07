package Model.fachada;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Model.negocios.NegocioTurma;
import Classes.turmas.Turma;

import java.io.IOException;
import java.util.List;

public class FachadaProfessor {
    private NegocioTurma negocioTurma;

    public FachadaProfessor() {
        //this.negocioTurma = this.carregarTurmas();
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
    List<Turma> listarTurmas() throws IOException, ClassNotFoundException {
        return this.negocioTurma.listarTurmas();
    }
}
