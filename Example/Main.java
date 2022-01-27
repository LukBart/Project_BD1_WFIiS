package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    Scene scene1, scene2, scene3;
    Stage mainStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        TextField text = new TextField();
//        wymusza coś przy zamknięciu
        primaryStage.setOnCloseRequest(windowEvent ->{
//            zjada zdarzenia - nie pozwala zamknąc okienka
            windowEvent.consume();
            closeProgram();
        });
//        Pierwsze okienko
        Label label1 = new Label("First scene");
        Button button1_1 = new Button("to scene 2");
//        przycisk zmiany sceny
        button1_1.setOnAction(e->primaryStage.setScene(scene2));
        Button button1_2 = new Button("to scene 3");
        button1_2.setOnAction(e->{
            System.out.println(ConfirmBox.display("title", "do u wanna?"));
        });
//        wprowadzanie danych
        Button button1_3 = new Button("wprowadz dane");
        button1_3.setOnAction(actionEvent -> isInt(text));

//        przycisk zamknięcia
        Button closeButton1 = new Button("close");
        closeButton1.setOnAction(e->closeProgram());


//ustawianie layoutu pierwzego okienka
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1_1, button1_2, text, button1_3, closeButton1);
        layout1.setAlignment(Pos.CENTER);

        scene1 = new Scene(layout1, 200, 200);

//scena 2
        Button button2_1 = new Button("to scene 1");
        button2_1.setOnAction(e -> primaryStage.setScene(scene1));
        Button button2_2 = new Button("to alert");
        button2_2.setOnAction(e->AlertBox.display("alert", "niepoprawnie cos"));
        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(button2_1, button2_2);
        layout2.setAlignment(Pos.CENTER);

        scene2 = new Scene(layout2, 600, 300);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("app");
        primaryStage.show();
    }

    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Uwaga", "Chcesz zamknąć program?");
        if (answer)
            mainStage.close();
    }

    private boolean isInt(TextField input){
        try{
            int n = Integer.parseInt(input.getText());
            AlertBox.display("Uwaga!", "int");
            return true;
        }catch (NumberFormatException e){
            AlertBox.display("Uwaga!", input.getText() + "\nnie jest int");
            return false;
        }
    }
}