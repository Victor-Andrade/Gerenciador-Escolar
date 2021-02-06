package Classes.pessoas;

import Classes.datas.Calendario;
import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.materia.Materia;
import Classes.excecoes.InvalidFieldException;

import java.util.ArrayList;

public class Aluno extends Pessoa{
    ArrayList<Materia> materias;
    Calendario faltas;

    public Aluno(String nome, String cpf, Data data, String email, String contato) throws InvalidFieldException, InvalidDateException {
        super(nome, cpf, data, email, contato);
        this.materias =  new ArrayList<>();
        this.faltas = new Calendario();
    }

    public ArrayList<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    public void adicionarFalta(Data data, boolean justificada){
        faltas.adicionarFalta(data, justificada);
    }

    public void removerFalta(Data data){
        faltas.removerFalta(data);
    }

    public int getFaltas(){
        return faltas.contarFaltas();
    }


}
