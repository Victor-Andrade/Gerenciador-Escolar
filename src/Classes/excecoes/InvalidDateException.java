package Classes.excecoes;

/**
 * Classe de excessão para ser utilizada se uma data for inserida incorretamente
 * Ela assegura que não haverá datas inexistentes 30 de fevereiro, ou alguma Data negativa.
 * @author: Pedro Vinicius, Victor Hugo
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
