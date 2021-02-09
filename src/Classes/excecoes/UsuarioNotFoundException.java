package Classes.excecoes;

public class UsuarioNotFoundException extends Exception{
    public UsuarioNotFoundException(String nome){
        super("Usuáio " + nome + " não encontrado no banco");
    }
}
