package model.excecoes;

/**
 * Classe de excessão para ser utilizada quando um campo for inválido
 * @author: Pedro Vinicius
 */
public class InvalidFieldException extends Exception{
    public InvalidFieldException(String dado) {
        super(dado + "vazio ou inválido");
    }

    public InvalidFieldException(String dado, String dado2) {
        super(dado + " " + dado2 + " vazio ou inválido");
    }
}
