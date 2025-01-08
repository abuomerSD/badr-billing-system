
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.ReturnDocumentDetails;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.returndocumentdetails.ReturnDocumentDetailsRepo;
import badrbillingsystem.repos.returndocumentheader.ReturnDocumentHeaderRepo;
import badrbillingsystem.repos.salesinvoicedetails.SalesInvoiceDetailsRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.DateFormatter;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DoubleStringConverter;


public class ReturnDocumentController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbReturnDocumentDetails.setPlaceholder(new Label("لا توجد بيانات في الجدول"));
        tbReturnDocumentsList.setPlaceholder(new Label("لا توجد بيانات في الجدول"));
        setDefaultDate();
        fillCustmerNameCB();
        
        ArrayList<ReturnDocumentHeader> list = returnDocumentHeaderRepo.findAll();
        ObservableList<ReturnDocumentHeader> data = FXCollections.observableArrayList(list);
        fillReturnDocumentHeaderTable(data);
    }
    
    @FXML
    TextField txtSalesInvoiceId;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableView<SalesInvoiceDetails> tbReturnDocumentDetails;

    @FXML
    private TableColumn<SalesInvoiceDetails, String> colPName;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colPQuantity;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colPPrice;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colPTotal;

    @FXML
    private TableColumn<SalesInvoiceDetails, String> colPDetails;

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
    
    DateFormatter dateFormatter = new DateFormatter();
    CustomerRepo customerRepo = new CustomerRepo();
    ReturnDocumentHeaderRepo returnDocumentHeaderRepo = new ReturnDocumentHeaderRepo();
    ReturnDocumentDetailsRepo returnDocumentDetailsRepo = new ReturnDocumentDetailsRepo();
    SalesInvoiceHeaderRepo salesInvoiceHeaderRepo = new SalesInvoiceHeaderRepo();
    SalesInvoiceDetailsRepo salesInvoiceDetailsRepo = new SalesInvoiceDetailsRepo();
    DecimalFormat df = new DecimalFormat("###.##");
    

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
    
    @FXML
    void getSalesInvoice(){
        try {
            String idStr = txtSalesInvoiceId.getText();
            long id = Long.valueOf(idStr);
            System.out.println("id = "+ id);
            SalesInvoiceHeader header = salesInvoiceHeaderRepo.findById(id);
            ArrayList<SalesInvoiceDetails> details = salesInvoiceDetailsRepo.findAllByHeaderId(id);
            ObservableList<SalesInvoiceDetails> data = FXCollections.observableArrayList(details);
            System.out.println(details);
            if(details.size() > 0) {
                fillReturnDocumentTable(data);
            } else {
                tbReturnDocumentDetails.getItems().clear();
            }
            
            // set the total, discount and tax
            
            txtDiscount.setText(String.valueOf(header.getDiscount()));
            txtTax.setText(String.valueOf(header.getTax()));
            txtTotal.setText(String.valueOf(header.getTotal()));
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
            tbReturnDocumentDetails.getItems().clear();
        }
    }

    private void setDefaultDate() {
        try {
            dpDate.setValue(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillCustmerNameCB() {
        try {
            ArrayList<Customer> customers = customerRepo.findAll();
            ObservableList<String> data = FXCollections.observableArrayList();
            for (Customer customer : customers) {
                data.add(customer.getName());
            }
            cbFilterTableByCustomerName.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillReturnDocumentHeaderTable(ObservableList<ReturnDocumentHeader> data) {
        try {  
            colListCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            colListDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colListDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colListId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colListInvoiceId.setCellValueFactory(new PropertyValueFactory<>("salesInvoiceId"));
            colListTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            tbReturnDocumentsList.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillReturnDocumentTable(ObservableList<SalesInvoiceDetails> data) {
        try {
            colPDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colPName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            colPPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colPQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colPTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            colPQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colPQuantity.setOnEditCommit(new EventHandler<CellEditEvent<SalesInvoiceDetails, Double>>() {
                @Override
                public void handle(CellEditEvent<SalesInvoiceDetails, Double> t) {
                    SalesInvoiceDetails d = t.getRowValue();
                    d.setQuantity(t.getNewValue());
                    double total = d.getQuantity() * d.getPrice();
                    d.setTotal(total);
                    tbReturnDocumentDetails.refresh();
                    calculateTotals();
                }

                
        
        });
            
            tbReturnDocumentDetails.setItems(data);
            calculateTotals();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    private void calculateTotals() {
        try {
            long salesInvoiceId = Long.valueOf(txtSalesInvoiceId.getText());
            SalesInvoiceHeader header = salesInvoiceHeaderRepo.findById(salesInvoiceId);
            double oldTax = header.getTax();
            double oldTotal = header.getTotal();
            double oldDiscount = header.getDiscount();
            
            double discountPercentage = (oldDiscount / oldTotal) ;
            double taxPercentage = (oldTax / oldTotal) ;
            System.out.println("discountPercentage "+ discountPercentage);
            System.out.println("taxPercentage " + taxPercentage);
            double newTotal = 0;
            
            ObservableList<SalesInvoiceDetails> details = tbReturnDocumentDetails.getItems();
            
            for (SalesInvoiceDetails d : details) {
                newTotal = newTotal + d.getTotal();
            }
            
            double newDiscount = newTotal * discountPercentage;
            double newTax = newTotal * taxPercentage;
            
            txtDiscount.setText(df.format(newDiscount));
            txtTax.setText(df.format(newTax));
            txtTotal.setText(df.format(newTotal));
            
        } catch (Exception e) {
        }
    }
    
}
