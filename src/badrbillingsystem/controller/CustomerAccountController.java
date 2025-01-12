
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


public class CustomerAccountController implements Initializable{

    public CustomerAccountController(Customer customer) {
        this.customer = customer;
    }
    
    Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
