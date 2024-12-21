/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
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
 *
 * @author DELL
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button btn1;
    @FXML
    private Hyperlink link1;
    @FXML
    private TextField userid2;
    @FXML
    private TextField passid2;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void submit1(ActionEvent event) {
         String userId = userid2.getText();
    String password = passid2.getText();

    System.out.println("User ID: " + userId);
    System.out.println("Password: " + password);
    
    if (!userId.isEmpty() && !password.isEmpty()) {
        System.out.println("Login details submitted successfully!");
    } 
    else {
        System.out.println("Please fill in both User ID and Password fields.");
    }
        
     }

    @FXML
    private void link(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("Regform.fxml"));
       Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
       stage.setScene(new Scene(root));
       stage.setTitle("Ragistration Form");
       stage.show();
       
    }
    
}
