package Controller.ControllersTelaProfessor;

import Classes.excecoes.AlunoNotFoundException;
import Classes.excecoes.TurmaNaoExisteException;
import Classes.pessoas.Aluno;
import Classes.pessoas.Professor;
import Classes.turmas.Turma;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import Model.fachada.FachadaProfessor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller da segunda tela do professor. Essa tela lista os alunos da turma selecionada na tela 1.
 */

public class ControllerT2 implements Initializable {

    private FachadaProfessor fachadaProfessor;
    private Turma turma;
    private Aluno alunoSelecionado;
    private Stage stage;
    private Professor professor;
    private ObservableList<String> alunos;
    private TextField nomeAluno;
    @FXML
    private ListView<String> listaAlunos;
    @FXML
    private TextField campoAdicao;
    @FXML
    private Text aviso;


    @FXML
    public void continuar(){
        if(this.alunoSelecionado != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T3 Professor.fxml"));

                ControllerT3 controller = new ControllerT3();

                controller.setParametros(this.stage, this.fachadaProfessor, this.turma, this.alunoSelecionado, this.professor);
                fxmlLoader.setController(controller);

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle(alunoSelecionado.getNome());

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            aviso.setText("Nenhum aluno foi selecionado");
        }
    }

    @FXML
    private void setAlunoSelecionado(){
        try{
            this.fachadaProfessor.recuperarAlunosTurma(this.turma);
            String alunoString = this.listaAlunos.getSelectionModel().getSelectedItem();
            for(Aluno aluno : this.turma.getAlunos()){
                if(aluno.equals(alunoString)){
                    this.alunoSelecionado = aluno;
                    break;
                }
            }
        }catch (IOException | ClassNotFoundException e){
            aviso.setText("Erro interno no repositorio");
        }
    }

    @FXML
    public void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/professorTela/T1 Professor.fxml"));

            ControllerT1 controller = new ControllerT1();

            controller.setParams(this.stage, this.professor, this.fachadaProfessor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Turmas");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void removerAluno() {
        if(alunoSelecionado != null){
            try{
                this.fachadaProfessor.removerAlunoDaTurma(turma, this.alunoSelecionado.getNome());
                this.fachadaProfessor.recuperarAlunosTurma(this.turma);
                inicializarLayout();
            }catch (ClassNotFoundException | IOException e){
                aviso.setText("Erro interno no banco");
            }catch(AlunoNotFoundException | TurmaNaoExisteException e){
                aviso.setText(e.getMessage());
            }
        }else{
            aviso.setText("Nenhum aluno selecionado");
        }


    }

    @FXML
    public void adicionarAluno(){
        if(this.campoAdicao.getText() != null){
            try{
                this.fachadaProfessor.adicionarAlunoEmTurma(turma, this.campoAdicao.getText());
                this.fachadaProfessor.recuperarAlunosTurma(this.turma);
                inicializarLayout();
            }catch (ClassNotFoundException | IOException e){
                aviso.setText("Erro interno no banco");
            }catch(AlunoNotFoundException | TurmaNaoExisteException e){
                aviso.setText(e.getMessage());
            }
        }else{
            aviso.setText("Nenhum aluno foi digitado");
        }
    }

    public void setParametros(Stage stage, FachadaProfessor fachadaProfessor, Turma turma, Professor professor){
        try{
            this.stage = stage;
            this.fachadaProfessor = fachadaProfessor;
            fachadaProfessor.recuperarAlunosTurma(turma);
            this.turma = turma;
            this.professor = professor;

        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void inicializarLayout(){
        this.alunos = FXCollections.observableArrayList(this.turma.getNomesAlunos());
        this.listaAlunos.setItems(this.alunos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarLayout();
    }
}