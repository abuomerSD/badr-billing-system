
package badrbillingsystem.controller;

import badrbillingsystem.models.User;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.NotificationMaker;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class UsersController  implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillUsersTable();
        tbUsers.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>(){
            @Override
            public void changed(ObservableValue<? extends TablePosition> observable, TablePosition oldValue, TablePosition newValue) {
                try{
                    User user = tbUsers.getSelectionModel().getSelectedItem();
                    if(user == null)
                        return;
                    txtUsername.setText(user.getUsername());
                    txtPassword.setText(user.getPassword());
                } catch(Exception e){
                    e.printStackTrace();
                    AlertMaker.showErrorALert(e.toString());
                }
            }
            
        });
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
        try {
            User u = tbUsers.getSelectionModel().getSelectedItem();
            if(u == null) {
                AlertMaker.showErrorALert("اختر مستخدم اولا");
                return;
            }
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد حذف المستخدم؟");
            if(r.get() != ButtonType.OK)
                return;
            if(repo.delete(u.getId())){
                fillUsersTable();
                clearTextFields();
                NotificationMaker.showInformation("تم حذف المستخدم بنجاح");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
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
            
            if(password.length() < 4) {
                AlertMaker.showErrorALert("ادخل كلمة مرور اكبر من ٣ حروف او ارقام");
                return;
            }
            
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
            
            User u = tbUsers.getSelectionModel().getSelectedItem();
            
            if(u == null) {
                AlertMaker.showErrorALert("اختر مستخدم اولا");
                return;
            }
            u.setUsername(username);
            u.setPassword(password);
            
            Optional<ButtonType> result = AlertMaker.showConfirmationAlert("هل تريد تعديل المستخدم؟");
            
            if(result.get() != ButtonType.OK)
                return;
            
            if(password.length() < 4) {
                AlertMaker.showErrorALert("ادخل كلمة مرور اكبر من ٣ حروف او ارقام");
                return;
            }
            
            if(repo.update(u)) {
                clearTextFields();
                fillUsersTable();
                NotificationMaker.showInformation("تم تعديل المستخدم بنجاح");
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
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
    
    @FXML
    private void filterTable(KeyEvent ev) {
        try {
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            
            String searchWords = txtSearchUsername.getText();
            ObservableList<User> data = FXCollections.observableArrayList(repo.findBySearchWords(searchWords));
            
            tbUsers.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
