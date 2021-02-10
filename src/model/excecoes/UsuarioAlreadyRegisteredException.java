package model.excecoes;

/**
 * Classe de excessão para previnir a criação de 2 cadastros para o mesmo usuário.
 * @author Pedro Vinicius, Victor Hugo
 */
public class UsuarioAlreadyRegisteredException extends Exception{
    public UsuarioAlreadyRegisteredException(String nome){
        super("Usuáio " + nome + " já cadastrado no banco");
    }
}
