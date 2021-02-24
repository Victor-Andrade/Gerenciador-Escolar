package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Administrador;
import model.excecoes.AlunoNotFoundException;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGeracaoDocumentos implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private Aluno aluno;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private CheckBox enviarEmail;
    @FXML
    private Text nome;
    @FXML
    private Text email;
    @FXML
    private TextField mensagem;
    @FXML
    private Text aviso;

    @FXML
    private void gerarBoletim() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.fachadaAdministrador.gerarBoletim(aluno);

    }
    @FXML
    private void gerarCertificadoMatricula() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        this.fachadaAdministrador.gerarCertificadoDeMatricula(aluno);
    }
    @FXML
    private void gerarCertificadoCurso() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        if(this.aluno instanceof AlunoHoraExtra){
            this.fachadaAdministrador.gerarCertificadoDeCurso((AlunoHoraExtra) aluno);
        }else{
            this.aviso.setText("Aluno não possui curso");
        }
    }

    public void setParametros(Stage stage, Aluno aluno, Administrador administrador, FachadaAdministrador fachadaAdministrador){
        this.administrador = administrador;
        this.fachadaAdministrador = fachadaAdministrador;
        this.aluno = aluno;
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
