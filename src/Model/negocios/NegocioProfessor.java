package Model.negocios;

import Classes.datas.Data;
import Classes.pessoas.Aluno;
import Classes.interfaces.IRepositorioAlunos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NegocioProfessor {
    private final IRepositorioAlunos repositorio;

    public NegocioProfessor(IRepositorioAlunos repositorio){
        this.repositorio = repositorio;
    }

    private ArrayList<Aluno> recuperarBancoArray() throws IOException, ClassNotFoundException {
        ArrayList<Aluno> temp;

        FileInputStream file = new FileInputStream("/src/Model/alunos.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Aluno>) is.readObject();
        is.close();

        return temp;
    }

    public void adicionarFalta(Aluno aluno, Data data, boolean justificar){
        aluno.adicionarFalta(data, justificar);
    }


}
