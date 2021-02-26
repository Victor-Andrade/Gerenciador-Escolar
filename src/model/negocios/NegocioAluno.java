package model.negocios;

import model.classes.Data;
import model.classes.Situacao;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.materia.Curso;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Usuario;
import model.classesUtilitarias.Email;
import model.classesUtilitarias.Formatador;
import model.classesUtilitarias.Verificacao;
import model.excecoes.*;
import model.interfaces.IRepositorioAlunos;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe responsável por realizar as funcionalidades e manipulação de objetos de alunos
 * @author Pedro Vinícius
 */

public class NegocioAluno {
    private final IRepositorioAlunos repositorioAlunos;

    public NegocioAluno(IRepositorioAlunos repositorioAlunos ){
        this.repositorioAlunos = repositorioAlunos;
    }

    public ArrayList<String> todosOsAlunosString() throws IOException, ClassNotFoundException {
        ArrayList<String> alunos = new ArrayList<>();
        for (Aluno aluno : this.repositorioAlunos.todosOsAlunosArray()) {
            alunos.add(aluno.getNome());
        }
        return alunos;
    }

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato,
                                String emailResponsavel) throws IOException, ClassNotFoundException,
            AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if (verificarCampos(cpf, data, email, contato)) {
            String nomeMaiusculo = nome.toUpperCase();
            String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
            Aluno alunoTemp = new Aluno(nomeMaiusculo, DigitosCpf, data, email, contato, emailResponsavel);
            if (!this.repositorioAlunos.existeNoBanco(alunoTemp)) {
                repositorioAlunos.adicionarAluno(alunoTemp);
            } else {
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato,
                                         String emailResponsavel, String curso)
            throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException,
            InvalidFieldException, InvalidDateException {
        if (verificarCampos(cpf, data, email, contato)) {
            String nomeMaiusculo = nome.toUpperCase();
            String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
            AlunoHoraExtra alunoTemp = new AlunoHoraExtra(nomeMaiusculo, DigitosCpf, data, email, contato, emailResponsavel, new Curso(curso));
            if (!this.repositorioAlunos.existeNoBanco(alunoTemp)) {
                repositorioAlunos.adicionarAluno(alunoTemp);
            } else {
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    public Aluno buscarAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        return repositorioAlunos.buscarAluno(aluno);
    }

    public void removerAluno(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException,
            InvalidDateException {
        if (repositorioAlunos.existeNoBanco(aluno)) {
            repositorioAlunos.removerAluno(aluno);
        } else {
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void atualizarInformacoesAluno(Aluno alunoAntigo, String nome, String cpf, Data data, String email,
                                          String contato, String emailResponsavel)
            throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException, AlunoAlredyRegisteredException, AlunoNotFoundException {
        if (repositorioAlunos.existeNoBanco(alunoAntigo)) {
            String nomeMaiusculo = nome.toUpperCase();
            String DigitosCpf = Formatador.removerCaracteresCpf(cpf);
            Aluno aluno = new Aluno(nomeMaiusculo, DigitosCpf, data, email, contato, emailResponsavel);
            if(!repositorioAlunos.existeNoBanco(aluno) || (alunoAntigo.getCpf().equalsIgnoreCase(aluno.getCpf()) && alunoAntigo.getNome().equalsIgnoreCase(aluno.getNome()))){
                if (verificarCampos(cpf, data, email, contato)) {
                    repositorioAlunos.atualizarAluno(alunoAntigo, aluno);
                }
            }else{
                throw new AlunoAlredyRegisteredException(aluno.getNome(), aluno.getCpf());
            }
        }else{
            throw new AlunoNotFoundException(alunoAntigo.getNome());
        }
    }


    public String gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            return alunoBanco.gerarBoletim();
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public String gerarCertificadoDeMatricula(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            return alunoBanco.gerarCertificadoDeMatricula();
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public String gerarCertificadoDeCurso(AlunoHoraExtra aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            Aluno alunoBanco = this.repositorioAlunos.buscarAluno(aluno);
            if(alunoBanco instanceof AlunoHoraExtra){
                return ((AlunoHoraExtra) alunoBanco).gerarCertificadoDeCurso();
            }else{
                throw new AlunoNotFoundException(aluno.getNome());
            }
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void atualizarNotasAluno(Aluno aluno) throws AlunoNotFoundException, IOException, ClassNotFoundException, NotasInvalidasException {
        if(this.repositorioAlunos.existeNoBanco(aluno)){
            if(Verificacao.verificarNotas(aluno.getMaterias())){
                if(aluno instanceof AlunoHoraExtra){
                    if(Verificacao.verificarHoras(((AlunoHoraExtra) aluno).getCurso().getHoras())){
                        this.repositorioAlunos.atualizarAluno(aluno, aluno);
                    }else{
                        throw new NotasInvalidasException("Horas informadas são inválidas");
                    }
                }else{
                    this.repositorioAlunos.atualizarAluno(aluno, aluno);
                }
            }else{
                throw new NotasInvalidasException("Nota informada inválida");
            }
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public ArrayList<String> todosAlunosComFaltasJustificadas() throws IOException, ClassNotFoundException {
        ArrayList<String> alunos = new ArrayList<>();
        for(Aluno aluno: this.repositorioAlunos.todosOsAlunosArray()){
            for(Falta falta: aluno.getFaltas()){
                if(falta instanceof FaltaJustificada){
                    alunos.add(aluno.getNome());
                }
            }
        }
        return alunos;
    }

    public void adicionarFalta(Aluno aluno, String mensagem, Data data) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            aluno.adicionarFalta(new Falta(data, mensagem, encontrarIdFalta(aluno)));
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void adicionarFaltaJustificada(Aluno aluno, String mensagem, Data data, String caminho) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            aluno.adicionarFalta(new FaltaJustificada(data, mensagem, caminho, encontrarIdFalta(aluno)));
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public ArrayList<String> todosAlunosComSituacoes() throws IOException, ClassNotFoundException {
        ArrayList<String> alunos = new ArrayList<>();
        for(Aluno aluno: this.repositorioAlunos.todosOsAlunosArray()){
            if(!aluno.getSituacoes().isEmpty()){
                alunos.add(aluno.getNome());
            }
        }
        return alunos;
    }

    public void adicionarSituacao(Aluno aluno, Data data, String mensagem) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno)){
            aluno.adicionarSituacao(new Situacao(mensagem, data, encontrarIdSituacao(aluno)));
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    private int encontrarIdSituacao(Aluno aluno){
        int maior = 0;
        for(Situacao situacao: aluno.getSituacoes()){
            if(situacao.getId() > maior){
                maior = situacao.getId();
            }
        }
        return maior+1;
    }

    private int encontrarIdFalta(Aluno aluno){
        int maior = 0;
        for(Falta falta: aluno.getFaltas()){
            if(falta.getId() > maior){
                maior = falta.getId();
            }
        }
        return maior+1;
    }

    public void enviarEmail(Usuario usuario, String destinatario, String mensagem, String data) throws EmailException {
        Email.enviarEmail(usuario.getEmail(), usuario.getSenha(), destinatario, mensagem, data);
    }

    public void enviarEmailAnexo(Usuario usuario, String destinatario, String mensagem, String caminho) throws EmailException {
        Email.enviarEmailComAnexo(usuario.getEmail(), usuario.getSenha(), caminho, destinatario, mensagem);
    }

    public void removerFalta(Aluno aluno, Falta falta) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if (this.repositorioAlunos.existeNoBanco(aluno)) {
            aluno.removerFalta(falta);
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void removerSituacao(Aluno aluno, Situacao situacao) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if (this.repositorioAlunos.existeNoBanco(aluno)) {
            aluno.removerSituacao(situacao);
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    public void mudarStatusFalta(Aluno aluno, Falta falta) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if (this.repositorioAlunos.existeNoBanco(aluno)) {
            aluno.getFalta(falta.getId()).alterarStatus();
            this.repositorioAlunos.atualizarAluno(aluno, aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getNome());
        }
    }

    private boolean verificarCampos(String cpf, Data data, String email, String contato)
            throws InvalidDateException, InvalidFieldException {
        if (Verificacao.verificarCpf(cpf)) {
            if (Verificacao.verificarEmail(email)) {
                if (Verificacao.verificarDataDeNascimento(data)) {
                    if (Verificacao.verificarNumeroParaContato(contato)) {
                        return true;
                    } else {
                        throw new InvalidFieldException("Numero" + contato);
                    }
                } else {
                    throw new InvalidFieldException("Data de nascimento" + data.formatarData());
                }
            } else {
                throw new InvalidFieldException("Email" + email);
            }
        } else {
            throw new InvalidFieldException("CPF", cpf);
        }
    }
}
