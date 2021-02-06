package Classes.turmas;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;

import java.util.List;

public class FachadaTurma {
    private NegocioTurma negocioTurma;

    public FachadaTurma() {
        //this.negocioTurma = this.carregarTurmas();
    }

    void adicionarTurma(double id, String apelido, List<Aluno> alunos)
            throws InvalidFieldException, TurmaRepetidaException {
        this.negocioTurma.adicionarTurma(id, apelido, alunos);
    }
    Turma getTurma(double id) throws TurmaNaoExisteException {
        return this.negocioTurma.pegarTurma(id);
    }
    void excluirTurma(double id) throws TurmaNaoExisteException {
        this.negocioTurma.removerTurma(id);
    }
    void atualizarTurma(double id, String apelido, List<Aluno> alunos) throws TurmaNaoExisteException {
        this.negocioTurma.atualizarTurma(id, apelido, alunos);
    }
    List<Turma> listarTurmas(){
        return this.negocioTurma.listarTurmas();
    }
}
