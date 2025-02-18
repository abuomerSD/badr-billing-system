
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.repos.customer.CustomerRepo;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CustomersController implements Initializable{
    
    @FXML
    private TableView<Customer> tbCustomer;

    @FXML
    private TableColumn<Customer, Integer> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPhone;
    
    @FXML
    TextField txtName;
    
    @FXML
    TextField txtPhone;
    @FXML
    TextField txtSearch;
    
    CustomerRepo repo = new CustomerRepo();
    
    ObservableList<Customer> data ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateCustomersTableData();
        tbCustomer.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>() {
            @Override
            public void changed(ObservableValue<? extends TablePosition> observable, TablePosition oldValue, TablePosition newValue) {
                try {
                    Customer c = tbCustomer.getSelectionModel().getSelectedItem();
                    
                    if(c == null) return;
                    txtName.setText(c.getName());
                    txtPhone.setText(c.getPhone());
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertMaker.showErrorALert(e.toString());
                }
            }
            
        });
        
        tbCustomer.setPlaceholder(new Label("لا توجد بيانات"));
    }
    
    
    
    

    @FXML
    void delete(ActionEvent event) {
        try {
            Customer customer = tbCustomer.getSelectionModel().getSelectedItem();
            if( customer == null) {
                AlertMaker.showErrorALert("اختر عميل");
                return;
            }
            Customer c = repo.findByName(customer.getName());
            
            Optional<ButtonType> result = AlertMaker.showConfirmationAlert("هل تريد حذف "+ customer.getName());
            if(result.get() == ButtonType.OK){
                repo.delete(c.getId());
                clearTextFields();
                updateCustomersTableData();
                NotificationMaker.showInformation("تم حذف " + customer.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void save(ActionEvent event) {
        
        
        try {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            
            if(name.isEmpty()){
                AlertMaker.showMessageAlert("ادخل اسم العميل");
                return;
            }
            
            if(phone.isEmpty()){
                AlertMaker.showMessageAlert("ادخل رقم الهاتف");
                return;
            }
            
            Customer c = new Customer();
            c.setName(name);
            c.setPhone(phone);
            
            repo.save(c);
            updateCustomersTableData();
            clearTextFields();
            NotificationMaker.showInformation("تم حفظ " + c.getName());
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void update(ActionEvent event) {
        try {
            Customer c = tbCustomer.getSelectionModel().getSelectedItem();
            if(c == null) {
                AlertMaker.showErrorALert("اختر عميل اولا");
                return;
            }
            
            Customer customer = repo.findByName(c.getName());
            
            String name = txtName.getText();
            String phone = txtPhone.getText();
            
            if(name.isEmpty()){
                AlertMaker.showErrorALert("ادخل الاسم");
                return;
            }
            
            if(phone.isEmpty()) {
                AlertMaker.showErrorALert("ادخل رقم الهاتف");
                return;
            }
            
            customer.setName(name);
            customer.setPhone(phone);
            
            Optional<ButtonType> result = AlertMaker.showConfirmationAlert("هل تريد تعديل العميل " + c.getName());
            if(result.get() == ButtonType.OK) {
                repo.update(customer);
                clearTextFields();
                updateCustomersTableData();
                NotificationMaker.showInformation("تم تعديل " + customer.getName());
            }
            customer.setName(name);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void updateCustomersTableData() {
        try {
            ObservableList<Customer> list = FXCollections.observableArrayList(repo.findAll()); 
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
            colPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            tbCustomer.setItems(list);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void clearTextFields(){
        txtName.clear();
        txtPhone.clear();
}
    
    
    @FXML
    private void filterTableByKeywords(KeyEvent event){
        try {
            String name = txtSearch.getText();
            
            ObservableList<Customer> list = FXCollections.observableArrayList(repo.findBySearchWords(name));
//            data.clear();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
            colPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            tbCustomer.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    
    @FXML 
    private void showCutomerAccount(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/badrbillingsystem/ui/customerAccount.fxml"));
            Customer customer = tbCustomer.getSelectionModel().getSelectedItem();
            
            if(customer == null){
                AlertMaker.showErrorALert("اختر العميل اولا");
                return;
            }
            
            CustomerAccountController controller = new CustomerAccountController(customer);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/badrbillingsystem/resources/style/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("كشف حساب العميل");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
