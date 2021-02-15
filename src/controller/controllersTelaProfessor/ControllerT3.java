package controller.controllersTelaProfessor;

import model.classes.excecoes.AlunoNotFoundException;
import model.classes.materia.Materia;
import model.classes.pessoas.Aluno;
import model.classes.pessoas.Professor;
import model.classes.turmas.Turma;
import model.fachada.FachadaProfessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller da terceira tela do Professor. Essa tela lista informações do aluno selecionado na tela 2
 */

public class ControllerT3 implements Initializable {
    private FachadaProfessor fachadaProfessor;
    private Turma turma;
    private Professor professor;
    private Stage stage;
    private Aluno aluno;
    ObservableList<String> materiasString;

    @FXML
    private Text txtNome;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtContato;

    @FXML
    private ChoiceBox<String> materias;

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

    @FXML
    public void gerarBoletim() throws IOException, ClassNotFoundException, AlunoNotFoundException {
        this.fachadaProfessor.gerarBoletim(this.aluno);
    }

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/T2 Professor.fxml"));

            ControllerT2 controller = new ControllerT2();

            controller.setParametros(this.stage, this.fachadaProfessor, this.turma, this.professor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Turma");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setParametros(Stage stage, FachadaProfessor fachadaProfessor, Turma turma, Aluno aluno, Professor professor){
        this.stage = stage;
        this.fachadaProfessor = fachadaProfessor;
        this.turma = turma;
        this.aluno = aluno;
        this.professor = professor;
    }

    private void inicializarInfoAluno() {
        ArrayList<String> materias = new ArrayList<>();
        for(Materia materia : aluno.getMaterias()){
            materias.add(materia.getNome());
        }

        this.materiasString = FXCollections.observableArrayList(materias);

        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        txtContato.setText(aluno.getNumeroParaContato());
        this.materias.setItems(this.materiasString);
    }

    @FXML
    private void inicializarNotas(){
        String materiaString = materias.getSelectionModel().getSelectedItem();
        if(materiaString != null){
            Materia materia = null;

            for(Materia materiaTemp : this.aluno.getMaterias()){
                if(materiaTemp.getNome().equals(materiaString)){
                    materia = materiaTemp;
                    break;
                }
            }

            if(materia != null){
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
    }

    @FXML
    private void salvarNotas(){

    }

    @FXML
    private void descartarModificacoes(){

    }

    private void inicializarLayout(){
        inicializarInfoAluno();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarLayout();
    }
}
