package controller.controllersTelaAdministrador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.excecoes.*;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerModificacaoUsuario implements Initializable {
    private Stage stage;
    private Administrador administrador;
    private FachadaAdministrador fachadaAdministrador;
    private Administrador administradorSelecionado;
    private Professor professorSelecionado;

    @FXML
    private Text aviso;
    @FXML
    private ListView<String> listaUsuarios;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private TextField contato;
    @FXML
    private TextField cpf;
    @FXML
    private TextField senha;

    @FXML
    private ChoiceBox<String> turmas;
    @FXML
    private ListView<String> listaTurmasAdicionar;

    /**
     * Falta Implementar
     */
    @FXML
    private void removerTurma(){

    }
    @FXML
    private void adicionarTurma(){
        //// USAR PARA CONVERTER OS PRIMEIROS CARACTERES DO ID PARA DOUBLE
        /*if(this.professorSelecionado != null){
            try{
                char[] nome = this.listaTurmasAdicionar.getSelectionModel().getSelectedItem().toCharArray();
                StringBuilder id = new StringBuilder();
                for (int i = 0; nome[i] != ':'; i++) {
                    id.append(nome[i]);
                }
                double idDouble = Double.parseDouble(id.toString());
                Turma turma = this.fachadaAdministrador.buscarTurma(idDouble);
                this.fachadaAdministrador.adicionarTurmaEmProfessor(turma, this.professorSelecionado);
                this.turmas.setItems(FXCollections.observableArrayList(
                this.fachadaAdministrador.buscarTurma(idDouble).getNomesAlunos()));
            } catch (IOException | TurmaRepetidaException | UsuarioAlreadyRegisteredException
            | UsuarioNotFoundException | ClassNotFoundException | TurmaNaoExisteException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Usuário não é professor ou não foi selecionado");
        }*/
    }
    @FXML
    private void salvar(){

    }
    @FXML
    private void removerUsuario(){
        try{
            if(this.administradorSelecionado != null){
                this.fachadaAdministrador.excluirUsuario(this.administradorSelecionado);
            }else if(this.professorSelecionado != null){
                this.fachadaAdministrador.excluirUsuario(this.professorSelecionado);
            }else{
                this.aviso.setText("Usuário não selecionado");
            }
        } catch (UsuarioNotFoundException | IOException | ClassNotFoundException | InvalidDateException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    @FXML
    private void inicializarUsuario(){
        try{
            String usuarioString = this.listaUsuarios.getSelectionModel().getSelectedItem();
            ///###POSSÌVEL ERRO AQUI???
            Usuario pessoa = this.fachadaAdministrador.buscarUsuario(new Professor(usuarioString, usuarioString,
                    new Data(2001, 1, 1),"", "", ""));
            if(pessoa instanceof Professor){
                this.professorSelecionado = (Professor) pessoa;
                this.administradorSelecionado = null;
                iniciarDadosAdministrador(this.professorSelecionado);
                iniciarDadosProfessor(this.professorSelecionado);
            }else{
                this.administradorSelecionado = (Administrador) pessoa;
                this.professorSelecionado = null;
                iniciarDadosAdministrador(this.administradorSelecionado);
            }
        } catch (UsuarioNotFoundException | ClassNotFoundException | IOException
                | TurmaNaoExisteException | InvalidDateException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    private void iniciarDadosProfessor(Professor professor)
            throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        ArrayList<String> turmas = new ArrayList<>();
        for(double id: professor.getTurmas()){
            turmas.add(id+ ": " + this.fachadaAdministrador.buscarTurma(id).getApelido());
        }
        this.turmas.setItems(FXCollections.observableArrayList(turmas));
        this.listaTurmasAdicionar.setItems(FXCollections.observableArrayList(this.fachadaAdministrador.todasAsTurmas()));
    }

    private void iniciarDadosAdministrador(Usuario pessoa){
        this.nome.setText(pessoa.getNome());
        this.email.setText(pessoa.getEmail());
        this.contato.setText(pessoa.getNumeroParaContato());
        this.cpf.setText(pessoa.getCpf());
        this.senha.setText(pessoa.getSenha());

        this.turmas.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.listaTurmasAdicionar.setItems(FXCollections.observableArrayList(new ArrayList<>()));
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
            ArrayList<String> usuarios = this.fachadaAdministrador.todosOsUsuarios();
            this.listaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
