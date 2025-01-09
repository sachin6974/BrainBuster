/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package brainblust;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author DELL
 */
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
    private Button btn4; // Update button
    @FXML
    private Button btn5; // Delete button

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Optional initialization logic, if any
    }

    @FXML
    private void submit3(ActionEvent event) {
        String question = text1.getText();
        String option0 = option1.getText();
        String option9 = option2.getText();
        String option8 = option3.getText();
        String option7 = option4.getText();
        
        if (!question.isEmpty() && !option0.isEmpty() && !option9.isEmpty() && !option8.isEmpty() && !option7.isEmpty()) {
            String fullQuestion = "Q: " + question + "\n" +
                                  "1. " + option0 + "\n" +
                                  "2. " + option9 + "\n" +
                                  "3. " + option8 + "\n" +
                                  "4. " + option7;
            questionList_View.getItems().add(fullQuestion);
            clearFields();
        } else {
            showAlert("Error", "All fields must be filled in!");
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
                String updatedQuestion = "Q: " + question + "\n" +
                                         "1. " + option0 + "\n" +
                                         "2. " + option9 + "\n" +
                                         "3. " + option8 + "\n" +
                                         "4. " + option7;
                questionList_View.getItems().set(selectedIndex, updatedQuestion);
                clearFields();
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
            questionList_View.getItems().remove(selectedIndex);
            clearFields();
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
