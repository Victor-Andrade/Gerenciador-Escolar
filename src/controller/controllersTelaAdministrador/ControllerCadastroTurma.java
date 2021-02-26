package controller.controllersTelaAdministrador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.classes.Turma;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerCadastroTurma implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private TextField nome;
    @FXML
    private ChoiceBox<String> professores;
    @FXML
    private ListView<String> alunosTotais;
    @FXML
    private ListView<String> alunosSelecionados;
    @FXML
    private Text aviso;

    private ArrayList<String> alunosSelecionadosArray;

    @FXML
    private void adicionar(){
        try{
            this.fachadaAdministrador.adicionarTurma(this.nome.getText(), alunosSelecionadosArray);

            //Pode acontecer algum erro?
            String nomeProfessor = this.professores.getSelectionModel().getSelectedItem();
            if(nomeProfessor != null){
                Turma turma = this.fachadaAdministrador.ultimaTurmaAdicionada();
                //Pode dar erro??? Conversão inválida???
                Professor professor = (Professor) this.fachadaAdministrador.buscarUsuario(new Professor(nomeProfessor,
                        nomeProfessor, new Data(2001, 1, 1),"", "", ""));
                this.fachadaAdministrador.adicionarTurmaEmProfessor(turma, professor);
            }
            this.aviso.setText("Adicionado com sucesso");
        }catch(Exception e){
            this.aviso.setText(e.getMessage());
        }
    }
    @FXML
    private void adicionarAluno(){
        String aluno = this.alunosTotais.getSelectionModel().getSelectedItem();
        if(aluno != null){
            if(!alunosSelecionadosArray.contains(aluno)){
                alunosSelecionadosArray.add(aluno);
                this.alunosSelecionados.getItems()
                        .setAll(FXCollections.observableArrayList(this.alunosSelecionadosArray));
            }else{
                this.aviso.setText("Aluno já adicionado");
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }
    @FXML
    private void removerAluno(){
        String aluno = this.alunosSelecionados.getSelectionModel().getSelectedItem();
        if(aluno != null){
            if(alunosSelecionadosArray.contains(aluno)){
                alunosSelecionadosArray.remove(aluno);
                this.alunosSelecionados.getItems()
                        .setAll(FXCollections.observableArrayList(this.alunosSelecionadosArray));
            }else{
                this.aviso.setText("Aluno não adicionado");
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telasAdministrador/TelaPrincipalAdministrador.fxml"));

            ControllerPrincipalAdministrador controller = new ControllerPrincipalAdministrador();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> professores = this.fachadaAdministrador.todosOsProfessores();
            ArrayList<String> alunos = this.fachadaAdministrador.todosOsAlunos();

            this.professores.getItems().addAll(FXCollections.observableArrayList(professores));
            this.alunosTotais.getItems().addAll(FXCollections.observableArrayList(alunos));
            this.alunosSelecionadosArray = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
