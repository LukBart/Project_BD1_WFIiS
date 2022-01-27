package project.Project_BD1_WFIiS;

import javafx.application.Application;
import javafx.stage.Stage;
import project.Project_BD1_WFIiS.GUI.GUI;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new GUI(stage);
    }
    public static void main(String[] args) {
        launch();
    }
}