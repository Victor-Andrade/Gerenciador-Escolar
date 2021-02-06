package Classes.excecoes;

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
