package Classes.pessoas;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Pessoa {
    StringProperty nome;
    StringProperty cpf;
    ObjectProperty<LocalDate> dataDeNascimento;
    StringProperty email;
    StringProperty numeroParaContato;

    public String getCpf() {
        return cpf.get();
    }


    public Pessoa(String nome, String cpf, int[] data, String email, String contato) {
        this.nome = new SimpleStringProperty(nome);
        this.cpf = new SimpleStringProperty(cpf);
        this.dataDeNascimento = new SimpleObjectProperty<>(LocalDate.of(data[0], data[1], data[2]));
        this.email = new SimpleStringProperty(email);
        this.numeroParaContato = new SimpleStringProperty(contato);
    }




}
