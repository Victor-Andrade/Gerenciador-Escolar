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
import model.classes.Turma;
import model.excecoes.*;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
import model.fachada.FachadaAdministrador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

    @FXML
    private Text data;
    @FXML
    private DatePicker dataSelecao;

    /**
     * Falta Implementar
     */
    @FXML
    private void salvar(){
        String nome = this.nome.getText();
        String cpf = this.cpf.getText();
        String contato = this.contato.getText();
        String senha = this.senha.getText();
        String email = this.email.getText();
        try{
            LocalDate dataTemp = this.dataSelecao.getValue();
            Data data = new Data(dataTemp.getYear(), dataTemp.getMonthValue(), dataTemp.getDayOfMonth());
            if(this.administradorSelecionado != null){
                this.fachadaAdministrador.atualizarInformacoesUsuario(this.administradorSelecionado, nome, cpf, data, email, contato, senha);
                reiniciarCampos();
                inicializarListaUsuarios();
                this.aviso.setText("Atualizado com sucesso!");
            }else if(this.professorSelecionado != null){
                this.fachadaAdministrador.atualizarInformacoesUsuario(this.professorSelecionado, nome, cpf, data, email, contato, senha);
                reiniciarCampos();
                inicializarListaUsuarios();
                this.aviso.setText("Atualizado com sucesso!");
            }else{
                this.aviso.setText("Usuário não selecionado");
            }
        } catch (InvalidDateException | IOException | InvalidFieldException | ClassNotFoundException e) {
            this.aviso.setText(e.getMessage());
        }

    }
    /**
     *
     */

    @FXML
    private void removerTurma(){
        if(this.professorSelecionado != null){
            String nomeTurma = this.turmas.getSelectionModel().getSelectedItem();
            if(nomeTurma != null){
                try{
                    char[] nome = nomeTurma.toCharArray();
                    StringBuilder id = new StringBuilder();
                    for (int i = 0; nome[i] != ':'; i++) {
                        id.append(nome[i]);
                    }
                    double idDouble = Double.parseDouble(id.toString());
                    Turma turma = this.fachadaAdministrador.buscarTurma(idDouble);
                    this.fachadaAdministrador.removerTurmaDoProfessor(turma, this.professorSelecionado);
                    inicializarUsuario();
                    iniciarDadosProfessor(this.professorSelecionado);
                } catch (IOException | UsuarioNotFoundException | ClassNotFoundException | TurmaNaoExisteException e) {
                    this.aviso.setText(e.getMessage());
                }
            }else{
                this.aviso.setText("Turma não selecionada");
            }
        }else{
            this.aviso.setText("Usuário não é professor ou não foi selecionado");
        }
    }

    @FXML
    private void adicionarTurma(){
        if(this.professorSelecionado != null){
            String nomeTurma = this.listaTurmasAdicionar.getSelectionModel().getSelectedItem();
            if(nomeTurma != null){
                try{
                    char[] nome = nomeTurma.toCharArray();
                    StringBuilder id = new StringBuilder();
                    for (int i = 0; nome[i] != ':'; i++) {
                        id.append(nome[i]);
                    }
                    double idDouble = Double.parseDouble(id.toString());
                    Turma turma = this.fachadaAdministrador.buscarTurma(idDouble);
                    this.fachadaAdministrador.adicionarTurmaEmProfessor(turma, this.professorSelecionado);
                    inicializarUsuario();
                    iniciarDadosProfessor(this.professorSelecionado);
                } catch (IOException | TurmaRepetidaException | UsuarioAlreadyRegisteredException | UsuarioNotFoundException | ClassNotFoundException | TurmaNaoExisteException e) {
                    this.aviso.setText(e.getMessage());
                }
            }else{
                this.aviso.setText("Turma não selecionada");
            }
        }else{
            this.aviso.setText("Usuário não é professor ou não foi selecionado");
        }
    }

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

    @FXML
    private void removerUsuario(){
        try{
            if(this.administradorSelecionado != null){
                this.fachadaAdministrador.excluirUsuario(this.administradorSelecionado);
                reiniciarCampos();
                inicializarListaUsuarios();
                this.aviso.setText("Removido com sucesso");
            }else if(this.professorSelecionado != null){
                this.fachadaAdministrador.excluirUsuario(this.professorSelecionado);
                inicializarListaUsuarios();
                this.aviso.setText("Removido com sucesso");
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

    private void inicializarListaUsuarios(){
        try {
            ArrayList<String> usuarios = this.fachadaAdministrador.todosOsUsuarios();
            this.listaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void iniciarDadosAdministrador(Usuario pessoa){
        this.nome.setText(pessoa.getNome());
        this.email.setText(pessoa.getEmail());
        this.contato.setText(pessoa.getNumeroParaContato());
        this.cpf.setText(pessoa.getCpf());
        this.senha.setText(pessoa.getSenha());
        this.data.setText(pessoa.getDataDeNascimento().formatarData());

        this.turmas.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.listaTurmasAdicionar.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    }

    private void iniciarDadosProfessor(Professor professor) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        ArrayList<String> turmas = new ArrayList<>();
        for(double id: professor.getTurmas()){
            turmas.add(id+ ": " + this.fachadaAdministrador.buscarTurma(id).getApelido());
        }
        this.turmas.setItems(FXCollections.observableArrayList(turmas));
        this.listaTurmasAdicionar.setItems(FXCollections.observableArrayList(this.fachadaAdministrador.todasAsTurmas()));
    }

    private void reiniciarCampos(){
        this.nome.setText("");
        this.email.setText("");
        this.contato.setText("");
        this.senha.setText("");
        this.cpf.setText("");
        this.data.setText("");
        this.listaUsuarios.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.listaTurmasAdicionar.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.turmas.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    }

    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarListaUsuarios();
    }
}
