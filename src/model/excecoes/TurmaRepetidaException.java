package model.excecoes;

/**
 * Classe de excessão para ser utilizada se algum usuário tentar criar uma turma já cadastrada.
 * @author Pedro Vinicius, Victor Hugo.
 */
public class TurmaRepetidaException extends Exception{
    public TurmaRepetidaException(String message) {
        super(message);
    }
}
