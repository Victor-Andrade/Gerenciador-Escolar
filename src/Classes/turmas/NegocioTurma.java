package Classes.turmas;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import java.util.List;

public class NegocioTurma {
    private RepositorioTurma repositorio;

    public NegocioTurma(RepositorioTurma repositorio) {
        this.repositorio = repositorio;
    }

    private boolean turmaExiste(double id){
        return this.repositorio.turmaExiste(id);
    }

    public void adicionarTurma(double id, String apelido, List<Aluno> alunos)
            throws InvalidFieldException, TurmaRepetidaException {
        if(apelido.isBlank()){
            throw new InvalidFieldException("Nome da turma");
        }
        if(repositorio.turmaExiste(id)){
            throw new TurmaRepetidaException("Já existe uma turma com esse ID");
        }
        Turma novaTurma = new Turma(id, apelido, alunos);
        this.repositorio.adicionarTurma(novaTurma);
    }

    public void removerTurma(double id) throws TurmaNaoExisteException {
        if(this.turmaExiste(id)){
            this.repositorio.excluirTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public Turma pegarTurma(double id) throws TurmaNaoExisteException {
        if(this.turmaExiste(id)){
            return this.repositorio.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public void atualizarTurma(double id, String apelido, List<Aluno> alunos)
            throws TurmaNaoExisteException {
        Turma nova = new Turma(id, apelido, alunos);
        if(this.turmaExiste(id)){
            this.repositorio.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    public List<Turma> listarTurmas(){
        return this.repositorio.listarTurmas();
    }
}
