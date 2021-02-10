package Controller.controllerTelaAdministrador;

import Classes.pessoas.Administrador;
import Model.fachada.FachadaAdministrador;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ControllerTelaAdministrador {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private void irParaAdicionarProfessor(){

    }

    public void setParams(Stage stage, Administrador administrador, FachadaAdministrador fachadaAdministrador){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachadaAdministrador;
    }


}
