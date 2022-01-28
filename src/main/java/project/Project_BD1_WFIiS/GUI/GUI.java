package project.Project_BD1_WFIiS.GUI;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import project.Project_BD1_WFIiS.Containers.*;
import project.Project_BD1_WFIiS.Database.Database;
import project.Project_BD1_WFIiS.Tools.Tools;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GUI {
    private final Stage mainStage;
    private Stage wyswietlStage, dodajStage, transakcjaStage, raportStage;
    private Scene start;
    private Scene wyswietlScene, dodajScene, transakcjaScene,transakcjaFinalScene, raportScene;
    private GridPane dodajSceneGrid, transakcjaSceneGrid;

    public GUI (Stage s){
        mainStage = s;
        Database.connectToBase();

        startScene();
        wyswietlScene();
        dodajScene();
        transakcjaScene();
        createRaportScene();
        mainStage.setTitle("app");
        mainStage.setScene(start);
        mainStage.show();
    }

    public void startScene(){
        Button wyswietlButton = new Button("Wyświetl zawartość bazy");
        wyswietlButton.setOnAction(e ->{
            wyswietlStage = new Stage();
            wyswietlStage.initModality(Modality.APPLICATION_MODAL);
            wyswietlStage.setTitle("Wyświetlanie zawartości bazy");
            wyswietlStage.setScene(wyswietlScene);
            wyswietlStage.show();
        });

        Button dodajButton = new Button("Dodaj lub usuń rekordy z bazy");
        dodajButton.setOnAction(e ->{
            dodajStage = new Stage();
            dodajStage.initModality(Modality.APPLICATION_MODAL);
            dodajStage.setTitle("Dodaj zawartość do bazy");
            dodajStage.setScene(dodajScene);
            dodajStage.show();
        });

        Button transakcjaButton = new Button("Przeprowadź transakcje");
        transakcjaButton.setOnAction(e -> {
            transakcjaStage = new Stage();
            transakcjaStage.initModality(Modality.APPLICATION_MODAL);
            transakcjaStage.setTitle("Transakcja");
            transakcjaStage.setScene(transakcjaScene);
            transakcjaStage.show();
        });

        Button raportButton = new Button("Sprawdź raporty");
        raportButton.setOnAction(e->{
            raportStage = new Stage();
            raportStage.initModality(Modality.APPLICATION_MODAL);
            raportStage.setTitle("Transakcja");
            raportStage.setScene(raportScene);
            raportStage.show();
        });
        Label label = new Label("Wybierz dzialanie");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setHgap(10);

        GridPane.setConstraints(label, 0,0,2,1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setConstraints(wyswietlButton,0,1);
        GridPane.setHalignment(wyswietlButton, HPos.CENTER);
        GridPane.setConstraints(dodajButton, 1, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setConstraints(transakcjaButton, 0, 2);
        GridPane.setHalignment(transakcjaButton, HPos.CENTER);
        GridPane.setConstraints(raportButton,1,2);
        GridPane.setHalignment(raportButton, HPos.CENTER);

        grid.getChildren().addAll(label,wyswietlButton,dodajButton, transakcjaButton,raportButton);
        grid.setAlignment(Pos.CENTER);

        start = new Scene(grid,600,200);
    }

    public VBox wyswietlVBox(){
        TabPane tabPane = new TabPane();

        GridPane ksiazkiGrid = new GridPane();
        TreeView<String> ksiazkaTree = Database.ksiazkaTreeView();
        ksiazkiGrid.setPadding(new Insets(20,20,20,20));
        ksiazkiGrid.setVgap(10);
        ksiazkiGrid.setHgap(10);
        GridPane.setConstraints(ksiazkaTree, 0,0);
        ksiazkiGrid.getChildren().addAll(ksiazkaTree);
        Tab ksiazkiTab = new Tab("Ksiazki", ksiazkiGrid);
        ksiazkiTab.setClosable(false);

        GridPane pracownikGrid = new GridPane();
        TreeView<String> pracownikTree = Database.pracownikTreeView();
        pracownikGrid.setPadding(new Insets(20,20,20,20));
        pracownikGrid.setVgap(10);
        pracownikGrid.setHgap(10);
        GridPane.setConstraints(pracownikTree, 0,0);
        pracownikGrid.getChildren().addAll(pracownikTree);
        Tab pracownikTab = new Tab("Pracownicy", pracownikGrid);
        pracownikTab.setClosable(false);

        GridPane ksiegarniaGrid = new GridPane();
        TreeView<String> ksiegarniaTree = Database.ksiegarniaTreeView();
        ksiegarniaGrid.setPadding(new Insets(20,20,20,20));
        ksiegarniaGrid.setVgap(10);
        ksiegarniaGrid.setHgap(10);
        GridPane.setConstraints(ksiegarniaTree, 0,0);
        ksiegarniaGrid.getChildren().addAll(ksiegarniaTree);
        Tab ksiegarniaTab = new Tab("Księgarnie", ksiegarniaGrid);
        ksiegarniaTab.setClosable(false);

        tabPane.getTabs().add(ksiazkiTab);
        tabPane.getTabs().add(pracownikTab);
        tabPane.getTabs().add(ksiegarniaTab);

        VBox result = new VBox(tabPane);
        result.setAlignment(Pos.CENTER);
        return result;
    }

    public void wyswietlScene(){
        wyswietlScene = new Scene(wyswietlVBox());
    }

    public void dodajScene (){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);
        GridPane buttonsGrid = dodajGridPaneWithButtons();
        GridPane.setConstraints(buttonsGrid,1,0);
        dodajSceneGrid.setPadding(new Insets(20,20,20,20));
        dodajSceneGrid.setVgap(10);
        dodajSceneGrid.setHgap(10);
        dodajSceneGrid.setAlignment(Pos.CENTER);

        dodajSceneGrid.getChildren().addAll(wyswietlVBox, buttonsGrid);

        dodajScene = new Scene(dodajSceneGrid, 694, 509);
    }

    public GridPane dodajGridPaneWithButtons(){
        Button ksiazkaButton = new Button("Książka");
        Button gatunekButton = new Button("Gatunek");
        Button dzialButton = new Button("Dział");
        Button menedzerButton = new Button("Menedzer");
        Button kasjerButton = new Button("Kasjer");
        Button sprzataczButton = new Button("Sprzątacz");
        Button ksiegarniaButton = new Button("Księgarnia");
        Button wlascicielButton = new Button("Właściciel");

        ksiazkaButton.setOnAction(e->createKsiazkaScene());
        gatunekButton.setOnAction(e->createGatunekScene());
        dzialButton.setOnAction(e->createDzialScene());
        menedzerButton.setOnAction(e->createMenedzerScene());
        kasjerButton.setOnAction(e->createKasjerScene());
        sprzataczButton.setOnAction(e->createSprzataczScene());
        ksiegarniaButton.setOnAction(e->createKsiegarniaScene());
        wlascicielButton.setOnAction(e->createWlascicielScene());

        GridPane buttonsGrid = new GridPane();
        Label topLabel = new Label("Wybierz tabele do dodania lub usunięcia rekordu");
        GridPane.setConstraints(topLabel, 0,0,2,1);
        GridPane.setHalignment(topLabel, HPos.CENTER);
        GridPane.setConstraints(ksiazkaButton, 0,1);
        GridPane.setHalignment(ksiazkaButton, HPos.CENTER);
        GridPane.setConstraints(gatunekButton, 1 ,1);
        GridPane.setHalignment(gatunekButton, HPos.CENTER);
        GridPane.setConstraints(dzialButton, 0, 2);
        GridPane.setHalignment(dzialButton, HPos.CENTER);
        GridPane.setConstraints(menedzerButton, 1,2);
        GridPane.setHalignment(menedzerButton, HPos.CENTER);
        GridPane.setConstraints(kasjerButton, 0,3);
        GridPane.setHalignment(kasjerButton, HPos.CENTER);
        GridPane.setConstraints(sprzataczButton,1,3);
        GridPane.setHalignment(sprzataczButton, HPos.CENTER);
        GridPane.setConstraints(ksiegarniaButton,0,4);
        GridPane.setHalignment(ksiegarniaButton, HPos.CENTER);
        GridPane.setConstraints(wlascicielButton,1,4);
        GridPane.setHalignment(wlascicielButton, HPos.CENTER);
        buttonsGrid.setAlignment(Pos.CENTER);
        buttonsGrid.setPadding(new Insets(20,20,20,20));
        buttonsGrid.setVgap(20);
        buttonsGrid.setHgap(10);
        buttonsGrid.getChildren().addAll(topLabel,ksiazkaButton,gatunekButton,dzialButton,menedzerButton,kasjerButton,sprzataczButton,ksiegarniaButton,wlascicielButton);
        return buttonsGrid;
    }

    public void createKsiazkaScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label tytulLabel = new Label("Tytuł:");
        Label cenaLabel = new Label("Cena:");
        Label autorLabel = new Label("Autor:");
        Label gatunekLabel = new Label("ID gatunku:");
        GridPane.setConstraints(tytulLabel,0,1);
        GridPane.setConstraints(cenaLabel,0,2);
        GridPane.setConstraints(autorLabel,0,3);
        GridPane.setConstraints(gatunekLabel,0,4);

        TextField tytulField = new TextField();
        TextField cenaField = new TextField();
        TextField autorField = new TextField();
        TextField gatunekField = new TextField();
        GridPane.setConstraints(tytulField,1,1);
        GridPane.setConstraints(cenaField,1,2);
        GridPane.setConstraints(autorField,1,3);
        GridPane.setConstraints(gatunekField,1,4);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,5);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT into public.ksiazka " +
                            "(id_ksiazka,autor, tytul, cena, id_gatunek) VALUES (default,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, autorField.getText());
                    ps.setObject(2, tytulField.getText());
                    ps.setObject(3, Double.parseDouble(cenaField.getText()));
                    ps.setObject(4, Integer.parseInt(gatunekField.getText()));

                    ps.executeUpdate();
                    createKsiazkaScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,5);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });

        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 6);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,7);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,7);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM ksiazka where id_ksiazka = ?");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();
                    createKsiazkaScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });


        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);

        addGrid.getChildren().addAll(tytulLabel,cenaLabel,autorLabel, gatunekLabel, tytulField, cenaField, autorField, gatunekField, confirmationButton, backButton,deletLabel,deleteButton,deleteField);
        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createGatunekScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label opisLabel = new Label("Opis:");
        Label dzialLabel = new Label("ID dział:");
        GridPane.setConstraints(opisLabel,0,1);
        GridPane.setConstraints(dzialLabel,0,2);

        TextField opisField = new TextField();
        TextField dzialField = new TextField();
        GridPane.setConstraints(opisField,1,1);
        GridPane.setConstraints(dzialField,1,2);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,3);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT into public.gatunek " +
                            "(id_gatunek, id_dzial, opis) VALUES (default,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(2, opisField.getText());
                    ps.setObject(1, Integer.parseInt(dzialField.getText()));
                    ps.executeUpdate();
                    createGatunekScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,3);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });

        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 4);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,5);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,5);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM gatunek where id_gatunek = ?");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();
                    createGatunekScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczby");
                }
            }
        });

        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);
        addGrid.getChildren().addAll(opisLabel, dzialLabel, opisField, dzialField, backButton, confirmationButton, deleteButton,deleteField,deletLabel);

        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createDzialScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label opisLabel = new Label("Opis:");
        Label kadraLabel = new Label("ID Kadry:");
        Label ksiegarniaLabel = new Label("ID Księgarni:");
        GridPane.setConstraints(opisLabel,0,1);
        GridPane.setConstraints(kadraLabel,0,2);
        GridPane.setConstraints(ksiegarniaLabel,0,3);

        TextField opisField = new TextField();
        TextField kadraField = new TextField();
        TextField ksiegarniaField = new TextField();
        GridPane.setConstraints(opisField,1,1);
        GridPane.setConstraints(kadraField,1,2);
        GridPane.setConstraints(ksiegarniaField,1,3);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,5);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT into public.dzial " +
                            "(id_dzial, id_ksiegarnia, id_kadra_pracownicza, nazwa) VALUES (default,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, Integer.parseInt(ksiegarniaField.getText()));
                    ps.setObject(3, opisField.getText());
                    ps.setObject(2, Integer.parseInt(kadraField.getText()));

                    ps.executeUpdate();
                    createDzialScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,5);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });
        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 6);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,7);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,7);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM dzial where id_dzial = ?");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();
                    createKsiazkaScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });
        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);

        addGrid.getChildren().addAll(opisLabel,kadraLabel, ksiegarniaLabel, opisField, kadraField, ksiegarniaField, confirmationButton, backButton, deleteButton,deleteField,deletLabel);
        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createMenedzerScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label imieLabel = new Label("Imie:");
        Label nazwiskoLabel = new Label("Nazwisko:");
        Label plecLabel = new Label("Płeć:");
        Label telefonLabel = new Label("Nr. Telefonu:");
        Label emailLabel = new Label("E-Mail:");
        Label biuroLabel = new Label("Biuro");
        Label kadraLabel = new Label("ID kadry:");
        GridPane.setConstraints(imieLabel,0,1);
        GridPane.setConstraints(nazwiskoLabel,0,2);
        GridPane.setConstraints(plecLabel,0,3);
        GridPane.setConstraints(telefonLabel,0,4);
        GridPane.setConstraints(emailLabel,0,5);
        GridPane.setConstraints(biuroLabel,0,6);
        GridPane.setConstraints(kadraLabel,0,7);

        TextField imieField = new TextField();
        TextField nazwiskoField = new TextField();
        TextField plecField = new TextField();
        TextField telefonField = new TextField();
        TextField emailField = new TextField();
        TextField biuroField = new TextField();
        TextField kadraField = new TextField();
        GridPane.setConstraints(imieField,1,1);
        GridPane.setConstraints(nazwiskoField,1,2);
        GridPane.setConstraints(plecField,1,3);
        GridPane.setConstraints(telefonField,1,4);
        GridPane.setConstraints(emailField,1,5);
        GridPane.setConstraints(biuroField,1,6);
        GridPane.setConstraints(kadraField,1,7);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,8);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement pracownikPs = Database.getCONNECTION().prepareStatement("INSERT into public.pracownik " +
                            "(id_pracownik, nazwisko, imie, plec, telefon, email, id_kadra_pracownicza) VALUES (default,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement menedzerPS = Database.getCONNECTION().prepareStatement("INSERT into menedzer " +
                            "(id_menedzer, id_pracownik, biuro) VALUES (DEFAULT,?,?);");
                    pracownikPs.setObject(1,nazwiskoField.getText());
                    pracownikPs.setObject(2,imieField.getText());
                    if (plecField.getText().equals(""))
                        pracownikPs.setObject(3,null);
                    else
                        pracownikPs.setObject(3,plecField.getText());
                    pracownikPs.setObject(4,telefonField.getText());
                    pracownikPs.setObject(5,emailField.getText());
                    pracownikPs.setObject(6,Integer.parseInt(kadraField.getText()));
                    pracownikPs.executeUpdate();
                    ResultSet rs = Database.getCONNECTION().prepareStatement("SELECT * FROM pracownikmaxid();", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
                    rs.next();
                    menedzerPS.setObject(1, Integer.parseInt(rs.getString("max")));
                    menedzerPS.setObject(2,biuroField.getText());
                    menedzerPS.executeUpdate();

                    createMenedzerScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,8);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });
        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 9);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,10);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,10);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM pracownik USING menedzer WHERE menedzer.id_menedzer = ? and pracownik.id_pracownik = menedzer.id_pracownik");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();

                    createMenedzerScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });
        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);

        addGrid.getChildren().addAll(imieLabel,nazwiskoLabel,plecLabel, telefonLabel,emailLabel,biuroLabel,kadraLabel,
                imieField, nazwiskoField, plecField, telefonField,emailField,biuroField,kadraField, confirmationButton,
                backButton, deleteButton, deleteField, deletLabel);
        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createKasjerScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label imieLabel = new Label("Imie:");
        Label nazwiskoLabel = new Label("Nazwisko:");
        Label plecLabel = new Label("Płeć:");
        Label telefonLabel = new Label("Nr. Telefonu:");
        Label emailLabel = new Label("E-Mail:");
        Label stanowiskoLabel = new Label("Nr. Stanowiska");
        Label kadraLabel = new Label("ID kadry:");
        GridPane.setConstraints(imieLabel,0,1);
        GridPane.setConstraints(nazwiskoLabel,0,2);
        GridPane.setConstraints(plecLabel,0,3);
        GridPane.setConstraints(telefonLabel,0,4);
        GridPane.setConstraints(emailLabel,0,5);
        GridPane.setConstraints(stanowiskoLabel,0,6);
        GridPane.setConstraints(kadraLabel,0,7);

        TextField imieField = new TextField();
        TextField nazwiskoField = new TextField();
        TextField plecField = new TextField();
        TextField telefonField = new TextField();
        TextField emailField = new TextField();
        TextField stanowiskoField = new TextField();
        TextField kadraField = new TextField();
        GridPane.setConstraints(imieField,1,1);
        GridPane.setConstraints(nazwiskoField,1,2);
        GridPane.setConstraints(plecField,1,3);
        GridPane.setConstraints(telefonField,1,4);
        GridPane.setConstraints(emailField,1,5);
        GridPane.setConstraints(stanowiskoField,1,6);
        GridPane.setConstraints(kadraField,1,7);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,8);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement pracownikPs = Database.getCONNECTION().prepareStatement("INSERT into public.pracownik " +
                            "(id_pracownik, nazwisko, imie, plec, telefon, email, id_kadra_pracownicza) VALUES (default,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement kasjerPs = Database.getCONNECTION().prepareStatement("INSERT into kasjer " +
                            "(id_kasjer, id_pracownik, stanowisko) VALUES (DEFAULT,?,?);");
                    pracownikPs.setObject(1,nazwiskoField.getText());
                    pracownikPs.setObject(2,imieField.getText());
                    if (plecField.getText().equals(""))
                        pracownikPs.setObject(3,null);
                    else
                        pracownikPs.setObject(3,plecField.getText());
                    pracownikPs.setObject(4,telefonField.getText());
                    pracownikPs.setObject(5,emailField.getText());
                    pracownikPs.setObject(6,Integer.parseInt(kadraField.getText()));
                    pracownikPs.executeUpdate();
                    ResultSet rs = Database.getCONNECTION().prepareStatement("SELECT * FROM pracownikmaxid();", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
                    rs.next();
                    kasjerPs.setObject(1, Integer.parseInt(rs.getString("max")));
                    kasjerPs.setObject(2,Integer.parseInt(stanowiskoField.getText()));
                    kasjerPs.executeUpdate();

                    createKasjerScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,8);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });
        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 9);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,10);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,10);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM pracownik USING kasjer WHERE kasjer.id_kasjer = ? and pracownik.id_pracownik = kasjer.id_pracownik");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();

                    createKasjerScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });
        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);

        addGrid.getChildren().addAll(imieLabel,nazwiskoLabel,plecLabel, telefonLabel,emailLabel,stanowiskoLabel,kadraLabel,
                imieField, nazwiskoField, plecField, telefonField,emailField,stanowiskoField,kadraField, confirmationButton,
                backButton, deleteButton, deleteField, deletLabel);
        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createSprzataczScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label imieLabel = new Label("Imie:");
        Label nazwiskoLabel = new Label("Nazwisko:");
        Label plecLabel = new Label("Płeć:");
        Label telefonLabel = new Label("Nr. Telefonu:");
        Label emailLabel = new Label("E-Mail:");
        Label sprzetLabel = new Label("Sprzęt:");
        Label kadraLabel = new Label("ID kadry:");
        GridPane.setConstraints(imieLabel,0,1);
        GridPane.setConstraints(nazwiskoLabel,0,2);
        GridPane.setConstraints(plecLabel,0,3);
        GridPane.setConstraints(telefonLabel,0,4);
        GridPane.setConstraints(emailLabel,0,5);
        GridPane.setConstraints(sprzetLabel,0,6);
        GridPane.setConstraints(kadraLabel,0,7);

        TextField imieField = new TextField();
        TextField nazwiskoField = new TextField();
        TextField plecField = new TextField();
        TextField telefonField = new TextField();
        TextField emailField = new TextField();
        TextField sprzetField = new TextField();
        TextField kadraField = new TextField();
        GridPane.setConstraints(imieField,1,1);
        GridPane.setConstraints(nazwiskoField,1,2);
        GridPane.setConstraints(plecField,1,3);
        GridPane.setConstraints(telefonField,1,4);
        GridPane.setConstraints(emailField,1,5);
        GridPane.setConstraints(sprzetField,1,6);
        GridPane.setConstraints(kadraField,1,7);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,8);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement pracownikPs = Database.getCONNECTION().prepareStatement("INSERT into public.pracownik " +
                            "(id_pracownik, nazwisko, imie, plec, telefon, email, id_kadra_pracownicza) VALUES (default,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement sprzataczPS = Database.getCONNECTION().prepareStatement("INSERT into sprzatacz " +
                            "(id_sprzatacz, id_pracownik, sprzet) VALUES (DEFAULT,?,?);");
                    pracownikPs.setObject(1,nazwiskoField.getText());
                    pracownikPs.setObject(2,imieField.getText());
                    if (plecField.getText().equals(""))
                        pracownikPs.setObject(3,null);
                    else
                        pracownikPs.setObject(3,plecField.getText());
                    pracownikPs.setObject(4,telefonField.getText());
                    pracownikPs.setObject(5,emailField.getText());
                    pracownikPs.setObject(6,Integer.parseInt(kadraField.getText()));
                    pracownikPs.executeUpdate();
                    ResultSet rs = Database.getCONNECTION().prepareStatement("SELECT * FROM pracownikmaxid();", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
                    rs.next();
                    sprzataczPS.setObject(1, Integer.parseInt(rs.getString("max")));
                    sprzataczPS.setObject(2,sprzetField.getText());
                    sprzataczPS.executeUpdate();

                    createSprzataczScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,8);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });
        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 9);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,10);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,10);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM pracownik USING sprzatacz WHERE sprzatacz.id_sprzatacz = ? and pracownik.id_pracownik = sprzatacz.id_pracownik");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();

                    createSprzataczScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });
        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);

        addGrid.getChildren().addAll(imieLabel,nazwiskoLabel,plecLabel, telefonLabel,emailLabel,sprzetLabel,kadraLabel,
                imieField, nazwiskoField, plecField, telefonField,emailField,sprzetField,kadraField, confirmationButton,
                backButton, deleteButton, deleteField, deletLabel);
        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createKsiegarniaScene(){
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0,0);

        Label nazwaLabel = new Label("Nazwa:");
        Label ulicaLabel = new Label("Ulica");
        Label numerLabel = new Label("Numer budynku;");
        Label kodLabel = new Label("Kod pocztowy:");
        Label miejscowoscLabel = new Label("Miejscowość:");
        Label wlascicelLabel = new Label("ID właściciela:");
        GridPane.setConstraints(nazwaLabel,0,1);
        GridPane.setConstraints(ulicaLabel,0,2);
        GridPane.setConstraints(numerLabel,0,3);
        GridPane.setConstraints(kodLabel,0,4);
        GridPane.setConstraints(miejscowoscLabel,0,5);
        GridPane.setConstraints(wlascicelLabel,0,6);

        TextField nazwaField = new TextField();
        TextField ulicaField = new TextField();
        TextField numerField = new TextField();
        TextField kodField = new TextField();
        TextField miejscowoscField = new TextField();
        TextField wlascicielField = new TextField();
        GridPane.setConstraints(nazwaField,1,1);
        GridPane.setConstraints(ulicaField, 1,2);
        GridPane.setConstraints(numerField,1,3);
        GridPane.setConstraints(kodField,1,4);
        GridPane.setConstraints(miejscowoscField,1,5);
        GridPane.setConstraints(wlascicielField,1,6);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0,7);
        confirmationButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT into public.ksiegarnia " +
                            "(id_ksiegarnia, nazwa, ulica, numer, kod, miejscowosc, id_wlasciciel) VALUES (default,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, nazwaField.getText());
                    ps.setObject(2,ulicaField.getText());
                    ps.setObject(3,numerField.getText());
                    ps.setObject(4,kodField.getText());
                    ps.setObject(5, miejscowoscField.getText());
                    ps.setObject(6, Integer.parseInt(wlascicielField.getText()));
                    ps.executeUpdate();
                    createKsiegarniaScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1,7);
        backButton.setOnAction(e->{
            dodajScene();
            dodajStage.setScene(dodajScene);
        });

        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 8);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField,0,9);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1,9);
        deleteButton.setOnAction(e->{
            if(Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")){
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM ksiegarnia where id_ksiegarnia = ?");
                    ps.setObject(1,Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();
                    createKsiegarniaScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                }catch (NumberFormatException intException){
                    Tools.AlertBox("Error", "Zły format liczby");
                }
            }
        });

        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20,20,20,20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);
        addGrid.getChildren().addAll(nazwaLabel,ulicaLabel,numerLabel,kodLabel,miejscowoscLabel ,wlascicelLabel,
                nazwaField,ulicaField,numerField,kodField,miejscowoscField, wlascicielField, backButton,
                confirmationButton, deleteButton,deleteField,deletLabel);

        GridPane.setConstraints(addGrid, 1,0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20,20,20,20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void createWlascicielScene() {
        dodajSceneGrid = new GridPane();
        VBox wyswietlVBox = wyswietlVBox();
        GridPane.setConstraints(wyswietlVBox, 0, 0);

        Label imieLabel = new Label("Imie:");
        Label nazwiskoLabel = new Label("Nazwisko:");
        GridPane.setConstraints(imieLabel, 0, 1);
        GridPane.setConstraints(nazwiskoLabel, 0, 2);

        TextField imieField = new TextField();
        TextField nazwiskoField = new TextField();
        GridPane.setConstraints(imieField, 1, 1);
        GridPane.setConstraints(nazwiskoField, 1, 2);

        Button confirmationButton = new Button("Dodaj");
        GridPane.setConstraints(confirmationButton, 0, 3);
        confirmationButton.setOnAction(e -> {
            if (Tools.ConfirmBox("Uwaga!", "Czy na pewno chcesz dodać\n rekord do tabeli?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT into public.wlasciciel " +
                            "(id_wlasciciel, imie, nazwisko) VALUES (default,?,?);", Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1, imieField.getText());
                    ps.setObject(2, nazwiskoField.getText());
                    ps.executeUpdate();
                    createWlascicielScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas dodawania danych:\n" + sqlException);
                } catch (NumberFormatException intException) {
                    Tools.AlertBox("Error", "Zły format liczb");
                }
            }
        });

        Button backButton = new Button("Cofnij do okna wyboru");
        GridPane.setConstraints(backButton, 1, 3);
        backButton.setOnAction(e -> {
            dodajScene();
            dodajStage.setScene(dodajScene);
        });

        Label deletLabel = new Label("Podaj ID rekordu do usunięcia");
        GridPane.setConstraints(deletLabel, 0, 4);
        TextField deleteField = new TextField();
        GridPane.setConstraints(deleteField, 0, 5);
        Button deleteButton = new Button("Usuń");
        GridPane.setConstraints(deleteButton, 1, 5);
        deleteButton.setOnAction(e -> {
            if (Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz\nusunąć rekord?")) {
                try {
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("DELETE FROM wlasciciel where id_wlasciciel = ?");
                    ps.setObject(1, Integer.parseInt(deleteField.getText()));
                    ps.executeUpdate();
                    createWlascicielScene();
                } catch (SQLException sqlException) {
                    Tools.AlertBox("SQLException", "Bład podczas usuwania danych:\n" + sqlException);
                } catch (NumberFormatException intException) {
                    Tools.AlertBox("Error", "Zły format liczby");
                }
            }
        });

        GridPane addGrid = new GridPane();
        addGrid.setAlignment(Pos.CENTER);
        addGrid.setPadding(new Insets(20, 20, 20, 20));
        addGrid.setHgap(20);
        addGrid.setVgap(10);
        addGrid.getChildren().addAll(imieLabel, nazwiskoLabel, imieField, nazwiskoField, backButton, confirmationButton, deleteButton, deleteField, deletLabel);

        GridPane.setConstraints(addGrid, 1, 0);
        GridPane finalGrid = new GridPane();
        finalGrid.setAlignment(Pos.CENTER);
        finalGrid.setPadding(new Insets(20, 20, 20, 20));
        finalGrid.getChildren().addAll(wyswietlVBox, addGrid);

        Scene scene = new Scene(finalGrid);
        dodajStage.setScene(scene);
    }

    public void transakcjaScene(){
        transakcjaSceneGrid = new GridPane();
        Label label = new Label("ID kasjera:");
        GridPane.setConstraints(label,0,0);
        TextField field = new TextField();
        GridPane.setConstraints(field, 1,0);
        Button button =new Button("Przejdź do transakcji");
        GridPane.setConstraints(button,2,0);

        button.setOnAction(e-> {
            try {
                PreparedStatement ps = Database.getCONNECTION().prepareStatement("SELECT countksiazki(?);", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setInt(1, Integer.parseInt(field.getText()));
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt("countksiazki") == 0) {
                    Tools.AlertBox("Error", "Kasjer nie istnieje, lub nie sprzedaje żadnych książek!");
                } else {
                    PreparedStatement psTransakcja = Database.getCONNECTION().prepareStatement("INSERT INTO transakcja (id_transakcja, id_kasjer, data)" +
                            " VALUES (default, ?, current_timestamp) RETURNING id_transakcja",ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    psTransakcja.setObject(1, Integer.parseInt(field.getText()));
                    ResultSet rsTransakcja = psTransakcja.executeQuery();
                    rsTransakcja.next();
                    transakcja(rsTransakcja.getInt("id_transakcja"), Integer.parseInt(field.getText()));
                }
            }catch (SQLException sqlException){
                Tools.AlertBox("Error", sqlException.toString());
            }

        });

        transakcjaSceneGrid.setPadding(new Insets(20,20,20,20));
        transakcjaSceneGrid.setVgap(10);
        transakcjaSceneGrid.setHgap(10);
        transakcjaSceneGrid.setAlignment(Pos.CENTER);

        transakcjaSceneGrid.getChildren().addAll(label,field,button);

        transakcjaScene = new Scene(transakcjaSceneGrid);

    }

    public void transakcja (int idTransakcja, int idKasjer){
        GridPane transakcjaGrid = new GridPane();
        transakcjaSceneGrid.setPadding(new Insets(20,20,20,20));
        transakcjaGrid.setVgap(10);
        transakcjaGrid.setHgap(10);
        transakcjaGrid.setAlignment(Pos.CENTER);

        ObservableList<Ksiazka> ksiazkaList = FXCollections.observableArrayList();
        TableView<Ksiazka> tabelaKsiazek = new TableView<>();
        TableColumn<Ksiazka, String> tytulColumn = new TableColumn<>("Tytuł");
        tytulColumn.setMinWidth(200);
        tytulColumn.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        TableColumn<Ksiazka, String> autorColumn = new TableColumn<>("Autor");
        autorColumn.setMinWidth(200);
        autorColumn.setCellValueFactory(new PropertyValueFactory<>("autor"));
        TableColumn<Ksiazka, Double> cenaColumn = new TableColumn<>("Cena");
        cenaColumn.setMinWidth(60);
        cenaColumn.setCellValueFactory(new PropertyValueFactory<>("cena"));
        TableColumn<Ksiazka, String> gatunekColumn = new TableColumn<>("Gatunek");
        gatunekColumn.setMinWidth(60);
        gatunekColumn.setCellValueFactory(new PropertyValueFactory<>("opisGatunek"));

        tabelaKsiazek.setItems(ksiazkaList);
        tabelaKsiazek.getColumns().addAll( tytulColumn, autorColumn, gatunekColumn, cenaColumn);
        GridPane.setConstraints(tabelaKsiazek, 0,0,3,1);

        try{
            PreparedStatement ps = Database.getCONNECTION().prepareStatement("SELECT * FROM ksiazkapracownik;", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getInt("id_kasjer") == idKasjer){
                    ksiazkaList.add(new Ksiazka(rs.getInt("id_ksiazka"),
                            rs.getString("tytul"),
                            rs.getString("autor"),
                            rs.getDouble("cena"),
                            rs.getString("opis")));
                }
            }

        }catch (SQLException sqlException){
            Tools.AlertBox("Error", "Błąd podczas wczytywania danych:\n" + sqlException);
        }
        ObservableList<Ksiazka> kupioneKsiazkaList = FXCollections.observableArrayList();
        TableView<Ksiazka> kupionaKsiazkaTable = new TableView<>();
        TableColumn<Ksiazka, String> tytulColumn1 = new TableColumn<>("Tytuł");
        tytulColumn1.setMinWidth(200);
        tytulColumn1.setCellValueFactory(new PropertyValueFactory<>("tytul"));
        TableColumn<Ksiazka, String> autorColumn1 = new TableColumn<>("Autor");
        autorColumn1.setMinWidth(200);
        autorColumn1.setCellValueFactory(new PropertyValueFactory<>("autor"));
        TableColumn<Ksiazka, Double> cenaColumn1 = new TableColumn<>("Cena");
        cenaColumn1.setMinWidth(60);
        cenaColumn1.setCellValueFactory(new PropertyValueFactory<>("cena"));
        TableColumn<Ksiazka, String> gatunekColumn1 = new TableColumn<>("Gatunek");
        gatunekColumn1.setMinWidth(60);
        gatunekColumn1.setCellValueFactory(new PropertyValueFactory<>("opisGatunek"));
        kupionaKsiazkaTable.setItems(kupioneKsiazkaList);
        kupionaKsiazkaTable.getColumns().addAll(tytulColumn1, autorColumn1, gatunekColumn1, cenaColumn1);
        GridPane.setConstraints(kupionaKsiazkaTable,3,0,3,1);



        Button dodajButton = new Button("Dodaj do zamówienia");
        GridPane.setConstraints(dodajButton,3,1);
        dodajButton.setOnAction(e-> kupioneKsiazkaList.add(tabelaKsiazek.getSelectionModel().getSelectedItem()));
        Button przeprowadButton = new Button("Przeprowadź transakcję");
        GridPane.setConstraints(przeprowadButton, 4,1);
        przeprowadButton.setOnAction(e->{
            if (Tools.ConfirmBox("Uwaga", "Czy na pewno chcesz utworzyc zamówienie?")){
                try{
                    PreparedStatement ps = Database.getCONNECTION().prepareStatement("INSERT INTO kopia_ksiazki " +
                            "(id_kopia, id_transakcja, id_ksiazka) VALUES (default, ?, ?)");
                    ps.setInt(1, idTransakcja);
                    for (Ksiazka k: kupioneKsiazkaList){
                        ps.setInt(2, k.getId());
                        ps.executeUpdate();
                    }
                }catch (SQLException sqlException){
                    Tools.AlertBox("Error", "Błąd podczas przeprowadzania transakcji:\n" + sqlException);
                }
                Tools.AlertBox("Uwaga!", "Transakcja przebiegła pomyślnie");
                transakcjaStage.setScene(transakcjaScene);
            }
        });

        transakcjaGrid.getChildren().addAll(tabelaKsiazek, kupionaKsiazkaTable, dodajButton, przeprowadButton);

        transakcjaFinalScene = new Scene(transakcjaGrid);
        transakcjaStage.setScene(transakcjaFinalScene);
    }

    public void createRaportScene(){
        Button obrotButton = new Button("Raport Obrotu");
        Button transakcjaButton = new Button("Raport Transakcji");
        Button pracownikButton = new Button("Raport Pracowników");
        Button kasjerButton = new Button("Raport Sprzedaży Kasjerów");

        GridPane.setConstraints(obrotButton,0,1);
        GridPane.setHalignment(obrotButton, HPos.CENTER);
        GridPane.setConstraints(pracownikButton, 1, 1);
        GridPane.setHalignment(pracownikButton, HPos.CENTER);
        GridPane.setConstraints(transakcjaButton, 0, 2);
        GridPane.setHalignment(transakcjaButton, HPos.CENTER);
        GridPane.setConstraints(kasjerButton,1,2);
        GridPane.setHalignment(kasjerButton, HPos.CENTER);

        obrotButton.setOnAction(e -> raportStage.setScene(createObrotRaportScene()));
        transakcjaButton.setOnAction(e -> raportStage.setScene(createTransakcjaRaport()));
        pracownikButton.setOnAction(e -> raportStage.setScene(createPracownikRaport()));
        kasjerButton.setOnAction(e -> raportStage.setScene(createKasjerRaport()));


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.getChildren().addAll(obrotButton, transakcjaButton, pracownikButton, kasjerButton);
        raportScene = new Scene(grid);
    }

    public Scene createObrotRaportScene(){
        GridPane grid = new GridPane();
        TableView<RaportObrot> tableView = Database.raportObrot();
        GridPane.setConstraints(tableView, 0, 0);

        Button button = new Button("Cofnij");
        GridPane.setConstraints(button, 0, 1);
        GridPane.setHalignment(button, HPos.CENTER);

        button.setOnAction(e->{
            createRaportScene();
            raportStage.setScene(raportScene);
        });

        grid.getChildren().addAll(tableView,button);


        grid.setAlignment(Pos.CENTER);
        return new Scene(grid);
    }

    public Scene createTransakcjaRaport(){
        GridPane grid = new GridPane();
        TableView<RaportTransakcja> tableView = Database.raportTransakcja();
        GridPane.setConstraints(tableView, 0, 0);

        Button button = new Button("Cofnij");
        GridPane.setConstraints(button, 0, 1);
        GridPane.setHalignment(button, HPos.CENTER);

        button.setOnAction(e->{
            createRaportScene();
            raportStage.setScene(raportScene);
        });

        grid.getChildren().addAll(tableView,button);


        grid.setAlignment(Pos.CENTER);
        return new Scene(grid);
    }

    public Scene createPracownikRaport(){
        GridPane grid = new GridPane();
        TableView<RaportPracownik> tableView = Database.raportPracownik();
        GridPane.setConstraints(tableView, 0, 0);

        Button button = new Button("Cofnij");
        GridPane.setConstraints(button, 0, 1);
        GridPane.setHalignment(button, HPos.CENTER);

        button.setOnAction(e->{
            createRaportScene();
            raportStage.setScene(raportScene);
        });

        grid.getChildren().addAll(tableView,button);


        grid.setAlignment(Pos.CENTER);
        return new Scene(grid);
    }

    public Scene createKasjerRaport(){
        GridPane grid = new GridPane();
        TableView<RaportKasjer> tableView = Database.raportKasjer();
        GridPane.setConstraints(tableView, 0, 0);

        Button button = new Button("Cofnij");
        GridPane.setConstraints(button, 0, 1);
        GridPane.setHalignment(button, HPos.CENTER);

        button.setOnAction(e->{
            createRaportScene();
            raportStage.setScene(raportScene);
        });

        grid.getChildren().addAll(tableView,button);


        grid.setAlignment(Pos.CENTER);
        return new Scene(grid);
    }
}
