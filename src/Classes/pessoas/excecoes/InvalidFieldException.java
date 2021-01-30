package Classes.pessoas.excecoes;

public class InvalidFieldException extends Exception{
    public InvalidFieldException(String dado) {
        super(dado + "vazio ou inválido");
    }
}
