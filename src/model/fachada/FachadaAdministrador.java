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
import model.negocios.NegocioAdministrador;
import model.cruds.CRUDAlunos;
import model.negocios.NegocioProfessor;
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
    private final NegocioAdministrador negocioAdministrador;
    private final NegocioProfessor negocioProfessor;
    private final NegocioTurma negocioTurma;

    public FachadaAdministrador(){
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDUsuarios());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos(), new CRUDUsuarios());
    }

    public void adicionarAdministrador(String nome, String cpf, Data data, String email, String contato, String senha)
            throws ClassNotFoundException, InvalidFieldException, UsuarioAlreadyRegisteredException,
            InvalidDateException, IOException {
        this.negocioAdministrador.adicionarAdministrador(nome, cpf, data, email, contato, senha);
    }

    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha)
            throws UsuarioAlreadyRegisteredException, IOException, ClassNotFoundException {
        this.negocioAdministrador.adicionarProfessor(nome, cpf,  data, email, contato, senha);
    }

    public void adicionarTurma(String apelido, List<String> alunos)
            throws InvalidFieldException, ClassNotFoundException, TurmaRepetidaException, IOException {
        this.negocioTurma.adicionarTurma(apelido, alunos);
    }

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato, String emailResponsavel)
            throws ClassNotFoundException, InvalidFieldException, AlunoAlredyRegisteredException,
            InvalidDateException, IOException {
        this.negocioAdministrador.matricularAluno(nome, cpf, data, email,  contato, emailResponsavel);
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato,
                                         String emailResponsavel, String curso) throws ClassNotFoundException,
                                         InvalidFieldException, AlunoAlredyRegisteredException,
                                         InvalidDateException, IOException {
        this.negocioAdministrador.matricularAlunoHoraExtra(nome, cpf, data, email, contato, emailResponsavel, curso);
    }

    public ArrayList<String> todosOsAlunos() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsAlunos();
    }

    public ArrayList<String> todasAsTurmas() throws IOException, ClassNotFoundException {
        return this.negocioTurma.todasAsTurmas();
    }

    public ArrayList<String> todosOsUsuarios() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsUsuariosString();
    }

    public ArrayList<String> todosOsProfessores() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsProfessores();
    }

    public Aluno buscarAluno(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException,
            InvalidDateException, AlunoNotFoundException {
        return this.negocioProfessor.buscarAluno(aluno);
    }

    public Turma buscarTurma(double id) throws ClassNotFoundException, IOException, TurmaNaoExisteException, TurmaNaoExisteException {
        return  this.negocioTurma.pegarTurma(id);
    }

    public List<Usuario> getUsuariosLogin() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsUsuarios();
    }

    public void adicionarTurmaEmProfessor(Turma turma, Professor professor)
            throws ClassNotFoundException, UsuarioNotFoundException, UsuarioAlreadyRegisteredException,
            TurmaNaoExisteException, IOException, TurmaRepetidaException, UsuarioNotFoundException, TurmaRepetidaException {
        this.negocioTurma.adicionarTurmaEmProfessor(turma, professor);
    }

    public void adicionarAlunoEmTurma(Turma turma, Aluno aluno)
            throws ClassNotFoundException, AlunoNotFoundException,
            TurmaNaoExisteException, IOException, InvalidDateException {
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
        this.negocioAdministrador.removerUsuario(pessoa);
    }

    public Turma ultimaTurmaAdicionada() throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        return this.negocioTurma.getUltimaTurmaAdicionada();
    }

    public Usuario buscarUsuario(Usuario usuario)
            throws UsuarioNotFoundException, IOException, ClassNotFoundException, InvalidDateException {
        return this.negocioAdministrador.buscarUsuario(usuario);
    }

    public void atualizarTurma(Turma turma, String apelido, List<String> alunos)
            throws ClassNotFoundException, IOException, TurmaNaoExisteException {
        this.negocioTurma.atualizarTurma(turma.getId(), apelido, alunos);
    }

    public void removerTurmaDoProfessor(Turma turma, Professor professor) throws ClassNotFoundException, UsuarioNotFoundException, TurmaNaoExisteException, IOException {
        this.negocioTurma.removerTurmaDoProfessor(turma, professor);
    }

    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.negocioProfessor.gerarBoletim(aluno);
    }

    public void gerarCertificadoDeMatricula(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioProfessor.gerarCertificadoDeMatricula(aluno);
    }

    public void gerarCertificadoDeCurso(AlunoHoraExtra aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.negocioProfessor.gerarCertificadoDeCurso(aluno);
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
