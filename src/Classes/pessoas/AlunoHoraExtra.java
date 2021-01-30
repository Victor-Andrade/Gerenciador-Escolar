package Classes.pessoas;

import Classes.datas.Data;
import Classes.datas.excecoes.InvalidDateException;
import Classes.materia.Materia;
import Classes.pessoas.excecoes.InvalidFieldException;

public class AlunoHoraExtra extends Aluno{
    Materia curso;

    public AlunoHoraExtra(String nome, String cpf, Data data, String email, String contato) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
    }


}
