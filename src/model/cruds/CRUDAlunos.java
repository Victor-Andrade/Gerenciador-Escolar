package model.cruds;

import model.classes.excecoes.AlunoNotFoundException;
import model.classes.pessoas.alunos.Aluno;
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
    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();

        temp.remove(aluno);

        atualizarModificacoes(temp);
    }

    @Override
    public Aluno buscarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        List<Aluno> temp = todosOsAlunosArray();

        for (Aluno alunoTemp: temp){
            if(alunoTemp.equals(aluno)){
                return alunoTemp;
            }
        }
        throw new AlunoNotFoundException(aluno.getNome());
    }

    @Override
    public void atualizarAluno(Aluno alunoAntigo, Aluno aluno) throws IOException, ClassNotFoundException {
        removerAluno(alunoAntigo);
        adicionarAluno(aluno);
    }

    @Override
    public boolean existeNoBanco(Aluno aluno) throws IOException, ClassNotFoundException {
        for(Aluno alunoTemp: todosOsAlunosArray()){
            if(alunoTemp.equals(aluno)){
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
