package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Administrador;
import model.excecoes.AlunoNotFoundException;
import model.fachada.FachadaAdministrador;
import org.apache.commons.mail.EmailException;

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
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telasAdministrador/ModificacaoAluno.fxml"));

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
    private void gerarBoletim() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        try {
            String caminho = this.fachadaAdministrador.gerarBoletim(aluno);
            this.aviso.setText("Salvo com sucesso");
            if(enviarEmail.isSelected()){
                if(this.mensagem.getText() != null){
                    this.fachadaAdministrador.enviarEmailAnexo(this.administrador, this.aluno.getEmail(), this.mensagem.getText(), caminho);
                    this.aviso.setText("Salvo e enviado com sucesso");
                }else{
                    this.aviso.setText("Mensagem não informada pdf criado mas não enviado");
                }
            }
        } catch (EmailException e) {
            this.aviso.setText(e.getMessage());
        }
    }
    @FXML
    private void gerarCertificadoMatricula() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        try {
            String caminho = this.fachadaAdministrador.gerarCertificadoDeMatricula(aluno);
            this.aviso.setText("Salvo com sucesso");
            if(enviarEmail.isSelected()){
                if(this.mensagem.getText() != null){
                    this.fachadaAdministrador.enviarEmailAnexo(this.administrador, this.aluno.getEmail(), this.mensagem.getText(), caminho);
                    this.aviso.setText("Salvo e enviado com sucesso");
                }else{
                    this.aviso.setText("Mensagem não informada pdf criado mas não enviado");
                }
            }
        } catch (EmailException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    @FXML
    private void gerarCertificadoCurso() throws AlunoNotFoundException, IOException, ClassNotFoundException {
        if(this.aluno instanceof AlunoHoraExtra){
            try {
                String caminho = this.fachadaAdministrador.gerarCertificadoDeCurso((AlunoHoraExtra) aluno);
                this.aviso.setText("Salvo com sucesso");
                if(enviarEmail.isSelected()){
                    if(this.mensagem.getText() != null){
                        this.fachadaAdministrador.enviarEmailAnexo(this.administrador, this.aluno.getEmail(), this.mensagem.getText(), caminho);
                        this.aviso.setText("Salvo e enviado com sucesso");
                    }else{
                        this.aviso.setText("Mensagem não informada pdf criado mas não enviado");
                    }
                }
            } catch (EmailException e) {
                this.aviso.setText(e.getMessage());
            }
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
        this.nome.setText(this.aluno.getNome());
        this.email.setText(this.aluno.getEmail());
    }
}
