package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Situacao;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.fachada.FachadaAdministrador;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTelaInfoSituacoes implements Initializable {
    private Aluno aluno;
    private Stage stage;
    private FachadaAdministrador fachadaAdministrador;
    private Administrador administrador;
    private int id;
    private Situacao situacao;

    @FXML
    private Text mensagem;
    @FXML
    private Text data;
    @FXML
    private Text nome;
    @FXML
    private Text aviso;

    @FXML
    private void voltar(){

    }

    @FXML
    private void relatar(){

    }

    @FXML
    private void resolvida(){

    }

    public void setParametros(Aluno aluno, Stage stage, FachadaAdministrador fachadaAdministrador, Administrador administrador, int id){
        this.aluno = aluno;
        this.stage = stage;
        this.fachadaAdministrador = fachadaAdministrador;
        this.administrador = administrador;
        this.id = id;
    }

    private void incializarLayout(){
        this.situacao = this.aluno.getSituacao(this.id);
        this.mensagem.setText(this.situacao.getMensagem());
        this.data.setText(this.situacao.getData().formatarData());
        this.nome.setText(this.aluno.getNome());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        incializarLayout();
    }
}
