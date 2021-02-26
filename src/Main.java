import model.classes.Data;
import model.classes.Situacao;
import model.classes.Turma;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.materia.Curso;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.excecoes.InvalidDateException;
import controller.controllerLogin.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.excecoes.InvalidFieldException;
import model.excecoes.UsuarioAlreadyRegisteredException;
import model.fachada.FachadaAdministrador;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    public Main() throws IOException, InvalidDateException, InvalidFieldException, UsuarioAlreadyRegisteredException, ClassNotFoundException {

        /// USADO PARA Popular o banco no primeiro teste. Ao rodar ele apaga os dados salvos e coloca alunos, turmas, um professor e um administrador.
        //popularBanco();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaLogin/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        (((ControllerLogin) fxmlLoader.getController())).setStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
    private void popularBanco() throws InvalidDateException, IOException {
        Aluno aluno1 = new Aluno("PEDRO SILVA", "12345678900", new Data(2001, 10, 12), "pedro@gmail.com", "(87)98132-0000", "pais@gmail.com");
        Aluno aluno3 = new Aluno("JOSÉ CLEYTON", "98765432101", new Data(2010, 11, 27), "gal@gmail.com", "(87)98152-2030", "paisTetset@gmail.com");
        Aluno aluno4 = new Aluno("ERICA SOARES", "45687610103", new Data(2003, 6, 3), "teste@gmail.com", "(87)98122-4321", "pais3@gmail.com");
        AlunoHoraExtra aluno5 = new AlunoHoraExtra("JUNIOR MELO", "34286725987", new Data(2002, 4, 15), "outro@gmail.com", "(87)98752-1270", "pais6@gmail.com", new Curso("Inglês"));

        aluno1.adicionarSituacao(new Situacao("Aluno apresentou mal comportamento com a professora", new Data(2001, 1, 1), 1));
        aluno5.adicionarSituacao(new Situacao("Aluno apresentou mal comportamento com a professora", new Data(2001, 1, 1), 1));

        aluno1.adicionarFalta(new Falta(new Data(2001, 1, 1), "Aluno faltou a aula de ingLes", 1));
        aluno3.adicionarFalta(new Falta(new Data(2001, 1, 1), "Aluno faltou a aula de ingLes", 1));
        aluno1.adicionarFalta(new FaltaJustificada(new Data(2001, 1, 1), "Aluno faltou a aula de ingLes", "C:///", 2));
        aluno5.adicionarFalta(new FaltaJustificada(new Data(2001, 1, 1), "Aluno faltou a aula de ingLes", "C:///", 2));
        Aluno outraTurma = new Aluno("TESTE DA SILVA", "73489134211", new Data(2005, 4, 29), "outroteste@gmail.com", "(87)98752-1270", "pais6@gmail.com");

        Turma turma2 = new Turma(2, "2º Ano");

        Turma turma = new Turma(1, "1º Ano");

        turma.adicionarAluno("JOSÉ CLEYTON");
        turma.adicionarAluno("ERICA SOARES");
        turma.adicionarAluno("JUNIOR MELO");

        turma2.adicionarAluno("TESTE DA SILVA");

        Professor professor = new Professor("CLEYTON AMANDO", "23412345645", new Data(2001, 10, 25), "admin@gmail.com", "(87)98132-0560", "admin1234");
        Administrador administrador = new Administrador("ARDOMIRO ERENO", "45678923454", new Data(2001, 10, 25), "ardomiro@gmail.com", "(87)98132-4570", "admin1234");



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

    public static void main(String[] args) {
        launch(args);
    }
}