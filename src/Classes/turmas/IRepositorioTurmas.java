package Classes.turmas;

import java.util.List;

public interface IRepositorioTurmas {
    void adicionarTurma(Turma turma);
    Turma getTurma(double id);
    void excluirTurma(double id);
    void atualizarTurma(Turma turma);
    boolean turmaExiste(double id);
    List<Turma> listarTurmas();

}
