package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.pessoas.Administrador;
import model.fachada.FachadaAdministrador;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerModificacaoAluno implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private Text aviso;
    @FXML
    private ListView<String> listaAlunos;
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private TextField email;
    @FXML
    private TextField contato;
    @FXML
    private TextField emailPais;

    @FXML
    private DatePicker data;
    @FXML
    private ChoiceBox<String> materias;
    @FXML
    private ChoiceBox<String> curso;

    @FXML
    private TextField b1N1;
    @FXML
    private TextField b1N2;
    @FXML
    private Text m1;

    @FXML
    private TextField b2N1;
    @FXML
    private TextField b2N2;
    @FXML
    private Text m2;

    @FXML
    private TextField b3N1;
    @FXML
    private TextField b3N2;
    @FXML
    private Text m3;

    @FXML
    private TextField b4N1;
    @FXML
    private TextField b4N2;
    @FXML
    private Text m4;

    /**
     * Falta implementar
     */

    @FXML
    private void reportarConduta(){

    }
    @FXML
    private void removerFalta(){

    }
    @FXML
    private void salvar(){

    }
    @FXML
    private void gerarBoletim(){

    }
    @FXML
    private void gerarCertificadoMatricula(){

    }
    @FXML
    private void gerarCertificadoCurso(){

    }
    @FXML
    private void removerAluno(){

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

    }
}
