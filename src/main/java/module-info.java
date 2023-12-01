module com.example.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.project to javafx.fxml;
    exports com.example.project;
    exports com.example.project.Model;
    opens com.example.project.Model to javafx.fxml;
    exports com.example.project.Interface;
    opens com.example.project.Interface to javafx.fxml;
    exports com.example.project.Controller;
    opens com.example.project.Controller to javafx.fxml;
    exports com.example.project.Database;
    opens com.example.project.Database to javafx.fxml;
}