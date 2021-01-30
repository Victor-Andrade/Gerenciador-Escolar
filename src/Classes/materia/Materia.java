package Classes.materia;

import Classes.pessoas.excecoes.InvalidFieldException;

public class Materia {
    String nome;
    Bimestre primeiroBimestre;
    Bimestre segundoBimestre;
    Bimestre terceiroBimestre;
    Bimestre quartoBimestre;

    public Materia(String nome){
        this.nome = nome;
        this.primeiroBimestre = new Bimestre();
        this.segundoBimestre = new Bimestre();
        this.terceiroBimestre = new Bimestre();
        this.quartoBimestre = new Bimestre();
    }

    public String getNome() {
        return nome;
    }

    public double[] getNotasPrimeiroBimestre() {
        return new double[] {this.primeiroBimestre.getNota1(), this.primeiroBimestre.getNota2(), this.primeiroBimestre.calcularMedia()};
    }

    public void setNotasBimestre(double n1, double n2) throws InvalidFieldException {
        if(!(n1 < 0) && !(n2 < 0)){
            this.primeiroBimestre.setNota1(n1);
            this.primeiroBimestre.setNota2(n2);
        }else if(!(n1 < 0) && (n2 < 0)){
            this.primeiroBimestre.setNota1(n1);
        }else if((n1 < 0) && !(n2 < 0)){
            this.primeiroBimestre.setNota2(n2);
        }
    }

    public double[] getNotasSegundoBimestre() {
        return new double[] {this.segundoBimestre.getNota1(), this.segundoBimestre.getNota2(), this.segundoBimestre.calcularMedia()};
    }

    public void setNotasSegundoBimestre(double n1, double n2) throws InvalidFieldException {
        if(!(n1 < 0) && !(n2 < 0)){
            this.segundoBimestre.setNota1(n1);
            this.segundoBimestre.setNota2(n2);
        }else if(!(n1 < 0) && (n2 < 0)){
            this.segundoBimestre.setNota1(n1);
        }else if((n1 < 0) && !(n2 < 0)){
            this.segundoBimestre.setNota2(n2);
        }
    }

    public double[] getTerceiroBimestre() {
        return new double[] {this.terceiroBimestre.getNota1(), this.terceiroBimestre.getNota2(), this.terceiroBimestre.calcularMedia()};
    }

    public void setNotasTerceiroBimestre(double n1, double n2) throws InvalidFieldException {
        if(!(n1 < 0) && !(n2 < 0)){
            this.terceiroBimestre.setNota1(n1);
            this.terceiroBimestre.setNota2(n2);
        }else if(!(n1 < 0) && (n2 < 0)){
            this.terceiroBimestre.setNota1(n1);
        }else if((n1 < 0) && !(n2 < 0)){
            this.terceiroBimestre.setNota2(n2);
        }
    }

    public double[] getNotasQuartoBimestre() {
        return new double[] {this.quartoBimestre.getNota1(), this.quartoBimestre.getNota2(), this.quartoBimestre.calcularMedia()};
    }

    public void setNotasQuartoBimestre(double n1, double n2) throws InvalidFieldException {
        if(!(n1 < 0) && !(n2 < 0)){
            this.terceiroBimestre.setNota1(n1);
            this.terceiroBimestre.setNota2(n2);
        }else if(!(n1 < 0) && (n2 < 0)){
            this.terceiroBimestre.setNota1(n1);
        }else if((n1 < 0) && !(n2 < 0)){
            this.terceiroBimestre.setNota2(n2);
        }
    }
}
