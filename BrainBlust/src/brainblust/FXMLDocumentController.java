package brainblust;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    
    // Static Admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // Sample dynamic user credentials (You can replace this with database interaction)
    private Map<String, String> userDatabase = new HashMap<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize a sample dynamic user list
        userDatabase.put("user1", "password1");
        userDatabase.put("user2", "password2");
    }    

    @FXML
    private void submit1(ActionEvent event) throws IOException {
        String userId = userid2.getText();
        String password = passid2.getText();

        // Check if user is Admin
        if (userId.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            messageLabel.setText("Admin Login successful!");
            messageLabel.setStyle("-fx-text-fill: blue;");
            
            // Load Admin Dashboard
            loadDashboard();
        } 
        // Check if user is in the dynamic user list
        else if (userDatabase.containsKey(userId) && userDatabase.get(userId).equals(password)) {
            messageLabel.setText("User Login successful!");
            messageLabel.setStyle("-fx-text-fill: green;");
            
            // Load User Dashboard
            loadDashboard();
        } 
        else {
            messageLabel.setText("Invalid User ID or Password!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadDashboard() throws IOException {
        // Load the Dashboard scene
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage = (Stage) btn1.getScene().getWindow();  // Get current window
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Dashboard");  // Set title of the dashboard
        stage.show();
    }

    @FXML
    private void link(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Regform.fxml"));
       Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.setTitle("Registration Form");
       stage.show();
    }
}
