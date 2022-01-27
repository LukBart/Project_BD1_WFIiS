package project.Project_BD1_WFIiS.Database;

import javafx.scene.control.*;
import project.Project_BD1_WFIiS.Tools.Tools;

import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;

public class Database {
    private final static String URL = "jdbc:postgresql://surus.db.elephantsql.com:5432/szfpcian";
    private final static String USER = "szfpcian";
    private final static String PASSWORD = "SinRXMpLj9coEhwHRoDztJ6gQKa2BSrd";
    private static Connection CONNECTION;
    public static Connection connectToBase(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            Tools.AlertBox("Error Message", "Nie znaleziono sterownika!");
            System.exit(1);
        }
        Connection c = null;
        try {
            c = DriverManager.getConnection (URL, USER, PASSWORD);
        } catch (SQLException se) {
            Tools.AlertBox("Error Message", "Brak polaczenia z baza danych!");
            System.exit(1);
        }
        if (c == null) {
            Tools.AlertBox("Error Massage", "Brak polaczenia z baza, dalsza czesc aplikacji nie jest wykonywana.");
        }
        CONNECTION = c;
        return c;
    }

    public static TreeView<String> ksiazkaTreeView() {
        try {
            PreparedStatement pst = CONNECTION.prepareStatement("SELECT * FROM ksiazkiview;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            TreeItem<String> root = new TreeItem<>();
            ResultSet rs = pst.executeQuery();
            Map<String,TreeItem<String>> dzialTree = new HashMap<>();
            Map<String,TreeItem<String>> gatunekTree = new HashMap<>();
            DecimalFormat df = new DecimalFormat("0.00");

            while (rs.next()) {
                if(!dzialTree.containsKey(rs.getString("id_dzial"))) {
                    dzialTree.put(rs.getString("id_dzial"), new TreeItem<String>(rs.getString("nazwa") + " ID: " + rs.getString("id_dzial") + "\nID Księgarni: " + rs.getString("id_ksiegarnia")));
                    root.getChildren().add(dzialTree.get(rs.getString("id_dzial")));
//                    dzialTree.get(rs.getString("id_dzial")).setExpanded(true);
                }
                if(!gatunekTree.containsKey(rs.getString("opis"))) {
                    gatunekTree.put(rs.getString("opis"), new TreeItem<String>(rs.getString("opis") + " ID: " + rs.getString("id_gatunek")));
                    dzialTree.get(rs.getString("id_dzial")).getChildren().add(gatunekTree.get(rs.getString("opis")));
                }
                TreeItem<String> ksiazka = new TreeItem<String>(rs.getString("tytul") + ", ID: " + rs.getString("id_ksiazka"));
                gatunekTree.get(rs.getString("opis")).getChildren().add(ksiazka);
                ksiazka.getChildren().add(new TreeItem<String>("Autor: " + rs.getString("autor") + "\nCena: " + df.format(rs.getDouble("cena"))));
            }
            TreeView<String> tree = new TreeView<>(root);
            tree.setShowRoot(false);
            return tree;

        } catch (SQLException e){
            Tools.AlertBox("Error", "Bład podczas przetwarzania danych książek:\n" + e);
        }
        return new TreeView<String>(new TreeItem<String>("null"));
    }

    public static TreeView<String> pracownikTreeView(){
        try {
            PreparedStatement menedzerPST = CONNECTION.prepareStatement("SELECT imie,nazwisko, plec, telefon, email, biuro, id_kadra_pracownicza, id_menedzer FROM menedzer JOIN pracownik p on p.id_pracownik = menedzer.id_pracownik", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            PreparedStatement sprzataczPST = CONNECTION.prepareStatement("SELECT imie,nazwisko, plec, telefon, email, sprzet, id_kadra_pracownicza, id_sprzatacz FROM sprzatacz JOIN pracownik p on p.id_pracownik = sprzatacz.id_pracownik", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            PreparedStatement kasjerPST = CONNECTION.prepareStatement("SELECT imie,nazwisko, plec, telefon, email, stanowisko, id_kadra_pracownicza, id_kasjer FROM kasjer JOIN pracownik p on p.id_pracownik = kasjer.id_pracownik", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            PreparedStatement kadraPST = CONNECTION.prepareStatement("SELECT id_kadra_pracownicza, opis from kadra_pracownicza", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);


            ResultSet menedzerRS = menedzerPST.executeQuery();
            ResultSet sprzataczRS = sprzataczPST.executeQuery();
            ResultSet kasjerRS = kasjerPST.executeQuery();
            ResultSet kadraRS = kadraPST.executeQuery();

            TreeItem<String> root = new TreeItem<>();

            Map<String, TreeItem<String>> kadraTree = new HashMap<>();
            Map<String, TreeItem<String>> kasjerTree = new HashMap<>();
            Map<String, TreeItem<String>> sprzataczTree = new HashMap<>();

            while (kadraRS.next()){
                kadraTree.put(kadraRS.getString("id_kadra_pracownicza"),new TreeItem<String>(kadraRS.getString("opis") + ", ID: " + kadraRS.getString("id_kadra_pracownicza")));
                root.getChildren().add(kadraTree.get(kadraRS.getString("id_kadra_pracownicza")));
            }
            while (menedzerRS.next()){
                TreeItem<String> menedzer = new TreeItem<>("Menedżer " + menedzerRS.getString("imie") + " " + menedzerRS.getString("nazwisko") + ", ID: " + menedzerRS.getString("id_menedzer"));
                kadraTree.get(menedzerRS.getString("id_kadra_pracownicza")).getChildren().add(menedzer);
                menedzer.getChildren().add(new TreeItem<>("Płeć: " + menedzerRS.getString("plec") + "\nTelefon: " + menedzerRS.getString("telefon") + "\nE-mail: " + menedzerRS.getString("email") + "\nBiuro: " + menedzerRS.getString("biuro")));
            }
            kadraRS.beforeFirst();
            while (kadraRS.next()){
                kasjerTree.put(kadraRS.getString("id_kadra_pracownicza"),new TreeItem<String>("Kasjerzy"));
                kadraTree.get(kadraRS.getString("id_kadra_pracownicza")).getChildren().add(kasjerTree.get(kadraRS.getString("id_kadra_pracownicza")));
                sprzataczTree.put(kadraRS.getString("id_kadra_pracownicza"),new TreeItem<String>("Sprzątacze"));
                kadraTree.get(kadraRS.getString("id_kadra_pracownicza")).getChildren().add(sprzataczTree.get(kadraRS.getString("id_kadra_pracownicza")));
            }
            while (kasjerRS.next()){
                TreeItem<String> kasjer = new TreeItem<>(kasjerRS.getString("imie") + " " + kasjerRS.getString("nazwisko") + ", ID: " + kasjerRS.getString("id_kasjer"));
                kasjerTree.get(kasjerRS.getString("id_kadra_pracownicza")).getChildren().add(kasjer);
                kasjer.getChildren().add(new TreeItem<>("Płeć: " + kasjerRS.getString("plec") + "\nTelefon: " + kasjerRS.getString("telefon") + "\nE-mail: " + kasjerRS.getString("email") + "\nStanowisko: " + kasjerRS.getString("stanowisko")));
            }
            while (sprzataczRS.next()){
                TreeItem<String> sprzatacz = new TreeItem<>(sprzataczRS.getString("imie") + " " + sprzataczRS.getString("nazwisko") + " id: " + sprzataczRS.getString("id_sprzatacz") );
                sprzataczTree.get(sprzataczRS.getString("id_kadra_pracownicza")).getChildren().add(sprzatacz);
                sprzatacz.getChildren().add(new TreeItem<>("Płeć: " + sprzataczRS.getString("plec") + "\nTelefon: " + sprzataczRS.getString("telefon") + "\nE-mail: " + sprzataczRS.getString("email") + "\nSprzęt: " + sprzataczRS.getString("sprzet")));
            }
            TreeView<String> tree = new TreeView<>(root);
            tree.setShowRoot(false);
            return tree;
        } catch (SQLException e){
            Tools.AlertBox("Error", "Błąd podczas przetwarzania danych pracowników:\n" + e);
        }
        return new TreeView<String>(new TreeItem<String>("null"));
    }

    public static TreeItem<String> ksiegarniaTreeItem (int id) throws SQLException {
        int[] parameters = new int[]{id};
        PreparedStatement pst = CONNECTION.prepareStatement("SELECT * FROM daneKsiegarni(?);"); //, parameters);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        rs.next();
        TreeItem<String> name = new TreeItem<>(rs.getString("nazwa"));
        TreeItem<String> informations = new TreeItem<>("ID: " + rs.getString("id_ksiegarnia") +
                "\nWłaściciel: " + rs.getString("imie") + " " + rs.getString("nazwisko") +
                ", ID: " + rs.getString("id_wlasciciel") + "\nLiczba pracowników: " +
                rs.getString("liczbapracownikow") + "\nLiczba książek: " +
                rs.getString("liczbaksiazek") + "\nAdres\n   " + rs.getString("ulica") + " " +
                rs.getString("numer")+ "\n   " + rs.getString("kod") + " " +
                rs.getString("miejscowosc"));

        name.getChildren().add(informations);

        return name;
    }

    public static TreeView<String> ksiegarniaTreeView (){
        try {
            PreparedStatement pst = CONNECTION.prepareStatement("SELECT id_ksiegarnia FROM ksiegarnia;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();

            TreeItem<String> root = new TreeItem<>();

            while (rs.next()){
                root.getChildren().add(ksiegarniaTreeItem(Integer.parseInt(rs.getString("id_ksiegarnia"))));
            }
            TreeView<String> tree = new TreeView<>(root);
            tree.setShowRoot(false);
            return tree;
        }catch (SQLException e){
            Tools.AlertBox("Error","Błąd podczas przetwarzania danych ksiegarni:\n" + e);
        }
        return new TreeView<String>(new TreeItem<String>("null"));
    }

    public static Connection getCONNECTION() {
        return CONNECTION;
    }

//    public static TableView raportTransakcji(){
//
//    }
}
