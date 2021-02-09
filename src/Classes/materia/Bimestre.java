package Classes.materia;

import java.io.Serializable;

public class Bimestre implements Serializable {
    private double nota1;
    private double nota2;

    public double calcularMedia(){
        return (this.nota1 + this.nota2)/2;
    }

    public double getNota1() {
        return this.nota1;
    }

    public void setNota1(double nota){
        this.nota1 = nota;
    }

    public double getNota2() {
        return this.nota2;
    }

    public void setNota2(double nota){
        this.nota2 = nota;
    }
}
