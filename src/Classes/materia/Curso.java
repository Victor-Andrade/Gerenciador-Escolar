package Classes.materia;

public class Curso {
    private String nome;
    private int horas;

    public Curso(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHoras() {
        return horas;
    }

    public void adicionarHoras() {
        this.horas += 2;
    }
}
