package com.example.project.Controller;
import com.example.project.Database.DBManager;

import com.example.project.LoginApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    TextField txtURL;
    @FXML
    TextField txtUser;
    @FXML
    TextField txtPassword;
    @FXML
    Label lblResult;
    @FXML
    CheckBox checkBoxCreateDB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblResult.setText("");
        txtURL.setText("jdbc:mysql://localhost:3306/store");
        txtUser.setText("root");
        txtPassword.setText("root");
    }

    public void testConnection(){
        String schema = "";
        try {
            Connection connection = DriverManager.getConnection(txtURL.getText(), txtUser.getText(), txtPassword.getText());
            var tmp = txtURL.getText().split("/");
            schema = tmp[tmp.length-1];
            lblResult.setText("Connection successful!");
            connection.close();
            DBManager.setCredentials(txtURL.getText(), txtUser.getText(), txtPassword.getText());
            if(!checkBoxCreateDB.isSelected() && !DBManager.isTablesCreated()) {
                lblResult.setText("Connection successful!\nBut some tables does not exist, \nValidate the checkbox 'Create DB'");
                return;
            }
            Stage stage = (Stage) lblResult.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            lblResult.setText("Connection failed. Error:\n" + e.getMessage());
            return;
        }
        try{
            if(checkBoxCreateDB.isSelected())
                DBManager.executeScriptFile("src/main/resources/sql/CreateDB_Project.sql", schema);
            Stage st = new Stage();
            Parent root = FXMLLoader.load(LoginApp.class.getResource("Project.fxml"));
            st.setTitle("Women Shop!");
            st.setScene(new Scene(root));
            st.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
