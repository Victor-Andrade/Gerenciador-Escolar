package Classes.materia;

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

    public Bimestre getPrimeiroBimestre(){
        return this.primeiroBimestre;
    }

    public double getMedia1Bimestre(){
        return primeiroBimestre.calcularMedia();
    }

    public void setNotasPrimeiroBimestre(double n1, double n2){
        this.primeiroBimestre.setNota1(n1);
        this.primeiroBimestre.setNota2(n2);
    }

    public Bimestre getSegundoBimestre(){
        return this.segundoBimestre;
    }

    public double getMedia2Bimestre(){
        return segundoBimestre.calcularMedia();
    }

    public void setNotasSegundoBimestre(double n1, double n2) {
        this.segundoBimestre.setNota1(n1);
        this.segundoBimestre.setNota2(n2);
    }

    public Bimestre getTerceiroBimestre(){
        return this.terceiroBimestre;
    }

    public double getMedia3Bimestre(){
        return terceiroBimestre.calcularMedia();
    }

    public void setNotasTerceiroBimestre(double n1, double n2){
        this.terceiroBimestre.setNota1(n1);
        this.terceiroBimestre.setNota2(n2);
    }

    public Bimestre getQuartoBimestre(){
        return this.quartoBimestre;
    }

    public double getMedia4Bimestre(){
        return quartoBimestre.calcularMedia();
    }

    public void setNotasQuartoBimestre(double n1, double n2) {
        this.quartoBimestre.setNota1(n1);
        this.quartoBimestre.setNota2(n2);
    }
}
