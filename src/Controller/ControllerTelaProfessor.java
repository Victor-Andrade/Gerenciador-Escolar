package Controller;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.excecoes.InvalidFieldException;
import Classes.materia.Bimestre;
import Classes.materia.Materia;
import Classes.pessoas.Aluno;
import Classes.pessoas.AlunoHoraExtra;
import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class ControllerTelaProfessor {

    private Professor professor;

    private Stage stage;

    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnAdicionarAluno;
    @FXML
    private Button btnRemoverAluno;
    @FXML
    private Button btnSalvarModificacoes;
    @FXML
    private Button btnDescartarAlteracoes;

    @FXML
    private ListView<Turma> listaTurmas;
    @FXML
    private ListView<Aluno> listaAlunos;


    @FXML
    private Text txtNome;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtContato;

    @FXML
    private ChoiceBox<Materia> materias;

    @FXML
    private TextField b1N1;
    @FXML
    private TextField b1N2;
    @FXML
    private Text m1;

    @FXML
    private TextField b2N1;
    @FXML
    private TextField b2N2;
    @FXML
    private Text m2;

    @FXML
    private TextField b3N1;
    @FXML
    private TextField b3N2;
    @FXML
    private Text m3;

    @FXML
    private TextField b4N1;
    @FXML
    private TextField b4N2;
    @FXML
    private Text m4;

    @FXML
    private DatePicker dataPicker;
    @FXML
    private CheckBox justificar;

    public void setStage(Stage stage){
        this.stage = stage;
    }



    public void setProfessor(Professor professor){
        this.professor = professor;
    }

    @FXML
    private void inicializarListaAlunos(){
        ObservableList<Aluno> alunos = FXCollections.observableArrayList((listaTurmas.getSelectionModel().getSelectedItem()).getAlunos());
        listaAlunos.setItems(alunos);
    }

    @FXML
    private void inicializarInfoAluno(){
        Aluno aluno = listaAlunos.getSelectionModel().getSelectedItem();
        ObservableList<Materia> materiasDoAluno = FXCollections.observableArrayList(aluno.getMaterias());

        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        txtContato.setText(aluno.getNumeroParaContato());
        materias.setItems(materiasDoAluno);
    }
    @FXML
    private void inicializarNotas(){
        Materia materia = materias.getValue();

        b1N1.setText(Double.toString(materia.getPrimeiroBimestre().getNota1()));
        b1N2.setText(Double.toString(materia.getPrimeiroBimestre().getNota2()));

        b2N1.setText(Double.toString(materia.getSegundoBimestre().getNota1()));
        b2N2.setText(Double.toString(materia.getSegundoBimestre().getNota2()));

        b3N1.setText(Double.toString(materia.getTerceiroBimestre().getNota1()));
        b3N2.setText(Double.toString(materia.getTerceiroBimestre().getNota2()));

        b4N1.setText(Double.toString(materia.getQuartoBimestre().getNota1()));
        b4N2.setText(Double.toString(materia.getQuartoBimestre().getNota2()));

        m1.setText(Double.toString(materia.getMedia1Bimestre()));
        m2.setText(Double.toString(materia.getMedia2Bimestre()));
        m3.setText(Double.toString(materia.getMedia3Bimestre()));
        m4.setText(Double.toString(materia.getMedia4Bimestre()));
    }

    @FXML
    private void navegarParaLogin(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaLogin/TelaLogin.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerLogin) fxmlLoader.getController()).setStage(this.stage);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle("Login");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    public void gerarBoletim() {

    }
}
