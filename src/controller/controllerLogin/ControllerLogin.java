package controller.controllerLogin;

import controller.controllersTelaAdministrador.ControllerPrincipalAdministrador;
import controller.controllersTelaProfessor.ControllerTelaPrincipalProfessor;
import model.interfaces.ILogin;
import model.classes.pessoas.usuarios.Administrador;
import model.classes.pessoas.Pessoa;
import model.classes.pessoas.usuarios.Professor;
import model.classes.pessoas.usuarios.Usuario;
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
    private final FachadaAdministrador fachadaAdministrador;

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

    public void realizarLogin() throws IOException, ClassNotFoundException {
        verificarExistenciaUsuarios();
        if(verificarCampos()){
            boolean encontrada = false;

            //Comportamento polimórfico
            for (ILogin loginBanco : fachadaAdministrador.getUsuariosLogin()){
                String usuario = removerCaracteres(this.txtUsuario.getText());
                String cpf = removerCaracteres(loginBanco.getCpf());
                if(usuario.equals(cpf)){
                    if(this.txtSenha.getText().equals(loginBanco.getSenha())){
                        realizarNavegacao((Pessoa) loginBanco);
                        encontrada = true;
                    }
                }
            }
            if(!encontrada){
                this.txtAviso.setText("Usuário ou senha inválidos");
            }
        } else{
            this.txtAviso.setText("Dados inválidos");
        }
    }

    private void realizarNavegacao(Pessoa pessoa){
        try{
            FXMLLoader fxmlLoader;
            if(pessoa instanceof Professor){
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/telaProfessor/TelaPrincipalProfessor.fxml"));
                ControllerTelaPrincipalProfessor controller = new ControllerTelaPrincipalProfessor();
                controller.setParametros(this.stage, (Professor) pessoa, new FachadaProfessor());
                fxmlLoader.setController(controller);
            }else if(pessoa instanceof Administrador){
                fxmlLoader = new FXMLLoader(getClass().getResource("/view/Administrador/Principal.fxml"));
                ControllerPrincipalAdministrador controller = new ControllerPrincipalAdministrador();
                controller.setParametros(this.stage, (Administrador) pessoa, new FachadaAdministrador());
                fxmlLoader.setController(controller);
            }else{
                throw new Exception("Classe não esperada");
            }
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inicio");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void verificarExistenciaUsuarios() {
        try {
            List<Usuario> usuarios =  fachadaAdministrador.getUsuariosLogin();
            if(usuarios.size() < 1){
                System.out.println("Criando Usuário");
                fachadaAdministrador.adicionarAdmPadrao();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro, criando usuário");
            fachadaAdministrador.adicionarAdmPadrao();
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
}
