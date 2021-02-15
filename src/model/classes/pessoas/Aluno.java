package model.classes.pessoas;

import model.classes.datas.Data;
import model.classes.excecoes.InvalidDateException;
import model.classes.faltas.Falta;
import model.classes.materia.Materia;
import model.classes.excecoes.InvalidFieldException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representando o aluno base no sistema
 * @author Victor Hugo e Pedro Vinícius
 */

public class Aluno extends Pessoa{
    private List<Materia> materias;
    private List<Falta> listaDeFaltas;
    private String emailPais;

    public Aluno(String nome, String cpf, Data data, String email, String contato, String emailPais) throws InvalidDateException {
        super(nome, cpf, data, email, contato);
        this.materias =  new ArrayList<>();
        this.emailPais = emailPais;
        inicializarMaterias();
    }

    public String getEmailPais() {
        return this.emailPais;
    }

    public void setEmailPais(String emailPais) {
        this.emailPais = emailPais;
    }

    public List<Materia> getMaterias() {
        return this.materias;
    }

    public void setMaterias(ArrayList<Materia> materias) {
        this.materias = materias;
    }

    //Em branco
    public void adicionarFalta(Data data, boolean justificada){

    }

    //Em branco
    public void removerFalta(Data data){

    }

    public Materia getMateria(String nomeDaMateria) throws InvalidFieldException {
        for(Materia materia: this.materias){
            if(nomeDaMateria.equals(materia.getNome())){
                return materia;
            }
        }
        return null;
    }

    //Em branco
    public void getFaltas(){

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
