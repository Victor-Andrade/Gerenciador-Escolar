package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.classesUtilitarias.GerenciadorDeArquivos;
import model.excecoes.AlunoNotFoundException;
import model.fachada.FachadaAdministrador;
import model.fachada.FachadaProfessor;

import java.io.IOException;
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
//
    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/TelaFaltas.fxml"));

            ControllerTelaFaltas controller = new ControllerTelaFaltas();

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
    private void mudarStatus(){
        try{
            this.fachadaAdministrador.mudarStatusFalta(this.aluno, this.falta);
            this.aviso.setText("Mudado com sucesso");
            if(this.confirmada.getText().equalsIgnoreCase("Confirmada")){
                this.confirmada.setText("Não confirmada");
            }else{
                this.confirmada.setText("Confirmada");
            }
        } catch (IOException | ClassNotFoundException | AlunoNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }
    }
    @FXML
    private void excluir(){
        try{
            this.fachadaAdministrador.removerFalta(this.aluno, this.falta);
            this.aviso.setText("Removido com sucessor");
            voltar();
        } catch (IOException | ClassNotFoundException | AlunoNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }
    }
    @FXML
    private void abrir(){
        if(this.caminho.getText() != null){
            try{
                GerenciadorDeArquivos.abrirArquivos(this.caminho.getText());
            }catch (IOException e){
                this.aviso.setText("Arquivo não encontrado");
            }
        }else{
            this.aviso.setText("Caminho do arquivo não especificado arquivo");
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
