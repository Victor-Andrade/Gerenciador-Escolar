package Model.cruds;

import Classes.pessoas.Aluno;
import Classes.interfaces.IRepositorioAlunos;

import java.io.*;
import java.util.ArrayList;

public class CRUDAluno implements IRepositorioAlunos {

    @Override
    public void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();
        temp.add(aluno);

        FileOutputStream file2 = new FileOutputStream("/src/Model/alunos.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(temp);
        os.close();
    }

    @Override
    public void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        for(Aluno aluno: temp){
            if(aluno.getNome().toLowerCase().equals(nomeOuCpf) || aluno.getCpf().equals(nomeOuCpf)){
                temp.remove(aluno);
                break;
            }
        }

        FileOutputStream file2 = new FileOutputStream("/src/Model/alunos.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(temp);
        os.close();
    }

    @Override
    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        temp.remove(aluno);

        FileOutputStream file2 = new FileOutputStream("/src/Model/alunos.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(temp);
        os.close();
    }

    @Override
    public Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        for (Aluno alunoTemp: temp){
            if(alunoTemp.getCpf().equals(nomeOuCpf) || alunoTemp.getNome().toLowerCase().equals(nomeOuCpf)){
                return alunoTemp;
            }
        }
        return null;
    }

    @Override
    public void atualizarAluno(String nomeOuCpf, Aluno aluno) throws IOException, ClassNotFoundException {
        removerAluno(nomeOuCpf);
        adicionarAluno(aluno);
    }
}
