package model.classesUtilitarias;

import javafx.stage.FileChooser;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe responsável por fazer a manipulação de diretórios de arquivos
 * @author Victor Hugo
 */

public abstract class GerenciadorDeArquivos {

    public static String selecionarArquivo() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
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

    public static boolean existe(String endereco){
        java.io.File file = new File(endereco);
        return file.exists();
    }

    public static String selecionarPasta(){

        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);

        return  f.getSelectedFile().getAbsolutePath();
    }

    public static void abrirArquivos(String caminho) throws IOException {
        Desktop.getDesktop().open(new File(caminho));
    }
}
