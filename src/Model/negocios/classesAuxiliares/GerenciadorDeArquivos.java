package Model.negocios.classesAuxiliares;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class GerenciadorDeArquivos {

    public static String selecionarArquivo() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            System.out.println(selectedFile);
            return selectedFile.toString();
        }else{
            throw new IOException("Arquivo Não encontrado");
        }
    }

    public static void copiarArquivo(String file, String destino){
        try {
            FileInputStream fs = new FileInputStream(file);
            int  b;
            FileOutputStream os = new FileOutputStream(destino);
            while ((b = fs.read()) != -1) {
                os.write(b);
            }
            os.close();
            fs.close();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
}