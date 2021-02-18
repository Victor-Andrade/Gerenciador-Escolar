package model.cruds;

import model.classes.excecoes.UsuarioNotFoundException;
import model.classes.interfaces.IRepositorioUsuarios;
import model.classes.pessoas.Pessoa;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fazer a manipulação primária de usuarios (Professor, administrador) em arquivos
 * @author Pedro Vinícius
 */

public class CRUDUsuarios implements IRepositorioUsuarios {

    @Override
    public void adicionarUsuario(Pessoa usuario) throws IOException, ClassNotFoundException {
        List<Pessoa> temp = todosOsUsuariosArray();
        temp.add(usuario);
        atualizarModificacoes(temp);
    }

    @Override
    public void removerUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException {
        List<Pessoa> temp = todosOsUsuariosArray();
        for(Pessoa usuario: temp){
            //############### NESSA LINHA #####################
            if(usuario.getNome().equalsIgnoreCase(nomeOuCpf) || usuario.getCpf().equals(nomeOuCpf)){
                temp.remove(usuario);
                break;
            }
        }
        atualizarModificacoes(temp);
    }

    @Override
    public void removerUsuario(Pessoa usuario) throws IOException, ClassNotFoundException {
        List<Pessoa> temp = todosOsUsuariosArray();
        temp.remove(usuario);
        atualizarModificacoes(temp);
    }

    @Override
    public Pessoa buscarUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        List<Pessoa> temp = todosOsUsuariosArray();

        for (Pessoa pessoaTemp : temp){
            if(pessoaTemp.getCpf().equals(nomeOuCpf) || pessoaTemp.getNome().equalsIgnoreCase(nomeOuCpf)){
                return pessoaTemp;
            }
        }

        throw new UsuarioNotFoundException(nomeOuCpf);
    }

    @Override
    public void atualizarUsuario(String nomeOuCpf, Pessoa pessoa) throws IOException, ClassNotFoundException {
        removerUsuario(nomeOuCpf);
        adicionarUsuario(pessoa);
    }

    @Override
    public boolean existeNoBanco(String nomeOuCpf) throws IOException, ClassNotFoundException {
        for(Pessoa usuario: todosOsUsuariosArray()){
            if(usuario.getCpf().equals(nomeOuCpf) || usuario.getNome().equalsIgnoreCase(nomeOuCpf)){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Pessoa> todosOsUsuariosArray() throws IOException, ClassNotFoundException {
        ArrayList<Pessoa> temp;

        FileInputStream file = new FileInputStream("usuarios.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Pessoa>) is.readObject();
        is.close();

        return temp;
    }

    private void atualizarModificacoes(List<Pessoa> pessoas) throws IOException {
        FileOutputStream file2 = new FileOutputStream("usuarios.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(pessoas);
        os.close();
    }
}
