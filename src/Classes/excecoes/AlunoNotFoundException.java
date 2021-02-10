package Classes.excecoes;

/**
 * Classe de excessão para aluno não encontrado.
 * Criada com a justifcativa de que um aluno pode não existir
 * em uma turma ou no repositório.
 * @author
 */
public class AlunoNotFoundException extends Exception{
    public AlunoNotFoundException(String dado){
        super("Aluno " + dado + " não encontrado");
    }
}
