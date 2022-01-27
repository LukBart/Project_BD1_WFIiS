package project.Project_BD1_WFIiS.GUI;

public class Ksiazka{
    private int id;
    private String tytul;
    private String autor;
    private double cena;
    private String opisGatunek;

    Ksiazka(int id, String tytul, String autor, double cena, String opisGatunek){
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
        this.cena = cena;
        this.opisGatunek = opisGatunek;
    }

    public int getId() {
        return id;
    }
    public String getTytul(){
        return tytul;
    }

    public String getAutor() {
        return autor;
    }

    public double getCena() {
        return cena;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getOpisGatunek() {
        return opisGatunek;
    }

    public void setOpisGatunek(String opisGatunek) {
        this.opisGatunek = opisGatunek;
    }
}