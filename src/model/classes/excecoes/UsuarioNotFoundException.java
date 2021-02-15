package model.classes.excecoes;

/**
 * Excessão usada quando se tenta realizar alguma operação com um usuário não encontrado
 * @author: Victor Hugo
 */
public class UsuarioNotFoundException extends Exception{
    public UsuarioNotFoundException(String nome){
        super("Usuáio " + nome + " não encontrado no banco");
    }
}
