package model.classes.pessoas.alunos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.classes.datas.Data;
import model.classes.excecoes.InvalidDateException;
import model.classes.faltas.Falta;
import model.classes.materia.Bimestre;
import model.classes.materia.Materia;
import model.classes.excecoes.InvalidFieldException;
import model.classes.pessoas.Pessoa;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe representando o aluno base no sistema
 * @author Victor Hugo e Pedro Vinícius
 */

public class Aluno extends Pessoa {
    private final List<Materia> materias;
    private int faltas;
    private String emailPais;

    public Aluno(String nome, String cpf, Data data, String email, String contato, String emailPais) throws InvalidDateException {
        super(nome, cpf, data, email, contato);
        this.materias =  new ArrayList<>();
        this.emailPais = emailPais;
        this.faltas = 0;
        inicializarMaterias();
    }

    public String getEmailPais() {
        return this.emailPais;
    }

    public void setEmailPais(String emailPais) {
        this.emailPais = emailPais;
    }

    public List<Materia> getMaterias() {
        return this.materias;
    }

    public void adicionarFalta(){
        this.faltas++;
    }

    public void removerFalta(){
        this.faltas--;
    }

    public int getFaltas(){
        return this.faltas;
    }

    public void gerarBoletim(){
        Document documento = new Document();
        documento.setPageSize(PageSize.A4.rotate());


        try {
            PdfWriter.getInstance(documento, new FileOutputStream("documento.pdf"));
            documento.open();

            String[] cabecalho  = {" ", "1º Bimestre", "2º Bimestre", "3º Bimestre", "4º Bimestre"};
            String[] cabecalhoNotas =  {"Nota 1", "Nota 2", "Media"};

            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
            Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 14);

            //Info do aluno + nome da escola
            documento.add(new Paragraph(new Phrase("Escola Coffe Java Orientada a Objetos", font)));
            documento.add(new Phrase("Nome: " + this.getNome(), font2));
            documento.add(new Phrase("   | Data de nascimento: "+ this.getDataDeNascimento().formatarData(), font2));
            documento.add(new Paragraph(new Phrase("Email: " + this.getEmail(), font2)));
            documento.add(new Paragraph(new Phrase("CPF: " + this.getCpf(), font2)));
            documento.add(new Paragraph(new Phrase("Número para contato: " + this.getNumeroParaContato(), font2)));
            documento.add(new Paragraph("\n"));

            //Nome Boletim
            Paragraph titulo = new Paragraph();
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.add(new Phrase("BOLETIM", font));
            documento.add(titulo);
            documento.add(new Paragraph("\n"));
            documento.add(new Paragraph("\n"));


            //Cabeçalho
            PdfPTable table = new PdfPTable(5);
            for(String materia: cabecalho){
                table.addCell(new PdfPCell(new Paragraph(materia)));
            }
            documento.add(table);

            //Cria o cabecalho das notas
            PdfPTable headerNotas = new PdfPTable(3);
            for(String notas: cabecalhoNotas){
                headerNotas.addCell(new PdfPCell(new Paragraph(notas)));
            }

            PdfPTable cabecalho2 = new PdfPTable(5);
            //Adiciona o cabecalho das notas no PDF
            cabecalho2.addCell(new PdfPCell(new Paragraph(" ")));
            cabecalho2.addCell(new PdfPCell(headerNotas));
            cabecalho2.addCell(new PdfPCell(headerNotas));
            cabecalho2.addCell(new PdfPCell(headerNotas));
            cabecalho2.addCell(new PdfPCell(headerNotas));
            cabecalho2.addCell(new PdfPCell(headerNotas));

            documento.add(cabecalho2);

            //Adiciona as notas
            PdfPTable materias = new PdfPTable(5);
            for(Materia materia : this.materias){
                PdfPTable b1 = new PdfPTable(3);
                PdfPTable b2 = new PdfPTable(3);
                PdfPTable b3 = new PdfPTable(3);
                PdfPTable b4 = new PdfPTable(3);

                Bimestre bimestre1 = materia.getPrimeiroBimestre();
                Bimestre bimestre2 = materia.getSegundoBimestre();
                Bimestre bimestre3 = materia.getTerceiroBimestre();
                Bimestre bimestre4 = materia.getQuartoBimestre();

                materias.addCell(new PdfPCell(new Paragraph(materia.getNome())));
                for(int i = 1; i <= 4; i++){
                    PdfPTable b;
                    Bimestre bi;
                    switch (i){
                        case 1: {
                            b = b1;
                            bi = bimestre1;
                            break;
                        }
                        case 2: {
                            b = b2;
                            bi = bimestre2;
                            break;
                        }
                        case 3: {
                            b = b3;
                            bi = bimestre3;
                            break;
                        }
                        case 4: {
                            b = b4;
                            bi = bimestre4;
                            break;
                        }
                        default:
                            throw new IllegalStateException("Unexpected value: " + i);
                    }
                    //Adição do parágrafo
                    b.addCell(new PdfPCell(new Paragraph(Double.toString(bi.getNota1()))));
                    b.addCell(new PdfPCell(new Paragraph(Double.toString(bi.getNota2()))));
                    b.addCell(new PdfPCell(new Paragraph(Double.toString(bi.calcularMedia()))));

                    materias.addCell(new PdfPCell(b));

                    if(i == 4){
                        documento.add(materias);
                        materias = new PdfPTable(5);
                    }
                }
            }
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } finally {
            documento.close();
        }
    }

    public void gerarCertificadoDeMatricula(){
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
            conteudo.add(new Phrase(this.getNome(), font3));
            conteudo.add(new Phrase(" inscrito no CPF: ", font2));
            conteudo.add(new Phrase(this.getCpf(), font3));
            conteudo.add(new Phrase(" tendo nascido em ", font2));
            conteudo.add(new Phrase(this.getDataDeNascimento().formatarData(), font3));
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
    }

    public Materia getMateria(String nomeDaMateria) {
        for(Materia materia: this.materias){
            if(nomeDaMateria.equalsIgnoreCase(materia.getNome())){
                return materia;
            }
        }
        return null;
    }

    private void inicializarMaterias(){
        this.materias.add(new Materia("Português"));
        this.materias.add(new Materia("Matemática"));
        this.materias.add(new Materia("História"));
        this.materias.add(new Materia("Geografia"));
        this.materias.add(new Materia("Ed. Fisica"));
        this.materias.add(new Materia("Artes"));
    }
}