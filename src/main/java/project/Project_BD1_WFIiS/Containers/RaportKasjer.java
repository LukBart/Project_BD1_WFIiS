package project.Project_BD1_WFIiS.Containers;

public class RaportKasjer {
    String nazwa;
    String id;
    String kasjer;
    String ilosc;
    String suma;

    public RaportKasjer(String nazwa, String id, String kasjer, String ilosc, String suma) {
        this.nazwa = nazwa;
        this.id = id;
        this.kasjer = kasjer;
        this.ilosc = ilosc;
        this.suma = suma;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKasjer() {
        return kasjer;
    }

    public void setKasjer(String kasjer) {
        this.kasjer = kasjer;
    }

    public String getIlosc() {
        return ilosc;
    }

    public void setIlosc(String ilosc) {
        this.ilosc = ilosc;
    }

    public String getSuma() {
        return suma;
    }

    public void setSuma(String suma) {
        this.suma = suma;
    }
}
