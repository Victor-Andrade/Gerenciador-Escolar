package Model.fachada;

import Model.cruds.CRUDTurma;
import Model.cruds.CRUDUsuarios;
import Model.negocios.NegocioAdministrador;
import Model.cruds.CRUDAlunos;
import Model.negocios.NegocioProfessor;
import Model.negocios.NegocioTurma;


public class FachadaAdministrador {
    private final NegocioAdministrador negocioAdministrador;
    private final NegocioProfessor negocioProfessor;
    private final NegocioTurma negocioTurma;

    public FachadaAdministrador(){
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDTurma(), new CRUDUsuarios());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
    }


}
