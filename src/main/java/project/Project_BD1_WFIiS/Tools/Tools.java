package project.Project_BD1_WFIiS.Tools;


import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Tools {
    static boolean answer;

    public static void AlertBox(String title, String message){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);

        stage.setMinWidth(250);
        Label label = new Label();
        label.setAlignment(Pos.CENTER);
        label.setText(message);


        Button closeButton = new Button("Close The window");

        closeButton.setOnAction(e->stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }


    public static boolean ConfirmBox(String title, String message){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setMinWidth(250);
        Label label = new Label();
        label.setText(message);;

        Button yesButton = new Button("yes");
        Button noButton = new Button("no");

        yesButton.setOnAction(e->{
            answer = true;
            stage.close();
        });

        noButton.setOnAction(e ->{
            answer = false;
            stage.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();

        return answer;
    }

}
