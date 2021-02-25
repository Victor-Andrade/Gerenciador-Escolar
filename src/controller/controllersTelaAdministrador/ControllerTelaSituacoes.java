package controller.controllersTelaAdministrador;

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
import model.classes.Situacao;
import model.classes.faltas.Falta;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.usuarios.Administrador;
import model.excecoes.AlunoNotFoundException;
import model.excecoes.InvalidDateException;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerTelaSituacoes implements Initializable {
    private Administrador administrador;
    private Stage stage;
    private FachadaAdministrador fachadaAdministrador;
    private Aluno aluno;

    @FXML
    private Text aviso;

    @FXML
    private ListView<String> alunos;
    @FXML
    private ListView<String> situacoes;

    @FXML
    private void avancar() {
        if (this.aluno != null) {
            String id = this.situacoes.getSelectionModel().getSelectedItem();
            if (id != null) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/TelaInfoSituacao.fxml"));

                    ControllerTelaInfoSituacoes controller = new ControllerTelaInfoSituacoes();

                    controller.setParametros(this.aluno, this.stage, this.fachadaAdministrador, this.administrador, Integer.parseInt(id));
                    fxmlLoader.setController(controller);

                    Parent root = fxmlLoader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Falta");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                this.aviso.setText("Falta não selecionada");
            }
        } else {
            this.aviso.setText("Aluno não selecionado");
        }
    }

    public void setParametros(Administrador administrador, Stage stage, FachadaAdministrador fachadaAdministrador){
        this.administrador = administrador;
        this.stage = stage;
        this.fachadaAdministrador = fachadaAdministrador;
    }

    @FXML
    private void inicializarAluno(){
        String aluno = this.alunos.getSelectionModel().getSelectedItem();
        if(aluno != null){
            try{
                this.aluno = this.fachadaAdministrador.buscarAluno(new Aluno(aluno, aluno, new Data(2001, 1, 1), "", "", ""));
                ArrayList<String> lista = new ArrayList<>();
                for(Situacao situacao: this.aluno.getSituacoes()){
                    lista.add(Integer.toString(situacao.getId()));
                }
                this.situacoes.setItems(FXCollections.observableArrayList(lista));
            } catch (AlunoNotFoundException | InvalidDateException | IOException | ClassNotFoundException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }

    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.alunos.setItems(FXCollections.observableArrayList(this.fachadaAdministrador.todosAlunosComSituacoes()));
        } catch (IOException | ClassNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }
    }
}
