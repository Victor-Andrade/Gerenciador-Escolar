package controller.controllersTelaAdministrador;

import controller.controllersTelaProfessor.ControllerInfoAluno;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.classes.faltas.Falta;
import model.classes.faltas.FaltaJustificada;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.excecoes.AlunoNotFoundException;
import model.excecoes.InvalidDateException;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTelaFaltas implements Initializable {
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;
    private Stage stage;
    private Aluno aluno;

    @FXML
    private ListView<String> alunos;
    @FXML
    private ListView<String> faltas;
    @FXML
    private Text aviso;

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/TelaPrincipalAdministrador.fxml"));

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

    @FXML
    private void avancar(){
        if(this.aluno != null){
            String id = this.faltas.getSelectionModel().getSelectedItem();
            if(id != null){
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/TelaInfoFaltas.fxml"));

                    ControllerTelaInfoFaltas controller = new ControllerTelaInfoFaltas();

                    controller.setParametros(this.aluno, this.stage, this.fachadaAdministrador, this.administrador, Integer.parseInt(id));
                    fxmlLoader.setController(controller);

                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Falta");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                this.aviso.setText("Falta não selecionada");
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }

    @FXML
    private void inicializarAluno(){
        String aluno = this.alunos.getSelectionModel().getSelectedItem();
        if(aluno != null){
            try{
                this.aluno = this.fachadaAdministrador.buscarAluno(new Aluno(aluno, aluno, new Data(2001, 1, 1), "", "", ""));
                ArrayList<String> lista = new ArrayList<>();
                for(Falta falta: this.aluno.getFaltas()){
                    if(falta instanceof FaltaJustificada){
                        lista.add(Integer.toString(falta.getId()));
                    }
                }
                this.faltas.setItems(FXCollections.observableArrayList(lista));
            } catch (AlunoNotFoundException | InvalidDateException | IOException | ClassNotFoundException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }

    public void setParametros(Administrador administrador, Stage stage, FachadaAdministrador fachadaAdministrador){
        this.administrador = administrador;
        this.stage = stage;
        this.fachadaAdministrador = fachadaAdministrador;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.alunos.setItems(FXCollections.observableArrayList(this.fachadaAdministrador.todosAlunosFaltaJustificada()));
        } catch (IOException | ClassNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }
    }
}
