package Classes.interfaces;

import Classes.pessoas.Aluno;
import Classes.excecoes.AlunoAlredyRegisteredException;

import java.io.IOException;
import java.util.ArrayList;

public interface IRepositorioAlunos {
    void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException;

    void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException;

    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException;

    Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException;

    public void atualizarAluno(String nomeOuCpf, Aluno aluno) throws IOException, ClassNotFoundException;

    static boolean existeNoBanco(ArrayList<Aluno> temp, Aluno aluno) {
        for(Aluno alunoTemp: temp){
            if(alunoTemp.getCpf().equals(aluno.getCpf()) || alunoTemp.getNome().equals(aluno.getNome())){
                return true;
            }
        }
        return false;
    }

    static boolean existeNoBanco(ArrayList<Aluno> temp, String nomeOuCpf) {
        for(Aluno alunoTemp: temp){
            if(alunoTemp.getCpf().equals(nomeOuCpf) || alunoTemp.getNome().equals(nomeOuCpf)){
                return true;
            }
        }
        return false;
    }
}

