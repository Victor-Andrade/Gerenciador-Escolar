package controller.controllersTelaProfessor;

import model.classes.pessoas.Professor;
import model.classes.turmas.Turma;
import controller.ControllerLogin;
import model.fachada.FachadaProfessor;
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

/**
 * Controller responsavel por todas as interações da primeira tela do professor.
 * Essa tela contém a lista de turmas de um professor
 */

public class ControllerT1 implements Initializable {
    private FachadaProfessor fachadaProfessor;
    private Professor professor;
    private Turma turmaSelecionada;
    private ObservableList<String> turmas;

    private Stage stage;

    @FXML
    private ListView<String> listaTurmas;

    @FXML
    private void setTurmaSelecionada(){
        String turmaString = this.listaTurmas.getSelectionModel().getSelectedItem();
        for(Turma turma : this.professor.getTurmasArrayList()){
            if(turma.getApelido().toLowerCase().equals(turmaString.toLowerCase())){
                this.turmaSelecionada = turma;
                break;
            }
        }
    }

    @FXML
    public void voltar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaLogin/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        ((ControllerLogin) fxmlLoader.getController()).setStage(this.stage);

        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.setTitle("Login");
    }

    @FXML
    public void continuar(){
        if(turmaSelecionada != null){
            try{
                fachadaProfessor.recuperarAlunosTurma(this.turmaSelecionada);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/professorTela/T2 Professor.fxml"));

                ControllerT2 controller = new ControllerT2();

                controller.setParametros(this.stage, this.fachadaProfessor, this.turmaSelecionada, this.professor);
                fxmlLoader.setController(controller);

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle(turmaSelecionada.getApelido());

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Não dá para navegar, não foi selecionado a turma");
        }
    }

    private void iniciarLayout(){
        ArrayList<String> apelidos = new ArrayList<>();
        for(Turma turma: this.professor.getTurmasArrayList()){
            apelidos.add(turma.getApelido());
        }
        this.turmas = FXCollections.observableArrayList(apelidos);

        listaTurmas.setItems(this.turmas);
    }

    public void setParams(Stage stage, Professor professor, FachadaProfessor fachadaProfessor) throws IOException, ClassNotFoundException {
        this.stage = stage;
        fachadaProfessor.recuperarTurmasProfessor(professor);
        this.professor = professor;
        this.fachadaProfessor = fachadaProfessor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciarLayout();
    }
}
