package Aplicacao;

import Aplicacao.login.LoginOverviewController;
import Aplicacao.pessoas.Administrador;
import Aplicacao.pessoas.Login;
import Aplicacao.pessoas.Pessoa;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private final ObservableList<Pessoa> personData = FXCollections.observableArrayList();
    private final ObservableList<Login> personLoginData = FXCollections.observableArrayList();

    private Stage primaryStage;
    private BorderPane rootLayout;

    public ObservableList<Pessoa> getPersonData() {
        return personData;
    }

    public ObservableList<Login> getPersonLoginData() {
        return personLoginData;
    }

    public Main(){
        this.personLoginData.add(new Login("12345678900", "12345678"));
        this.personData.add(new Administrador("Amanda Coelho Alves Santos", "12345678900", new int[]{2021, 1, 14}, "teste@gmail.com", "(87)981067233"));
    }

    @Override
    public void start(Stage primaryStage) {
        //Inicializa o layout da aplicação
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();
        showLoginOverview();
    }


    public void initRootLayout() {
        try {
            // Carrega o root layout do arquivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Mostra a scene (cena) contendo o root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginOverview() {
        try {
            // Carrega o Login overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("login/LoginOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Define o person overview dentro do root layout.
            rootLayout.setCenter(personOverview);

            // Dá ao controlador acesso aos dados de Main
            LoginOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Retorna o Palco principal
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
