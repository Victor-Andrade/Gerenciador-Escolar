package Classes.excecoes;

/**
 * Classe de excessão para aluno já cadastrado.
 * Criado com a justifcativa de que um aluno pode já estar cadastrado
 * em uma turma ou no repositório.
 * @author
 */

public class AlunoAlredyRegisteredException extends Exception{
    public AlunoAlredyRegisteredException(String nome, String cpf){
        super("Aluno com nome " + nome + " e/ou cpf " + cpf + " já cadastrado!");
    }
}
