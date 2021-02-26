package model.interfaces;

/**
 * Interface usada para alterar o status de uma falta e verificar
 * se ela é justificada ou não.
 * @author Pedro Vinícius
 */

public interface IFalta {
    boolean isConfirmada();

    void alterarStatus();
}
