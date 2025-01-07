
package badrbillingsystem.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class ReturnDocumentController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    @FXML
    private Tab colProductName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<?> tbReturnDocumentDetails;

    @FXML
    private TableColumn<?, ?> colPName;

    @FXML
    private TableColumn<?, ?> colPQuantity;

    @FXML
    private TableColumn<?, ?> colPPrice;

    @FXML
    private TableColumn<?, ?> colPTotal;

    @FXML
    private TableColumn<?, ?> colPDetails;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtTax;

    @FXML
    private TextField txtTotal;

    @FXML
    private ComboBox<?> cbFilterTableByCustomerName;

    @FXML
    private TableView<?> tbReturnDocumentsList;

    @FXML
    private TableColumn<?, ?> colListDate;

    @FXML
    private TableColumn<?, ?> colListId;

    @FXML
    private TableColumn<?, ?> colListInvoiceId;

    @FXML
    private TableColumn<?, ?> colListCustomerName;

    @FXML
    private TableColumn<?, ?> colListDetails;

    @FXML
    private TableColumn<?, ?> colListTotal;

    @FXML
    void deleteReturnDocumentFromList(ActionEvent event) {

    }

    @FXML
    void newReturnDocument(ActionEvent event) {

    }

    @FXML
    void printReturnDocument(ActionEvent event) {

    }

    @FXML
    void printReturnDocumentFromList(ActionEvent event) {

    }

    @FXML
    void refreshListTable(ActionEvent event) {

    }

    @FXML
    void saveReturnDocument(ActionEvent event) {

    }
    
}
