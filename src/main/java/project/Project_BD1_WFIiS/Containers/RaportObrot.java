package project.Project_BD1_WFIiS.Containers;

public class RaportObrot {
    private String nazwa;
    private String wlasciciel;
    private String obrot;

    public RaportObrot(String nazwa, String wlasciciel, String obrot) {
        this.nazwa = nazwa;
        this.wlasciciel = wlasciciel;
        this.obrot = obrot;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getWlasciciel() {
        return wlasciciel;
    }

    public void setWlasciciel(String wlasciciel) {
        this.wlasciciel = wlasciciel;
    }

    public String getObrot() {
        return obrot;
    }

    public void setObrot(String obrot) {
        this.obrot = obrot;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
