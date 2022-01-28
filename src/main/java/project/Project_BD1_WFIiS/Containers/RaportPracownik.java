package project.Project_BD1_WFIiS.Containers;

public class RaportPracownik {
    String nazwa;
    String kasjerzy;
    String sprzatacze;
    String menedzerowie;

    public RaportPracownik(String nazwa, String kasjerzy, String sprzatacze, String menedzerowie) {
        this.nazwa = nazwa;
        this.kasjerzy = kasjerzy;
        this.sprzatacze = sprzatacze;
        this.menedzerowie = menedzerowie;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKasjerzy() {
        return kasjerzy;
    }

    public void setKasjerzy(String kasjerzy) {
        this.kasjerzy = kasjerzy;
    }

    public String getSprzatacze() {
        return sprzatacze;
    }

    public void setSprzatacze(String sprzatacze) {
        this.sprzatacze = sprzatacze;
    }

    public String getMenedzerowie() {
        return menedzerowie;
    }

    public void setMenedzerowie(String menedzerowie) {
        this.menedzerowie = menedzerowie;
    }
}
