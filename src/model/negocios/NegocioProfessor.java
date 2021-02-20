package model.negocios;

import model.classes.datas.Data;
import model.classes.excecoes.AlunoAlredyRegisteredException;
import model.classes.excecoes.AlunoNotFoundException;
import model.classes.pessoas.alunos.Aluno;
import model.classes.interfaces.IRepositorioAlunos;

import java.io.*;

/**
 * Classe responsável por realizar a funcionalidades do professor
 * @author Pedro Vinícius
 */

public class NegocioProfessor {
    private final IRepositorioAlunos repositorioAlunos;


    public NegocioProfessor(IRepositorioAlunos repositorioAlunos ){
        this.repositorioAlunos = repositorioAlunos;
    }

    public Aluno buscarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        return repositorioAlunos.buscarAluno(aluno);
    }

    //Adiciona uma falta em um aluno e atualiza o repositorio ++++++++ FALTA IMPLEMENTAR
    public void adicionarFalta(Aluno aluno, Data data, boolean justificar) throws AlunoNotFoundException, IOException, ClassNotFoundException, AlunoAlredyRegisteredException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            aluno.adicionarFalta();
            repositorioAlunos.removerAluno(aluno);
            repositorioAlunos.adicionarAluno(aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getCpf());
        }
    }

    //Gera o boletim de um aluno qualquer
    public void gerarBoletim(Aluno aluno){
        aluno.gerarBoletim();
    }

    //Falta implementar
    public void reportarSituacaoDoAluno(){

    }

    //Falta implementar
    public void anexarArquivoNoAluno(){

    }

}
