package controller.controllersTelaAdministrador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.classes.Data;
import model.excecoes.*;
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
    private ListView<String> materias;
    @FXML
    private Text curso;

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
    @FXML
    private Text dataString;

    /**
     * Falta implementar
     */

    @FXML
    private void modificar(){
        if(this.AlunoSelecionado != null){
            try {
                double b1n1 = Double.parseDouble(this.b1N1.getText());
                double b1n2 = Double.parseDouble(this.b1N2.getText());

                double b2n1 = Double.parseDouble(this.b2N1.getText());
                double b2n2 = Double.parseDouble(this.b2N2.getText());

                double b3n1 = Double.parseDouble(this.b3N1.getText());
                double b3n2 = Double.parseDouble(this.b3N2.getText());

                double b4n1 = Double.parseDouble(this.b4N1.getText());
                double b4n2 = Double.parseDouble(this.b4N2.getText());

                String materiaString = this.materias.getSelectionModel().getSelectedItem();
                if(materiaString != null){
                    Materia materia = this.AlunoSelecionado.getMateria(materiaString);

                    materia.setNotasPrimeiroBimestre(b1n1, b1n2);
                    materia.setNotasSegundoBimestre(b2n1, b2n2);
                    materia.setNotasTerceiroBimestre(b3n1, b3n2);
                    materia.setNotasQuartoBimestre(b4n1, b4n2);

                    this.AlunoSelecionado.setMateria(materia);

                    this.fachadaAdministrador.atualizarNotasAluno(this.AlunoSelecionado);
                    this.AlunoSelecionado = null;
                    reiniciarLayoutAluno();
                    inicializarListaAlunos();
                    this.aviso.setText("Atualizado com sucesso");
                }else{
                    this.aviso.setText("Materia não selecionada");
                }
            }catch (NumberFormatException  e){
                this.aviso.setText("Valor nos campos inválido");
            } catch (AlunoNotFoundException | NotasInvalidasException | IOException | ClassNotFoundException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }

    @FXML
    private void salvar(){
        if(this.AlunoSelecionado != null){
            try{
                this.fachadaAdministrador.atualizarDadosPessoaisAluno(this.AlunoSelecionado, this.nome.getText(), this.cpf.getText(), new Data(this.data.getValue()), this.email.getText(), this.contato.getText(), this.emailPais.getText());
                inicializarListaAlunos();
                this.AlunoSelecionado = null;
                reiniciarLayoutAluno();
                this.aviso.setText("Atualizado com sucesso");
            } catch (InvalidDateException | InvalidFieldException | ClassNotFoundException | IOException | AlunoNotFoundException | AlunoAlredyRegisteredException e) {
                this.aviso.setText(e.getMessage());
            } catch (NullPointerException e){
                this.aviso.setText("Dados não preenchidos");
            }
        }
    }

    @FXML
    private void removerAluno(){
        if(this.AlunoSelecionado != null){
            try{
                this.fachadaAdministrador.excluirAluno(this.AlunoSelecionado);
                this.AlunoSelecionado = null;
                reiniciarLayoutAluno();
                inicializarListaAlunos();
            } catch (AlunoNotFoundException | InvalidDateException | IOException | ClassNotFoundException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
    }

    @FXML
    private void adicionarHoras(){
        if(this.AlunoSelecionado instanceof AlunoHoraExtra){
            try {
                ((AlunoHoraExtra)AlunoSelecionado).getCurso().adicionarHoras();
                this.fachadaAdministrador.atualizarNotasAluno(this.AlunoSelecionado);
                inicializarAluno();
                inicializarLayoutAluno();
                this.aviso.setText("Adicionado com sucesso");
            } catch (AlunoNotFoundException | IOException | ClassNotFoundException | NotasInvalidasException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não possui curso ou não foi selecionado");
        }
    }

    @FXML
    private void removerHoras(){
        if(this.AlunoSelecionado instanceof AlunoHoraExtra){
            try {
                ((AlunoHoraExtra)AlunoSelecionado).getCurso().removerHoras();
                this.fachadaAdministrador.atualizarNotasAluno(this.AlunoSelecionado);
                inicializarAluno();
                inicializarLayoutAluno();
                this.aviso.setText("Removido com sucesso");
            } catch (AlunoNotFoundException | IOException | ClassNotFoundException | NotasInvalidasException e) {
                this.aviso.setText(e.getMessage());
            }
        }else{
            this.aviso.setText("Aluno não possui curso ou não foi selecionado");
        }
    }

    @FXML
    private void documentos(){
        if(this.AlunoSelecionado != null){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/GeracaoDocumentos.fxml"));

                ControllerGeracaoDocumentos controller = new ControllerGeracaoDocumentos();

                controller.setParametros(this.stage, this.AlunoSelecionado, this.administrador, this.fachadaAdministrador);
                fxmlLoader.setController(controller);

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Documentos");
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            this.aviso.setText("Aluno não selecionado");
        }
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

    @FXML
    private void inicializarDadosMateria(){
        String materiaString = this.materias.getSelectionModel().getSelectedItem();
        if(materiaString != null){
            if(this.AlunoSelecionado != null){
                Materia materia = this.AlunoSelecionado.getMateria(materiaString);

                b1N1.setText(Double.toString(materia.getPrimeiroBimestre().getNota1()));
                b1N2.setText(Double.toString(materia.getPrimeiroBimestre().getNota2()));

                b2N1.setText(Double.toString(materia.getSegundoBimestre().getNota1()));
                b2N2.setText(Double.toString(materia.getSegundoBimestre().getNota2()));

                b3N1.setText(Double.toString(materia.getTerceiroBimestre().getNota1()));
                b3N2.setText(Double.toString(materia.getTerceiroBimestre().getNota2()));

                b4N1.setText(Double.toString(materia.getQuartoBimestre().getNota1()));
                b4N2.setText(Double.toString(materia.getQuartoBimestre().getNota2()));

                m1.setText(Double.toString(materia.getMedia1Bimestre()));
                m2.setText(Double.toString(materia.getMedia2Bimestre()));
                m3.setText(Double.toString(materia.getMedia3Bimestre()));
                m4.setText(Double.toString(materia.getMedia4Bimestre()));
            }else{
                this.aviso.setText("Aluno não selecionado");
            }
        }else{
            this.aviso.setText("Materia não selecionada");
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
        this.cpf.setText(this.AlunoSelecionado.getCpfFormatado());
        this.emailPais.setText(this.AlunoSelecionado.getEmailPais());
        this.dataString.setText(this.AlunoSelecionado.getDataDeNascimento().formatarData());
        this.curso.setText("");
        this.horas.setText("");
        //Falta data
        if(this.AlunoSelecionado instanceof AlunoHoraExtra){
            this.curso.setText(((AlunoHoraExtra) this.AlunoSelecionado).getCurso().getNome());
            this.horas.setText((Integer.toString(((AlunoHoraExtra) this.AlunoSelecionado).getCurso().getHoras())));
        }
    }

    private void reiniciarLayoutAluno(){
        this.nome.setText("");
        this.cpf.setText("");
        this.email.setText("");
        this.emailPais.setText("");
        this.dataString.setText("");
        this.contato.setText("");
        this.curso.setText("");
        this.horas.setText("");
        this.materias.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        this.b1N1.setText("");
        this.b1N2.setText("");
        this.b2N1.setText("");
        this.b2N2.setText("");
        this.b3N1.setText("");
        this.b3N2.setText("");
        this.b4N1.setText("");
        this.b4N2.setText("");
        this.m1.setText("");
        this.m2.setText("");
        this.m3.setText("");
        this.m4.setText("");
    }

    public void setParametros(Stage stage, Administrador administrador, FachadaAdministrador fachada){
        this.stage = stage;
        this.administrador = administrador;
        this.fachadaAdministrador = fachada;
    }

    private void inicializarListaAlunos(){
        try {
            ArrayList<String> alunos = this.fachadaAdministrador.todosOsAlunos();
            this.listaAlunos.setItems(FXCollections.observableArrayList(alunos));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarListaAlunos();
    }
}
