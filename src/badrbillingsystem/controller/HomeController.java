
package badrbillingsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;


public class HomeController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    BorderPane borderPane;
    
    @FXML
    private void showCustomersUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/customers.fxml"));
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
}
