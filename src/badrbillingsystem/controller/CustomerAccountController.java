
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.CustomerAccount;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.customeraccount.CustomerAccountRepo;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class CustomerAccountController implements Initializable{

    public CustomerAccountController(Customer customer) {
        this.customer = customer;
    }
    
    Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtCustomerId.setText(String.valueOf(customer.getId()));
        txtCustomerName.setText(customer.getName());
        fillTable();
    }
    
    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private DatePicker dpFromDate;

    @FXML
    private DatePicker dpToDate;

    @FXML
    private TableView<CustomerAccount> tbCustomerAccount;

    @FXML
    private TableColumn<CustomerAccount, String> colDate;

    @FXML
    private TableColumn<CustomerAccount, Double> colIncoming;

    @FXML
    private TableColumn<CustomerAccount, Double> colOutgoing;

    @FXML
    private TableColumn<CustomerAccount, Double> colBalance;

    @FXML
    private TableColumn<CustomerAccount, String> colInfo;

    @FXML
    private Label txtTotal;
    
    CustomerRepo customerRepo = new CustomerRepo();
    CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();

    @FXML
    void resetTable(ActionEvent event) {
        fillTable();
    }
    
    void fillTable() {
        try {
            colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colIncoming.setCellValueFactory(new PropertyValueFactory<>("incoming"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
            colOutgoing.setCellValueFactory(new PropertyValueFactory<>("outgoing"));
            
            long customerId = Long.valueOf(txtCustomerId.getText());
            
            System.out.println("customer id: "+ customerId);
            
            ArrayList<CustomerAccount> list = customerAccountRepo.findByCutomerId(customerId);
            
            double totalBalance = 0;
            for (int i = 0 ; i< list.size(); i++) {
                if(i == list.size()-1) {
                    totalBalance = list.get(i).getTotalBalance();
                }
            }
            
            DecimalFormat df = new DecimalFormat("#,###,###.00");
            
            txtTotal.setText(df.format(totalBalance) + " " + "ريال سعودي");
            
            ObservableList<CustomerAccount> data = FXCollections.observableArrayList(list);
            System.out.println(data);
            tbCustomerAccount.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML 
    void filterTableByDate(ActionEvent event){
        try {
            
            DateFormatter dateFormatter = new DateFormatter();
            
            String fromDate = dateFormatter.format(dpFromDate.getValue());
//            String fromDate = String.valueOf(dpFromDate.getValue());
            String toDate = dateFormatter.format(dpToDate.getValue());
            
            if(fromDate.isEmpty() || toDate.isEmpty()) {
                AlertMaker.showErrorALert("اختر التواريخ اولا");
                return;
            }
            
            System.out.println("fromDate " + fromDate);
            System.out.println("toDate " + toDate);
            
            ArrayList<CustomerAccount> list = customerAccountRepo.findByDates(fromDate, toDate);
            ObservableList<CustomerAccount> data = FXCollections.observableArrayList(list);
            
            
            double total = 0;
            double totalIn = 0;
            double totalOut = 0;
            for (CustomerAccount customerAccount : data) {
                totalOut = customerAccount.getOutgoing();
                totalIn = customerAccount.getIncoming();
                total = total + (totalOut - totalIn);
            }
            
            txtTotal.setText(String.valueOf(total) + " ريال سعودي");
            
            colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colIncoming.setCellValueFactory(new PropertyValueFactory<>("incoming"));
            colInfo.setCellValueFactory(new PropertyValueFactory<>("info"));
            colOutgoing.setCellValueFactory(new PropertyValueFactory<>("outgoing"));
            
            
            tbCustomerAccount.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
