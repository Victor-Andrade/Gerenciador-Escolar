package Controller.ControllersTelaProfessor;

import Classes.pessoas.Aluno;
import Classes.turmas.Turma;
import model.fachada.FachadaProfessor;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerT2 {

    private String mensagem;
    private FachadaProfessor fachadaProfessor;
    private Turma turma;
    private Aluno alunoSelecionado;
    private Stage stage;

    @FXML
    private ListView<Aluno> listaAlunos;

    @FXML
    public void continuar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T3 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT3) fxmlLoader.getController()).setStage(this.stage);
            ((ControllerT3) fxmlLoader.getController()).setParametros(this.fachadaProfessor, this.turma, this.alunoSelecionado);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle(alunoSelecionado.getNome());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setParametros(FachadaProfessor fachadaProfessor, Turma turma){

        try{
            this.fachadaProfessor = fachadaProfessor;
            fachadaProfessor.recuperarAlunosTurma(turma);
            this.turma = turma;
            this.listaAlunos.setItems((ObservableList<Aluno>) turma.getAlunos());

        }catch(IOException | ClassNotFoundException e){
            this.mensagem = e.getMessage();
        }
    }


}
