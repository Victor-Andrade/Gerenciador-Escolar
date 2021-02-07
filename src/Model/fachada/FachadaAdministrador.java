package Model.fachada;

import Model.cruds.CRUDTurma;
import Model.cruds.CRUDUsuarios;
import Model.negocios.NegocioAdministrador;
import Model.cruds.CRUDAlunos;


public class FachadaAdministrador {
    private final NegocioAdministrador negocioProfessor;

    public FachadaAdministrador(){
        this.negocioProfessor = new NegocioAdministrador(new CRUDAlunos(), new CRUDTurma(), new CRUDUsuarios());
    }


}
