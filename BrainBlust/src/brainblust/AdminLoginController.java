package brainblust;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class for Admin Login
 */
public class AdminLoginController implements Initializable {

    @FXML
    private TextField adminid; 
    @FXML
    private TextField adminpass; 
    @FXML
    private Button adminsubmit; 
    @FXML
    private Label msglabeladmin; 
    @FXML
    private Hyperlink studentlogin; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

 @FXML
private void submit(ActionEvent event) {
    String adminId = adminid.getText().trim();
    String adminPassword = adminpass.getText().trim();

    if ("sachin2025".equals(adminId) && "1234".equals(adminPassword)) {
        msglabeladmin.setText("Admin Login Successful!");
        msglabeladmin.setStyle("-fx-text-fill: green;");
        System.out.println("Admin login successful!");

        // Load the Dashboard
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) adminsubmit.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            msglabeladmin.setText("Error loading dashboard!");
        }
    } else {
        msglabeladmin.setText("Invalid Admin ID or Password!");
        msglabeladmin.setStyle("-fx-text-fill: red;");
        System.out.println("Admin login failed!");
    }
}

   

   @FXML
    private void loginstudent(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Stage stage = (Stage) studentlogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Student Login");
            stage.show();
        } catch (IOException e) {
            System.out.println("Failed to load the Student Login page.");
        }
    }
}
