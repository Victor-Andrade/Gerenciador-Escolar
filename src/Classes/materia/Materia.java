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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Bimestre getPrimeiroBimestre() {
        return primeiroBimestre;
    }

    public void setPrimeiroBimestre(Bimestre primeiroBimestre) {
        this.primeiroBimestre = primeiroBimestre;
    }

    public Bimestre getSegundoBimestre() {
        return segundoBimestre;
    }

    public void setSegundoBimestre(Bimestre segundoBimestre) {
        this.segundoBimestre = segundoBimestre;
    }

    public Bimestre getTerceiroBimestre() {
        return terceiroBimestre;
    }

    public void setTerceiroBimestre(Bimestre terceiroBimestre) {
        this.terceiroBimestre = terceiroBimestre;
    }

    public Bimestre getQuartoBimestre() {
        return quartoBimestre;
    }

    public void setQuartoBimestre(Bimestre quartoBimestre) {
        this.quartoBimestre = quartoBimestre;
    }
}
