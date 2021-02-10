package Classes.materia;

import java.io.Serializable;

/**
 * Classe responsável por guardar todas as notas e nome de uma matéria de um aluno
 * @author
 */

public class Materia implements Serializable {
    private final String nome;
    private final Bimestre primeiroBimestre;
    private final Bimestre segundoBimestre;
    private final Bimestre terceiroBimestre;
    private final Bimestre quartoBimestre;

    public Materia(String nome){
        this.nome = nome;
        this.primeiroBimestre = new Bimestre();
        this.segundoBimestre = new Bimestre();
        this.terceiroBimestre = new Bimestre();
        this.quartoBimestre = new Bimestre();
    }

    public String getNome() {
        return this.nome;
    }

    public Bimestre getPrimeiroBimestre(){
        return this.primeiroBimestre;
    }

    public double getMedia1Bimestre(){
        return this.primeiroBimestre.calcularMedia();
    }

    public void setNotasPrimeiroBimestre(double n1, double n2){
        this.primeiroBimestre.setNota1(n1);
        this.primeiroBimestre.setNota2(n2);
    }

    public Bimestre getSegundoBimestre(){
        return this.segundoBimestre;
    }

    public double getMedia2Bimestre(){
        return this.segundoBimestre.calcularMedia();
    }

    public void setNotasSegundoBimestre(double n1, double n2) {
        this.segundoBimestre.setNota1(n1);
        this.segundoBimestre.setNota2(n2);
    }

    public Bimestre getTerceiroBimestre(){
        return this.terceiroBimestre;
    }

    public double getMedia3Bimestre(){
        return this.terceiroBimestre.calcularMedia();
    }

    public void setNotasTerceiroBimestre(double n1, double n2){
        this.terceiroBimestre.setNota1(n1);
        this.terceiroBimestre.setNota2(n2);
    }

    public Bimestre getQuartoBimestre(){
        return this.quartoBimestre;
    }

    public double getMedia4Bimestre(){
        return this.quartoBimestre.calcularMedia();
    }

    public void setNotasQuartoBimestre(double n1, double n2) {
        this.quartoBimestre.setNota1(n1);
        this.quartoBimestre.setNota2(n2);
    }
}
