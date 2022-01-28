package project.Project_BD1_WFIiS.Containers;

public class RaportTransakcja {
    private String kasjer;
    private String stanowisko;
    private Integer transakcja;
    private String data;
    private String suma;

    public RaportTransakcja(String kasjer, String stanowisko, Integer transakcja, String data, String suma) {
        this.kasjer = kasjer;
        this.stanowisko = stanowisko;
        this.transakcja = transakcja;
        this.data = data;
        this.suma = suma;
    }

    public String getKasjer() {
        return kasjer;
    }

    public void setKasjer(String kasjer) {
        this.kasjer = kasjer;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    public Integer getTransakcja() {
        return transakcja;
    }

    public void setTransakcja(Integer transakcja) {
        this.transakcja = transakcja;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }
}
