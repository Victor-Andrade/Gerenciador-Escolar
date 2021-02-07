package Classes.interfaces;

import Classes.excecoes.UsuarioAlreadyRegisteredException;
import Classes.excecoes.UsuarioNotFoundException;
import Classes.pessoas.Pessoa;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepositorioUsuarios {

    void adicionarUsuario(Pessoa usuario) throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException;

    void removerUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException;

    void removerUsuario(Pessoa usuario) throws IOException, ClassNotFoundException;

    Pessoa buscarUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException, UsuarioNotFoundException;

    void atualizarUsuario(String nomeOuCpf, Pessoa usuario) throws IOException, ClassNotFoundException;

    boolean existeNoBanco(String nomeOuCpf) throws IOException, ClassNotFoundException;

    ArrayList<Pessoa> todosOsUsuariosArray() throws IOException, ClassNotFoundException;
}
