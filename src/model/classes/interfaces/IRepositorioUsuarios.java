package model.classes.interfaces;

import model.classes.excecoes.UsuarioAlreadyRegisteredException;
import model.classes.excecoes.UsuarioNotFoundException;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.usuarios.Usuario;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface responsável por definir os métodos do repositório de usuários
 * @author Victor Hugo
 */

public interface IRepositorioUsuarios {

    void adicionarUsuario(Usuario usuario) throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException;

    void removerUsuario(Usuario usuario) throws IOException, ClassNotFoundException;

    Usuario buscarUsuario(Usuario pessoa) throws IOException, ClassNotFoundException, UsuarioNotFoundException;

    void atualizarUsuario(Usuario usuarioAntigo, Usuario usuario) throws IOException, ClassNotFoundException;

    boolean existeNoBanco(Usuario pessoa) throws IOException, ClassNotFoundException;

    ArrayList<Usuario> todosOsUsuariosArray() throws IOException, ClassNotFoundException;

}
