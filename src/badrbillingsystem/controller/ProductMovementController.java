
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ProductMovementController implements Initializable {
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCustomerComboBox();
        updateMovementTableData();
        tbProduct.setPlaceholder(new Label("لا توجد بيانات"));
        cbCustomerName.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String fromDate = dpFromDate.getValue().toString();
                if(fromDate == null)
                    fromDate = "";
                String toDate = dpToDate.getValue().toString();
                if(toDate == null)
                    toDate = "";
                String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
                CustomerRepo customerRepo =  new CustomerRepo();
                Customer customer = customerRepo.findByName(customerName);
                
                filterTable(fromDate, toDate, customer.getId());
        AlertMaker.showMessageAlert(fromDate);
            }
            
        });
    }    
    
    
    @FXML
    public TextField txtProductId;

    @FXML
    public TextField txtProductName;

    @FXML
    private DatePicker dpFromDate;

    @FXML
    private DatePicker dpToDate;   

    @FXML
    private TableView<ProductMovement> tbProduct;

    @FXML
    private TableColumn<ProductMovement, String> colDate;

    @FXML
    private TableColumn<ProductMovement, Double> colIn;

    @FXML
    private TableColumn<ProductMovement, Double> colOut;

    @FXML
    private TableColumn<ProductMovement, String> colDetails;
    
    @FXML
    private ComboBox<String> cbCustomerName;
    
    
    
    @FXML
    private TableColumn<ProductMovement, String> colInfo;
    

    @FXML
    private Label lbTotalOut;
    
    ProductMovementRepo repo = new ProductMovementRepo();
    
    ObservableList data = FXCollections.observableArrayList(repo.findAllById(1));

    @FXML
    void printReport(ActionEvent event) {
        String fromDate = dpFromDate.getValue().toString();
        AlertMaker.showMessageAlert(fromDate);
    }

    private void updateMovementTableData() {
        try {
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colIn.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("movementInfo"));
            colOut.setCellValueFactory(new PropertyValueFactory<>("salesQuantity"));
            
            tbProduct.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    private void filterTable(String fromDate, String toDate , long customerId) {
        try {
            
            ObservableList<ProductMovement> data = FXCollections.observableArrayList(repo.findAllByKeywords(fromDate, toDate, customerId));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colIn.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("movementInfo"));
            colOut.setCellValueFactory(new PropertyValueFactory<>("salesQuantity"));
            
            tbProduct.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillCustomerComboBox() {
        try {
            CustomerRepo repo = new CustomerRepo();
            ArrayList<Customer> l = repo.findAll();
            ObservableList<String> list = FXCollections.observableArrayList();
            for(Customer c : l) {
                list.add(c.getName());
            }
            cbCustomerName.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

}
