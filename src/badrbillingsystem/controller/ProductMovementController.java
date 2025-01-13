
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.DateFormatter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

    public ProductMovementController(long proudctId) {
        this.productId = proudctId;
    }
    
    long productId;
   
    DateFormatter dateFormatter = new DateFormatter();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCustomerComboBox();
        ActionEvent e = new ActionEvent();
        updateMovementTableData(e);
        tbProduct.setPlaceholder(new Label("لا توجد بيانات"));
//        cbCustomerName.valueProperty().addListener(new ChangeListener<String>(){
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//               
//                String fromDate = "";
//                String toDate = "";
//                try{
//                   fromDate = dateFormatter.format(dpFromDate.getValue());
//                
//                   toDate = dateFormatter.format(dpToDate.getValue()); 
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//                
//                
//                String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
//                CustomerRepo customerRepo =  new CustomerRepo();
//                Customer customer = customerRepo.findByName(customerName);
//                
//                filterTable(fromDate, toDate, customer.getId());
////                AlertMaker.showMessageAlert(fromDate);
//            }
//            
//        });
    }    
    
    
    @FXML
    Label lbTotalIn;
    
    @FXML
    Label lbTotal;
    
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

//    @FXML
//    private TableColumn<ProductMovement, String> colDetails;
    
    @FXML
    private ComboBox<String> cbCustomerName;
    
    
    
    @FXML
    private TableColumn<ProductMovement, String> colInfo;
    

    @FXML
    private Label lbTotalOut;
    
    ProductMovementRepo repo = new ProductMovementRepo();
    DecimalFormat decimalFormat = new DecimalFormat("#,###,###.##");
    

    @FXML
    void printReport(ActionEvent event) {
        String fromDate = dpFromDate.getValue().toString();
        AlertMaker.showMessageAlert(fromDate);
    }

    @FXML
    private void updateMovementTableData(ActionEvent event) {
        try {
            cbCustomerName.getSelectionModel().clearSelection();
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colIn.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("movementInfo"));
            colOut.setCellValueFactory(new PropertyValueFactory<>("salesQuantity"));
//            long id = Long.valueOf(txtProductId.getText());
            ObservableList data = FXCollections.observableArrayList(repo.findAllById(productId));
            tbProduct.setItems(data);
            calculateTotals(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    private void filterTable(long productId, String fromDate, String toDate , long customerId) {
        try {
            
            ObservableList<ProductMovement> data;
            if(customerId == 0) {
                 data = FXCollections.observableArrayList(repo.findAllByDateRange(fromDate, toDate, productId));
            } else if(customerId > 0 && (fromDate.isEmpty() || toDate.isEmpty())) {
                 data = FXCollections.observableArrayList(repo.findAllByCustomerId(customerId, productId));
            } else {
                data = FXCollections.observableArrayList(repo.findAllByKeywords(productId ,fromDate, toDate, customerId));
            }
            
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colIn.setCellValueFactory(new PropertyValueFactory<>("returnQuantity"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("movementInfo"));
            colOut.setCellValueFactory(new PropertyValueFactory<>("salesQuantity"));
            
            tbProduct.setItems(data);
            
            // set totals labels
            
            calculateTotals(data);
            
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

    
    @FXML
    void searchButtonOnAction(ActionEvent event) {
        try {
            String fromDate = "";
            String toDate = "";
                try{
                   fromDate = dateFormatter.format(dpFromDate.getValue());
                
                   toDate = dateFormatter.format(dpToDate.getValue()); 
                } catch(Exception e) {
                    e.printStackTrace();
                }
                
                long productId = Long.valueOf(txtProductId.getText());
                
                String customerName = cbCustomerName.getSelectionModel().getSelectedItem();
                CustomerRepo customerRepo =  new CustomerRepo();
                Customer customer = customerRepo.findByName(customerName);
                filterTable(productId, fromDate, toDate, customer.getId());
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    
    void calculateTotals(ObservableList<ProductMovement> data){
        try {
            long totalIn = 0;
            long totalOut = 0;
            long total = 0;
                    
            for (ProductMovement movement : data) {
                totalIn += movement.getReturnQuantity();
                totalOut += movement.getSalesQuantity();
            }
            
            total = totalOut - totalIn;
            
            lbTotal.setText(decimalFormat.format(total));
            lbTotalIn.setText(decimalFormat.format(totalIn));
            lbTotalOut.setText(decimalFormat.format(totalOut));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
