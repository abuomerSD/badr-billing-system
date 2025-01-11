
package badrbillingsystem.controller;

import badrbillingsystem.models.User;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }
    
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    void exit(ActionEvent event) {

    }

    @FXML
    void login(ActionEvent event) {
        try {
            
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            
            if(username.isEmpty()) {
                AlertMaker.showErrorALert("ادخل اسم المستخدم");
                return;
            }
            
            if(password.isEmpty()){
                AlertMaker.showErrorALert("ادخل كلمة المرور");
                return;
            }
            
            UserRepo userRepo = new UserRepo();
            
            ArrayList<User> usersList = userRepo.findAll();
            
//            HashMap<String, String> usersHashmap = new HashMap<>();
//            
//            for (User user : usersList) {
//                usersHashmap.put(user.getUsername(), user.getPassword());
//            }
            
            for (User user : usersList) {
                if(user.getUsername() == username && user.getPassword() == password){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/badrbillingsystem/ui/login.fxml"));
                    HomeController controller = loader.getController();
                }
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
