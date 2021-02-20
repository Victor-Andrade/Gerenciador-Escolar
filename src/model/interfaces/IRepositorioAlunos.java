package model.interfaces;

import model.excecoes.AlunoNotFoundException;
import model.classes.pessoas.alunos.Aluno;
import model.excecoes.AlunoAlredyRegisteredException;

import java.io.IOException;
import java.util.List;

/**
 * Interface responsável por definir os métodos do repositório de alunos
 * @author Pedro Vinícius
 */

public interface IRepositorioAlunos {
    void adicionarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException;

    void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException;

    Aluno buscarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException;

    void atualizarAluno(Aluno alunoAntigo, Aluno aluno) throws IOException, ClassNotFoundException;

    boolean existeNoBanco(Aluno aluno) throws IOException, ClassNotFoundException;

    List<Aluno> todosOsAlunosArray() throws IOException, ClassNotFoundException;
}

