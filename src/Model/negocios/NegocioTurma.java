package Model.negocios;

import Classes.excecoes.AlunoNotFoundException;
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

/**
 * Classe responsável por realizar as funcionalidades e manipulação de objetos de turma
 * @author
 */

public class NegocioTurma {
    private final IRepositorioTurmas repositorioTurmas;
    private final IRepositorioAlunos repositorioAlunos;

    public NegocioTurma(IRepositorioTurmas repositorioTurmas, IRepositorioAlunos repositorioAlunos) {
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioAlunos = repositorioAlunos;
    }

    /**
     * Adiciona uma turma ao professor
     * @param id Double com o id da turma.
     * @param apelido String com o nome da turma.
     * @param alunos Lista de Alunos.
     * @throws InvalidFieldException indica que algum dos dados é invalido.
     * @throws TurmaRepetidaException indica que a turma já está cadastrada.
     * @throws IOException indica que houve um erro na gravação do arquivo
     * @throws ClassNotFoundException algum dos arquivos foi passado com uma classe inválida
     */
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

    /**
     * Remove a Turma do sistema
     * @param id Número unico da turma
     * @throws TurmaNaoExisteException se o id não está vinculado a nenhuma turma cadastrada
     * @throws IOException Erro genérico na gravação do arquivo
     * @throws ClassNotFoundException
     */
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
    public void adicionarAlunoEmTurma(Turma turma, String nomeOuCpf) throws TurmaNaoExisteException, IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
                turma.adicionarAluno(this.repositorioAlunos.buscarAluno(nomeOuCpf).getNome());
                this.repositorioTurmas.excluirTurma(turma.getId());
                this.repositorioTurmas.adicionarTurma(turma);
            }else{
                throw new AlunoNotFoundException(nomeOuCpf);
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    //Remove um aluno em uma turma (Somente o nome) e faz a atualizações no banco
    public void removerAlunoDaTurma(Turma turma, String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
                turma.removerAluno(nomeOuCpf);
                this.repositorioTurmas.excluirTurma(turma.getId());
                this.repositorioTurmas.adicionarTurma(turma);
            }else{
                throw new AlunoNotFoundException(nomeOuCpf);
            }
        }else {
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    //Atualiza o arrayLista da turma, dessa vez com um arrayList de turmas(Função provavelmente usada exclusivamente nos Controllers);
    public Turma recuperarAlunosTurma(Turma turma) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> alunos = new ArrayList<>();

        for(String nomeAluno : turma.getNomesAlunos()){
            for(Aluno aluno : repositorioAlunos.todosOsAlunosArray()){
                if(aluno.getNome().toLowerCase().equals(nomeAluno.toLowerCase())){
                    alunos.add(aluno);
                }
            }
        }
        turma.setAlunos(alunos);
        return turma;
    }
}
