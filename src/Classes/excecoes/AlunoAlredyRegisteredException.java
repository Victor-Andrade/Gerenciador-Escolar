package Classes.excecoes;

public class AlunoAlredyRegisteredException extends Exception{
    public AlunoAlredyRegisteredException(String nome, String cpf){
        super("Aluno com nome " + nome + " e/ou cpf " + cpf + " já cadastrado!");
    }
}
