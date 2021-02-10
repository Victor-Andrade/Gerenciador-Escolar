package Controller.ControllersTelaProfessor;

import Classes.excecoes.AlunoNotFoundException;
import Classes.materia.Materia;
import Classes.pessoas.Aluno;
import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import Model.fachada.FachadaProfessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller da terceira tela do Professor. Essa tela lista informações do aluno selecionado na tela 2
 */

public class ControllerT3 {
    private FachadaProfessor fachadaProfessor;
    private Turma turma;
    private Professor professor;

    private Stage stage;

    private Aluno aluno;

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

    public ControllerT3() {
        inicializarInfoAluno();
    }

    @FXML
    public void gerarBoletim() throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.fachadaProfessor.gerarBoletim(this.aluno);
    }

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T2 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT2) fxmlLoader.getController()).setStage(this.stage);
            ((ControllerT2) fxmlLoader.getController()).setParametros(this.fachadaProfessor, this.turma, this.professor);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle("Turma");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setParametros(FachadaProfessor fachadaProfessor, Turma turma, Aluno aluno, Professor professor){
            this.fachadaProfessor = fachadaProfessor;
            this.turma = turma;
            this.aluno = aluno;
            this.professor = professor;
    }

    private void inicializarInfoAluno() {

        ObservableList<Materia> materiasDoAluno = FXCollections.observableArrayList(aluno.getMaterias());

        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        txtContato.setText(aluno.getNumeroParaContato());
        materias.setItems(materiasDoAluno);
    }

    @FXML
    private void inicializarNotas(){
        Materia materia = materias.getSelectionModel().getSelectedItem();

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
}
