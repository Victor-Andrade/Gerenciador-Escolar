package Classes.interfaces;

import Classes.excecoes.AlunoNotFoundException;
import Classes.pessoas.Aluno;
import Classes.excecoes.AlunoAlredyRegisteredException;

import java.io.IOException;
import java.util.List;

/**
 * Interface responsável por definir os métodos do repositório de alunos
 * @author
 */

public interface IRepositorioAlunos {
    void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException;

    void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException;

    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException;

    Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException;

    public void atualizarAluno(String nomeOuCpf, Aluno aluno) throws IOException, ClassNotFoundException;

    boolean existeNoBanco(String nomeOuCpf) throws IOException, ClassNotFoundException;

    List<Aluno> todosOsAlunosArray() throws IOException, ClassNotFoundException;
}

