package Classes.pessoas;

import Classes.datas.Calendario;
import Classes.datas.Data;
import Classes.excecoes.InvalidDateException;
import Classes.materia.Materia;
import Classes.excecoes.InvalidFieldException;

import java.util.ArrayList;

public class Aluno extends Pessoa{
    private ArrayList<Materia> materias;
    private Calendario faltas;

    public Aluno(String nome, String cpf, Data data, String email, String contato) throws InvalidDateException {
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

    public Materia getMateria(String nomeDaMateria) throws InvalidFieldException {
        for(Materia materia: this.materias){
            if(nomeDaMateria.equals(materia.getNome())){
                return materia;
            }
        }

        throw new InvalidFieldException("Materia", nomeDaMateria);
    }

    public int getFaltas(){
        return faltas.contarFaltas();
    }

    private void inicializarMaterias(){
        this.materias.add(new Materia("Português"));
        this.materias.add(new Materia("Matemática"));
        this.materias.add(new Materia("História"));
        this.materias.add(new Materia("Geografia"));
        this.materias.add(new Materia("Ed. Fisica"));
        this.materias.add(new Materia("Artes"));
    }


}
