
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.CustomerAccount;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.models.ReturnDocumentDetails;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.customeraccount.CustomerAccountRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.repos.returndocumentdetails.ReturnDocumentDetailsRepo;
import badrbillingsystem.repos.returndocumentheader.ReturnDocumentHeaderRepo;
import badrbillingsystem.repos.salesinvoicedetails.SalesInvoiceDetailsRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.DateFormatter;
import badrbillingsystem.utils.NotificationMaker;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
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
        
        cbFilterTableByCustomerName.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try{
                    String customerName = cbFilterTableByCustomerName.getSelectionModel().getSelectedItem();
                    Customer c = customerRepo.findByName(customerName);
                    ArrayList<ReturnDocumentHeader> list = returnDocumentHeaderRepo.findByCustomerId(c.getId());
                    ObservableList<ReturnDocumentHeader> data = FXCollections.observableArrayList(list);
                    
                    tbReturnDocumentsList.setItems(data);
                } catch(Exception e) {
                    e.printStackTrace();
                    AlertMaker.showErrorALert(e.toString());
                }
            }
            
        });
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
    private TextField txtDetails;

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
    private TextField txtFilterTableById;
    
    DateFormatter dateFormatter = new DateFormatter();
    CustomerRepo customerRepo = new CustomerRepo();
    ReturnDocumentHeaderRepo returnDocumentHeaderRepo = new ReturnDocumentHeaderRepo();
    ReturnDocumentDetailsRepo returnDocumentDetailsRepo = new ReturnDocumentDetailsRepo();
    SalesInvoiceHeaderRepo salesInvoiceHeaderRepo = new SalesInvoiceHeaderRepo();
    SalesInvoiceDetailsRepo salesInvoiceDetailsRepo = new SalesInvoiceDetailsRepo();
    DecimalFormat df = new DecimalFormat("###.##");
    ProductRepo productRepo = new ProductRepo();
    CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();
    ProductMovementRepo productMovementRepo = new ProductMovementRepo();
            
            
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
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد فتح مستند مردود جديد؟");
            if(r.get() != ButtonType.OK){
                return;
            }
            resetDocument();
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
            ArrayList<ReturnDocumentHeader> list = returnDocumentHeaderRepo.findAll();
            ObservableList<ReturnDocumentHeader> data = FXCollections.observableArrayList(list);
            fillReturnDocumentHeaderTable(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void saveReturnDocument(ActionEvent event) {
        try {
            // saving return document header 
            
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد حفظ المردود؟");
            
            if(r.get() != ButtonType.OK) return;
            
            ReturnDocumentHeader header = new ReturnDocumentHeader();
            long salesInvoiceId = Long.valueOf(txtSalesInvoiceId.getText());
            SalesInvoiceHeader salesInvoiceHeader = salesInvoiceHeaderRepo.findById(salesInvoiceId);
//            Customer customer = customerRepo.findById(salesInvoiceHeader.getCustomerId());
            long customerId = salesInvoiceHeader.getCustomerId();
            header.setCustomerId(customerId);
            String date = dateFormatter.format(dpDate.getValue());
            header.setDate(date);
            String details = txtDetails.getText();
            header.setDetails(details);
            header.setSalesInvoiceId(salesInvoiceId);
            double total = Double.valueOf(txtTotal.getText());
            header.setTotal(total);
            
            long headerId  = returnDocumentHeaderRepo.save(header);
            
            String info = "مردود مبيعات رقم " + headerId;
            
            // saving return document details
            ObservableList<SalesInvoiceDetails> salesInvoiceDetails = tbReturnDocumentDetails.getItems();
            ObservableList<ReturnDocumentDetails> returnDocumentDetails = FXCollections.observableArrayList();
            
            for (SalesInvoiceDetails d : salesInvoiceDetails) {
                ReturnDocumentDetails detail = new ReturnDocumentDetails();
                detail.setDetails(d.getDetails());
                detail.setHeaderId(headerId);
                detail.setProductId(d.getProductId());
                detail.setQuantity(d.getQuantity());
                returnDocumentDetails.add(detail);
                returnDocumentDetailsRepo.save(detail);
                
                // adding product movement
                ProductMovement movement = new ProductMovement();
                movement.setCustomerId(customerId);
                movement.setDate(date);
                movement.setDetails(d.getDetails());
                movement.setMovementInfo(info);
                movement.setProductId(d.getProductId());
                movement.setReturnInvoiceId(headerId);
                movement.setReturnQuantity(d.getQuantity());
                movement.setSalesInvoiceId(0);
                movement.setSalesQuantity(0);
                
                productMovementRepo.save(movement);
            }
            
            // adding to customer account 
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setCustomerId(customerId);
            customerAccount.setDate(date);
            customerAccount.setIncoming(total);
            customerAccount.setInfo(info);
            customerAccount.setOutgoing(0);
            customerAccount.setReturnDocumentId(headerId);
            customerAccount.setSalesInvoiceId(0);
            
            customerAccountRepo.save(customerAccount);
            
            resetDocument();
            
            ArrayList<ReturnDocumentHeader> documents = returnDocumentHeaderRepo.findAll();
            ObservableList<ReturnDocumentHeader> documentsOL = FXCollections.observableArrayList(documents);
            fillReturnDocumentHeaderTable(documentsOL);
            NotificationMaker.showInformation("تم إضافة مستند مردود المبيعات بنجاح");
            
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
            ArrayList<ReturnDocumentHeader> returnInvoicesList = returnDocumentHeaderRepo.findAll();
            for (ReturnDocumentHeader r : returnInvoicesList) {
                if(r.getSalesInvoiceId() == id) {
                    AlertMaker.showErrorALert("هنالك مردود مبيعات اخر لهذه الفاتورة");
                    System.out.println("هنالك مردود اخر لهذه الفاتورة");
                    return;
                }
            }
            System.out.println("id = "+ id);
            SalesInvoiceHeader header = salesInvoiceHeaderRepo.findById(id);
            ArrayList<SalesInvoiceDetails> details = salesInvoiceDetailsRepo.findAllByHeaderId(id);
            ObservableList<SalesInvoiceDetails> data = FXCollections.observableArrayList(details);
            System.out.println(details);
            if(details.size() > 0) {
                fillReturnDocumentDetailsTable(data);
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

    private void fillReturnDocumentDetailsTable(ObservableList<SalesInvoiceDetails> data) {
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
                    SalesInvoiceHeader header = salesInvoiceHeaderRepo.findById(Long.valueOf(txtSalesInvoiceId.getText()));
                    ArrayList<SalesInvoiceDetails> details = salesInvoiceDetailsRepo.findAllByHeaderId(header.getId());
                    SalesInvoiceDetails oldDetail = new SalesInvoiceDetails();
                    for (SalesInvoiceDetails detail : details) {
                        if(detail.getProductId()== d.getProductId()){
                            oldDetail.setQuantity(detail.getQuantity()); 
                        }
                    }

                    double newValue = t.getNewValue();
                    if(newValue <= 0) {
                        AlertMaker.showErrorALert("ادخل عدد اكبر من صفر ");
                        d.setQuantity(oldDetail.getQuantity());
                        tbReturnDocumentDetails.refresh();
                        return;
                    }
                    
                    System.out.println(oldDetail.getQuantity());
                    System.out.println("details "+ details);
                    if(newValue > oldDetail.getQuantity()){
//                        d.setQuantity(d.getQuantity());
                        tbReturnDocumentDetails.refresh();
                        return;
                    }
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
            
            double oldTotalWithoutTaxAndDiscount = (oldTotal - oldTax) + oldDiscount;
            
            double discountPercentage = (oldDiscount / oldTotalWithoutTaxAndDiscount) ;
            double taxPercentage = (oldTax / oldTotalWithoutTaxAndDiscount) ;
            System.out.println("discountPercentage "+ discountPercentage);
            System.out.println("taxPercentage " + taxPercentage);
            double newTotal = 0;
            
            ObservableList<SalesInvoiceDetails> details = tbReturnDocumentDetails.getItems();
            
            for (SalesInvoiceDetails d : details) {
                newTotal = newTotal + d.getTotal();
            }
            
            double newDiscount = newTotal * discountPercentage;
            double newTax = newTotal * taxPercentage;
            double totalReturn = (newTotal + newTax) - newDiscount;
            txtDiscount.setText(df.format(newDiscount));
            txtTax.setText(df.format(newTax));
            txtTotal.setText(df.format(totalReturn));
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    
    private void resetDocument() {
        try {
            dpDate.setValue(LocalDate.now());
            txtSalesInvoiceId.clear();
            txtDetails.clear();
            tbReturnDocumentDetails.getItems().clear();
            txtDiscount.setText("0.0");
            txtTax.setText("0.0");
            txtTotal.setText("0.0");
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    void deleteItemFromTable() {
        try {
            SalesInvoiceDetails detail = tbReturnDocumentDetails.getSelectionModel().getSelectedItem();
            
            if(detail == null) {
                return;
            }
            tbReturnDocumentDetails.getItems().remove(detail);
            calculateTotals();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    void filterTableById(KeyEvent event) {
        try {
            String idStr = txtFilterTableById.getText();
            
            if(idStr.isEmpty()){
                ObservableList<ReturnDocumentHeader> data = FXCollections.observableArrayList(returnDocumentHeaderRepo.findAll());
                fillReturnDocumentHeaderTable(data);
                return;
            }
            
            
            colListCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            colListDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colListDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colListId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colListInvoiceId.setCellValueFactory(new PropertyValueFactory<>("salesInvoiceId"));
            colListTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            long id = Long.valueOf(idStr);
            ObservableList<ReturnDocumentHeader> data = FXCollections.observableArrayList(returnDocumentHeaderRepo.findAllById(id));
            
            tbReturnDocumentsList.setItems(data);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
