package model.cruds;

import model.classes.excecoes.AlunoNotFoundException;
import model.classes.pessoas.Aluno;
import model.classes.interfaces.IRepositorioAlunos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fazer a manipulação primária de objetos de Aluno em arquivos
 * @author Pedro Vinícius
 */

public class CRUDAlunos implements IRepositorioAlunos {

    @Override
    public void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();
        temp.add(aluno);
        atualizarModificacoes(temp);
    }

    @Override
    public void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();

        for(Aluno aluno: temp){
            if(aluno.equals(nomeOuCpf)){
                temp.remove(aluno);
                break;
            }
        }

        atualizarModificacoes(temp);
    }

    @Override
    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();

        temp.remove(aluno);

        atualizarModificacoes(temp);
    }

    @Override
    public Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();

        for (Aluno alunoTemp: temp){
            if(alunoTemp.equals(nomeOuCpf)){
                return alunoTemp;
            }
        }
        throw new AlunoNotFoundException(nomeOuCpf);
    }

    @Override
    public void atualizarAluno(String nomeOuCpf, Aluno aluno) throws IOException, ClassNotFoundException {
        removerAluno(nomeOuCpf);
        adicionarAluno(aluno);
    }

    @Override
    public boolean existeNoBanco(String nomeOuCpf) throws IOException, ClassNotFoundException {
        for(Aluno aluno: todosOsAlunosArray()){
            if(aluno.equals(nomeOuCpf)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Aluno> todosOsAlunosArray() throws IOException, ClassNotFoundException {
        List<Aluno> temp;

        FileInputStream file = new FileInputStream("alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        return temp;

    }

    private void atualizarModificacoes(List<Aluno> alunos) throws IOException {
        FileOutputStream file2 = new FileOutputStream("alunos.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(alunos);
        os.close();
    }
}
