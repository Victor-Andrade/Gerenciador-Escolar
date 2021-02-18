package model.negocios;

import model.classes.datas.Data;
import model.classes.excecoes.*;
import model.classes.interfaces.ILogin;
import model.classes.interfaces.IRepositorioTurmas;
import model.classes.interfaces.IRepositorioUsuarios;
import model.classes.materia.Curso;
import model.classes.pessoas.*;
import model.classes.interfaces.IRepositorioAlunos;
import model.classes.turmas.Turma;
import model.negocios.classesAuxiliares.Verificacao;
import com.itextpdf.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar a funcionalidades do administrador
 * @author Victor Hugo e Pedro Vinícius
 */

public class NegocioAdministrador {

    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioUsuarios repositorioUsuarios;
    private final IRepositorioTurmas repositorioTurmas;


    public NegocioAdministrador(IRepositorioAlunos repositorioAlunos, IRepositorioUsuarios repositorioUsuarios, IRepositorioTurmas repositorioTurmas){
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioTurmas = repositorioTurmas;
    }

    public ArrayList<String> todosOsAlunos() throws IOException, ClassNotFoundException {
        ArrayList<String> alunos = new ArrayList<>();
        for(Aluno aluno: this.repositorioAlunos.todosOsAlunosArray()){
            alunos.add(aluno.getNome());
        }
        return alunos;
    }

    public ArrayList<String> todosOsProfessores() throws IOException, ClassNotFoundException {
        ArrayList<String> professores = new ArrayList<>();
        for(Pessoa pessoa: this.repositorioUsuarios.todosOsUsuariosArray()){
            if(pessoa instanceof Professor){
                professores.add(pessoa.getNome());
            }
        }
        return professores;
    }

    public ArrayList<String> todosOsUsuariosString() throws IOException, ClassNotFoundException {
        ArrayList<String> pessoas = new ArrayList<>();
        for(Pessoa pessoa: this.repositorioUsuarios.todosOsUsuariosArray()){
            pessoas.add(pessoa.getNome());
        }
        return pessoas;
    }

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato, String emailResponsavel) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if(verificarCampos(nome, cpf, data, email, contato)){
            if(!this.repositorioAlunos.existeNoBanco(nome) && !this.repositorioAlunos.existeNoBanco(cpf)){
                Aluno alunoTemp = new Aluno(nome, cpf, data, email, contato, emailResponsavel);
                repositorioAlunos.adicionarAluno(alunoTemp);
            }else{
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    public void matricularAlunoHoraExtra(String nome, String cpf, Data data, String email, String contato, String emailResponsavel, String curso) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if(verificarCampos(nome, cpf, data, email, contato)){
            if(!this.repositorioAlunos.existeNoBanco(nome) && !this.repositorioAlunos.existeNoBanco(cpf)){
                AlunoHoraExtra alunoTemp = new AlunoHoraExtra(nome, cpf, data, email, contato, emailResponsavel, new Curso(curso));
                repositorioAlunos.adicionarAluno(alunoTemp);
            }else{
                throw new AlunoAlredyRegisteredException(nome, cpf);
            }
        }
    }

    //Remove aluno do repositório
    public void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
            repositorioAlunos.removerAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    //Busca um aluno específico
    public Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
            return repositorioAlunos.buscarAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    //Atualiza informações de uma aluno
    public void atualizarInformacoesAluno(Aluno alunoAntigo, String nome, String cpf, Data data, String email, String contato, String emailResponsavel) throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException {
        if(verificarCampos(nome, cpf, data, email, contato)){
            if(repositorioAlunos.existeNoBanco(cpf) || this.repositorioAlunos.existeNoBanco(nome)){
                repositorioAlunos.atualizarAluno(alunoAntigo.getNome(), new Aluno(nome, cpf, data, email, contato, emailResponsavel));
            }
        }
    }

    //Gera um certificado de conclusão de um aluno com hora extra
    public void gerarCertificadoDeConclusao(AlunoHoraExtra aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno.getCpf())){
            Document documento = new Document();
            try{
                PdfWriter.getInstance(documento, new FileOutputStream("Declaração de Conlcusão.pdf"));
                documento.open();

                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 14);
                Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD | Font.ITALIC);

                //Info do aluno + nome da escola
                Paragraph titulo = new Paragraph();
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.add(new Phrase("Certificado de Conclusão de Curso", font));

                documento.add(new Paragraph("\n\n\n\n"));

                documento.add(titulo);

                documento.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));

                Paragraph conteudo = new Paragraph();

                conteudo.add(new Phrase("Certificamos que o aluno ", font2));
                conteudo.add(new Phrase(aluno.getNome(), font3));
                conteudo.add(new Phrase(" concluiu o curso ", font2));
                conteudo.add(new Phrase(aluno.getCurso().getNome(), font3));
                conteudo.add(new Phrase(" com uma carga horária de ", font2));
                conteudo.add(new Phrase(aluno.getCurso().getHoras() + " horas", font3));
                conteudo.add(new Phrase(" na instituição de ensino Escola Coffe Java Orientada a Objetos no ano de 2021."));

                conteudo.setAlignment(Element.ALIGN_CENTER);

                documento.add(conteudo);

                documento.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));

                Paragraph assinatura = new Paragraph();
                assinatura.setAlignment(Element.ALIGN_CENTER);

                assinatura.add(new Phrase("_____________________________________________________\n"));
                assinatura.add(new Phrase("Assinatura do(a) reitor", font3));

                documento.add(assinatura);
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                documento.close();
            }
        }else {
            throw new AlunoNotFoundException(aluno.getCpf());
        }
    }

    //Gera um certificado de matrícula para um aluno qualquer
    public void gerarCertificadoDeMatricula(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno.getCpf())){
            Document documento = new Document();
            try{
                PdfWriter.getInstance(documento, new FileOutputStream("Declaração de matrícula.pdf"));
                documento.open();

                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 14);
                Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD | Font.ITALIC);

                //Info do aluno + nome da escola
                Paragraph titulo = new Paragraph();
                titulo.setAlignment(Element.ALIGN_CENTER);
                titulo.add(new Phrase("Declaração de matrícula", font));

                documento.add(new Paragraph("\n\n\n\n"));

                documento.add(titulo);

                documento.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n"));

                Paragraph conteudo = new Paragraph();

                conteudo.add(new Phrase("Declaramos que o aluno ", font2));
                conteudo.add(new Phrase(aluno.getNome(), font3));
                conteudo.add(new Phrase(" inscrito no CPF: ", font2));
                conteudo.add(new Phrase(aluno.getCpf(), font3));
                conteudo.add(new Phrase(" tendo nascido em ", font2));
                conteudo.add(new Phrase(aluno.getDataDeNascimento().formatarData(), font3));
                conteudo.add(new Phrase(" está devidamente matriculado na instituição de ensino fundamental Escola Coffe Java Orientada a Objetos."));

                conteudo.setAlignment(Element.ALIGN_CENTER);

                documento.add(conteudo);

                documento.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));

                documento.add(new Paragraph("Data: ___/___/______"));

                Paragraph assinatura = new Paragraph();
                assinatura.setAlignment(Element.ALIGN_CENTER);

                documento.add(new Paragraph("\n\n"));
                assinatura.add(new Phrase("_____________________________________________________\n"));
                assinatura.add(new Phrase("Assinatura do(a) reitor", font3));

                documento.add(assinatura);
            } catch (FileNotFoundException | DocumentException e) {
                e.printStackTrace();
            }finally {
                documento.close();
            }
        }else {
            throw new AlunoNotFoundException(aluno.getCpf());
        }
    }

    //Adiciona um professor no repositório
    public void adicionarProfessor(String nome, String cpf, Data data, String email, String contato, String senha) throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException {
        Professor professor = new Professor(nome, cpf, data, email, contato, senha);
        if(verificarSenha(professor)){
            if(!repositorioUsuarios.existeNoBanco(professor.getCpf()) && !repositorioUsuarios.existeNoBanco(professor.getNome())){
                this.repositorioUsuarios.adicionarUsuario(professor);
            }else {
                throw new UsuarioAlreadyRegisteredException(professor.getNome());
            }
        }
    }

    //Adiciona um administrador no repositório
    public void adicionarAdministrador(String nome, String cpf, Data data, String email, String contato, String senha) throws IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException, InvalidFieldException, InvalidDateException {
        Administrador admin = new Administrador(nome, cpf, data, email, contato, senha);
        if(verificarSenha(admin)){
            if(!repositorioUsuarios.existeNoBanco(admin.getCpf()) && !repositorioUsuarios.existeNoBanco(admin.getNome())){
                this.repositorioUsuarios.adicionarUsuario(admin);
            }else {
                throw new UsuarioAlreadyRegisteredException(admin.getNome());
            }
        }
    }

    public void adicionarTurmaEmProfessor(Turma turma, Professor professor) throws TurmaNaoExisteException, IOException, ClassNotFoundException, UsuarioAlreadyRegisteredException, UsuarioNotFoundException {
        if(repositorioTurmas.turmaExiste(turma.getId())){
            if(repositorioUsuarios.existeNoBanco(professor.getCpf())){
                professor.adicionarTurma(turma.getId());
                this.repositorioUsuarios.removerUsuario(professor.getNome());
                this.repositorioUsuarios.adicionarUsuario(professor);
            }else{
                throw new UsuarioNotFoundException(professor.getNome());
            }
        }else{
            throw new TurmaNaoExisteException("Turma com o id : " + turma.getId() + " não existe");
        }
    }

    public Pessoa buscarUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        return this.repositorioUsuarios.buscarUsuario(nomeOuCpf);
    }

    public List<Pessoa> todosOsUsuarios() throws IOException, ClassNotFoundException {
        return this.repositorioUsuarios.todosOsUsuariosArray();
    }

    //Remove um usuário
    public void removerUsuario(String nomeOuCpf) throws IOException, ClassNotFoundException, UsuarioNotFoundException {
        if(repositorioUsuarios.existeNoBanco(nomeOuCpf)){
            repositorioUsuarios.removerUsuario(nomeOuCpf);
        }else{
            throw new UsuarioNotFoundException(nomeOuCpf);
        }
    }

    //FALTA FAZER
    public void confirmarJustificativaDeFalta(){

    }

    //FALTA FAZER
    public void reportarSituacaoDoAluno(){

    }


    //Verifica os campos de um alunos, não considera se ele já se encontra no banco
    private boolean verificarCampos(String nome, String cpf, Data data, String email, String contato) throws InvalidDateException, InvalidFieldException {
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

    //Comportamento polimórfico?
    private boolean verificarSenha(ILogin pessoa){
        return pessoa.getSenha().length() >= 8;
    }
}
