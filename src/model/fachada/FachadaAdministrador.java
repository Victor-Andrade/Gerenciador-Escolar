package model.fachada;

import model.classes.Data;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.classes.Turma;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
import model.excecoes.*;
import model.negocios.NegocioAluno;
import model.negocios.NegocioUsuario;
import model.cruds.CRUDAlunos;
import model.negocios.NegocioTurma;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Fachada responsável por gerenciar informações do administrador, ela atende as solicitações do controller e reponde
 * de acordo com as regras de negócio tanto da turma quanto do professor.
 * @author Victor Hugo e Pedro Vinícius
 */
public class FachadaAdministrador {
    private final NegocioUsuario negocioUsuario;
    private final NegocioAluno negocioAluno;
    private final NegocioTurma negocioTurma;

    public FachadaAdministrador(){
        this.negocioAluno = new NegocioAluno(new CRUDAlunos());
        this.negocioUsuario = new NegocioUsuario(new CRUDAlunos(), new CRUDUsuarios());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos(), new CRUDUsuarios());
    }

    public void adicionarAdministrador(String nome, String cpf, Data data, String email, String contato, String senha)
            throws ClassNotFoundException, InvalidFieldException, UsuarioAlreadyRegisteredException,
            InvalidDateException, IOException {
        this.negocioUsuario.adicionarAdministrador(nome, cpf, data, email, contato, senha);
    }

    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha)
            throws UsuarioAlreadyRegisteredException, IOException, ClassNotFoundException, InvalidFieldException {
        this.negocioUsuario.adicionarProfessor(nome, cpf,  data, email, contato, senha);
    }

    public void adicionarTurma(String apelido, List<String> alunos)
            throws InvalidFieldException, ClassNotFoundException, TurmaRepetidaException, IOException {
        this.negocioTurma.adicionarTurma(apelido, alunos);
    }

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato, String emailResponsavel)
            throws ClassNotFoundException, InvalidFieldException, AlunoAlredyRegisteredException,
            InvalidDateException, IOException {
        this.negocioAluno.matricularAluno(nome, cpf, data, email,  contato, emailResponsavel);
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato,
                                         String emailResponsavel, String curso) throws ClassNotFoundException,
                                         InvalidFieldException, AlunoAlredyRegisteredException,
                                         InvalidDateException, IOException {
        this.negocioAluno.matricularAlunoHoraExtra(nome, cpf, data, email, contato, emailResponsavel, curso);
    }

    public ArrayList<String> todosOsAlunos() throws IOException, ClassNotFoundException {
        return this.negocioAluno.todosOsAlunosString();
    }

    public ArrayList<String> todasAsTurmas() throws IOException, ClassNotFoundException {
        return this.negocioTurma.todasAsTurmas();
    }

    public ArrayList<String> todosOsUsuarios() throws IOException, ClassNotFoundException {
        return this.negocioUsuario.todosOsUsuariosString();
    }

    public ArrayList<String> todosOsProfessores() throws IOException, ClassNotFoundException {
        return this.negocioUsuario.todosOsProfessores();
    }

    public Aluno buscarAluno(Aluno aluno) throws IOException, ClassNotFoundException,
            InvalidDateException, AlunoNotFoundException {
        return this.negocioAluno.buscarAluno(aluno);
    }

    public Turma buscarTurma(double id) throws ClassNotFoundException, IOException, TurmaNaoExisteException {
        return this.negocioTurma.pegarTurma(id);
    }

    public List<Usuario> getUsuariosLogin() throws IOException, ClassNotFoundException {
        return this.negocioUsuario.todosOsUsuarios();
    }

    public void adicionarTurmaEmProfessor(Turma turma, Professor professor)
            throws ClassNotFoundException, UsuarioAlreadyRegisteredException,
            TurmaNaoExisteException, IOException, UsuarioNotFoundException, TurmaRepetidaException {
        this.negocioTurma.adicionarTurmaEmProfessor(turma, professor);
    }

    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno)
            throws ClassNotFoundException, AlunoNotFoundException,
            TurmaNaoExisteException, IOException, InvalidDateException, AlunoAlredyRegisteredException {
        this.negocioTurma.adicionarAlunoEmTurma(turma, aluno);
    }

    public void removerAlunoDaTurma(Turma turma, Aluno aluno)
            throws ClassNotFoundException, AlunoNotFoundException,
            TurmaNaoExisteException, IOException, InvalidDateException {
        this.negocioTurma.removerAlunoDaTurma(turma, aluno);
    }

    public void excluirTurma(Turma turma) throws ClassNotFoundException, IOException, TurmaNaoExisteException {
        this.negocioTurma.removerTurma(turma.getId());
    }

    public void excluirUsuario(Usuario pessoa) throws UsuarioNotFoundException,
            IOException, ClassNotFoundException, InvalidDateException {
        this.negocioUsuario.removerUsuario(pessoa);
    }

    public void excluirAluno(Aluno aluno) throws ClassNotFoundException, AlunoNotFoundException, InvalidDateException, IOException {
        this.negocioAluno.removerAluno(aluno);
    }

    public Turma ultimaTurmaAdicionada() throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        return this.negocioTurma.getUltimaTurmaAdicionada();
    }

    public Usuario buscarUsuario(Usuario usuario)
            throws UsuarioNotFoundException, IOException, ClassNotFoundException, InvalidDateException {
        return this.negocioUsuario.buscarUsuario(usuario);
    }

    public void atualizarTurma(Turma turma, String apelido, List<String> alunos)
            throws ClassNotFoundException, IOException, TurmaNaoExisteException {
        this.negocioTurma.atualizarTurma(turma.getId(), apelido, alunos);
    }

    public void removerTurmaDoProfessor(Turma turma, Professor professor) throws ClassNotFoundException, UsuarioNotFoundException, TurmaNaoExisteException, IOException {
        this.negocioTurma.removerTurmaDoProfessor(turma, professor);
    }

    public void atualizarInformacoesUsuario(Usuario usuario, String nome, String cpf, Data data, String email, String contato, String senha) throws ClassNotFoundException, InvalidFieldException, InvalidDateException, IOException, UsuarioAlreadyRegisteredException, UsuarioNotFoundException {
        this.negocioUsuario.atualizarInformacoesUsuario(usuario, nome, cpf, data, email, contato, senha);
    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.negocioAluno.gerarBoletim(aluno);
    }

    public void gerarCertificadoDeMatricula(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioAluno.gerarCertificadoDeMatricula(aluno);
    }

    public void gerarCertificadoDeCurso(AlunoHoraExtra aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioAluno.gerarCertificadoDeCurso(aluno);
    }

    public void atualizarNotasAluno(Aluno aluno) throws ClassNotFoundException, AlunoNotFoundException, NotasInvalidasException, IOException {
        this.negocioAluno.atualizarNotasAluno(aluno);
    }

    public void atualizarDadosPessoaisAluno(Aluno aluno, String nome, String cpf, Data data, String email, String contato, String emailResponsavel) throws ClassNotFoundException, InvalidFieldException, InvalidDateException, IOException, AlunoAlredyRegisteredException, AlunoNotFoundException {
        this.negocioAluno.atualizarInformacoesAluno(aluno, nome, cpf, data, email, contato, emailResponsavel);
    }

    //USO DO MÉTODO?
    public void adicionarAdmPadrao(){
        try{
            Data data = new Data(2021, 2, 10);
            Administrador adm = new Administrador("admin", "12345678910", data
                    ,"admin@admin.com", "(87)99999-9999", "admin123");
            List<Usuario> p = new ArrayList<>(10);
            FileOutputStream file = new FileOutputStream("usuarios.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            p.add(adm);
            os.writeObject(p);
            os.close();
        } catch (InvalidDateException | IOException e) {
            e.printStackTrace();
        }
    }
}
