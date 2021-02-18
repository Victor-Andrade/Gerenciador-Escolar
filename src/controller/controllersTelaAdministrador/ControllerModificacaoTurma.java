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
import model.classes.excecoes.AlunoNotFoundException;
import model.classes.excecoes.TurmaNaoExisteException;
import model.classes.pessoas.Administrador;
import model.classes.turmas.Turma;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerModificacaoTurma implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    private Turma turmaSelecionada;

    @FXML
    private Text aviso;
    @FXML
    private ListView<String> listaTurmas;
    @FXML
    private ListView<String> listaAlunos;
    @FXML
    private TextField nomeTurma;
    @FXML
    private TextField campoAluno;

    /**
     * Falta implementar
     */
    @FXML
    private void adicionarAluno() {
        try {
            this.fachadaAdministrador.adicionarAlunoEmTurma(this.turmaSelecionada, this.campoAluno.getText());
            inicializarTurma();
        } catch (AlunoNotFoundException | TurmaNaoExisteException | ClassNotFoundException | IOException e) {
            this.aviso.setText(e.getMessage());
        } catch (NullPointerException e) {
            this.aviso.setText("Turma não selecionada");
        }
    }

    @FXML
    private void removerAluno() {
        /*try {
            String aluno = this.campoAluno.getText();
            this.fachadaAdministrador.removerAlunoDaTurma(this.turmaSelecionada, aluno);
            inicializarTurma();
            this.aviso.setText("Removido com sucesso!");
        } catch (AlunoNotFoundException | TurmaNaoExisteException | ClassNotFoundException | IOException e) {
            this.aviso.setText(e.getMessage());
        } catch (NullPointerException e){
            this.aviso.setText("Turma não selecionada");
        }*/
    }

    //Falta
    @FXML
    private void atualizarInfo() {

    }

    //Falta
    @FXML
    private void mostrarEstatisticas() {

    }

    @FXML
    private void excluirTurma() {
        //############## AQUI OCORRE O ERRO ##############
        try {
            this.fachadaAdministrador.excluirTurma(this.turmaSelecionada);
            this.turmaSelecionada = null;
            this.nomeTurma.setText("");
            this.listaAlunos.setItems(FXCollections.observableArrayList(new ArrayList<>()));
            this.campoAluno.setText("");
            this.aviso.setText("Removido com sucesso");

            ArrayList<String> turmas = this.fachadaAdministrador.todasAsTurmas();
            this.listaTurmas.setItems(FXCollections.observableArrayList(turmas));
        } catch (TurmaNaoExisteException | IOException | ClassNotFoundException e) {
            this.aviso.setText(e.getMessage());
        } catch (NullPointerException e) {
            this.aviso.setText("Turma não selecionada");
        }
    }

    @FXML
    private void inicializarTurma() {
        try {
            char[] nome = this.listaTurmas.getSelectionModel().getSelectedItem().toCharArray();
            StringBuilder id = new StringBuilder();
            for (int i = 0; nome[i] != ':'; i++) {
                id.append(nome[i]);
            }
            double idDouble = Double.parseDouble(id.toString());
            this.turmaSelecionada = this.fachadaAdministrador.buscarTurma(idDouble);
            this.nomeTurma.setText(turmaSelecionada.getApelido());
            this.listaAlunos.setItems(FXCollections.observableArrayList(turmaSelecionada.getNomesAlunos()));
        } catch (TurmaNaoExisteException | ClassNotFoundException | IOException e) {
            this.aviso.setText(e.getMessage());
        } catch (NullPointerException e){
            this.aviso.setText("Turma não selecionada");
        }
    }

    /**
     *
     */

    @FXML
    private void voltar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/Principal.fxml"));

            ControllerPrincipalAdministrador controller = new ControllerPrincipalAdministrador();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada) {
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> turmas = this.fachadaAdministrador.todasAsTurmas();
            this.listaTurmas.setItems(FXCollections.observableArrayList(turmas));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
