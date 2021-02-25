package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Situacao;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.excecoes.AlunoNotFoundException;
import model.fachada.FachadaAdministrador;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
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
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/TelaSituacoes.fxml"));

            ControllerTelaSituacoes controller = new ControllerTelaSituacoes();

            controller.setParametros(this.administrador, this.stage, this.fachadaAdministrador);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Falta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void relatar(){
        try {
            this.fachadaAdministrador.enviarEmail(this.administrador, this.aluno.getEmailPais(), this.mensagem.getText(), this.data.getText());
            this.aviso.setText("Enviado com sucesso!");
        } catch (EmailException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    @FXML
    private void resolvida(){
        try{
            this.fachadaAdministrador.removerSituacao(this.aluno, this.situacao);
            this.aviso.setText("Removida com sucesso");
            voltar();
        } catch (IOException | ClassNotFoundException | AlunoNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }
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
