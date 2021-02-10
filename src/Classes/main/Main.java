package Classes.main;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.excecoes.InvalidFieldException;
import Classes.pessoas.Administrador;
import Classes.pessoas.Aluno;
import Classes.pessoas.Pessoa;
import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import Controller.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {


    public Main() throws IOException, InvalidDateException, InvalidFieldException, InvalidDateException {

        /*Aluno aluno1 = new Aluno("Pedro Vinícius", "123.456.789-00", new Data(2001, 10, 12), "pedro@gmail.com", "(87)98132-0000", "pais@gmail.com");
        Aluno aluno3 = new Aluno("José Cleyton", "987.654.321-01", new Data(2010, 11, 27), "gal@gmail.com", "(87)98152-2030", "paisTetset@gmail.com");
        Aluno aluno4 = new Aluno("Erica Soares", "456.876.101-03", new Data(2003, 6, 3), "teste@gmail.com", "(87)98122-4321", "pais3@gmail.com");
        Aluno aluno5 = new Aluno("Junior melo", "342.867.259-87", new Data(2002, 4, 15), "outro@gmail.com", "(87)98752-1270", "pais6@gmail.com");

        Aluno outraTurma = new Aluno("Teste da Silva", "734.891.342-11", new Data(2005, 4, 29), "outroteste@gmail.com", "(87)98752-1270", "pais6@gmail.com");

        Turma turma2 = new Turma(2, "2º Ano");

        Turma turma = new Turma(1, "1º Ano");

        turma.adicionarAluno("Pedro Vinícius");
        turma.adicionarAluno("José Cleyton");
        turma.adicionarAluno("Erica Soares");
        turma.adicionarAluno("Junior melo");

        turma2.adicionarAluno("Teste da Silva");

        Professor professor = new Professor("Cleyton Amando", "234.123.456-45", new Data(2001, 10, 25), "admin@gmail.com", "(87)98132-0560", "admin1234");
        Administrador administrador = new Administrador("Ardomiro Ereno", "456.789.234-54", new Data(2001, 10, 25), "ardomiro@gmail.com", "(87)98132-4570", "admin1234");



        professor.adicionarTurma(turma.getId());
        professor.adicionarTurma(turma2.getId());

        FileOutputStream file1 = new FileOutputStream("alunos.dat");
        ObjectOutputStream os1 = new ObjectOutputStream(file1);

        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno1);
        alunos.add(aluno3);
        alunos.add(aluno4);
        alunos.add(aluno5);
        alunos.add(outraTurma);


        os1.writeObject(alunos);
        os1.close();




        List<Turma> turmas = new ArrayList<>();
        turmas.add(turma);
        turmas.add(turma2);

        FileOutputStream file2 = new FileOutputStream("turmas.dat");
        ObjectOutputStream os2 = new ObjectOutputStream(file2);

        os2.writeObject(turmas);
        os2.close();


        List<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(professor);
        pessoas.add(administrador);


        FileOutputStream file3 = new FileOutputStream("usuarios.dat");
        ObjectOutputStream os3 = new ObjectOutputStream(file3);

        os3.writeObject(pessoas);
        os3.close();*/

    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaLogin/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        (((ControllerLogin) fxmlLoader.getController())).setStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
//        try {
//            carregarBAnco();
//        } catch (InvalidDateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        launch(args);
    }

    private static void carregarBAnco() throws InvalidDateException, IOException {
        Aluno aluno1 = new Aluno("Pedro Vinícius", "123.456.789-00", new Data(2001, 10, 12), "pedro@gmail.com", "(87)98132-0000", "pais@gmail.com");
        Aluno aluno3 = new Aluno("José Cleyton", "987.654.321-01", new Data(2010, 11, 27), "gal@gmail.com", "(87)98152-2030", "paisTetset@gmail.com");
        Aluno aluno4 = new Aluno("Erica Soares", "456.876.101-03", new Data(2003, 6, 3), "teste@gmail.com", "(87)98122-4321", "pais3@gmail.com");
        Aluno aluno5 = new Aluno("Junior melo", "342.867.259-87", new Data(2002, 4, 15), "outro@gmail.com", "(87)98752-1270", "pais6@gmail.com");

        Aluno outraTurma = new Aluno("Teste da Silva", "734.891.342-11", new Data(2005, 4, 29), "outroteste@gmail.com", "(87)98752-1270", "pais6@gmail.com");

        Turma turma2 = new Turma(2, "2º Ano");

        Turma turma = new Turma(1, "1º Ano");

        turma.adicionarAluno("Pedro Vinícius");
        turma.adicionarAluno("José Cleyton");
        turma.adicionarAluno("Erica Soares");
        turma.adicionarAluno("Junior melo");

        turma2.adicionarAluno("Teste da Silva");

        Professor professor = new Professor("Cleyton Amando", "234.123.456-45", new Data(2001, 10, 25), "admin@gmail.com", "(87)98132-0560", "admin1234");
        Administrador administrador = new Administrador("Ardomiro Ereno", "456.789.234-54", new Data(2001, 10, 25), "ardomiro@gmail.com", "(87)98132-4570", "admin1234");



        professor.adicionarTurma(turma.getId());
        professor.adicionarTurma(turma2.getId());

        FileOutputStream file1 = new FileOutputStream("alunos.dat");
        ObjectOutputStream os1 = new ObjectOutputStream(file1);

        List<Aluno> alunos = new ArrayList<>();
        alunos.add(aluno1);
        alunos.add(aluno3);
        alunos.add(aluno4);
        alunos.add(aluno5);
        alunos.add(outraTurma);

        os1.writeObject(alunos);
        os1.close();

        List<Turma> turmas = new ArrayList<>();
        turmas.add(turma);
        turmas.add(turma2);

        FileOutputStream file2 = new FileOutputStream("turmas.dat");
        ObjectOutputStream os2 = new ObjectOutputStream(file2);

        os2.writeObject(turmas);
        os2.close();
        List<Pessoa> pessoas = new ArrayList<>();

        pessoas.add(professor);
        pessoas.add(administrador);


        FileOutputStream file3 = new FileOutputStream("usuarios.dat");
        ObjectOutputStream os3 = new ObjectOutputStream(file3);

        os3.writeObject(pessoas);
        os3.close();
    }
}
