package Classes.excecoes;

public class UsuarioAlreadyRegisteredException extends Exception{
    public UsuarioAlreadyRegisteredException(String nome){
        super("Usuáio " + nome + " já cadastrado no banco");
    }
}
