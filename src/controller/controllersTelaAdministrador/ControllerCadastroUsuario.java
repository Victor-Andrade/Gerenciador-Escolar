package controller.controllersTelaAdministrador;

import controller.controllerLogin.ControllerLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.datas.Data;
import model.classes.excecoes.InvalidDateException;
import model.classes.excecoes.InvalidFieldException;
import model.classes.excecoes.UsuarioAlreadyRegisteredException;
import model.classes.pessoas.Administrador;
import model.classes.pessoas.Professor;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerCadastroUsuario implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker data;
    @FXML
    private TextField email;
    @FXML
    private TextField contato;
    @FXML
    private TextField senha;
    @FXML
    private TextField senhaConfirmar;
    @FXML
    private ChoiceBox<String> tipo;

    @FXML
    private Text aviso;

    /**
     * Falta Implementar
     */
    @FXML
    private void adicionar(){
        String tipo = this.tipo.getValue();
        if(this.senha.getText().equals(this.senhaConfirmar.getText())){
            if(tipo.equals("Administrador")){
                adicionarAdministrador();
            }else if(tipo.equals("Professor")){
                adicionarProfessor();
            }else{
                this.aviso.setText("Tipo de usuário não selecionado");
            }
        }else{
            this.aviso.setText("Senhas não batem");
        }
    }

    private void adicionarAdministrador(){
        LocalDate dataLayout = data.getValue();
        if(dataLayout != null){
            try{
                Data data = new Data(dataLayout.getYear(), dataLayout.getMonthValue(), dataLayout.getDayOfMonth());
                this.fachadaAdministrador.adicionarAdministrador(nome.getText(), cpf.getText(), data, email.getText(), contato.getText(), senha.getText());
                this.aviso.setText("Adicionado com sucesso!");
            }catch(InvalidDateException | IOException | UsuarioAlreadyRegisteredException | ClassNotFoundException | InvalidFieldException e){
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Data não informada");
        }
    }

    private void adicionarProfessor(){
        LocalDate dataLayout = data.getValue();
        if(dataLayout != null){
            try{
                Data data = new Data(dataLayout.getYear(), dataLayout.getMonthValue(), dataLayout.getDayOfMonth());
                this.fachadaAdministrador.adicionarProfessor(nome.getText(), cpf.getText(), data, email.getText(), contato.getText(), senha.getText());
                this.aviso.setText("Adicionado com sucesso!");
            }catch(InvalidDateException | IOException | UsuarioAlreadyRegisteredException | ClassNotFoundException e){
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Data não informada");
        }
    }

    @FXML
    private void apagar(){
        this.nome.setText("");
        this.cpf.setText("");
        this.email.setText("");
        this.contato.setText("");
        this.senhaConfirmar.setText("");
        this.senha.setText("");
        this.tipo.setValue(null);
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
        this.tipo.getItems().addAll("Administrador", "Professor");
    }
}
