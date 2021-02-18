package model.negocios;

import model.classes.excecoes.AlunoNotFoundException;
import model.classes.excecoes.TurmaNaoExisteException;
import model.classes.excecoes.TurmaRepetidaException;
import model.classes.interfaces.IRepositorioAlunos;
import model.classes.interfaces.IRepositorioTurmas;
import model.classes.interfaces.IRepositorioUsuarios;
import model.classes.pessoas.Aluno;
import model.classes.excecoes.InvalidFieldException;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.Professor;
import model.classes.turmas.Turma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar as funcionalidades e manipulação de objetos de turma
 * @author Pedro Vinícius
 */

public class NegocioTurma {
    private final IRepositorioTurmas repositorioTurmas;
    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioUsuarios repositorioUsuarios;

    public NegocioTurma(IRepositorioTurmas repositorioTurmas, IRepositorioAlunos repositorioAlunos, IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Adiciona uma turma ao professor
     * @param apelido String com o nome da turma.
     * @param alunos Lista de Alunos.
     * @throws InvalidFieldException indica que algum dos dados é invalido.
     * @throws TurmaRepetidaException indica que a turma já está cadastrada.
     * @throws IOException indica que houve um erro na gravação do arquivo
     * @throws ClassNotFoundException algum dos arquivos foi passado com uma classe inválida
     */
    public void adicionarTurma(String apelido, List<String> alunos) throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
        if(apelido.isBlank()){
            throw new InvalidFieldException("Nome da turma");
        }
        if(repositorioTurmas.turmaExiste(encontrarID())){
            throw new TurmaRepetidaException("Já existe uma turma com esse ID");
        }
        Turma novaTurma = new Turma(encontrarID(), apelido, alunos);
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
            removerTurmasDosProfessores(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    //############## NESSA FUNÇÃO ######################
    private void removerTurmasDosProfessores(double id) throws IOException, ClassNotFoundException {
        ArrayList<Pessoa> pessoas = this.repositorioUsuarios.todosOsUsuariosArray();
        for(Pessoa pessoa: pessoas){
            if(pessoa instanceof Professor){
                Professor professor = ((Professor) pessoa);
                for(double ids: professor.getTurmas()){
                    if(ids == id){
                        professor.removerTurmas(id);
                        this.repositorioUsuarios.atualizarUsuario(professor.getCpf(), professor);
                        break;
                    }
                }
            }
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
    public void atualizarTurma(double id, String apelido, List<String> alunos) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
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

    public ArrayList<String> todasAsTurmas() throws IOException, ClassNotFoundException {
        ArrayList<String> turmas = new ArrayList<>();
        for(Turma turma: this.repositorioTurmas.listarTurmas()){
            String id = Double.toString(turma.getId());
            turmas.add(id.length() == 1 ? "0" + id + ": " + turma.getApelido() : id + ": " + turma.getApelido());
        }
        return turmas;
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
                if(aluno.getNome().equalsIgnoreCase(nomeAluno)){
                    alunos.add(aluno);
                }
            }
        }
        turma.setAlunos(alunos);
        return turma;
    }

    public Turma getUltimaTurmaAdicionada() throws IOException, ClassNotFoundException, TurmaNaoExisteException {
        double id = encontrarID() - 1;
        return this.repositorioTurmas.getTurma(id);
    }

    private double encontrarID() throws IOException, ClassNotFoundException {
        double maior = 0;
        for(Turma turma: this.repositorioTurmas.listarTurmas()){
            if(turma.getId() > maior){
                maior = turma.getId();
            }
        }
        return maior + 1;
    }
}
