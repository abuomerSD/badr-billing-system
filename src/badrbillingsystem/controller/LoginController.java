
package badrbillingsystem.controller;

import badrbillingsystem.models.User;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            
            System.out.println(usersList);
            for (User user : usersList) {
                if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/badrbillingsystem/ui/home.fxml"));
//                    Parent root = loader.load();
                    HomeController homeController = new HomeController(username);
//                    HomeController controller = loader.getController();
                    loader.setController(homeController);
                    Parent root = loader.load();
//                    controller.lbUsername.setText(username);
//                    controller.user = new User(0, username, password);
                    
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("الواجهة الرئيسية");
                    stage.show();
                    Stage loginStage = (Stage) txtUsername.getScene().getWindow();
                    loginStage.close();
                } else {
                    AlertMaker.showErrorALert("خطأ في إسم المستخدم أو كلمة المرور");
                }
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
