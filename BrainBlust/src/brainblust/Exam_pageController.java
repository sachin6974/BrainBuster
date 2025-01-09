package brainblust;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private Label informationLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization if needed
    }

    @FXML
    private void finished(ActionEvent event) {
        // Logic for finishing the exam
    }

    @FXML
    private void questionop(ActionEvent event) {
        // Logic for handling question options
    }
}
