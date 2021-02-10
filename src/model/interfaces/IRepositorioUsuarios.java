package model.interfaces;

import model.excecoes.UsuarioAlreadyRegisteredException;
import model.excecoes.UsuarioNotFoundException;
import model.classes.pessoas.Pessoa;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface responsável por definir os métodos do repositório de usuários
 * @author
 */

public interface IRepositorioUsuarios {

    void adicionarUsuario(Pessoa usuario) throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException;

    void removerUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException;

    void removerUsuario(Pessoa usuario) throws IOException, ClassNotFoundException;

    Pessoa buscarUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException, UsuarioNotFoundException;

    void atualizarUsuario(String nomeOuCpf, Pessoa usuario) throws IOException, ClassNotFoundException;

    boolean existeNoBanco(String nomeOuCpf) throws IOException, ClassNotFoundException;

    ArrayList<Pessoa> todosOsUsuariosArray() throws IOException, ClassNotFoundException;

}
