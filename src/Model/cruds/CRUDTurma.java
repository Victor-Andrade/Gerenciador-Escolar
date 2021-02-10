package Model.cruds;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.interfaces.IRepositorioTurmas;
import Classes.turmas.Turma;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por fazer a manipulação primária de objetos de turmas em arquivos
 * @author
 */

public class CRUDTurma implements IRepositorioTurmas {

    @Override
    public void adicionarTurma(Turma turma) throws IOException, ClassNotFoundException {
        List<Turma> temp = listarTurmas();
        temp.add(turma);
        atualizarModificacoes(temp);
    }

    @Override
    public void excluirTurma(double id) throws IOException, ClassNotFoundException, TurmaNaoExisteException {
        List<Turma> temp = listarTurmas();

        if(turmaExiste(id)){
            Turma turma = getTurma(id);
            temp.remove(turma);
        }
        atualizarModificacoes(temp);
    }

    @Override
    public Turma getTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException {
        List<Turma> temp = listarTurmas();

        for (Turma turma : temp) {
            if(turma.getId() == id){
                return turma;
            }
        }
        throw new TurmaNaoExisteException("Turma não encontrada");
    }

    @Override
    public void atualizarTurma(Turma turma) throws IOException, ClassNotFoundException, TurmaNaoExisteException {
        double id = turma.getId();

        excluirTurma(id);
        adicionarTurma(turma);
    }

     @Override
    public boolean turmaExiste(double id) throws IOException, ClassNotFoundException {
        for (Turma turma : listarTurmas()) {
            if(turma.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Turma> listarTurmas() throws IOException, ClassNotFoundException {
        List<Turma> temp;

        FileInputStream file = new FileInputStream("turmas.dat");
        ObjectInputStream is = new ObjectInputStream(file);

        temp = (ArrayList<Turma>) is.readObject();
        is.close();
        return temp;
    }

    private void atualizarModificacoes(List<Turma> turmas) throws IOException {
        FileOutputStream file2 = new FileOutputStream("usuarios.dat");
        ObjectOutputStream os = new ObjectOutputStream(file2);

        os.writeObject(turmas);
        os.close();
    }
}
