
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.repos.customer.CustomerRepo;
import com.badrbillingsystem.utils.AlertMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

public class CustomersController {
    
    @FXML
    private TableView<Customer> tbCustomer;

    @FXML
    private TableColumn<Object, Long> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPhone;
    
    @FXML
    TextField txtName;
    
    @FXML
    TextField txtPhone;
    
    CustomerRepo repo = new CustomerRepo();
    
    ObservableList<Customer> data = FXCollections.observableArrayList();

    @FXML
    void delete(ActionEvent event) {

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
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    @FXML
    void update(ActionEvent event) {

    }

    private void updateCustomersTableData() {
        try {
            ObservableList<Customer> list = (ObservableList<Customer>) repo.findAll();
            colId.setCellValueFactory(new PropertyValueFactory<>(""));
            colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
            colPhone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
            tbCustomer.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
