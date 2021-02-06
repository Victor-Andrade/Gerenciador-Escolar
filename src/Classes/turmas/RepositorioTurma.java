package Classes.turmas;

import java.util.ArrayList;
import java.util.List;

public class RepositorioTurma implements IRepositorioTurmas {
    private List<Turma> turmas;

    public RepositorioTurma() {
        this.turmas = new ArrayList<>();
    }

    public RepositorioTurma(List<Turma> turmas) {
        this.turmas = turmas;
    }

    @Override
    public void adicionarTurma(Turma turma){
        turmas.add(turma);
    }

     @Override
    public boolean turmaExiste(double id){
        for (Turma turma : turmas) {
            if(turma.getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Turma> listarTurmas() {
        return this.turmas;
    }

    @Override
    public Turma getTurma(double id) {
        for (Turma turma : turmas) {
            if(turma.getId() == id){
                return turma;
            }
        }
        return null;
    }

    @Override
    public void excluirTurma(double id){
        Turma turma = getTurma(id);
        this.turmas.remove(turma);
    }

    @Override
    public void atualizarTurma(Turma turma){
        double id = turma.getId();
        Turma t = this.getTurma(id);
        int index = this.turmas.indexOf(t);
        this.turmas.set(index, turma);
    }

}
