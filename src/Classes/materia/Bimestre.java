package Classes.materia;

import java.io.Serializable;

public class Bimestre implements Serializable {
    private double nota1;
    private double nota2;

    public double calcularMedia(){
        return (nota1+nota2)/2;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota){
        this.nota1 = nota;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota){
        this.nota2 = nota;
    }
}
