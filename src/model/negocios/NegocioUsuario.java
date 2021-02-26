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

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public ArrayList<String> todosOsProfessores() throws IOException, ClassNotFoundException {
        ArrayList<String> professores = new ArrayList<>();
        for (Usuario pessoa : this.repositorioUsuarios.todosOsUsuariosArray()) {
            if (pessoa instanceof Professor) {
                professores.add(pessoa.getNome());
            }
        }
        return professores;
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public ArrayList<String> todosOsUsuariosString() throws IOException, ClassNotFoundException {
        ArrayList<String> pessoas = new ArrayList<>();
        for (Usuario pessoa : this.repositorioUsuarios.todosOsUsuariosArray()) {
            pessoas.add(pessoa.getNome());
        }
        return pessoas;
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     */
    public List<Usuario> todosOsUsuarios() throws IOException, ClassNotFoundException {
        return this.repositorioUsuarios.todosOsUsuariosArray();
    }


    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws UsuarioAlreadyRegisteredException Trata para que não exista usuários iguais cadastrados no banco
     * @throws InvalidFieldException Faz o tratamento para que não existam informações inválidas do professor
     */
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

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws UsuarioAlreadyRegisteredException Trata para que não exista usuários iguais cadastrados no banco
     * @throws InvalidFieldException Faz o tratamento para que não existam informações inválidas do professor
     */
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

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws UsuarioNotFoundException Informa que o usuário a ser removido não foi encontrado
     */
    public void removerUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        if (repositorioUsuarios.existeNoBanco(usuario)) {
            repositorioUsuarios.removerUsuario(usuario);
        } else {
            throw new UsuarioNotFoundException(usuario.getNome());
        }
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws UsuarioNotFoundException Informa que o usuário buscado não foi encontrado
     */
    public Usuario buscarUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        return this.repositorioUsuarios.buscarUsuario(usuario);
    }

    /**
     * @throws IOException Faz o tratamento para caso o arquivo não seja encontrado
     * @throws ClassNotFoundException Faz o tratamento para caso a classe do arquivo não seja do tipo esperado
     * @throws InvalidFieldException Garante que nenhuma informação do usuário seja inválida
     * @throws InvalidDateException Informa que a data é inválida
     * @throws UsuarioAlreadyRegisteredException Trata para que não existam usuários repetidos ao atualizar
     * @throws UsuarioNotFoundException Informa que o usuário a ser atualizado não foi encontrado
     */
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
