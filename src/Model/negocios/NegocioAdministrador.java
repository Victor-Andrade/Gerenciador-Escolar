package Model.negocios;

import Classes.datas.Data;
import Classes.excecoes.*;
import Classes.interfaces.IRepositorioUsuarios;
import Classes.pessoas.*;
import Classes.interfaces.IRepositorioAlunos;
import Model.negocios.classesAuxiliares.Verificacao;
import com.itextpdf.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.List;

/**
 * Classe responsável por validar as funcionalidades do administrador
 * @author
 */

public class NegocioAdministrador {

    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioUsuarios repositorioUsuarios;


    public NegocioAdministrador(IRepositorioAlunos repositorioAlunos, IRepositorioUsuarios repositorioUsuarios){
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    //Adiciona aluno o repositorio
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
        if(verificarCadastroUsuario(professor)){
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
        if(verificarCadastroUsuario(admin)){
            if(!repositorioUsuarios.existeNoBanco(admin.getCpf()) && !repositorioUsuarios.existeNoBanco(admin.getNome())){
                this.repositorioUsuarios.adicionarUsuario(admin);
            }else {
                throw new UsuarioAlreadyRegisteredException(admin.getNome());
            }
        }
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

    //Verifica se um professor já está cadastrado no banco (Nome ou cpf)
    private boolean verificarCadastroUsuario(Professor professor) throws IOException, ClassNotFoundException {
        if(repositorioUsuarios.existeNoBanco(professor.getCpf()) || repositorioUsuarios.existeNoBanco(professor.getNome())){
            return professor.getSenha().length() >= 8;
        }
        return false;
    }

    //Verifica se um admin já está cadasrtrado no banco (Nome ou cpf)
    private boolean verificarCadastroUsuario(Administrador professor) throws IOException, ClassNotFoundException {
        if(repositorioUsuarios.existeNoBanco(professor.getCpf()) || repositorioUsuarios.existeNoBanco(professor.getNome())){
            return professor.getSenha().length() >= 8;
        }
        return false;
    }

}
