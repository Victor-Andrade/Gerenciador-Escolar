package Model.negocios;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.interfaces.IRepositorioTurmas;
import Classes.interfaces.IRepositorioUsuarios;
import Classes.pessoas.Aluno;
import Classes.excecoes.InvalidFieldException;
import Classes.excecoes.AlunoAlredyRegisteredException;
import Classes.excecoes.AlunoNotFoundException;
import Classes.interfaces.IRepositorioAlunos;
import Classes.pessoas.AlunoHoraExtra;
import Model.negocios.classesAuxiliares.Verificacao;
import com.itextpdf.text.Document;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class NegocioAdministrador {

    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioTurmas repositorioTurmas;
    private final IRepositorioUsuarios repositorioUsuarios;


    public NegocioAdministrador(IRepositorioAlunos repositorioAlunos, IRepositorioTurmas repositorioTurmas, IRepositorioUsuarios repositorioUsuarios){
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioTurmas = repositorioTurmas;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Colocar um try catch nos CRUDS para remover IOException, ClassNotFoundException dos métodos?
     */

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, AlunoAlredyRegisteredException, InvalidFieldException, InvalidDateException {
        if(verificarCampos(nome, cpf, data, email, contato)){
            Aluno alunoTemp = new Aluno(nome, cpf, data, email, contato);
            repositorioAlunos.adicionarAluno(alunoTemp);
        }
    }

    public void removerAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
            repositorioAlunos.removerAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    public Aluno buscarAluno(String nomeOuCpf) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(nomeOuCpf)){
            return repositorioAlunos.buscarAluno(nomeOuCpf);
        }else{
            throw new AlunoNotFoundException(nomeOuCpf);
        }
    }

    public void atualizarInformacoesAluno(Aluno alunoAntigo, String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, InvalidFieldException, InvalidDateException {
        if(verificarCamposAtualizacao(nome, cpf, data, email, contato)){
            if(repositorioAlunos.existeNoBanco(cpf) || repositorioAlunos.existeNoBanco(nome)){
                if(verificarCamposAtualizacao(nome, cpf, data, email, contato)){
                    repositorioAlunos.atualizarAluno(alunoAntigo.getNome(), new Aluno(nome, cpf, data, email, contato));
                }
            }
        }
    }

    public void gerarCertificadoDeConclusao(AlunoHoraExtra aluno) throws IOException, ClassNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno.getNome())){
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
        }
    }

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

    private boolean verificarCampos(String nome, String cpf, Data data, String email, String contato) throws IOException, ClassNotFoundException, InvalidDateException, InvalidFieldException, AlunoAlredyRegisteredException {
        if(!repositorioAlunos.existeNoBanco(nome)){
            if(Verificacao.verificarCpf(cpf)){
                if(!repositorioAlunos.existeNoBanco(cpf)){
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
