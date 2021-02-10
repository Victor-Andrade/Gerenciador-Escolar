package Classes.interfaces;

import Classes.excecoes.TurmaNaoExisteException;
import Classes.turmas.Turma;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Interface responsável por definir os métodos do repositório de turmas
 * @author
 */

public interface IRepositorioTurmas {
    void adicionarTurma(Turma turma) throws IOException, ClassNotFoundException;
    Turma getTurma(double id) throws TurmaNaoExisteException, IOException, ClassNotFoundException;
    void excluirTurma(double id) throws IOException, ClassNotFoundException, TurmaNaoExisteException;
    void atualizarTurma(Turma turma) throws IOException, ClassNotFoundException, TurmaNaoExisteException;
    boolean turmaExiste(double id) throws IOException, ClassNotFoundException;
    List<Turma> listarTurmas() throws IOException, ClassNotFoundException;

}
