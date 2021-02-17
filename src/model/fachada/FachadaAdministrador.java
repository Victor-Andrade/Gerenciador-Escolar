package model.fachada;

import model.classes.datas.Data;
import model.classes.excecoes.*;
import model.classes.pessoas.Administrador;
import model.classes.pessoas.Aluno;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.Professor;
import model.classes.turmas.Turma;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
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
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDUsuarios(), new CRUDTurma());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
    }

    public void adicionarAdministrador(String nome, String cpf, Data data, String email, String contato, String senha) throws ClassNotFoundException, InvalidFieldException, UsuarioAlreadyRegisteredException, InvalidDateException, IOException {
        this.negocioAdministrador.adicionarAdministrador(nome, cpf, data, email, contato, senha);
    }

    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha) throws UsuarioAlreadyRegisteredException, IOException, ClassNotFoundException {
        this.negocioAdministrador.adicionarProfessor(nome, cpf,  data, email, contato, senha);
    }

    public void adicionarTurma(String apelido, List<String> alunos) throws InvalidFieldException, ClassNotFoundException, TurmaRepetidaException, IOException {
        this.negocioTurma.adicionarTurma(apelido, alunos);
    }

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato, String emailResponsavel) throws ClassNotFoundException, InvalidFieldException, AlunoAlredyRegisteredException, InvalidDateException, IOException {
        this.negocioAdministrador.matricularAluno(nome, cpf, data, email,  contato, emailResponsavel);
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato, String emailResponsavel, String curso) throws ClassNotFoundException, InvalidFieldException, AlunoAlredyRegisteredException, InvalidDateException, IOException {
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

    public Aluno buscarAluno(String nomeOuCpf) throws AlunoNotFoundException, IOException, ClassNotFoundException {
        return this.negocioAdministrador.buscarAluno(nomeOuCpf);
    }

    public List<Pessoa> getUsuariosLogin() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsUsuarios();
    }

    public void adicionarTurmaEmProfessor(Turma turma, Professor professor) throws ClassNotFoundException, UsuarioNotFoundException, UsuarioAlreadyRegisteredException, TurmaNaoExisteException, IOException {
        this.negocioAdministrador.adicionarTurmaEmProfessor(turma, professor);
    }

    public Turma ultimaTurmaAdicionada() throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        return this.negocioTurma.getUltimaTurmaAdicionada();
    }

    public Pessoa buscarUsuario(String nomeOuCpf) throws UsuarioNotFoundException, IOException, ClassNotFoundException {
        return this.negocioAdministrador.buscarUsuario(nomeOuCpf);
    }

    public void adicionarAdmPadrao(){
        try{
            Data data = new Data(2021, 2, 10);
            Administrador adm = new Administrador("admin", "12345678910", data
                    ,"admin@admin.com", "(87)99999-9999", "admin123");
            List<Pessoa> p = new ArrayList<Pessoa>(10);
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
