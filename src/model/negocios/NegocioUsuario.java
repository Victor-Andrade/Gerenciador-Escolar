package model.negocios;

import model.classes.Data;
import model.classesUtilitarias.Formatador;
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

public class NegocioUsuario {

    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioUsuarios repositorioUsuarios;


    public NegocioUsuario(IRepositorioAlunos repositorioAlunos, IRepositorioUsuarios repositorioUsuarios) {
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
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

    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha)
            throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException, InvalidFieldException {
        String nomeMaiusculo = nome.toUpperCase();
        String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
        Professor professor = new Professor(nomeMaiusculo, DigitosCpf, data, email, contato, senha);
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
        String nomeMaiusculo = nome.toUpperCase();
        String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
        Administrador admin = new Administrador(nomeMaiusculo, DigitosCpf, data, email, contato, senha);
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

    public void removerUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        if (repositorioUsuarios.existeNoBanco(usuario)) {
            repositorioUsuarios.removerUsuario(usuario);
        } else {
            throw new UsuarioNotFoundException(usuario.getNome());
        }
    }

    public Usuario buscarUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        return this.repositorioUsuarios.buscarUsuario(usuario);
    }

    public void atualizarInformacoesUsuario(Usuario usuario, String nome, String cpf, Data data, String email, String contato, String senha) throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException, UsuarioAlreadyRegisteredException, UsuarioNotFoundException {
        if(this.repositorioUsuarios.existeNoBanco(usuario)){
            String nomeMaiusculo = nome.toUpperCase();
            String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
            Usuario usuarioTemp = new Usuario(nomeMaiusculo, DigitosCpf, data, email, contato, senha);
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
