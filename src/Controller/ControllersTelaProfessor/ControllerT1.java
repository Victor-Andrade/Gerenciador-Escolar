package Controller.ControllersTelaProfessor;

import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import Controller.ControllerLogin;
import Model.fachada.FachadaProfessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerT1 implements Initializable {
    private String mensagem;
    private FachadaProfessor fachadaProfessor;
    private Professor professor;
    private Turma turmaSelecionada;
    private ObservableList<Turma> turmas;

    private Stage stage;


    @FXML
    private ListView<Turma> listaTurmas;

    @FXML
    private void setTurmaSelecionada(){
        Turma turma = this.listaTurmas.getSelectionModel().getSelectedItem();

        this.turmaSelecionada = turma;
    }

    @FXML
    public void voltar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaLogin/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        ((ControllerLogin) fxmlLoader.getController()).setStage(this.stage);

        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setTitle("Login");
    }


    @FXML
    public void continuar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T2 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT2) fxmlLoader.getController()).setStage(this.stage);
            ((ControllerT2) fxmlLoader.getController()).setParametros(this.fachadaProfessor, this.turmaSelecionada, this.professor);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle(turmaSelecionada.getApelido());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void iniciarLayout(ObservableList<Turma> turma){
        //listaTurmas.getItems().setAll(turmas);
        listaTurmas.setItems(turma);
    }

    public void setStage(Stage stage, Professor professor, FachadaProfessor fachadaProfessor) throws IOException, ClassNotFoundException {
        this.stage = stage;
        this.professor = professor;
        this.fachadaProfessor = fachadaProfessor;
        this.turmas = FXCollections.observableArrayList(this.professor.getTurmasArrayList());
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    //234.123.456-45
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarLayout(this.turmas);
    }
}
