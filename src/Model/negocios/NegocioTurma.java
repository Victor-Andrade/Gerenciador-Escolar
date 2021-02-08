package Model.negocios;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.interfaces.IRepositorioAlunos;
import Classes.interfaces.IRepositorioTurmas;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.turmas.Turma;

import java.io.IOException;
import java.util.List;

public class NegocioTurma {
    private IRepositorioTurmas repositorioTurmas;
    private IRepositorioAlunos repositorioAlunos;

    public NegocioTurma(IRepositorioTurmas repositorioTurmas, IRepositorioAlunos repositorioAlunos) {
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioAlunos = repositorioAlunos;
    }

    public void adicionarTurma(double id, String apelido, List<Aluno> alunos)
            throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
        if(apelido.isBlank()){
            throw new InvalidFieldException("Nome da turma");
        }
        if(repositorioTurmas.turmaExiste(id)){
            throw new TurmaRepetidaException("Já existe uma turma com esse ID");
        }
        Turma novaTurma = new Turma(id, apelido, alunos);
        this.repositorioTurmas.adicionarTurma(novaTurma);
    }

    public void removerTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.excluirTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public Turma pegarTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            return this.repositorioTurmas.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    public void atualizarTurma(double id, String apelido, List<Aluno> alunos)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        Turma nova = new Turma(id, apelido, alunos);
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno) throws Exception {
        if(repositorioTurmas.turmaExiste(turma.getId()) && repositorioAlunos.existeNoBanco(aluno.getNome())){
            turma.adicionarAluno(aluno.getNome());
            this.repositorioTurmas.excluirTurma(turma.getId());
            this.repositorioTurmas.adicionarTurma(turma);
        }
        throw new Exception();
        ////// VOLTAR AQUI/////
    }

    public void removerAlunoDaTurma(Turma turma, Aluno aluno) throws Exception {
        if(repositorioTurmas.turmaExiste(turma.getId()) && repositorioAlunos.existeNoBanco(aluno.getNome())){
            turma.removerAluno(aluno.getNome());
            this.repositorioTurmas.excluirTurma(turma.getId());
            this.repositorioTurmas.adicionarTurma(turma);
        }
        throw new Exception();
        ////// VOLTAR AQUI/////
    }

    public List<Turma> listarTurmas() throws IOException, ClassNotFoundException {
        return this.repositorioTurmas.listarTurmas();
    }
}
