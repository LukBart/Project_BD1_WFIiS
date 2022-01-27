module project.projekt {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires java.sql;

    opens project.Project_BD1_WFIiS to javafx.fxml;
    exports project.Project_BD1_WFIiS;
    exports project.Project_BD1_WFIiS.GUI;
    opens project.Project_BD1_WFIiS.GUI to javafx.fxml;
}

