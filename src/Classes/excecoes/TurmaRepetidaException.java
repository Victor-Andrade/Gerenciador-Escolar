package Classes.excecoes;

/**
 * Classe de excessão para ser utilizada se algum usuário tentar criar uma turma duplicada
 * @author: Pedro Vinicius, Victor Hugo
 */
public class TurmaRepetidaException extends Exception{
    public TurmaRepetidaException(String message) {
        super(message);
    }
}
