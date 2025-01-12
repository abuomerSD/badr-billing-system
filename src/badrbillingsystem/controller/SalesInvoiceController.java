
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.CustomerAccount;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.reports.SalesInvoiceReport;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.customeraccount.CustomerAccountRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.repos.salesinvoicedetails.SalesInvoiceDetailsRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.Constants;
import badrbillingsystem.utils.DateFormatter;
import badrbillingsystem.utils.NotificationMaker;
import badrbillingsystem.utils.OSDetector;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;


public class SalesInvoiceController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductsGrid();
        tbInvocieDetails.setPlaceholder(new Label("لا توجد بيانات"));
        setInvoiceDate();
        fillCustomersComboBox();
        fillInvoicesListTable();
        fillFilterInvoicesListCustomerCB();
        btnPrintInvoice.setDisable(true);
        lbInvoiceId.setText("0");
        txtTaxInPercentage.setText("15");
        
        cbCustomerName.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Customer c = customerRepo.findByName(cbCustomerName.getSelectionModel().getSelectedItem());
                txtCutomerPhone.setText(c.getPhone());
            }        
        });
        
        cbFilterListByCutomer.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterInvoicesListTable();
            }
            
        });
    }
    
    @FXML 
    private Label lbInvoiceId;
    
    @FXML
    private Button btnSaveInvoice;
    
    @FXML
    private Button btnPrintInvoice;
    
    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<String> cbCustomerName;
    
    @FXML
    private ComboBox<String> cbFilterListByCutomer;

    @FXML
    private TextField txtCutomerPhone;

    @FXML
    private TextField txtSearchCustomer;

    @FXML
    private GridPane gridPane;

    @FXML
    private TableView<SalesInvoiceDetails> tbInvocieDetails;

    @FXML
    private TableColumn<SalesInvoiceDetails, String> colProductName;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colQuantity;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colPrice;

    @FXML
    private TableColumn<SalesInvoiceDetails, Double> colTotal;

    @FXML
    private TableColumn<SalesInvoiceDetails, String> colDetails;

    @FXML
    private TextField txtSubTotal;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtTaxInPercentage;

    @FXML
    private Label txtTaxInNumber;

    @FXML
    private TextField txtTotal;
    
    @FXML
    TableView<SalesInvoiceHeader> tbInvoicesList;
    
    @FXML
    TableColumn<SalesInvoiceHeader, String> colListDate;
    
    @FXML
    TableColumn<SalesInvoiceHeader, Long> colListId;
    
    @FXML
    TableColumn<SalesInvoiceHeader, String> colListCustmerName;
    
    @FXML
    TableColumn<SalesInvoiceHeader, Double> colListDiscount;
    
    @FXML
    TableColumn<SalesInvoiceHeader, Double> colListTax;
    
    @FXML
    TableColumn<SalesInvoiceHeader, Double> colListTotal;
    
    
    ObservableList<SalesInvoiceDetails> data = FXCollections.observableArrayList();
    DecimalFormat df = new DecimalFormat("###.##");
    CustomerRepo customerRepo = new CustomerRepo();
    DateFormatter dateFormatter = new DateFormatter();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
    SalesInvoiceHeaderRepo headerRepo = new SalesInvoiceHeaderRepo();
    ProductMovementRepo productMovementRepo = new ProductMovementRepo();
    SalesInvoiceDetailsRepo detailsRepo = new SalesInvoiceDetailsRepo();
    CustomerAccountRepo customerAccountRepo = new CustomerAccountRepo();
    

    @FXML
    void newInvoice(ActionEvent event) {
        try {
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد فتح فاتورة جديدة؟");
            
            if(r.get() != ButtonType.OK)
                return;
            
            resetInvoice();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    
    @FXML
    void listInvoicePrint(){
        try {
            SalesInvoiceHeader header = tbInvoicesList.getSelectionModel().getSelectedItem();
            
            SalesInvoiceDetailsRepo detailsRepo = new SalesInvoiceDetailsRepo();
            Customer c = customerRepo.findByName(header.getCutomerName());
            
            
            if(header == null) {
                return;
            }
            
            long id = header.getId();
            ArrayList<SalesInvoiceDetails> details = detailsRepo.findAllByHeaderId(id);
            
                    
            String invoicePath = Constants.SALES_INVOICE_SUFFIX + id + Constants.SALES_INVOICE_EXTENTION;
            File invoiceFile = new File(invoicePath);
            
            if(! invoiceFile.isFile()){
                System.out.println("invoice not found, "+ id+" creating another invoice");
                SalesInvoiceReport report = new SalesInvoiceReport();
                report.saveSalesInvoiceAsPdf(id, header, details, c);
                showInvoiceFile(invoiceFile);
            } else {
                showInvoiceFile(invoiceFile);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    void listInvoiceDelete() { 
        try {
            SalesInvoiceHeader header = tbInvoicesList.getSelectionModel().getSelectedItem();
            long headerId = header.getId();
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد حذف الفاتورة" +" " + headerId + " ؟");
            if (r.get() != ButtonType.OK) {
                return;
            }
            headerRepo.delete(headerId);
            
            ArrayList<SalesInvoiceDetails> details = detailsRepo.findAllByHeaderId(headerId);
            
            for(SalesInvoiceDetails d : details) {
                detailsRepo.delete(d.getProductId(), headerId);
                productMovementRepo.deleteBySalesInvoiceId(d.getProductId(), headerId);
            }
            
            // delete customer account
            customerAccountRepo.deleteBySalesInvoiceId(headerId, header.getCustomerId());
            NotificationMaker.showInformation("تم حذف الفاتورة بنجاح");
            fillInvoicesListTable();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void printInvoice(ActionEvent event) {
        try {
            long headerId = Long.valueOf(lbInvoiceId.getText());
            
            SalesInvoiceHeaderRepo headerRepo = new SalesInvoiceHeaderRepo();
            SalesInvoiceDetailsRepo detailsRepo = new SalesInvoiceDetailsRepo();
            
            SalesInvoiceHeader header = headerRepo.findById(headerId);
            ArrayList<SalesInvoiceDetails> details = detailsRepo.findAllByHeaderId(headerId);
            
            String customerName = header.getCutomerName();
            Customer c = customerRepo.findByName(customerName);
            
            SalesInvoiceReport report = new SalesInvoiceReport();
            String invoiceName = report.saveSalesInvoiceAsPdf(headerId, header, details, c);
            
            showInvoiceFile(new File(invoiceName));
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void saveInvoice(ActionEvent event) {
        try {
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد حفظ الفاتورة؟");
            if(r.get() != ButtonType.OK)
                return;
            String customerName = cbCustomerName.getValue();
            if(customerName == null){
                AlertMaker.showErrorALert("اختر عميل اولا");
                return;
            }
            
            for (SalesInvoiceDetails d : data) {
                if (d.getQuantity() == 0) {
                    AlertMaker.showErrorALert("اضف الكمية لجميع العناصر في الفاتورة");
                    return;
                }
            }
            Customer customer = customerRepo.findByName(customerName);
            long customerId = customer.getId();
            
            LocalDate date = dpDate.getValue();
            double discount = Double.valueOf(txtDiscount.getText());
            double tax = Double.valueOf(txtTaxInNumber.getText());
            double total = Double.valueOf(txtTotal.getText());
            
            
            SalesInvoiceHeader header = new SalesInvoiceHeader();
            header.setCustomerId(customerId);
            header.setDate(dateFormatter.format(date));
            header.setDiscount(discount);
            header.setTax(tax);
            header.setTotal(total);
            header.setUserId(0);
            long headerId = headerRepo.save(header);
            String info = "فاتورة مبيعات رقم " + headerId;
            
            DateFormatter dateFormatter = new DateFormatter();
            for (SalesInvoiceDetails d : data) {
                ProductMovement movement = new ProductMovement();
                movement.setCustomerId(customerId);
                movement.setDate(dateFormatter.format(date));
                movement.setDetails(d.getDetails());
                movement.setMovementInfo(info);
                movement.setProductId(d.getProductId());
                movement.setReturnInvoiceId(0);
                movement.setReturnQuantity(0);
                movement.setSalesInvoiceId(headerId);
                movement.setSalesQuantity(d.getQuantity());
                
                productMovementRepo.save(movement);
                
                d.setHeaderId(headerId);
                detailsRepo.save(d);
                
            }
            
            // add invoice to customer account
            CustomerAccount account = new CustomerAccount();
            account.setCustomerId(customerId);
            account.setDate(dateFormatter.format(date));
            account.setIncoming(0);
            account.setInfo(info);
            account.setOutgoing(total);
            account.setReturnDocumentId(0);
            account.setSalesInvoiceId(headerId);
            
            customerAccountRepo.save(account);
            
            
            
            resetInvoice();
            NotificationMaker.showInformation("تم حفظ الفاتورة بالرقم " + headerId);
            fillInvoicesListTable();
            lbInvoiceId.setText(String.valueOf(headerId));
            btnSaveInvoice.setDisable(true);
            btnPrintInvoice.setDisable(false);
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    ProductRepo productRepo = new ProductRepo();
    private void fillProductsGrid() {
        try {
            
            ArrayList<Product> products = productRepo.findAll();
            
            int column = 0;
            int row = 0;
            
            for(Product p : products) {
                VBox box = new VBox();
                Label name = new Label(p.getName());
                name.setStyle("-fx-font-size: 16;");
                InputStream in = new FileInputStream(new File(p.getImage()));
                Image imageFile = new Image(in);
                ImageView image = new ImageView(imageFile);
                image.setFitHeight(80);
                image.setFitWidth(140);
                
                Label price = new Label(String.valueOf(p.getPrice()));
                price.setStyle("-fx-font-weight: bold;");
                box.getChildren().add(name);
                box.getChildren().add(image);
                box.getChildren().add(price);
                
                box.setAlignment(Pos.CENTER);
                box.setPadding(new Insets(5, 5, 5, 5));
                
                
                Button addButton = new Button("إضافة");
                box.getChildren().add(addButton);            
                
                gridPane.add(box, column, row);
                
//                System.out.println("row = "+ row + " column = "+column);
                
                column++;
                if(column == 2) {
                    row++;
                    column = 0;
                }
                
                addButton.setStyle("-fx-font-family: 'almarai';");
                
                
                // action listener
                
                addButton.setOnAction(e -> {
                    SalesInvoiceDetails details = new SalesInvoiceDetails();
                    details.setPrice(p.getPrice());
                    details.setQuantity(0);
                    details.setProductName(p.getName());
                    Product product = productRepo.findByName(p.getName());
                    details.setDetails("");
                    details.setProductId(product.getId());
                    addProductToInvoiceTable(details);
                });
                
            }
            
//            gridPane.getChildren().addAll(elements);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void addProductToInvoiceTable(SalesInvoiceDetails details) {
        try {
            
            // col details
            colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
            colDetails.setCellFactory(TextFieldTableCell.forTableColumn());
            colDetails.setOnEditCommit(new EventHandler<CellEditEvent<SalesInvoiceDetails, String>>(){
                @Override
                public void handle(CellEditEvent<SalesInvoiceDetails, String> event) {
                    SalesInvoiceDetails d = event.getRowValue();
                    d.setDetails(event.getNewValue());
                }
            
            });
        
            
            // col price
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colPrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colPrice.setOnEditCommit(new EventHandler<CellEditEvent<SalesInvoiceDetails, Double>>() {
                @Override
                public void handle(CellEditEvent<SalesInvoiceDetails, Double> t) {
                    SalesInvoiceDetails d = t.getRowValue();
                    double newValue = t.getNewValue();
                    if(newValue <= 0) {
                        AlertMaker.showErrorALert("ادخل عدد اكبر من صفر ");
                        return;
                    }
                    d.setPrice(newValue);
                    double total = d.getPrice() * d.getQuantity();
                    d.setTotal(total);
                    tbInvocieDetails.refresh();
                    calculateTotals();
                }
            });
            
            // col name
            colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
            
            // col quantity
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            colQuantity.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            colQuantity.setOnEditCommit(new EventHandler<CellEditEvent<SalesInvoiceDetails, Double>>() {
                @Override
                public void handle(CellEditEvent<SalesInvoiceDetails, Double> t) {
                    SalesInvoiceDetails d = t.getRowValue();
                    double newValue = t.getNewValue();
                    if(newValue <= 0) {
                        AlertMaker.showErrorALert("ادخل عدد اكبر من صفر ");
                        return;
                    }
                    d.setQuantity(newValue);
                    double total = d.getPrice() * d.getQuantity();
                    d.setTotal(total);
                    tbInvocieDetails.refresh();
                    calculateTotals();
                }
            });
            
            // col total
            colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            for (SalesInvoiceDetails d : data) {
                if(d.getProductName() == details.getProductName()){
                    AlertMaker.showErrorALert("هذا المنتج تم اضافته سابقا . اختر عنصر اخر");
                    return;
                }
            }
            
            data.add(details);
            tbInvocieDetails.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    private void calculateTotals(){
        try {
            
            if(txtDiscount.getText().isEmpty()){
                txtDiscount.setText("0.0");
            }
            
            if(txtTaxInPercentage.getText().isEmpty()){
                txtTaxInPercentage.setText("0.0");
            }
            
            double subtotal = 0;
            for (SalesInvoiceDetails d : data) {
                subtotal = subtotal + d.getTotal();
            }
            txtSubTotal.setText(df.format(subtotal));
            
            double discount = Double.valueOf(txtDiscount.getText());
            double taxInPercentage = Double.valueOf(txtTaxInPercentage.getText());
            double tax = subtotal * (taxInPercentage / 100);
            txtTaxInNumber.setText(df.format(tax));
            
            double total = (subtotal + tax) - discount;
            
            txtTotal.setText(df.format(total));
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML 
    private void deleteProductFromTable() {
        try {
            SalesInvoiceDetails d = tbInvocieDetails.getSelectionModel().getSelectedItem();
            
            data.remove(d);
            calculateTotals();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void setInvoiceDate() {
        try {
            String date = dtf.format(LocalDate.now());
            dpDate.setValue(LocalDate.parse(date, dtf));
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillCustomersComboBox() {
        try {
            ArrayList<Customer> customers = customerRepo.findAll();
            ObservableList<String> data = FXCollections.observableArrayList();
            
            for(Customer c : customers) {
                data.add(c.getName());
            }
            
            cbCustomerName.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void resetInvoice() {
        try {
            txtCutomerPhone.setText("");
            txtDiscount.setText("0.0");
            txtSearchCustomer.setText("");
            txtSubTotal.setText("0.0");
            txtTaxInNumber.setText("0.0");
            txtTaxInPercentage.setText("0.0");
            txtTotal.setText("0.0");
            data.clear();
            cbCustomerName.getSelectionModel().clearSelection();
            setInvoiceDate();
            btnPrintInvoice.setDisable(true);
            btnSaveInvoice.setDisable(false);
            lbInvoiceId.setText("0");
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    private void fillInvoicesListTable() {
        try {
            
            cbFilterListByCutomer.getSelectionModel().clearSelection();
            
            colListCustmerName.setCellValueFactory(new PropertyValueFactory<>("cutomerName"));
            colListDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colListDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colListId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colListTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
            colListTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            
            ObservableList<SalesInvoiceHeader> data = FXCollections.observableArrayList(headerRepo.findAll());
            
            SortedList<SalesInvoiceHeader> sortedList = new SortedList<>(data);
            
            sortedList.comparatorProperty().bind(tbInvoicesList.comparatorProperty());
            
            tbInvoicesList.setItems(sortedList);
            
            colListId.setSortType(TableColumn.SortType.DESCENDING);
            tbInvoicesList.getSortOrder().addAll(colListId);
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    @FXML
    void filterInvoicesListTable(){
        try {
            String customerName = cbFilterListByCutomer.getSelectionModel().getSelectedItem();

            Customer c = customerRepo.findByName(customerName);
            
            if(c == null)
            {
                filterInvoicesListTable();
                return;
            }
                
                
            
            colListCustmerName.setCellValueFactory(new PropertyValueFactory<>("cutomerName"));
            colListDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            colListDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
            colListId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colListTax.setCellValueFactory(new PropertyValueFactory<>("tax"));
            colListTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            
            ObservableList<SalesInvoiceHeader> data = FXCollections.observableArrayList(headerRepo.findByCutomerId(c.getId()));
            
            SortedList<SalesInvoiceHeader> sortedList = new SortedList<>(data);
            
            sortedList.comparatorProperty().bind(tbInvoicesList.comparatorProperty());
            
            tbInvoicesList.setItems(sortedList);
            
            colListId.setSortType(TableColumn.SortType.DESCENDING);
            tbInvoicesList.getSortOrder().addAll(colListId);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    private void fillFilterInvoicesListCustomerCB() {
        try {
            ObservableList<String> d = FXCollections.observableArrayList();
            ArrayList<Customer> list = customerRepo.findAll();
            
            for(Customer c : list) {
                d.add(c.getName());
            }
            
            cbFilterListByCutomer.setItems(d);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    
    
     private void showInvoiceFile(File file) {
        try {
            if (OSDetector.isWindows())
        {
            Runtime.getRuntime().exec(new String[]
            {"rundll32", "url.dll,FileProtocolHandler",
             file.getAbsolutePath()});
//            return true;
        } else if (OSDetector.isLinux() || OSDetector.isMac())
        {
            Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
                                                   file.getAbsolutePath()});
//            return true;
        } else
        {
            // Unknown OS, try with desktop
            if (Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().open(file);
//                return true;
            }
            else
            {
//                return false;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

}
