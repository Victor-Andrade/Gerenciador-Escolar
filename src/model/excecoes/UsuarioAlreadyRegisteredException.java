package model.excecoes;

/**
 * Classe de excessão para previnir a criação de 2 cadastros para o mesmo usuário
 * @author: Victor Hugo
 */
public class UsuarioAlreadyRegisteredException extends Exception{
    public UsuarioAlreadyRegisteredException(String nome){
        super("Usuário " + nome + " já cadastrado no banco");
    }
}
