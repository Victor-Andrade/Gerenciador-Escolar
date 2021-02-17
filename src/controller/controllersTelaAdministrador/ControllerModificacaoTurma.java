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
import model.classes.pessoas.Administrador;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerModificacaoTurma implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

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
    private void adicionarAluno(){

    }
    @FXML
    private void removerAluno(){

    }
    @FXML
    private void atualizarInfo(){

    }
    @FXML
    private void mostrarEstatisticas(){

    }
    /**
     *
     */

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/Principal.fxml"));

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
            ArrayList<String> turmas = this.fachadaAdministrador.todasAsTurmas();
            this.listaTurmas.setItems(FXCollections.observableArrayList(turmas));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
