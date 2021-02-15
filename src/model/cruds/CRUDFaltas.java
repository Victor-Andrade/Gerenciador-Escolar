package model.cruds;

import model.classes.datas.Data;
import model.classes.faltas.Falta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fazer a manipulação primária de objetos de faltas em arquivos
 * @author Victor Hugo
 */

public class CRUDFaltas {
    List<Falta> faltas;
    String cpfAluno;
    String endereco;

    public CRUDFaltas(String cpfAluno) {
        this.cpfAluno = cpfAluno;
        this.faltas = new ArrayList<>();
        this.endereco = String.format("./dados/faltas/%s.pdf", cpfAluno);
    }

    public static CRUDFaltas carregarFaltas(String cpf) throws IOException, ClassNotFoundException {
        String endereco = String.format("./dados/faltas/%s.pdf", cpf);
        FileInputStream file = new FileInputStream(endereco);
        ObjectInputStream os = new ObjectInputStream(file);
        CRUDFaltas crud = (CRUDFaltas) os.readObject();
        os.close();

        return crud;
    }

    private void salvarAlteracoes() throws IOException {
        FileOutputStream file2 = new FileOutputStream(this.endereco);
        ObjectOutputStream os = new ObjectOutputStream(file2);
        os.writeObject(faltas);
        os.close();
    }

    public String getCpfAluno(){
        return this.cpfAluno;
    }

    public String getEndereco(){
        return this.endereco;
    }
    public void adicionarFalta(Falta falta) throws IOException {
            faltas.add(falta);
            salvarAlteracoes();
    }

    public List<Falta> listarFaltas(){
        return this.faltas;
    }

    public void removerFalta(Data data) throws IOException, ClassNotFoundException {
        for (Falta falta : this.faltas) {
            if(data.equals(falta.getData())){
                faltas.remove(falta);
                this.salvarAlteracoes();
                return;
            }
        }
        throw new ClassNotFoundException("Falta não encontrada");
    }

    public void justificarFalta(Data data, String documento) throws ClassNotFoundException, IOException {
        for (Falta falta : this.faltas) {
            if(data.equals(falta.getData())){
                falta.justificar(documento);
                this.salvarAlteracoes();
                return;
            }
        }
        throw new ClassNotFoundException("Falta não encontrada");
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CRUDFaltas){
            CRUDFaltas crud = (CRUDFaltas) obj;
            return this.cpfAluno.equals(crud.getCpfAluno());
        }
        return false;
    }
}
