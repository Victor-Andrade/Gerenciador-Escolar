package controller;

import model.classes.pessoas.Administrador;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.Professor;
import controller.controllersTelaProfessor.ControllerT1;
import model.fachada.FachadaAdministrador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.fachada.FachadaProfessor;
import java.io.IOException;
import java.util.List;

/**
 * Controller responsável pelas interações na tela de login
 */
public class ControllerLogin {
    private FachadaAdministrador fachadaAdministrador;

    private Stage stage;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtSenha;
    @FXML
    private Text txtAviso;

    public ControllerLogin(){
        this.fachadaAdministrador = new FachadaAdministrador();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void navegarParaTelaProfessor(Professor professor){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/professorTela/T1 Professor.fxml"));

            ControllerT1 controller = new ControllerT1();

            controller.setParams(this.stage, professor, new FachadaProfessor());
            fxmlLoader.setController(controller);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Professor");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navegarParaTelaAdministrador(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaAdministrador/TelaAdministrador.fxml"));
            ControllerTelaAdministrador controller = new ControllerTelaAdministrador();
            controller.setTest("UHUUUUUU");
            System.out.println(controller.getTest());
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            ((ControllerTelaAdministrador) fxmlLoader.getController()).setStage(this.stage);


//            fxmlLoader.setController(controller);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Administrador");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Remove caracteres especiais no CPF
    private String removerCaracteres(String cpf){
        String a = cpf.replace(".", "").replace("-", "").trim();
        return a;
    }

    //Verifica se uma string é um inteiro
    private boolean eInteiro( String s ) {
        char[] c = s.toCharArray();

        for (char value : c)
            if (!Character.isDigit(value)) {
                return false;
            }
        return true;
    }

    //Verifica se os campos de login e senha são válidos
    private boolean verificarCampos(){
        //Remove caracteres especiais e de espaço
        String usuario = removerCaracteres(this.txtUsuario.getText());

        return usuario.length() >= 11 && eInteiro(usuario) && this.txtSenha.getText().length() >= 8;
    }

    //Tenta realizar o login
    public void realizarLogin() throws IOException, ClassNotFoundException {
        verificarExistenciaUsuarios();
       if(verificarCampos()){
            boolean encontrada = false;

            for (Pessoa loginBanco : fachadaAdministrador.getUsuariosLogin()){
                String text = removerCaracteres(this.txtUsuario.getText());
                String cpf = removerCaracteres(loginBanco.getCpf());
                if (text.equals(cpf)){
                    if(loginBanco instanceof Administrador){
                        Administrador temp = (Administrador) loginBanco;
                        if(temp.getSenha().equals(this.txtSenha.getText())){
                            encontrada = true;
                            navegarParaTelaAdministrador();
                        }
                    }
                    if(loginBanco instanceof Professor){
                        Professor temp = (Professor) loginBanco;
                        if(temp.getSenha().equals(this.txtSenha.getText())){
                            encontrada = true;
                            navegarParaTelaProfessor(temp);
                        }
                    }
                }
            }

            if(!encontrada){
                this.txtAviso.setText("Usuário ou senha inválidos");
            }
       }
       else{
            this.txtAviso.setText("Dados inválidos");
       }
    }

    private void verificarExistenciaUsuarios() {
        try {
            List<Pessoa> usuarios =  fachadaAdministrador.getUsuariosLogin();
            if(usuarios.size() < 1){
                System.out.println("Criando Usuário");
                fachadaAdministrador.adicionarAdmPadrao();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro, criando usuário");
            fachadaAdministrador.adicionarAdmPadrao();
        }
    }
}
