package model.classes.pessoas.usuarios;

import model.classes.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Classe representando o professor do sistema, guarda slista das turmas e a senha para autenticação
 * @author Victor Hugo e Pedro Vinícius
 */

public class Professor extends Usuario {
    private List<Double> turmas;

    public Professor(String nome, String cpf, Data data, String email, String contato, String senha) {
        super(nome, cpf, data, email, contato, senha);
        this.turmas = new ArrayList<>();
    }

    public List<Double> getTurmas() {
        return this.turmas;
    }

    public void setTurmas(List<Double> turmas) {
        this.turmas = turmas;
    }

    public void adicionarTurma(double id){
        this.turmas.add(id);
    }

    public void removerTurmas(double id){
        this.turmas.remove(id);
    }

    /**
     * COMPORTAMENTO POLIMORFICO
     * O comportamento polimórfico ocorre no método toString pois ele é solicitado
     * no layout de administrador e professor apresentando comportamentos diferentes
     */

    @Override
    public String toString(){
        return super.toString() + "\nSenha: " + this.getSenha() + "\nQuantidade de turmas: " + this.turmas.size();
    }
}
