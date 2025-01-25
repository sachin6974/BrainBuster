package brainblust;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RegformController implements Initializable {

    @FXML
    private TextField userid;
    @FXML
    private TextField passid;
    @FXML
    private RadioButton op2;
    @FXML
    private ToggleGroup radiobutton;
    @FXML
    private RadioButton op1;
    @FXML
    private TextField name;
    @FXML
    private Button btn2;
    @FXML
    private Label messageLabel1;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/brainblust_db";
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  // Ensure this is your database password

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize anything if needed
    }

    @FXML
    private void radiobutton(ActionEvent event) {
        // Add any action related to radio button if needed
    }

    @FXML
    private void submit2(ActionEvent event) {
        String fullName = name.getText();
        String userId = userid.getText();
        String password = passid.getText();
        String selectedOption = op1.isSelected() ? op1.getText() : op2.isSelected() ? op2.getText() : "None";
        
        if (fullName.isEmpty() || userId.isEmpty() || password.isEmpty()) {
            messageLabel1.setText("Please fill in all the fields!");
            messageLabel1.setStyle("-fx-text-fill: red;");
        } else {
            // Check if user already exists
            if (isUserExist(userId)) {
                messageLabel1.setText("User ID already exists!");
                messageLabel1.setStyle("-fx-text-fill: red;");
            } else {
                if (insertUserData(fullName, userId, password, selectedOption)) {
                    showRegistrationSuccessPopup();
                    loadNextPage();
                } else {
                    messageLabel1.setText("Error during registration.");
                    messageLabel1.setStyle("-fx-text-fill: red;");
                }
            }
        }
    }

    private boolean insertUserData(String fullName, String userId, String password, String selectedOption) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO users (full_name, user_id, password, selected_option) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            
            stmt.setString(1, fullName);
            stmt.setString(2, userId);
            stmt.setString(3, password);
            stmt.setString(4, selectedOption);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Check if user exists in the database
    private boolean isUserExist(String userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userId);
            
            ResultSet resultSet = stmt.executeQuery();
            
            return resultSet.next();  // Returns true if user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showRegistrationSuccessPopup() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Success");
        alert.setHeaderText("Registration Successful");
        alert.setContentText("You have been successfully registered!");
        alert.showAndWait();
    }

    private void loadNextPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn2.getScene().getWindow(); 
            stage.setScene(new Scene(root)); 
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel1.setText("Error loading exam page!");
            messageLabel1.setStyle("-fx-text-fill: red;");
        }
    }
}
