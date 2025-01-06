
package badrbillingsystem.controller;

import badrbillingsystem.models.Product;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.utils.AlertMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private GridPane gridPane;
    
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
                
                
                // action listener
                
                addButton.setOnAction(e -> {
                    AlertMaker.showMessageAlert(p.getName());
                    System.out.println(p.getName());
                });
                
            }
            
//            gridPane.getChildren().addAll(elements);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
