package Classes.materia;

import Classes.pessoas.excecoes.InvalidFieldException;

public class Bimestre {
    private double nota1;
    private double nota2;

    public double calcularMedia(){
        return (nota1+nota2)/2;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota) throws InvalidFieldException {
        if(nota >= 0 && nota <= 10){
            this.nota1 = nota;
        }else{
            throw new InvalidFieldException("Nota");
        }
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota) throws InvalidFieldException{
        if(nota >= 0 && nota <= 10){
            this.nota2 = nota;
        }else{
            throw new InvalidFieldException("Nota");
        }
    }
}
