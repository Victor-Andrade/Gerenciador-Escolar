package model.excecoes;

/**
 * Classe de excessão para aluno não encontrado.
 * Criada para usar quando for solicitada alguma operação sobre um aluno que não está cadastrado
 * @author Pedro Vinicius, Victor Hugo
 */
public class AlunoNotFoundException extends Exception{
    public AlunoNotFoundException(String dado){
        super("Aluno " + dado + " não encontrado");
    }
}
