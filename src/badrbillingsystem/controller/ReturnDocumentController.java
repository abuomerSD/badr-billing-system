
package badrbillingsystem.controller;

import badrbillingsystem.models.ReturnDocumentDetails;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class ReturnDocumentController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbReturnDocumentDetails.setPlaceholder(new Label("لا توجد بيانات في الجدول"));
        tbReturnDocumentsList.setPlaceholder(new Label("لا توجد بيانات في الجدول"));
    }
    
    @FXML
    TextField txtSalesInvoiceId;
    
    @FXML
    private Tab colProductName;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<ReturnDocumentDetails> tbReturnDocumentDetails;

    @FXML
    private TableColumn<ReturnDocumentDetails, String> colPName;

    @FXML
    private TableColumn<ReturnDocumentDetails, Double> colPQuantity;

    @FXML
    private TableColumn<ReturnDocumentDetails, Double> colPPrice;

    @FXML
    private TableColumn<ReturnDocumentDetails, Double> colPTotal;

    @FXML
    private TableColumn<ReturnDocumentDetails, String> colPDetails;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtTax;

    @FXML
    private TextField txtTotal;

    @FXML
    private ComboBox<String> cbFilterTableByCustomerName;

    @FXML
    private TableView<ReturnDocumentHeader> tbReturnDocumentsList;

    @FXML
    private TableColumn<ReturnDocumentHeader, String> colListDate;

    @FXML
    private TableColumn<ReturnDocumentHeader, Long> colListId;

    @FXML
    private TableColumn<ReturnDocumentHeader, Long> colListInvoiceId;

    @FXML
    private TableColumn<ReturnDocumentHeader, String> colListCustomerName;

    @FXML
    private TableColumn<ReturnDocumentHeader, String> colListDetails;

    @FXML
    private TableColumn<ReturnDocumentHeader, Double> colListTotal;

    @FXML
    void deleteReturnDocumentFromList(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void newReturnDocument(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void printReturnDocument(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void printReturnDocumentFromList(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void refreshListTable(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void saveReturnDocument(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }
    
}
