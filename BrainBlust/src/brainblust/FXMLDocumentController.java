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



public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Hyperlink link1;
    @FXML
    private TextField userid2;
    @FXML
    private TextField passid2;
    @FXML
    private Label messageLabel;
    @FXML
    private Hyperlink link2;

   @FXML
private void submit1(ActionEvent event) {
    String userId = userid2.getText();
    String password = passid2.getText();

    if (!userId.isEmpty() && !password.isEmpty()) {
        System.out.println("Submitted Successfully!");
        messageLabel.setText("Login Success");

        // Load the Exam Page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn1.getScene().getWindow(); 
            stage.setScene(new Scene(root)); 
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading exam page!");
        }
    } else {
        System.out.println("Please fill in both User ID and Password fields!");
        messageLabel.setText("Login Not Successful");
    }
}

    
    @FXML
    private void link(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Regform.fxml"));
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registration Form");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading Regform.fxml: " + e.getMessage());
        }
    }
    
 
    @FXML
    private void linkk(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Admin Login Form");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading AdminLogin.fxml: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize any required logic here.
    }
}
