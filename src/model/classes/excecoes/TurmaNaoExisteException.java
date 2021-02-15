package model.classes.excecoes;

/**
 * Classe de excessão para ser utilizada se algum usuário tentar acessar ou
 * alterar informação de uma turma não cadastrada
 * @author: Victor Hugo
 */
public class TurmaNaoExisteException extends Exception{
    public TurmaNaoExisteException(String message) {
        super(message);
    }
}
