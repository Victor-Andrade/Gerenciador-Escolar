package model.negocios;

import model.classes.datas.Data;
import model.excecoes.AlunoAlredyRegisteredException;
import model.excecoes.AlunoNotFoundException;
import model.interfaces.IRepositorioTurmas;
import model.classes.materia.Bimestre;
import model.classes.materia.Materia;
import model.classes.pessoas.Aluno;
import model.interfaces.IRepositorioAlunos;
import model.classes.pessoas.Professor;
import model.classes.turmas.Turma;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe responsável por realizar a funcionalidades do professor
 * @author
 */

public class NegocioProfessor {
    private final IRepositorioAlunos repositorioAlunos;
    private final IRepositorioTurmas repositorioTurmas;


    public NegocioProfessor(IRepositorioAlunos repositorioAlunos, IRepositorioTurmas repositorioTurmas){
        this.repositorioAlunos = repositorioAlunos;
        this.repositorioTurmas = repositorioTurmas;
    }

    //Adiciona uma falta em um aluno e atualiza o repositorio ++++++++ FALTA IMPLEMENTAR
    public void adicionarFalta(Aluno aluno, Data data, boolean justificar) throws AlunoNotFoundException, IOException, ClassNotFoundException, AlunoAlredyRegisteredException {
        if(repositorioAlunos.existeNoBanco(aluno.getNome())){
            aluno.adicionarFalta(data, justificar);
            repositorioAlunos.removerAluno(aluno);
            repositorioAlunos.adicionarAluno(aluno);
        }else{
            throw new AlunoNotFoundException(aluno.getCpf());
        }
    }

    //Gera o boletim de um aluno qualquer
    public void gerarBoletim(Aluno aluno) throws IOException, ClassNotFoundException, AlunoNotFoundException {
        if(repositorioAlunos.existeNoBanco(aluno.getNome())){
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
                documento.add(new Phrase("Nome: " + aluno.getNome(), font2));
                documento.add(new Phrase("   | Data de nascimento: "+ aluno.getDataDeNascimento().formatarData(), font2));
                documento.add(new Paragraph(new Phrase("Email: " + aluno.getEmail(), font2)));
                documento.add(new Paragraph(new Phrase("CPF: " + aluno.getCpf(), font2)));
                documento.add(new Paragraph(new Phrase("Número para contato: " + aluno.getNumeroParaContato(), font2)));
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
                for(Materia materia : aluno.getMaterias()){
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

    //Atualiza o arrayLista turmas de uma professor Obs: não os nomes (Função provavelmente usada exclusivamente nos Controllers);
    public void recuperarTurmasProfessor(Professor professor) throws IOException, ClassNotFoundException {
        ArrayList<Turma> turmas = new ArrayList<>();
        for(double id : professor.getTurmas()){
            for(Turma turma: repositorioTurmas.listarTurmas()){
                if(turma.getId() == id){
                    turmas.add(turma);
                }
            }
        }
        professor.setTurmasArrayList(turmas);
    }
}
