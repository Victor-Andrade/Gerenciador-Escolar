package model.cruds;

import model.excecoes.UsuarioNotFoundException;
import model.interfaces.IRepositorioUsuarios;
import model.classes.pessoas.usuarios.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fazer a manipulação primária de usuarios (Professor, administrador) em arquivos
 * @author Pedro Vinícius
 */

public class CRUDUsuarios implements IRepositorioUsuarios {

    @Override
    public void adicionarUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        List<Usuario> temp = todosOsUsuariosArray();
        temp.add(usuario);
        atualizarModificacoes(temp);
    }

    @Override
    public void removerUsuario(Usuario usuario) throws IOException, ClassNotFoundException {
        List<Usuario> temp = todosOsUsuariosArray();
        temp.remove(usuario);
        atualizarModificacoes(temp);
    }

    @Override
    public Usuario buscarUsuario(Usuario pessoa) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        List<Usuario> temp = todosOsUsuariosArray();

        for (Usuario pessoaTemp : temp){
            if(pessoaTemp.equals(pessoa)){
                return pessoaTemp;
            }
        }

        throw new UsuarioNotFoundException(pessoa.getNome());
    }

    @Override
    public void atualizarUsuario(Usuario usuarioAntigo, Usuario usuario) throws IOException, ClassNotFoundException {
        List<Usuario> temp = todosOsUsuariosArray();
        int index = temp.indexOf(usuarioAntigo);
        temp.get(index).setSenha(usuario.getSenha());
        temp.get(index).setCpf(usuario.getCpf());
        temp.get(index).setEmail(usuario.getEmail());
        temp.get(index).setNome(usuario.getNome());
        temp.get(index).setDataDeNascimento(usuario.getDataDeNascimento());
        temp.get(index).setNumeroParaContato(usuario.getNumeroParaContato());
        atualizarModificacoes(temp);
    }

    @Override
    public boolean existeNoBanco(Usuario pessoa) throws IOException, ClassNotFoundException {
        for(Usuario usuario: todosOsUsuariosArray()){
            if(usuario.equals(pessoa)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Usuario> todosOsUsuariosArray() throws IOException, ClassNotFoundException {
        ArrayList<Usuario> temp;

        FileInputStream file = new FileInputStream("usuarios.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Usuario>) is.readObject();
        is.close();

        return temp;
    }

    private void atualizarModificacoes(List<Usuario> pessoas) throws IOException {
        FileOutputStream file2 = new FileOutputStream("usuarios.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(pessoas);
        os.close();
    }
}
