package Controller.ControllersTelaProfessor;

import Classes.pessoas.Aluno;
import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import javafx.scene.control.TextField;
import Model.fachada.FachadaProfessor;
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
    private Professor professor;

    private TextField nomeAluno;

    @FXML
    private ListView<Aluno> listaAlunos;

    @FXML
    public void continuar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T3 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT3) fxmlLoader.getController()).setStage(this.stage);
            ((ControllerT3) fxmlLoader.getController()).setParametros(this.fachadaProfessor, this.turma, this.alunoSelecionado, this.professor);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle(alunoSelecionado.getNome());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T1 Professor.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerT1) fxmlLoader.getController()).setStage(this.stage, this.professor, this.fachadaProfessor);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle(professor.getNome());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void removerAluno() throws Exception {
        this.fachadaProfessor.removerAlunoDaTurma(turma, this.nomeAluno.getText());
    }

    @FXML
    public void adicionarAluno() throws Exception {
        this.fachadaProfessor.adicionarAlunoEmTurma(turma, this.nomeAluno.getText());
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setParametros(FachadaProfessor fachadaProfessor, Turma turma, Professor professor){

        try{
            this.fachadaProfessor = fachadaProfessor;
            fachadaProfessor.recuperarAlunosTurma(turma);
            this.turma = turma;
            this.listaAlunos.setItems((ObservableList<Aluno>) turma.getAlunos());
            this.professor = professor;

        }catch(IOException | ClassNotFoundException e){
            this.mensagem = e.getMessage();
        }
    }


}
