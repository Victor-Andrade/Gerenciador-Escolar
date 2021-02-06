package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerLogin {
    private Stage stage;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtSenha;
    @FXML
    private Text txtAviso;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void navegarParaTelaProfessor(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaProfessor/TelaProfessor.fxml"));
            Parent root = fxmlLoader.load();
            ((ControllerTelaProfessor) fxmlLoader.getController()).setStage(this.stage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Professor");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navegarParaTelaAdministrador(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/telaAdministrador/TelaAdministrador.fxml"));
            Parent root = fxmlLoader.load();
            ((ControllerTelaAdministrador) fxmlLoader.getController()).setStage(this.stage);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Administrador");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //Remove caracteres especiais no CPF
    private String removerCaracteres(String cpf){
        return cpf.replace(".", "").replace("-", "").trim();
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
    public void realizarLogin() throws IOException {
        navegarParaTelaProfessor();
       /* if(verificarCampos()){
            boolean encontrada = false;

            for (ILogin loginBanco : mainApp.getPersonData()){
                if (loginBanco.getCpf().equals(removerCaracteres(this.txtUsuario.getText()))){
                    if(loginBanco.getSenha().equals(this.txtSenha.getText())){
                        encontrada = true;
                        irParaTelaInicial();
                    }
                }
            }

            if(!encontrada){
                this.txtAviso.setText("Usuário ou senha inválidos");
            }
        }else{
            this.txtAviso.setText("Dados inválidos");
        }*/

    }

    //Leva o usuário para a tela inicial
    private void irParaTelaInicial(){
        System.out.println("Aqui vai a parte de alterar telas");
    }

    //Construtor
    public ControllerLogin(){

    }
}
