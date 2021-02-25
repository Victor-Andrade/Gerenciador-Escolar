package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.fachada.FachadaAdministrador;
import model.fachada.FachadaProfessor;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTelaInfoFaltas implements Initializable {
    private Aluno aluno;
    private Stage stage;
    private FachadaAdministrador fachadaAdministrador;
    private Administrador administrador;
    private int id;
    private Falta falta;

    @FXML
    private Text mensagem;
    @FXML
    private Text data;
    @FXML
    private Text confirmada;
    @FXML
    private Text caminho;
    @FXML
    private Text aviso;

    @FXML
    private void voltar(){

    }
    @FXML
    private void mudarStatus(){

    }
    @FXML
    private void excluir(){

    }
    @FXML
    private void abrir(){

    }

    public void setParametros(Aluno aluno, Stage stage, FachadaAdministrador fachadaAdministrador, Administrador administrador, int id){
        this.aluno = aluno;
        this.stage = stage;
        this.fachadaAdministrador = fachadaAdministrador;
        this.administrador = administrador;
        this.id = id;
    }

    private void incializarLayout(){
        this.falta = this.aluno.getFalta(this.id);
        this.mensagem.setText(this.falta.getMensagem());
        this.data.setText(this.falta.getData().formatarData());
        this.confirmada.setText(this.falta.isConfirmada() ? "Confirmada" : "Não confirmada");
        if(this.falta instanceof FaltaJustificada){
            this.caminho.setText(((FaltaJustificada) falta).getCaminho());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incializarLayout();
    }
}
