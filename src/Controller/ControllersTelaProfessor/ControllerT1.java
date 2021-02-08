package Controller.ControllersTelaProfessor;

import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import Model.fachada.FachadaProfessor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControllerT1 {
    private FachadaProfessor fachadaProfessor;

    private Professor professor;

    private Stage stage;

    @FXML
    private ListView<Turma> listaTurmas;

    public ControllerT1(){
        listaTurmas.setItems((ObservableList<Turma>) professor.getTurmasArrayList());
    }


    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setProfessor(Professor professor){this.professor = professor;}
    
}
