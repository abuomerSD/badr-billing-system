
package badrbillingsystem.controller;

import badrbillingsystem.models.User;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.NotificationMaker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersController  implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillUsersTable();
    }
    
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtSearchUsername;

    @FXML
    private TableView<User> tbUsers;
    
    @FXML
    private TableColumn<User, Long> colId;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colPassword;
    
    UserRepo repo = new UserRepo();

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {
        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            
            if(username.isEmpty()) {
                AlertMaker.showErrorALert("ادخل اسم المستخدم");
                return;
            }
            
            if(password.isEmpty()){
                AlertMaker.showErrorALert("ادخل كلمة السر");
                return;
            }
            
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            
            if(repo.save(u)) {
                clearTextFields();
                fillUsersTable();
                NotificationMaker.showInformation("تم إضافة المستخدم بنجاح");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void update(ActionEvent event) {

    }

    private void fillUsersTable() {
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            
            ObservableList<User> data = FXCollections.observableArrayList(repo.findAll());
            
            tbUsers.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void clearTextFields() {
        txtUsername.clear();
        txtPassword.clear();
    }
    
}
