package Aplicacao.login;

import Aplicacao.Main;
import Aplicacao.pessoas.Login;
import Aplicacao.pessoas.Pessoa;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginOverviewController {
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtSenha;
    @FXML
    private Text txtAviso;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
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
    public void realizarLogin(){
        if(verificarCampos()){
            boolean encontrada = false;
            Login loginTemp = new Login(removerCaracteres(this.txtUsuario.getText()), this.txtSenha.getText());

            for (Login loginBanco : mainApp.getPersonLoginData()){
                if (loginBanco.equals(loginTemp)){
                    irParaTelaInicial();
                    encontrada = true;
                    break;
                }
            }
            if(!encontrada){
                this.txtAviso.setText("Usuário ou senha inválidos");
            }
        }else{
            this.txtAviso.setText("Dados inválidos");
        }

    }

    //Leva o usuário para a tela inicial
    private void irParaTelaInicial(){
        for (Pessoa pessoaBanco: mainApp.getPersonData()){
            if(pessoaBanco.getCpf().equals(removerCaracteres(this.txtUsuario.getText()))){
                System.out.println("Aqui vai a parte de alterar telas");
            }
        }
    }

    //Construtor
    public LoginOverviewController(){

    }
}
