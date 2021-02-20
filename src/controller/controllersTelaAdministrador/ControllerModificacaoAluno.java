package controller.controllersTelaAdministrador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.excecoes.AlunoNotFoundException;
import model.excecoes.InvalidDateException;
import model.classes.materia.Materia;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerModificacaoAluno implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;

    private Aluno AlunoSelecionado;

    @FXML
    private Text aviso;
    @FXML
    private ListView<String> listaAlunos;
    @FXML
    private TextField nome;
    @FXML
    private TextField cpf;
    @FXML
    private TextField email;
    @FXML
    private TextField contato;
    @FXML
    private TextField emailPais;

    @FXML
    private DatePicker data;
    @FXML
    private ChoiceBox<String> materias;
    @FXML
    private ChoiceBox<String> curso;

    @FXML
    private TextField b1N1;
    @FXML
    private TextField b1N2;
    @FXML
    private Text m1;

    @FXML
    private TextField b2N1;
    @FXML
    private TextField b2N2;
    @FXML
    private Text m2;

    @FXML
    private TextField b3N1;
    @FXML
    private TextField b3N2;
    @FXML
    private Text m3;

    @FXML
    private TextField b4N1;
    @FXML
    private TextField b4N2;
    @FXML
    private Text m4;

    @FXML
    private Text horas;

    /**
     * Falta implementar
     */

    @FXML
    private void reportarConduta(){

    }
    @FXML
    private void removerFalta(){

    }
    @FXML
    private void salvar(){

    }
    @FXML
    private void gerarBoletim(){

    }
    @FXML
    private void gerarCertificadoMatricula(){

    }
    @FXML
    private void gerarCertificadoCurso(){

    }
    @FXML
    private void removerAluno(){

    }

    @FXML
    private void adicionarHoras(){

    }

    @FXML
    private void removerHoras(){

    }

    @FXML
    private void inicializarAluno(){
        String aluno = this.listaAlunos.getSelectionModel().getSelectedItem();
        try{
            if(aluno != null){
                this.AlunoSelecionado = this.fachadaAdministrador.buscarAluno(new Aluno(aluno, aluno,
                        new Data(2001, 1, 1), "", "", ""));
                inicializarLayoutAluno();
            }else{
                this.aviso.setText("Aluno não selecionado");
            }
        } catch (IOException | AlunoNotFoundException | ClassNotFoundException | InvalidDateException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    private void inicializarLayoutAluno(){
        ArrayList<String> materias = new ArrayList<>();
        for(Materia materia : this.AlunoSelecionado.getMaterias()){
            materias.add(materia.getNome());
        }

        this.materias.getItems().setAll(FXCollections.observableArrayList(materias));

        this.nome.setText(this.AlunoSelecionado.getNome());
        this.email.setText(this.AlunoSelecionado.getEmail());
        this.contato.setText(this.AlunoSelecionado.getNumeroParaContato());
        this.cpf.setText(this.AlunoSelecionado.getCpf());
        this.emailPais.setText(this.AlunoSelecionado.getEmailPais());
        //Falta data
        if(this.AlunoSelecionado instanceof AlunoHoraExtra){
            this.curso.getItems().setAll(((AlunoHoraExtra) this.AlunoSelecionado).getCurso().getNome());
            this.horas.setText((Integer.toString(((AlunoHoraExtra) this.AlunoSelecionado).getCurso().getHoras())));
        }
    }
    /**
     *
     */


    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/Principal.fxml"));

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
        try {
            ArrayList<String> alunos = this.fachadaAdministrador.todosOsAlunos();
            this.listaAlunos.setItems(FXCollections.observableArrayList(alunos));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
