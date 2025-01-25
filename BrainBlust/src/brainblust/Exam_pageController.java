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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class Exam_pageController implements Initializable {

    @FXML
    private Button examfinised;
    @FXML
    private RadioButton S1;
    @FXML
    private ToggleGroup option;
    @FXML
    private RadioButton S2;
    @FXML
    private RadioButton S3;
    @FXML
    private RadioButton S4;
    @FXML
    private Label informationLabel;
    @FXML
    private Label userIdLabel;
    @FXML
    private ListView<String> listviewQs;
    @FXML
    private TextArea textarea;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/brainblust_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private String userId;
    private String fullName;
    private int score = 0;  // Variable to keep track of the score

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadQuestions(); // Load questions when the page initializes
    }

    // Method to set user data from the previous page
    public void setUserData(String userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
        userIdLabel.setText("User ID: " + userId);
        informationLabel.setText("Full Name: " + fullName);
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

    // Method to load questions into the ListView
    private void loadQuestions() {
        listviewQs.getItems().clear();
        try (Connection conn = connectDB()) {
            String query = "SELECT * FROM questions";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String question = rs.getString("question");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                String option4 = rs.getString("option4");

                // Construct the question format for display in ListView
                String formattedQuestion = "Q: " + question + "\n"
                                            + "1. " + option1 + "\n"
                                            + "2. " + option2 + "\n"
                                            + "3. " + option3 + "\n"
                                            + "4. " + option4;
                listviewQs.getItems().add(formattedQuestion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load questions.");
        }
    }

    // Method to track the user's answer and compare it with the correct answer
    private void checkAnswer(String selectedOption, String correctAnswer) {
        if (selectedOption.equals(correctAnswer)) {
            score += 1;  // Increment score if the answer is correct
        }
    }

    @FXML
    private void finished(ActionEvent event) {
        // Fetch the selected answers and calculate the score
        try (Connection conn = connectDB()) {
            String query = "SELECT * FROM questions";  // Fetch all questions
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            int questionIndex = 0;
            while (rs.next()) {
                String correctAnswer = rs.getString("correct_answer");
                RadioButton selectedRadioButton = (RadioButton) option.getSelectedToggle();
                if (selectedRadioButton != null) {
                    String selectedOption = selectedRadioButton.getText();
                    checkAnswer(selectedOption, correctAnswer);
                }
                questionIndex++;
            }

            // Show the total score
            showAlert("Exam Finished", "Your score: " + score);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to calculate the score.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetRadioButtonSelection() {
        option.selectToggle(null);  // This will unselect any selected radio button
    }
    
    
    @FXML
    private void questionop(ActionEvent event) {
        String selectedOption = ((RadioButton) option.getSelectedToggle()).getText();
        System.out.println("Selected option: " + selectedOption);
    }
}
