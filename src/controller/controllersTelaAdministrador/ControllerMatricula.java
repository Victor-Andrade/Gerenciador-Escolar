package controller.controllersTelaAdministrador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.excecoes.AlunoAlredyRegisteredException;
import model.excecoes.InvalidDateException;
import model.excecoes.InvalidFieldException;
import model.classes.pessoas.usuarios.Administrador;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerMatricula implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private DatePicker data;
    @FXML
    private TextField email;
    @FXML
    private TextField contato;
    @FXML
    private TextField emailPais;
    @FXML
    private CheckBox comCurso;
    @FXML
    private ChoiceBox<String> cursos;

    @FXML
    private Text aviso;

    /**
     * Falta Implementar
     */
    @FXML
    private void adicionar(){
        if(this.comCurso.isSelected()){
            adicionarAlunoCurso();
        }else{
            adicionarAlunoRegular();
        }
    }

    private void adicionarAlunoCurso(){
        LocalDate dataLayout = data.getValue();
        if(dataLayout != null){
            if(this.cursos.getSelectionModel().getSelectedItem() != null){
                try{
                    Data data = new Data(dataLayout.getYear(), dataLayout.getMonthValue(), dataLayout.getDayOfMonth());
                    this.fachadaAdministrador.matricularAlunoHoraExtra(nome.getText(), cpf.getText(), data,
                            email.getText(), contato.getText(), emailPais.getText(),
                            cursos.getSelectionModel().getSelectedItem());
                    this.aviso.setText("Adicionado com sucesso!");
                }catch(ClassNotFoundException | InvalidFieldException | AlunoAlredyRegisteredException
                        | InvalidDateException |IOException e){
                    this.aviso.setText(e.getMessage());
                }
            }else{
                this.aviso.setText("Curso não informado");
            }
        }else{
            this.aviso.setText("Data Inválida");
        }
    }

    private void adicionarAlunoRegular() {
        LocalDate dataLayout = data.getValue();
        if(dataLayout != null){
            try{
                Data data = new Data(dataLayout.getYear(), dataLayout.getMonthValue(), dataLayout.getDayOfMonth());
                this.fachadaAdministrador.matricularAluno(nome.getText(), cpf.getText(),
                        data, email.getText(), contato.getText(), emailPais.getText());
                this.aviso.setText("Adicionado com sucesso");
            }catch(ClassNotFoundException | InvalidFieldException | AlunoAlredyRegisteredException
                    | InvalidDateException |IOException e){
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Data Inválida");
        }
    }

    @FXML
    private void apagar(){
        this.nome.setText("");
        this.cpf.setText("");
        this.email.setText("");
        this.contato.setText("");
        this.emailPais.setText("");
        this.comCurso.setSelected(false);
        this.cursos.setValue(null);
    }
    /**
     *
     */
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

    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cursos.getItems().addAll("Inglês", "Robótica", "Música", "Judô");
    }
}
