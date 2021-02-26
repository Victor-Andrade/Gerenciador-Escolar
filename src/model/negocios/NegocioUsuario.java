package model.negocios;

import model.classes.Data;
import model.classesUtilitarias.Formatador;
import model.excecoes.*;
import model.interfaces.IRepositorioUsuarios;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.classesUtilitarias.Verificacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar as funcionalidades e manipulação de objetos de usuários
 * @author Victor Hugo e Pedro Vinícius
 */

public class NegocioUsuario {

    private final IRepositorioUsuarios repositorioUsuarios;

    public NegocioUsuario(IRepositorioUsuarios repositorioUsuarios) {
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
            if(repositorioUsuarios.existeNoBanco(usuarioTemp)){
                if(verificarCampos(cpf, data, email, contato)){
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

    private boolean verificarCampos(String cpf, Data data, String email, String contato)
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
