package Model.fachada;

import Model.negocios.NegocioProfessor;
import Model.cruds.CRUDAluno;
import Classes.excecoes.AlunoNotFoundException;

import java.io.IOException;

public class FachadaProfessor {
    private final NegocioProfessor negocioProfessor;

    public FachadaProfessor(){
        this.negocioProfessor = new NegocioProfessor(new CRUDAluno());
    }


}
