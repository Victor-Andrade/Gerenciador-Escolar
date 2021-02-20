package controller.controllersTelaProfessor;

import controller.controllerLogin.ControllerLogin;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.datas.Data;
import model.classes.excecoes.AlunoNotFoundException;
import model.classes.excecoes.InvalidDateException;
import model.classes.excecoes.TurmaNaoExisteException;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Professor;
import model.classes.turmas.Turma;
import model.fachada.FachadaProfessor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTelaPrincipalProfessor implements Initializable {
    private Stage stage;
    private Professor professor;
    private FachadaProfessor fachadaProfessor;
    private ArrayList<Turma> turmas;
    private Turma turmaSelecionada;
    private Aluno alunoSelecionado;

    @FXML
    private TextArea txtProfessor;
    @FXML
    private ListView<String> listaTurmas;
    @FXML
    private ListView<String> listaAlunos;

    @FXML
    private Text aviso;

    @FXML
    private TextField campoAluno;

    @FXML
    private void adicionar(){
        String nomeAluno = this.campoAluno.getText();
        if(nomeAluno != null){
            try{
                this.fachadaProfessor.adicionarAlunoEmTurma(this.turmaSelecionada, new Aluno(nomeAluno, nomeAluno, new Data(2001, 1, 1), "","", ""));
                turmaSelecionada = this.fachadaProfessor.buscarTurma(this.turmaSelecionada.getId());
                this.listaAlunos.setItems(FXCollections.observableArrayList(this.turmaSelecionada.getNomesAlunos()));
                this.aviso.setText("Adicionado com sucesso");
                this.campoAluno.setText("");
            }catch (ClassNotFoundException | IOException e){
                aviso.setText("Erro interno no banco");
            }catch(AlunoNotFoundException | TurmaNaoExisteException | InvalidDateException e){
                aviso.setText(e.getMessage());
            }
        }else{
            aviso.setText("Nenhum aluno foi digitado");
        }
    }
    @FXML
    private void remover(){
        String alunoString = this.campoAluno.getText();
        if(alunoString != null){
            try{
                this.fachadaProfessor.removerAlunoDaTurma(this.turmaSelecionada, new Aluno(alunoString, alunoString, new Data(2001,1,1), "", "", ""));
                turmaSelecionada = this.fachadaProfessor.buscarTurma(this.turmaSelecionada.getId());
                this.listaAlunos.setItems(FXCollections.observableArrayList(this.turmaSelecionada.getNomesAlunos()));
                this.aviso.setText("Removido com sucesso");
                this.campoAluno.setText("");
            }catch (ClassNotFoundException | IOException e){
                aviso.setText("Erro interno no banco");
            }catch(AlunoNotFoundException | TurmaNaoExisteException e){
                aviso.setText(e.getMessage());
            } catch (InvalidDateException e) {
                e.printStackTrace();
            }
        }else{
            aviso.setText("Nenhum aluno selecionado");
        }
    }

    @FXML
    private void continuar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/T3 Professor.fxml"));

            ControllerInfoAluno controller = new ControllerInfoAluno();

            controller.setParametros(this.stage, this.professor, this.alunoSelecionado, this.fachadaProfessor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aluno");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaLogin/TelaLogin.fxml"));
            Parent root = fxmlLoader.load();

            ((ControllerLogin) fxmlLoader.getController()).setStage(this.stage);

            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.setTitle("Login");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void inicializarTurma(){
        String turmaString = this.listaTurmas.getSelectionModel().getSelectedItem();
        if(turmaString != null){
            for(Turma turma : this.turmas){
                if(turma.getApelido().equalsIgnoreCase(turmaString)){
                    this.turmaSelecionada = turma;
                    break;
                }
            }
            this.listaAlunos.setItems(FXCollections.observableArrayList(this.turmaSelecionada.getNomesAlunos()));
        }else{
            this.aviso.setText("Turma não selecionada");
        }
    }

    @FXML
    private void inicializarAlunos(){
        String alunoString = this.listaAlunos.getSelectionModel().getSelectedItem();
        if(alunoString != null){
            try{
                this.alunoSelecionado = this.fachadaProfessor.buscarAluno(new Aluno(alunoString, alunoString, new Data(2001, 1, 1), "", "", ""));
            } catch (InvalidDateException | IOException | ClassNotFoundException | AlunoNotFoundException e) {
                this.aviso.setText(e.getMessage());
            }
        }
    }

    public void setParametros(Stage stage, Professor professor, FachadaProfessor fachadaProfessor){
        this.stage = stage;
        this.professor = professor;
        this.fachadaProfessor = fachadaProfessor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> turmas = new ArrayList<>();
            this.turmas = new ArrayList<>();
            for(double id: professor.getTurmas()){
                Turma turma = this.fachadaProfessor.buscarTurma(id);
                turmas.add(turma.getApelido());
                this.turmas.add(turma);
            }
            this.listaTurmas.setItems(FXCollections.observableArrayList(turmas));
            this.txtProfessor.setText(this.professor.toString());
        } catch (ClassNotFoundException | IOException | TurmaNaoExisteException e) {
            this.aviso.setText(e.getMessage());
        }
    }
}
