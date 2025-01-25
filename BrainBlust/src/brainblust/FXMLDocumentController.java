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

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/brainblust_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @FXML
    private void submit1(ActionEvent event) {
        String userId = userid2.getText();
        String password = passid2.getText();

        // Check if both userId and password are provided
        if (userId.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in both User ID and Password.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Validate credentials
        if (validateUserCredentials(userId, password)) {
            System.out.println("Login Successful!");
            messageLabel.setText("Login Successful");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Redirect to Exam page after successful login
            loadNextPage();
        } else {
            messageLabel.setText("Invalid User ID or Password.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // Validates user credentials from the database
    private boolean validateUserCredentials(String userId, String password) {
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        // For debugging purposes
        System.out.println("Attempting to validate user credentials. User ID: " + userId + ", Password: " + password);

        // Query with case-insensitive comparison
        String query = "SELECT * FROM login WHERE LOWER(user_id) = LOWER(?) AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, userId);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return true;  // User found
        } else {
            return false;  // User not found
        }
    } catch (SQLException e) {
        e.printStackTrace();
        messageLabel.setText("Database error. Please try again later.");
        messageLabel.setStyle("-fx-text-fill: red;");
        return false;
    }
}


    // Load the Exam Page after successful login
    private void loadNextPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Exam_page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Error loading exam page!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // Navigate to Registration page when hyperlink is clicked
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

    // Navigate to Admin Login page when hyperlink is clicked
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
        // Initialization logic (if any) can go here
    }
}
