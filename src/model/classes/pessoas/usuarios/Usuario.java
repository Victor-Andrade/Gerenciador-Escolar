package model.classes.pessoas.usuarios;

import model.classes.Data;
import model.interfaces.ILogin;
import model.classes.pessoas.Pessoa;

/**
 * Classe pai da hierarquia de usuários do projeto.
 * Escolhemos tal hierarquia pois foi observado que todos os objetos
 * administrador e professor detem da caracteristica senha o qual faria mais sentido para aplicação
 * de polimorfismo nos negócios.
 *
 * @author Victor Hugo e Pedro Vinícius
 */

public class Usuario extends Pessoa implements ILogin {
    private String senha;

    public Usuario(String nome, String cpf, Data dataDeNascimento, String email,
                   String numeroParaContato, String senha) {
        super(nome, cpf, dataDeNascimento, email, numeroParaContato);
        this.senha = senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    @Override
    public String getSenha() {
        return this.senha;
    }
}
