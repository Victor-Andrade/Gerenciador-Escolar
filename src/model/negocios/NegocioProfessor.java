package model.negocios;

import model.classes.Data;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.excecoes.AlunoAlredyRegisteredException;
import model.excecoes.AlunoNotFoundException;
import model.classes.pessoas.alunos.Aluno;
import model.interfaces.IRepositorioAlunos;

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
    public void adicionarFalta(Aluno aluno, Data data, boolean justificar)
            throws AlunoNotFoundException, IOException, ClassNotFoundException, AlunoAlredyRegisteredException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            aluno.adicionarFalta();
            repositorioAlunos.removerAluno(aluno);
            repositorioAlunos.adicionarAluno(aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getCpf());
        }
    }

    //Gera o boletim de um aluno qualquer
    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            alunoBanco.gerarBoletim();
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void gerarCertificadoDeMatricula(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            alunoBanco.gerarCertificadoDeMatricula();
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void gerarCertificadoDeCurso(AlunoHoraExtra aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            if(alunoBanco instanceof AlunoHoraExtra){
                alunoBanco.gerarCertificadoDeMatricula();
            }
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    //Falta implementar
    public void reportarSituacaoDoAluno(){

    }

    //Falta implementar
    public void anexarArquivoNoAluno(){

    }

}
