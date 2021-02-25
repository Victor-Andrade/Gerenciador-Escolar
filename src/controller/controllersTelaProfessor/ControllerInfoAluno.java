package controller.controllersTelaProfessor;

import javafx.scene.control.*;
import model.classes.materia.Materia;
import model.classes.pessoas.alunos.Aluno;
import model.classes.pessoas.alunos.AlunoHoraExtra;
import model.classes.pessoas.usuarios.Professor;
import model.excecoes.AlunoNotFoundException;
import model.excecoes.NotasInvalidasException;
import model.fachada.FachadaProfessor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller da terceira tela do Professor. Essa tela lista informações do aluno selecionado na tela 2
 */

public class ControllerInfoAluno implements Initializable {
    private Stage stage;
    private Professor professor;
    private Aluno aluno;
    private FachadaProfessor fachadaProfessor;

    public void setParametros(Stage stage, Professor professor, Aluno aluno, FachadaProfessor fachadaProfessor){
        this.stage = stage;
        this.professor = professor;
        this.aluno = aluno;
        this.fachadaProfessor = fachadaProfessor;
    }

    @FXML
    private Text txtNome;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtContato;
    @FXML
    private Text curso;
    @FXML
    private Text horas;

    @FXML
    private Text aviso;

    @FXML
    private ListView<String> materias;

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
    private DatePicker dataPicker;
    @FXML
    private CheckBox enviarEmail;


    /**
     * Falta Fazer
     */
    @FXML
    public void gerarBoletim(){
        try {
            String caminho = this.fachadaProfessor.gerarBoletim(this.aluno);
            this.aviso.setText("Salvo com sucesso");
            if(this.enviarEmail.isSelected()){
                this.fachadaProfessor.enviarEmailAnexo(this.professor, this.aluno.getEmail(), "", caminho);
                this.aviso.setText("Salvo e enviado com sucesso");
            }
        } catch (IOException | ClassNotFoundException | AlunoNotFoundException | EmailException e) {
            this.aviso.setText(e.getMessage());
        }
    }

    @FXML
    private void salvarNotas(){
        if(this.aluno != null){
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
                    Materia materia = this.aluno.getMateria(materiaString);

                    materia.setNotasPrimeiroBimestre(b1n1, b1n2);
                    materia.setNotasSegundoBimestre(b2n1, b2n2);
                    materia.setNotasTerceiroBimestre(b3n1, b3n2);
                    materia.setNotasQuartoBimestre(b4n1, b4n2);

                    this.aluno.setMateria(materia);

                    this.fachadaProfessor.atualizarNotasAluno(this.aluno);
                    this.aluno = this.fachadaProfessor.buscarAluno(this.aluno);
                    inicializarLayoutAluno();
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
    private void adicionarFalta(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/AdicaoDeFaltas.fxml"));

            ControllerAdicionarFalta controller = new ControllerAdicionarFalta();

            controller.setParametros(this.aluno, this.stage, this.professor, this.fachadaProfessor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Adicionar Falta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reportarSituacao(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/ReportarCondulta.fxml"));

            ControllerReportarCondulta controller = new ControllerReportarCondulta();

            controller.setParametros(this.aluno, this.stage, this.professor, this.fachadaProfessor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Reportar Condulta");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     */
    @FXML
    private void inicializarNotas(){
        String materiaString = materias.getSelectionModel().getSelectedItem();
        if(materiaString != null){
            Materia materia = aluno.getMateria(materiaString);
            if(materia != null){
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
                this.aviso.setText("Matéria não encontrada");
            }
        }else{
            this.aviso.setText("Materia não selecionada");
        }
    }

    @FXML
    private void voltar(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/TelaPrincipalProfessor.fxml"));

            ControllerTelaPrincipalProfessor controller = new ControllerTelaPrincipalProfessor();

            controller.setParametros(this.stage, this.professor, this.fachadaProfessor);
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Início");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inicializarLayoutAluno(){
        ArrayList<String> materias = new ArrayList<>();
        for(Materia materia : this.aluno.getMaterias()){
            materias.add(materia.getNome());
        }

        this.materias.setItems(FXCollections.observableArrayList(materias));

        txtNome.setText(aluno.getNome());
        txtEmail.setText(aluno.getEmail());
        txtContato.setText(aluno.getNumeroParaContato());
        this.curso.setText("");
        this.horas.setText("");
        if(this.aluno instanceof AlunoHoraExtra){
            AlunoHoraExtra alunoHoraExtra = (AlunoHoraExtra) this.aluno;
            this.curso.setText(alunoHoraExtra.getCurso().getNome());
            this.horas.setText(Integer.toString(alunoHoraExtra.getCurso().getHoras()));
        }
    }

    public void setParametros(Stage stage, FachadaProfessor fachadaProfessor, Aluno aluno, Professor professor){
        this.stage = stage;
        this.fachadaProfessor = fachadaProfessor;
        this.aluno = aluno;
        this.professor = professor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializarLayoutAluno();
    }
}
