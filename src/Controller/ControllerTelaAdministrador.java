package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ControllerTelaAdministrador {
    private Stage stage;
    private String test;
    @FXML
    private Button buttonTest;

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setTest(String g){
        this.test = g;
    }
    public String getTest(){
        return this.test;
    }


    @FXML
    private void button(){
        System.out.println(this.test);
        buttonTest.setText(this.test);
    }

}
