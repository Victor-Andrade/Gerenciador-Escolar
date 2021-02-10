package Model.fachada;

import Classes.datas.Data;
import Classes.excecoes.AlunoAlredyRegisteredException;
import Classes.excecoes.InvalidDateException;
import Classes.excecoes.InvalidFieldException;
import Classes.pessoas.Administrador;
import Classes.pessoas.Pessoa;
import Model.cruds.CRUDTurma;
import Model.cruds.CRUDUsuarios;
import Model.negocios.NegocioAdministrador;
import Model.cruds.CRUDAlunos;
import Model.negocios.NegocioProfessor;
import Model.negocios.NegocioTurma;

import java.io.FileNotFoundException;
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

    public void matricularAluno(String nome, String cpf, Data data, String email, String contato, String emailResponsavel) throws ClassNotFoundException, InvalidFieldException, AlunoAlredyRegisteredException, InvalidDateException, IOException {
        this.negocioAdministrador.matricularAluno(nome, cpf, data, email,  contato, emailResponsavel);
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
