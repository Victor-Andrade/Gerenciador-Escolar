package model.fachada;

import model.classes.datas.Data;
import model.excecoes.InvalidDateException;
import model.classes.pessoas.Administrador;
import model.classes.pessoas.Pessoa;
import model.cruds.CRUDTurma;
import model.cruds.CRUDUsuarios;
import model.negocios.NegocioAdministrador;
import model.cruds.CRUDAlunos;
import model.negocios.NegocioProfessor;
import model.negocios.NegocioTurma;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Fachada responsável por gerenciar informações do administrador, ela atende as solicitações do controller e reponde
 * de acordo com as regras de negócio tanto da turma quanto do professor.
 */
public class FachadaAdministrador {
    private final NegocioAdministrador negocioAdministrador;
    private final NegocioProfessor negocioProfessor;
    private final NegocioTurma negocioTurma;

    public FachadaAdministrador(){
        this.negocioProfessor = new NegocioProfessor(new CRUDAlunos(), new CRUDTurma());
        this.negocioAdministrador = new NegocioAdministrador(new CRUDAlunos(), new CRUDUsuarios());
        this.negocioTurma = new NegocioTurma(new CRUDTurma(), new CRUDAlunos());
    }

    public List<Pessoa> getUsuariosLogin() throws IOException, ClassNotFoundException {
        return this.negocioAdministrador.todosOsUsuarios();
    }

    public void adicionarAdmPadrao(){
        try{
            Data data = new Data(2021, 2, 10);
            Administrador adm = new Administrador("admin", "12345678910", data
                    ,"admin@admin.com", "(87)99999-9999", "admin123");
            List<Pessoa> p = new ArrayList<Pessoa>(10);
            FileOutputStream file = new FileOutputStream("usuarios.dat");
            ObjectOutputStream os = new ObjectOutputStream(file);
            p.add(adm);
            os.writeObject(p);
            os.close();
        } catch (InvalidDateException | IOException e) {
            e.printStackTrace();
        }
    }
}
