package model.fachada;

import Classes.pessoas.Pessoa;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
import model.negocios.NegocioAdministrador;
import model.cruds.CRUDAlunos;
import model.negocios.NegocioProfessor;
import model.negocios.NegocioTurma;

import java.io.IOException;
import java.util.List;


public class FachadaAdministrador {
    private final NegocioAdministrador negocioAdministrador;
    private final NegocioProfessor negocioProfessor;
    private final NegocioTurma negocioTurma;

    public FachadaAdministrador(){
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDTurma(), new CRUDUsuarios());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
    }

    public List<Pessoa> getUsuariosLogin() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsUsuarios();
    }
}
