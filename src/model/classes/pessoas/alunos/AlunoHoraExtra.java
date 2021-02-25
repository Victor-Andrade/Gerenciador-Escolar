package model.classes.pessoas.alunos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.classes.Data;
import model.classesUtilitarias.GerenciadorDeArquivos;
import model.excecoes.InvalidDateException;
import model.classes.materia.Curso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Extensão da classe de aluno, guarda um curso que o aluno pode fazer
 * @author Pedro Vinícius
 */

public class AlunoHoraExtra extends Aluno{
    private Curso curso;

    public AlunoHoraExtra(String nome, String cpf, Data data, String email, String contato, String emailPais,
                          Curso curso) throws InvalidDateException {
        super(nome, cpf, data, email, contato, emailPais);
        this.curso = curso;
    }

    public String gerarCertificadoDeCurso(){
        Document documento = new Document();
        String caminho = GerenciadorDeArquivos.selecionarPasta();
        try{
            PdfWriter.getInstance(documento, new FileOutputStream(caminho+"/Declaração de Conlcusão.pdf"));
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
            conteudo.add(new Phrase(this.getNome(), font3));
            conteudo.add(new Phrase(" concluiu o curso ", font2));
            conteudo.add(new Phrase(this.curso.getNome(), font3));
            conteudo.add(new Phrase(" com uma carga horária de ", font2));
            conteudo.add(new Phrase(this.curso.getHoras() + " horas", font3));
            conteudo.add(new Phrase(" na instituição de ensino Escola " +
                    "Coffe Java Orientada a Objetos no ano de 2021."));

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
        return caminho+"/Declaração de Conlcusão.pdf";
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
