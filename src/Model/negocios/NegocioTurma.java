package Model.negocios;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.interfaces.IRepositorioTurmas;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.turmas.Turma;

import java.io.IOException;
import java.util.List;

public class NegocioTurma {
    private IRepositorioTurmas repositorio;

    public NegocioTurma(IRepositorioTurmas repositorio) {
        this.repositorio = repositorio;
    }

    private boolean turmaExiste(double id) throws IOException, ClassNotFoundException {
        return this.repositorio.turmaExiste(id);
    }

    public void adicionarTurma(double id, String apelido, List<Aluno> alunos)
            throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
        if(apelido.isBlank()){
            throw new InvalidFieldException("Nome da turma");
        }
        if(repositorio.turmaExiste(id)){
            throw new TurmaRepetidaException("Já existe uma turma com esse ID");
        }
        Turma novaTurma = new Turma(id, apelido, alunos);
        this.repositorio.adicionarTurma(novaTurma);
    }

    public void removerTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(this.turmaExiste(id)){
            this.repositorio.excluirTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public Turma pegarTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(this.turmaExiste(id)){
            return this.repositorio.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public void atualizarTurma(double id, String apelido, List<Aluno> alunos)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        Turma nova = new Turma(id, apelido, alunos);
        if(this.turmaExiste(id)){
            this.repositorio.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    public List<Turma> listarTurmas() throws IOException, ClassNotFoundException {
        return this.repositorio.listarTurmas();
    }
}
