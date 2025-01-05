
package badrbillingsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class ProductMovementController implements Initializable {
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbProduct.setPlaceholder(new Label("لا توجد بيانات"));
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
    private TextField txtSearchCustomer;

    @FXML
    private TableView<?> tbProduct;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colIn;

    @FXML
    private TableColumn<?, ?> colOut;

    @FXML
    private TableColumn<?, ?> colDetails;

    @FXML
    private Label lbTotalOut;

    @FXML
    void printReport(ActionEvent event) {

    }

}
