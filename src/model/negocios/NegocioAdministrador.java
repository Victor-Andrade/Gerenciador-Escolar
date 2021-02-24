package model.negocios;

import model.classes.Data;
import model.excecoes.*;
import model.interfaces.IRepositorioUsuarios;
import model.classes.materia.Curso;
import model.interfaces.IRepositorioAlunos;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.classesUtilitarias.Verificacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar a funcionalidades do administrador
 *
 * @author Victor Hugo e Pedro Vinícius
 */

public class NegocioAdministrador {

    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioUsuarios repositorioUsuarios;


    public NegocioAdministrador(IRepositorioAlunos repositorioAlunos, IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public ArrayList<String> todosOsAlunos() throws IOException, ClassNotFoundException {
        ArrayList<String> alunos = new ArrayList<>();
        for (Aluno aluno : this.repositorioAlunos.todosOsAlunosArray()) {
            alunos.add(aluno.getNome());
        }
        return alunos;
    }

    public ArrayList<String> todosOsProfessores() throws IOException, ClassNotFoundException {
        ArrayList<String> professores = new ArrayList<>();
        for (Usuario pessoa : this.repositorioUsuarios.todosOsUsuariosArray()) {
            if (pessoa instanceof Professor) {
                professores.add(pessoa.getNome());
            }
        }
        return professores;
    }

    public ArrayList<String> todosOsUsuariosString() throws IOException, ClassNotFoundException {
        ArrayList<String> pessoas = new ArrayList<>();
        for (Usuario pessoa : this.repositorioUsuarios.todosOsUsuariosArray()) {
            pessoas.add(pessoa.getNome());
        }
        return pessoas;
    }

    public List<Usuario> todosOsUsuarios() throws IOException, ClassNotFoundException {
        return this.repositorioUsuarios.todosOsUsuariosArray();
    }

    //MÉTODOS DE ADIÇÃO NO BANCO DE DADOS
    public void matricularAluno(String nome, String cpf, Data data, String email, String contato,
                                String emailResponsavel) throws IOException, ClassNotFoundException,
            AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if (verificarCampos(nome, cpf, data, email, contato)) {
            Aluno alunoTemp = new Aluno(nome, cpf, data, email, contato, emailResponsavel);
            if (!this.repositorioAlunos.existeNoBanco(alunoTemp)) {
                repositorioAlunos.adicionarAluno(alunoTemp);
            } else {
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato,
                                         String emailResponsavel, String curso)
            throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException,
            InvalidFieldException, InvalidDateException {
        if (verificarCampos(nome, cpf, data, email, contato)) {
            AlunoHoraExtra alunoTemp = new AlunoHoraExtra(nome, cpf, data, email, contato, emailResponsavel, new Curso(curso));
            if (!this.repositorioAlunos.existeNoBanco(alunoTemp)) {
                repositorioAlunos.adicionarAluno(alunoTemp);
            } else {
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha)
            throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException, InvalidFieldException {
        Professor professor = new Professor(nome, cpf, data, email, contato, senha);
        if (Verificacao.verificarSenha(professor)) {
            if (!repositorioUsuarios.existeNoBanco(professor)) {
                this.repositorioUsuarios.adicionarUsuario(professor);
            } else {
                throw new UsuarioAlreadyRegisteredException(professor.getNome());
            }
        }else{
            throw new InvalidFieldException("Senha", senha);
        }
    }

    public void adicionarAdministrador(String nome, String cpf, Data data, String email, String contato, String senha)
            throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException, InvalidFieldException {
        Administrador admin = new Administrador(nome, cpf, data, email, contato, senha);
        if (Verificacao.verificarSenha(admin)) {
            if (!repositorioUsuarios.existeNoBanco(admin)) {
                this.repositorioUsuarios.adicionarUsuario(admin);
            } else {
                throw new UsuarioAlreadyRegisteredException(admin.getNome());
            }
        }else{
            throw new InvalidFieldException("Senha", senha);
        }
    }

    //MÉTODOS DE REMOÇÃO NO BANCO DE DADOS

    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException,
            InvalidDateException {
        if (repositorioAlunos.existeNoBanco(aluno)) {
            repositorioAlunos.removerAluno(aluno);
        } else {
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void removerUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        if (repositorioUsuarios.existeNoBanco(usuario)) {
            repositorioUsuarios.removerUsuario(usuario);
        } else {
            throw new UsuarioNotFoundException(usuario.getNome());
        }
    }

    //MÉTODOS DE BUSCA NO BANCO DE DADOS

    public Usuario buscarUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        return this.repositorioUsuarios.buscarUsuario(usuario);
    }

    //MÉTODOS DE ATUALIZAÇÃO NO BANCO DE DADOS

    public void atualizarInformacoesAluno(Aluno alunoAntigo, String nome, String cpf, Data data, String email,
                                          String contato, String emailResponsavel)
            throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException, AlunoAlredyRegisteredException, AlunoNotFoundException {
        if (repositorioAlunos.existeNoBanco(alunoAntigo)) {
            Aluno aluno = new Aluno(nome, cpf, data, email, contato, emailResponsavel);
            if(!repositorioAlunos.existeNoBanco(aluno) || (alunoAntigo.getCpf().equalsIgnoreCase(aluno.getCpf()) && alunoAntigo.getNome().equalsIgnoreCase(aluno.getNome()))){
                if (verificarCampos(nome, cpf, data, email, contato)) {
                    repositorioAlunos.atualizarAluno(alunoAntigo, aluno);
                }
            }else{
                throw new AlunoAlredyRegisteredException(aluno.getNome(), aluno.getCpf());
            }
        }else{
            throw new AlunoNotFoundException(alunoAntigo.getNome());
        }
    }

    public void atualizarNotasAluno(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException, NotasInvalidasException {
        if(this.repositorioAlunos.existeNoBanco(aluno)){
            if(Verificacao.verificarNotas(aluno.getMaterias())){
                if(aluno instanceof AlunoHoraExtra){
                    if(Verificacao.verificarHoras(((AlunoHoraExtra) aluno).getCurso().getHoras())){
                        this.repositorioAlunos.atualizarAluno(aluno, aluno);
                    }else{
                        throw new NotasInvalidasException("Horas informadas são inválidas");
                    }
                }else{
                    this.repositorioAlunos.atualizarAluno(aluno, aluno);
                }
            }else{
                throw new NotasInvalidasException("Nota informada inválida");
            }
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void atualizarInformacoesUsuario(Usuario usuario, String nome, String cpf, Data data, String email, String contato, String senha) throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException, UsuarioAlreadyRegisteredException, UsuarioNotFoundException {
        if(this.repositorioUsuarios.existeNoBanco(usuario)){
            Usuario usuarioTemp = new Usuario(nome, cpf, data, email, contato, senha);
            if(!repositorioUsuarios.existeNoBanco(usuarioTemp)){
                if(verificarCampos(nome, cpf, data, email, contato)){
                    if(Verificacao.verificarSenha(usuarioTemp)){
                        this.repositorioUsuarios.atualizarUsuario(usuario, usuarioTemp);
                    }else{
                        throw new InvalidFieldException("Senha", usuario.getSenha());
                    }
                }
            }else{
                throw new UsuarioAlreadyRegisteredException(usuario.getNome());
            }
        }else{
            throw new UsuarioNotFoundException(usuario.getNome());
        }
    }

    /*public void confirmarJustificativaDeFalta(Aluno aluno)
            throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if (this.repositorioAlunos.existeNoBanco(aluno)) {
            aluno.removerFalta();
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        } else {
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }*/

    //Verifica os dados do alunos, não considera se ele já se encontra no banco ####ADICIONAR ALGUMA REGRA NO NOME
    private boolean verificarCampos(String nome, String cpf, Data data, String email, String contato)
            throws InvalidDateException, InvalidFieldException {
        if (Verificacao.verificarCpf(cpf)) {
            if (Verificacao.verificarEmail(email)) {
                if (Verificacao.verificarDataDeNascimento(data)) {
                    if (Verificacao.verificarNumeroParaContato(contato)) {
                        return true;
                    } else {
                        throw new InvalidFieldException("Numero" + contato);
                    }
                } else {
                    throw new InvalidFieldException("Data de nascimento" + data.formatarData());
                }
            } else {
                throw new InvalidFieldException("Email" + email);
            }
        } else {
            throw new InvalidFieldException("CPF", cpf);
        }
    }
}
