package controller.controllersTelaProfessor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Professor;
import model.classesUtilitarias.GerenciadorDeArquivos;
import model.excecoes.AlunoNotFoundException;
import model.excecoes.InvalidDateException;
import model.fachada.FachadaProfessor;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAdicionarFalta implements Initializable {
    private  Aluno aluno;
    private Stage stage;
    private Professor professor;
    private FachadaProfessor fachadaProfessor;

    @FXML
    private DatePicker data;
    @FXML
    private TextArea mensagem;
    @FXML
    private TextField txtCaminho;
    @FXML
    private CheckBox justificada;
    @FXML
    private Text aviso;

    @FXML
    private void selecionar() {
        try{
            this.txtCaminho.setText(GerenciadorDeArquivos.selecionarArquivo());
        } catch (IOException e){
            this.aviso.setText(e.getMessage());
        }
    }

    @FXML
    public void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/InfoAluno.fxml"));

            ControllerInfoAluno controller = new ControllerInfoAluno();

            controller.setParametros(this.stage, this.fachadaProfessor, this.aluno, this.professor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Aluno");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adicionar(){
        LocalDate dataTemp = this.data.getValue();
        if(dataTemp != null){
            String mensagem = this.mensagem.getText();
            if(mensagem != null){
                if(this.justificada.isSelected()){
                    String caminho = this.txtCaminho.getText();
                    if(caminho != null){
                        try{
                            this.fachadaProfessor.adicionarFaltaJustificada(this.aluno, mensagem, new Data(dataTemp.getYear(), dataTemp.getMonthValue(), dataTemp.getDayOfMonth()), caminho);
                            this.aviso.setText("Adicionado com sucesso");
                        } catch (InvalidDateException | IOException | ClassNotFoundException | AlunoNotFoundException e) {
                            this.aviso.setText(e.getMessage());
                        }
                    }else{
                        try{
                            this.fachadaProfessor.adicionarFalta(this.aluno, mensagem, new Data(dataTemp.getYear(), dataTemp.getMonthValue(), dataTemp.getDayOfMonth()));
                            this.aviso.setText("Adicionado com sucesso");
                        } catch (InvalidDateException | IOException | ClassNotFoundException | AlunoNotFoundException e) {
                            this.aviso.setText(e.getMessage());
                        }
                    }
                }
            }else{
                this.aviso.setText("Mensagem não informada");
            }
        }else{
            this.aviso.setText("Data não informada");
        }
    }

    public void setParametros(Aluno aluno, Stage stage, Professor professor, FachadaProfessor fachadaProfessor){
        this.aluno = aluno;
        this.stage = stage;
        this.professor = professor;
        this.fachadaProfessor = fachadaProfessor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
