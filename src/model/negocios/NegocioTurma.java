package model.negocios;

import model.excecoes.*;
import model.interfaces.IRepositorioAlunos;
import model.interfaces.IRepositorioTurmas;
import model.interfaces.IRepositorioUsuarios;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.classes.Turma;

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

    public NegocioTurma(IRepositorioTurmas repositorioTurmas, IRepositorioAlunos repositorioAlunos,
                        IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public ArrayList<String> todasAsTurmas() throws IOException, ClassNotFoundException {
        ArrayList<String> turmas = new ArrayList<>();
        for(Turma turma: this.repositorioTurmas.listarTurmas()){
            String id = Double.toString(turma.getId());
            turmas.add(id.length() == 1 ? "0" + id + ": " + turma.getApelido() : id + ": " + turma.getApelido());
        }
        return turmas;
    }

    // MÉTODOS DE ADIÇÃO
    /**
     * Adiciona uma turma ao professor
     * @param apelido String com o nome da turma.
     * @param alunos Lista de Alunos.
     * @throws InvalidFieldException indica que algum dos dados é invalido.
     * @throws TurmaRepetidaException indica que a turma já está cadastrada.
     * @throws IOException indica que houve um erro na gravação do arquivo
     * @throws ClassNotFoundException algum dos arquivos foi passado com uma classe inválida
     */
    public void adicionarTurma(String apelido, List<String> alunos)
            throws InvalidFieldException, TurmaRepetidaException, IOException, ClassNotFoundException {
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
     * @throws TurmaNaoExisteException se o id não está vinculado a nenhuma turma cadastrada
     * @throws IOException Erro genérico na gravação do arquivo
     * @throws ClassNotFoundException Erro genérico para classe não compatível
     */

    public void adicionarTurmaEmProfessor(Turma turma, Professor professor)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException,
            UsuarioNotFoundException, TurmaRepetidaException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioUsuarios.existeNoBanco(professor)){
                for(double ids: professor.getTurmas()){
                    if(ids == turma.getId()){
                        throw new TurmaRepetidaException("Turma já se encontra cadastrada no professor");
                    }
                }
                professor.adicionarTurma(turma.getId());
                this.repositorioUsuarios.removerUsuario(professor);
                this.repositorioUsuarios.adicionarUsuario(professor);
            }else{
                throw new UsuarioNotFoundException(professor.getNome());
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioAlunos.existeNoBanco(aluno)){
                turma.adicionarAluno(this.repositorioAlunos.buscarAluno(aluno).getNome());
                this.repositorioTurmas.excluirTurma(turma.getId());
                this.repositorioTurmas.adicionarTurma(turma);
            }else{
                throw new AlunoNotFoundException(aluno.getNome());
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    //MÉTODOS DE REMOÇÃO
    public void removerTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.excluirTurma(id);
            removerTurmasDosProfessores(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    private void removerTurmasDosProfessores(double id) throws IOException, ClassNotFoundException {
        ArrayList<Usuario> pessoas = this.repositorioUsuarios.todosOsUsuariosArray();
        for(Usuario pessoa: pessoas){
            if(pessoa instanceof Professor){
                Professor professor = ((Professor) pessoa);
                for(double ids: professor.getTurmas()){
                    if(ids == id){
                        professor.removerTurmas(id);
                        this.repositorioUsuarios.atualizarUsuario(professor, professor);
                        break;
                    }
                }
            }
        }
    }

    public void removerAlunoDaTurma(Turma turma, Aluno aluno)
            throws IOException, ClassNotFoundException, AlunoNotFoundException, TurmaNaoExisteException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioAlunos.existeNoBanco(aluno)){
                turma.removerAluno(aluno.getNome());
                this.repositorioTurmas.atualizarTurma(turma);
            }else{
                throw new AlunoNotFoundException(aluno.getNome());
            }
        }else {
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    //MÉTODOS DE BUSCA
    public Turma pegarTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            return this.repositorioTurmas.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    //Atualiza as informações de uma turma no banco
    public void atualizarTurma(double id, String apelido, List<String> alunos)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        Turma nova = new Turma(id, apelido, alunos);
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    public Turma getUltimaTurmaAdicionada() throws IOException, ClassNotFoundException, TurmaNaoExisteException {
        double id = encontrarID() - 1;
        return this.repositorioTurmas.getTurma(id);
    }

    //AutoIncrementar ID
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
