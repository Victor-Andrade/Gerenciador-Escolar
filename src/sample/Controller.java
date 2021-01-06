package sample;

import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class Controller {
    public Text txt;
    public Button btn;

    public void btnClick() {
        txt.setText("Deu certo!");
    }
}
