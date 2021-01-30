package Controller;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.pessoas.Aluno;
import Classes.pessoas.excecoes.InvalidFieldException;
import Controller.excecoes.ObjectNotFoundException;

import java.io.*;
import java.util.ArrayList;

public class CRUDAluno {

    public static void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException {
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

    public static void removerAluno(Aluno aluno) throws ObjectNotFoundException, IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        try{
            temp.remove(aluno);
        }catch (Exception e){
            throw new ObjectNotFoundException();
        }

        FileOutputStream file2 = new FileOutputStream("/src/Model/alunos.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(temp);
        os.close();
    }

    public static Aluno buscarAluno(String nomeOuCpf) throws ObjectNotFoundException, IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        for (Aluno alunoTemp: temp){
            if(alunoTemp.getCpf().equals(nomeOuCpf) || alunoTemp.getNome().equals(nomeOuCpf)){
                return alunoTemp;
            }
        }

        throw new ObjectNotFoundException();
    }

    public static void atualizarAluno(String nomeOuCpf, String nome, String cpf, Data data, String email, String contato) throws ObjectNotFoundException, IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException {
        Aluno temp = buscarAluno(nomeOuCpf);
        removerAluno(temp);

        temp.setNome(nome);
        temp.setCpf(cpf);
        temp.setDataDeNascimento(data);
        temp.setEmail(email);
        temp.setNumeroParaContato(contato);

        adicionarAluno(temp);
    }
}
