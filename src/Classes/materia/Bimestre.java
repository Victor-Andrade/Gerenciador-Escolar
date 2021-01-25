package Classes.materia;

public class Bimestre {
    double nota1;
    double nota2;

    public double calculaMedia(){
        return (nota1+nota2)/2;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota) throws Exception{
        if(nota >= 0 && nota <= 10){
            this.nota1 = nota;
        }else{
            throw new Exception();
        }
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota) throws Exception{
        if(nota >= 0 && nota <= 10){
            this.nota2 = nota;
        }else{
            throw new Exception();
        }
    }
}
