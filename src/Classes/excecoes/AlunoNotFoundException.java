package Classes.excecoes;

public class AlunoNotFoundException extends Exception{
    public AlunoNotFoundException(String dado){
        super("Aluno " + dado + " não encontrado");
    }
}
