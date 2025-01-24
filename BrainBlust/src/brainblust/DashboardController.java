package brainblust;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DashboardController implements Initializable {

    @FXML
    private TextField text1;
    @FXML
    private TextField option1;
    @FXML
    private TextField option2;
    @FXML
    private TextField option3;
    @FXML
    private TextField option4;
    @FXML
    private Button btn3;
    @FXML
    private ListView<String> questionList_View;
    @FXML
    private Button btn4; 
    @FXML
    private Button btn5; 

    private static final String DB_URL = "jdbc:mysql://localhost:3306/brainblust_db";  
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "";  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadQuestions(); // Load questions from DB
    }

    private Connection connectDB() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to connect to the database.");
            return null;
        }
    }

    @FXML
    private void submit3(ActionEvent event) {
        String question = text1.getText();
        String option0 = option1.getText();
        String option9 = option2.getText();
        String option8 = option3.getText();
        String option7 = option4.getText();

        if (!question.isEmpty() && !option0.isEmpty() && !option9.isEmpty() && !option8.isEmpty() && !option7.isEmpty()) {
            try (Connection conn = connectDB()) {
                String query = "INSERT INTO questions (question, option1, option2, option3, option4) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, question);
                stmt.setString(2, option0);
                stmt.setString(3, option9);
                stmt.setString(4, option8);
                stmt.setString(5, option7);
                stmt.executeUpdate();

                showAlert("Success", "Question added successfully!");
                loadQuestions(); 
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add the question.");
            }
        } else {
            showAlert("Error", "All fields must be filled in!");
        }
    }

    private void loadQuestions() {
        questionList_View.getItems().clear();
        try (Connection conn = connectDB()) {
            String query = "SELECT * FROM questions";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fullQuestion = "Q: " + rs.getString("question") + "\n" +
                                      "1. " + rs.getString("option1") + "\n" +
                                      "2. " + rs.getString("option2") + "\n" +
                                      "3. " + rs.getString("option3") + "\n" +
                                      "4. " + rs.getString("option4");
                questionList_View.getItems().add(fullQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load questions.");
        }
    }

    @FXML
    private void update(ActionEvent event) {
        int selectedIndex = questionList_View.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String question = text1.getText();
            String option0 = option1.getText();
            String option9 = option2.getText();
            String option8 = option3.getText();
            String option7 = option4.getText();

            if (!question.isEmpty() && !option0.isEmpty() && !option9.isEmpty() && !option8.isEmpty() && !option7.isEmpty()) {
                try (Connection conn = connectDB()) {
                    String query = "UPDATE questions SET question = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);

                    // Assume the ID is stored in the ListView's backing data (you'll need to track IDs)
                    int id = selectedIndex + 1; // Replace with actual ID logic
                    stmt.setString(1, question);
                    stmt.setString(2, option0);
                    stmt.setString(3, option9);
                    stmt.setString(4, option8);
                    stmt.setString(5, option7);
                    stmt.setInt(6, id);

                    stmt.executeUpdate();
                    showAlert("Success", "Question updated successfully!");
                    loadQuestions();
                    clearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to update the question.");
                }
            } else {
                showAlert("Error", "All fields must be filled in to update!");
            }
        } else {
            showAlert("Error", "No question selected to update!");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        int selectedIndex = questionList_View.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            try (Connection conn = connectDB()) {
                String query = "DELETE FROM questions WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);

                // Assume the ID is stored in the ListView's backing data (you'll need to track IDs)
                int id = selectedIndex + 1; // Replace with actual ID logic
                stmt.setInt(1, id);

                stmt.executeUpdate();
                showAlert("Success", "Question deleted successfully!");
                loadQuestions();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete the question.");
            }
        } else {
            showAlert("Error", "No question selected to delete!");
        }
    }

    private void clearFields() {
        text1.clear();
        option1.clear();
        option2.clear();
        option3.clear();
        option4.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
