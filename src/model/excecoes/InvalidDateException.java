package model.excecoes;

/**
 * Classe de excessão para ser utilizada se uma data for inserida incorretamente
 * Ela é usada quando há a tentativa de criar uma data inexistente como 30 de fevereiro, ou alguma Data negativa.
 * @author Pedro Vinicius, Victor Hugo
 */
public class InvalidDateException extends Exception{
    int value;

    public InvalidDateException(int value, String info) {
        super("Valor inválido para " + info + " = " + value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
