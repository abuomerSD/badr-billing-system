
package badrbillingsystem.controller;

import badrbillingsystem.models.User;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class HomeController implements Initializable{

    public HomeController(String username) {
        this.username = username;
    }

//    public HomeController() {
//        this("");
//    }
//
//    public HomeController(String username, Button btnReturnDocument, Button btnUsers, User user, Label lbUsername, BorderPane borderPane) {
//        this.username = username;
//        this.btnReturnDocument = btnReturnDocument;
//        this.btnUsers = btnUsers;
//        this.user = user;
//        this.lbUsername = lbUsername;
//        this.borderPane = borderPane;
//    }
    
    
    
    private String username;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // admin permissions
        
        lbUsername.setText(username);
        System.out.println(lbUsername.getText());
        if(lbUsername.getText().equals("admin")){
            btnReturnDocument.setDisable(false);
            btnUsers.setDisable(false);
        } else {
            btnReturnDocument.setDisable(true);
            btnUsers.setDisable(true);
        }
    }
    
    @FXML 
    Button btnReturnDocument;
    
    @FXML 
    Button btnUsers;
    
    public User user;
    
    @FXML
    public Label lbUsername;
    
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
    
    @FXML
    private void showProductsUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/products.fxml"));
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    @FXML
    private void showUsersUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/users.fxml"));
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    @FXML
    private void showCompanyInfoUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/companyInfo.fxml"));
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    @FXML
    private void showSalesInvoicesUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/salesInvoices.fxml"));
            borderPane.setCenter(root);
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.setTitle("فواتير المبيعات");
//            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    @FXML
    private void showReturnDocumentUI(ActionEvent event)  {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/returnDocument.fxml"));
            borderPane.setCenter(root);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }
    
    @FXML
    void logout(ActionEvent ev) {
        try {
            
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد تسجيل الخروج؟");
            
            if(r.get() != ButtonType.OK) {
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/badrbillingsystem/ui/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("تسجيل الدخول");
            stage.show();
            Stage oldStage = (Stage) borderPane.getScene().getWindow();
            oldStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
