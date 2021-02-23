package model.excecoes;

/**
 * Excessão usada para quando uma das notas do aluno ou o número de horas de um
 * aluno com curso for inválida
 */
public class NotasInvalidasException extends Exception{
    public NotasInvalidasException(String mensagem){
        super(mensagem);
    }
}
