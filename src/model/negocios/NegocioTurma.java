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
 * @author Victor Hugo
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

    /**
     * @throws IOException Faz o tratamento de erro para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public ArrayList<String> todasAsTurmas() throws IOException, ClassNotFoundException {
        ArrayList<String> turmas = new ArrayList<>();
        for(Turma turma: this.repositorioTurmas.listarTurmas()){
            String id = Double.toString(turma.getId());
            turmas.add(id.length() == 1 ? "0" + id + ": " + turma.getApelido() : id + ": " + turma.getApelido());
        }
        return turmas;
    }

    /**
     * @throws InvalidFieldException Trata que nenhum dado da turma esteja inválido
     * @throws TurmaRepetidaException Trata para que não exista uma turma igual no repositório
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
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
     * @throws TurmaRepetidaException Trata para que não exista uma turma igual no professor
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws TurmaNaoExisteException Informa que a turma especificada não foi encontrada no banco
     * @throws UsuarioNotFoundException Informa que o professor que está sendo adicionado a turma não foi encontrado
     */
    public void adicionarTurmaEmProfessor(Turma turma, Professor professor) throws TurmaNaoExisteException, IOException, ClassNotFoundException, UsuarioNotFoundException, TurmaRepetidaException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioUsuarios.existeNoBanco(professor)){
                for(double ids: professor.getTurmas()){
                    if(ids == turma.getId()){
                        throw new TurmaRepetidaException("Turma já se encontra cadastrada no professor");
                    }
                }
                professor.adicionarTurma(turma.getId());
                this.repositorioUsuarios.atualizarUsuario(professor, professor);
            }else{
                throw new UsuarioNotFoundException(professor.getNome());
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws TurmaNaoExisteException Informa que a turma especificada não foi encontrada no banco
     * @throws UsuarioNotFoundException Informa que o professor que está sendo removido a turma não foi encontrado
     */
    public void removerTurmaDoProfessor(Turma turma, Professor professor) throws IOException, ClassNotFoundException, UsuarioNotFoundException, TurmaNaoExisteException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioUsuarios.existeNoBanco(professor)){
                boolean encontrada = false;
                for(double ids: professor.getTurmas()){
                    if(ids == turma.getId()){
                        encontrada = true;
                        break;
                    }
                }
                if(encontrada){
                    professor.removerTurmas(turma.getId());
                    this.repositorioUsuarios.atualizarUsuario(professor, professor);
                }else{
                    throw new TurmaNaoExisteException("Turma não encontrada no professor");
                }
            }else{
                throw new UsuarioNotFoundException(professor.getNome());
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    /**
     * @throws AlunoNotFoundException Informa que o aluno que está tentando ser adicionado não foi encontrado no repositório
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws TurmaNaoExisteException Informa que a turma especificada não foi encontrada no repositório
     * @throws AlunoAlredyRegisteredException Trata para que não exista nenhum aluno igual cadastrado na mesma turma
     */
    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException, AlunoNotFoundException, AlunoAlredyRegisteredException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioAlunos.existeNoBanco(aluno)){
                for(String alunoTemp: turma.getNomesAlunos()){
                    if(alunoTemp.equalsIgnoreCase(aluno.getNome()) || alunoTemp.equalsIgnoreCase(aluno.getCpf())){
                        throw new AlunoAlredyRegisteredException(aluno.getNome(), aluno.getCpf());
                    }
                }
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

    /**
     * @throws TurmaNaoExisteException Informa que a turma a ser removida não foi encontrada
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */

    public void removerTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.excluirTurma(id);
            removerTurmasDosProfessores(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
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

    /**
     * @throws TurmaNaoExisteException Informa que a turma a ser removida não foi encontrada
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws AlunoNotFoundException Informa que o aluno a ser removido da turma não foi encontrado
     */
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
    /**
     * @throws TurmaNaoExisteException Informa que a turma buscada não foi encontrada
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public Turma pegarTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        if(repositorioTurmas.turmaExiste(id)){
            return this.repositorioTurmas.getTurma(id);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " + id);
        }
    }
    /**
     * @throws TurmaNaoExisteException Informa que a turma a ser modificada não foi encontrada
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public void atualizarTurma(double id, String apelido, List<String> alunos)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        Turma nova = new Turma(id, apelido, alunos);
        if(repositorioTurmas.turmaExiste(id)){
            this.repositorioTurmas.atualizarTurma(nova);
        }else{
            throw new TurmaNaoExisteException("Não existe turma com o id " +id );
        }
    }

    /**
     * @throws TurmaNaoExisteException Informa que a turma a buscada não foi encontrada
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
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
