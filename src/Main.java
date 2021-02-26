import model.classes.Data;
import model.excecoes.InvalidDateException;
import controller.controllerLogin.ControllerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.excecoes.InvalidFieldException;
import model.excecoes.UsuarioAlreadyRegisteredException;
import model.fachada.FachadaAdministrador;

import java.io.IOException;



public class Main extends Application {
    public Main() throws IOException, InvalidDateException, InvalidFieldException, UsuarioAlreadyRegisteredException, ClassNotFoundException {

        /// USADO PARA ADICIONAR UM ADMINISTRADOR NO BANCO, DEVE SER COMENTADO DEPOIS DA PRIMEIRA INICIALIZAÇÃO
        /*FachadaAdministrador fachadaAdministrador = new FachadaAdministrador();

        fachadaAdministrador.adicionarAdministrador("José Cleyton da Silva", "123.535.524-00", new Data(2001, 1, 1), "josealvessobralteste@gmail.com", "(87)98102-2345", "P@ssAlun0");*/
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaLogin/TelaLogin.fxml"));
        Parent root = fxmlLoader.load();

        (((ControllerLogin) fxmlLoader.getController())).setStage(primaryStage);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
