package Controller.ControllersTelaProfessor;

import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import model.fachada.FachadaProfessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerT1 {
    private String mensagem;
    private FachadaProfessor fachadaProfessor;
    private Professor professor;
    private Turma turmaSelecionada;

    private Stage stage;

    public ControllerT1 (){
        this.fachadaProfessor = new FachadaProfessor();
    }

    @FXML
    private ListView<Turma> listaTurmas;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setProfessor(Professor professor, FachadaProfessor fachadaProfessor) {
        try{
            fachadaProfessor.recuperarTurmasProfessor(professor);
            this.professor = professor;

            ObservableList<Turma> turmas = FXCollections.observableArrayList(professor.getTurmasArrayList());

            listaTurmas.setItems(turmas);
            this.fachadaProfessor = fachadaProfessor;
        }catch (IOException | ClassNotFoundException e){
            this.mensagem = "Professor não cadastrado no banco";
        }
    }

    public void continuar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T2 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT2) fxmlLoader.getController()).setStage(this.stage);
            ((ControllerT2) fxmlLoader.getController()).setParametros(this.fachadaProfessor, this.turmaSelecionada);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle(turmaSelecionada.getApelido());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
