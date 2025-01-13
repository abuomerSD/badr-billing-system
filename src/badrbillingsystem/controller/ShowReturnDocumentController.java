
package badrbillingsystem.controller;

import badrbillingsystem.models.ReturnDocumentDetails;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.repos.returndocumentdetails.ReturnDocumentDetailsRepo;
import badrbillingsystem.repos.returndocumentheader.ReturnDocumentHeaderRepo;
import badrbillingsystem.utils.AlertMaker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShowReturnDocumentController implements Initializable{

    public ShowReturnDocumentController(long headerId) {
        this.headerId = headerId;
    }
    
    long headerId;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setData();
    }
    
    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtSalesInvoiceId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDetails;

    @FXML
    private TableView<ReturnDocumentDetails> tbReturnDocumentDetails;

    @FXML
    private TableColumn<ReturnDocumentDetails, String> colPName;

    @FXML
    private TableColumn<ReturnDocumentDetails, Double> colQty;

//    @FXML
//    private TableColumn<ReturnDocumentDetails, Double> colPrice;
//
//    @FXML
//    private TableColumn<ReturnDocumentDetails, Double> colTotal;
//
//    @FXML
//    private TableColumn<ReturnDocumentDetails, String> colDetails;

    @FXML
    private TextField txtTotal;

    private void setData() {
        try {
//            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colPName.setCellValueFactory(new PropertyValueFactory<>("productName"));
//            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            ReturnDocumentHeaderRepo headerRepo = new ReturnDocumentHeaderRepo();
            ReturnDocumentDetailsRepo detailsRepo = new ReturnDocumentDetailsRepo();
            
            ReturnDocumentHeader header = headerRepo.findById(headerId);
            ArrayList<ReturnDocumentDetails> detailsList = detailsRepo.findAllByHeaderId(headerId);
            
            txtCustomerName.setText(header.getCustomerName());
            txtDate.setText(header.getDate());
            txtDetails.setText(header.getDetails());
            txtId.setText(String.valueOf(header.getId()));
            txtSalesInvoiceId.setText(String.valueOf(header.getSalesInvoiceId()));
            System.out.println("doc total = "+header.getTotal());
            txtTotal.setText(String.valueOf(header.getTotal()));
            
            ObservableList<ReturnDocumentDetails> data = FXCollections.observableArrayList(detailsList);
            
            tbReturnDocumentDetails.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
