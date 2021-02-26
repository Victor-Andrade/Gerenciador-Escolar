package controller.controllersTelaAdministrador;

import controller.controllerLogin.ControllerLogin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.classes.pessoas.usuarios.Administrador;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPrincipalAdministrador implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private TextArea infoAdmin;


    @FXML
    private void matricula(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telasAdministrador/Matricula.fxml"));

            ControllerMatricula controller = new ControllerMatricula();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Matricula");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void modificarAluno(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/telasAdministrador/ModificacaoAluno.fxml"));

            ControllerModificacaoAluno controller = new ControllerModificacaoAluno();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Alunos");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void cadastrarUsuario(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/telasAdministrador/CadastroUsuario.fxml"));

            ControllerCadastroUsuario controller = new ControllerCadastroUsuario();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cadastro de usuário");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void modificarUsuario(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/telasAdministrador/ModificacaoUsuario.fxml"));

            ControllerModificacaoUsuario controller = new ControllerModificacaoUsuario();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Usuários");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void cadastrarTurma(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/telasAdministrador/CadastroTurma.fxml"));

            ControllerCadastroTurma controller = new ControllerCadastroTurma();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Cadastrar turmas");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    private void modificarTurma(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                    .getResource("/view/telasAdministrador/ModificacaoTurma.fxml"));

            ControllerModificacaoTurma controller = new ControllerModificacaoTurma();

            controller.setParametros(this.stage, this.administrador, this.fachadaAdministrador);
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
    private void situacoesReportadas(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telasAdministrador/TelaSituacoes.fxml"));

            ControllerTelaSituacoes controller = new ControllerTelaSituacoes();

            controller.setParametros(this.administrador, this.stage, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Situações reportadas");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void faltas(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telasAdministrador/TelaFaltas.fxml"));

            ControllerTelaFaltas controller = new ControllerTelaFaltas();

            controller.setParametros(this.administrador, this.stage, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Faltas Justificadas");
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

    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.infoAdmin.setText(this.administrador.toString());
    }
}
