package Classes.pessoas;

import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.materia.Materia;
import Classes.excecoes.InvalidFieldException;

public class AlunoHoraExtra extends Aluno{
    Materia curso;

    public AlunoHoraExtra(String nome, String cpf, Data data, String email, String contato) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
    }


}
