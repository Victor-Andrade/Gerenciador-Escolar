package View;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.pessoas.excecoes.InvalidFieldException;
import Classes.pessoas.interfaces.ILogin;
import Controller.LoginOverviewController;
import Classes.pessoas.Administrador;
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

    private final ObservableList<ILogin> personData = FXCollections.observableArrayList();


    private Stage primaryStage;
    private BorderPane rootLayout;

    public ObservableList<ILogin> getPersonData() {
        return personData;
    }

    public Main(){
        try{
            this.personData.add(new Administrador("Amanda Coelho Alves Santos", "12345678900", new Data(2021, 1, 14), "teste@gmail.com", "(87)981067233", "12345678"));
            Data data = new Data(2020, 10, 23);
        }catch (InvalidFieldException e){
            System.out.println(e.getMessage());
        }catch (InvalidDateException e){
            System.out.println("Data inválida");
        }
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
