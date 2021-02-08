package model.negocios;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.excecoes.TurmaRepetidaException;
import Classes.interfaces.IRepositorioAlunos;
import Classes.interfaces.IRepositorioTurmas;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.turmas.Turma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NegocioTurma {
    private IRepositorioTurmas repositorioTurmas;
    private IRepositorioAlunos repositorioAlunos;

    public NegocioTurma(IRepositorioTurmas repositorioTurmas, IRepositorioAlunos repositorioAlunos) {
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioAlunos = repositorioAlunos;
    }

    //Adiciona uma turma no banco
    public void adicionarTurma(double id, String apelido, List<Aluno> alunos) throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
        if(apelido.isBlank()){
            throw new InvalidFieldException("Nome da turma");
        }
        if(repositorioTurmas.turmaExiste(id)){
            throw new TurmaRepetidaException("Já existe uma turma com esse ID");
        }
        Turma novaTurma = new Turma(id, apelido, alunos);
        this.repositorioTurmas.adicionarTurma(novaTurma);
    }

    //Remove uma turma do banco
    public void removerTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.excluirTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    //Busca uma turma pelo Id no banco
    public Turma pegarTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            return this.repositorioTurmas.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    //Atualiza as informações de uma turma no banco
    public void atualizarTurma(double id, String apelido, List<Aluno> alunos) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        Turma nova = new Turma(id, apelido, alunos);
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    //Adiciona um aluno em uma turma (Somente o nome) e faz a atualização no banco
    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno) throws Exception {
        if(repositorioTurmas.turmaExiste(turma.getId()) && repositorioAlunos.existeNoBanco(aluno.getNome())){
            turma.adicionarAluno(aluno.getNome());
            this.repositorioTurmas.excluirTurma(turma.getId());
            this.repositorioTurmas.adicionarTurma(turma);
        }else{
            throw new Exception();
            ////// VOLTAR AQUI/////
        }
    }

    //Remove um aluno em uma turma (Somente o nome) e faz a atualizações no banco
    public void removerAlunoDaTurma(Turma turma, Aluno aluno) throws Exception {
        if(repositorioTurmas.turmaExiste(turma.getId()) && repositorioAlunos.existeNoBanco(aluno.getNome())){
            turma.removerAluno(aluno.getNome());
            this.repositorioTurmas.excluirTurma(turma.getId());
            this.repositorioTurmas.adicionarTurma(turma);
        }else {
            throw new Exception();
            ////// VOLTAR AQUI/////
        }

    }

    //Atualiza o arrayLista da turma, dessa vez com um arrayList de turmas(Função provavelmente usada exclusivamente nos Controllers);
    public Turma recuperarAlunosTurma(Turma turma) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> alunos = new ArrayList<>();

        for(String nomeAluno : turma.getNomesAlunos()){
            for(Aluno aluno : repositorioAlunos.todosOsAlunosArray()){
                if(aluno.getNome().equals(nomeAluno)){
                    alunos.add(aluno);
                }
            }
        }
        turma.setAlunos(alunos);
        return turma;
    }
}
