
package badrbillingsystem.controller;

import badrbillingsystem.models.Product;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.utils.AlertMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class SalesInvoiceController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillProductsGrid();
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
//                    AlertMaker.showMessageAlert(p.getName());
                    System.out.println(p.getName());
                    SalesInvoiceDetails details = new SalesInvoiceDetails();
//                    details.setDetails(details);
//                    details.setHeaderId(details);
                    details.setPrice(p.getPrice());
                    details.setQuantity(0);
                    details.setProductName(p.getName());
                    details.setDetails("");
//                    details.setDetails(details);
//                    details.setDetails(details);
//                    details.setDetails(details);
//                    details.setDetails(details);
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
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    
}
