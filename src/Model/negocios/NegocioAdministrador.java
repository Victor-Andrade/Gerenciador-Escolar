package Model.negocios;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.excecoes.AlunoAlredyRegisteredException;
import Classes.excecoes.AlunoNotFoundException;
import Classes.interfaces.IRepositorioAlunos;
import Model.negocios.classesAuxiliares.Verificacao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class NegocioAdministrador {

    private final IRepositorioAlunos repositorio;

    public NegocioAdministrador(IRepositorioAlunos repositorio){
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

    public void adicionarAluno(String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if(verificarCampos(nome, cpf, data, email, contato)){
            Aluno alunoTemp = new Aluno(nome, cpf, data, email, contato);
            repositorio.adicionarAluno(alunoTemp);
        }
    }

    public void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(IRepositorioAlunos.existeNoBanco(recuperarBancoArray(),nomeOuCpf)){
            repositorio.removerAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    public Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(IRepositorioAlunos.existeNoBanco(recuperarBancoArray(), nomeOuCpf)){
            return repositorio.buscarAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    public void atualizarInformacoesAluno(Aluno alunoAntigo, String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, AlunoNotFoundException, InvalidFieldException, InvalidDateException {
        if(IRepositorioAlunos.existeNoBanco(recuperarBancoArray(), alunoAntigo.getCpf()) || IRepositorioAlunos.existeNoBanco(recuperarBancoArray(), alunoAntigo.getNome())){
            if(verificarCamposAtualizacao(nome, cpf, data, email, contato)){
                repositorio.atualizarAluno(alunoAntigo.getNome(), new Aluno(nome, cpf, data, email, contato));
            }
        }
    }

    private boolean verificarCampos(String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, InvalidDateException, InvalidFieldException, AlunoAlredyRegisteredException {
        if(!IRepositorioAlunos.existeNoBanco(recuperarBancoArray(), nome)){
            if(Verificacao.verificarCpf(cpf)){
                if(!IRepositorioAlunos.existeNoBanco(recuperarBancoArray(), cpf)){
                    if(Verificacao.verificarEmail(email)){
                        if(Verificacao.verificarDataDeNascimento(data)){
                            if(Verificacao.verificarNumeroParaContato(contato)){
                                return true;
                            }else{
                                throw new InvalidFieldException("Numero"  + contato);
                            }
                        }else{
                            throw new InvalidFieldException("Data de nascimento"  + data.formatarData());
                        }
                    }else{
                        throw new InvalidFieldException("Email" + email);
                    }
                } else{
                    throw new AlunoAlredyRegisteredException(nome, cpf);
                }
            }else{
                throw new InvalidFieldException("CPF", cpf);
            }
        }else{
            throw new AlunoAlredyRegisteredException(nome, cpf);
        }
    }

    private boolean verificarCamposAtualizacao(String nome, String cpf, Data data, String email, String contato) throws InvalidDateException, InvalidFieldException {
        if(Verificacao.verificarCpf(cpf)){
            if(Verificacao.verificarEmail(email)){
                if(Verificacao.verificarDataDeNascimento(data)){
                    if(Verificacao.verificarNumeroParaContato(contato)){
                        return true;
                    }else{
                        throw new InvalidFieldException("Numero"  + contato);
                    }
                }else{
                    throw new InvalidFieldException("Data de nascimento"  + data.formatarData());
                }
            }else{
                throw new InvalidFieldException("Email" + email);
            }
        }else{
            throw new InvalidFieldException("CPF", cpf);
        }
    }
}
