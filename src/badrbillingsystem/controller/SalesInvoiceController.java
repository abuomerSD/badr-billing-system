
package badrbillingsystem.controller;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.utils.AlertMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
        
        cbCustomerName.valueProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Customer c = customerRepo.findByName(cbCustomerName.getSelectionModel().getSelectedItem());
                txtCutomerPhone.setText(c.getPhone());
            }        
        });
    }
    
    @FXML
    private DatePicker dpDate;

    @FXML
    private ComboBox<String> cbCustomerName;

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
    
    ObservableList<SalesInvoiceDetails> data = FXCollections.observableArrayList();
    DecimalFormat df = new DecimalFormat("#,###,###.##");
    CustomerRepo customerRepo = new CustomerRepo();
//    DateFormatter dateFormatter = new DateFormatter();
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

    @FXML
    void newInvoice(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void printInvoice(ActionEvent event) {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void saveInvoice(ActionEvent event) {
        try {
            
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
                    details.setDetails("");
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
                    d.setPrice(t.getNewValue());
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
                    d.setQuantity(t.getNewValue());
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
            txtTaxInNumber.setText(df.format(tax) + " ريال");
            
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

}
