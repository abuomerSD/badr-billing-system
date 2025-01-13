
package badrbillingsystem.controller;

import badrbillingsystem.models.Product;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.Constants;
import badrbillingsystem.utils.ImageResizer;
import badrbillingsystem.utils.NotificationMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class ProductsController implements Initializable {
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDefaultImagePath();
        updateProductTableData();
        
        tbProduct.getFocusModel().focusedCellProperty().addListener(new ChangeListener<TablePosition>(){
            @Override
            public void changed(ObservableValue<? extends TablePosition> observable, TablePosition oldValue, TablePosition newValue) {
                try{
                    Product product = tbProduct.getSelectionModel().getSelectedItem();
                    if(product == null)
                        return;
                    InputStream in = new FileInputStream(new File(product.getImage()));
                    imageview.setImage(new Image(in));
                    
                    txtImage.setText(product.getImage());
                    txtName.setText(product.getName());
                    txtPrice.setText(String.valueOf(product.getPrice()));
                    
                } catch(Exception e) {
                    e.printStackTrace();
                    AlertMaker.showErrorALert(e.toString());
                }
            }
            
        });
        
        tbProduct.setPlaceholder(new Label("لا توجد بيانات"));
    }    
    
    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtImage;
    
    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnOpenFileChooser;

    @FXML
    private TableView<Product> tbProduct;

    @FXML
    private TableColumn<Product, Integer> colNumber;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, Double> colPrice;
    @FXML
    ImageView imageview;
    
    ObservableList<Product> data ;
    ProductRepo repo = new ProductRepo();
    String imagePath = "src/badrbillingsystem/resources/images/default.jpg";

    @FXML
    void OpenFileChooser(ActionEvent event) {
           FileChooser fileChooser = new FileChooser();
           fileChooser.getExtensionFilters().addAll(
                   new ExtensionFilter("Images", "*.jpeg", "*.jpg", "*.png")
           );
//           
           File selectedFile = fileChooser.showOpenDialog(null);
            
           if(selectedFile != null){
               txtImage.setText(selectedFile.getAbsolutePath());
               try {
                   InputStream in = new FileInputStream(new File(selectedFile.getAbsolutePath()));
                   imageview.setImage(new Image(in));
               } catch (Exception e) {
                   e.printStackTrace();
                   AlertMaker.showErrorALert(e.toString());
               }
               
           }
    }

    @FXML
    void delete(ActionEvent event) {
        try {
            Product p = tbProduct.getSelectionModel().getSelectedItem();
            
            if(p == null){
                AlertMaker.showErrorALert("اختر المنتج اولا");
                return;
            }
            
            Optional<ButtonType> result = AlertMaker.showConfirmationAlert("هل تريد حذف المنتج؟");
            if(result.get() != ButtonType.OK)
                return;
            
            ProductMovementRepo movementRepo = new ProductMovementRepo();
            
            ArrayList<ProductMovement> list = movementRepo.findAllById(p.getId());
            
            if(list.size() > 0) {
                AlertMaker.showErrorALert("لا يمكن حذف صنف لديه حركة في المبيعات");
                return;
            }
            
            if(repo.delete(p.getId())){
                updateProductTableData();
                clearTextFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void filterByKeywords(KeyEvent event) {
        try {
            String keywords = txtSearch.getText();
            
            ObservableList<Product> data = FXCollections.observableArrayList(repo.findBySearchWords(keywords));
            
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            
            
            tbProduct.setItems(data);
            
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void save(ActionEvent event) {
        try {
             String name = txtName.getText();
             double price = Double.parseDouble(txtPrice.getText());
             String imagePath = txtImage.getText();
             
             if(name.isEmpty()){
                 AlertMaker.showErrorALert("ادخل اسم المنتج");
                 return;
             }
             
             if(txtPrice.getText().isEmpty() ) {
                 AlertMaker.showErrorALert("ادخل سعر المنتج");
                 return;
             }
             Product p = new Product();
             p.setName(name);
             p.setPrice(price);
             p.setImage(copyImage(new File(txtImage.getText())));
             
             if(repo.save(p)){
                 NotificationMaker.showInformation("تم اضافة المنتج بنجاح");
                 clearTextFields();
                 updateProductTableData();
             }
             
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void showProductMovementWindow(ActionEvent event) {
        try {
            Product p = tbProduct.getSelectionModel().getSelectedItem();
            if(p == null) {
                AlertMaker.showErrorALert("اختر المنتج اولا");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/badrbillingsystem/ui/productMovement.fxml"));
            
            ProductMovementController controller = new ProductMovementController(p.getId());
           loader.setController(controller);
            Parent root = loader.load();
            
            controller.txtProductId.setText(String.valueOf(p.getId()));
            controller.txtProductName.setText(p.getName());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("حركة المنتج");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }

    @FXML
    void update(ActionEvent event) {
        try{
            Product oldProduct = tbProduct.getSelectionModel().getSelectedItem();
            if(oldProduct == null) {
                AlertMaker.showErrorALert("اختر المنتج اولا");
                return;
            }
            String name = txtName.getText();
            double price = Double.valueOf(txtPrice.getText());
            String imagePath = txtImage.getText();
            
            if(name.isEmpty()){
                 AlertMaker.showErrorALert("ادخل اسم المنتج");
                 return;
             }
             
             if(txtPrice.getText().isEmpty() ) {
                 AlertMaker.showErrorALert("ادخل سعر المنتج");
                 return;
             }
             
             Product p = new Product();
             p.setName(name);
             p.setId(oldProduct.getId());
             p.setPrice(price);
             p.setImage(copyImage(new File(txtImage.getText())));
             
             Optional<ButtonType> result = AlertMaker.showConfirmationAlert("هل تريد تعديل المنتج؟");
             if(result.get() != ButtonType.OK)
                 return;
             if(repo.update(p)){
                 updateProductTableData();
                 clearTextFields();
             }
            
        } catch(Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    private void updateProductTableData() {
        try {
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colNumber.setCellValueFactory(new PropertyValueFactory<>("id"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            data = FXCollections.observableArrayList(repo.findAll());
            
            tbProduct.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
    private void setDefaultImagePath(){
        
        txtImage.setText(imagePath);
        try {
            InputStream in = new FileInputStream(new File(imagePath));
            imageview.setImage(new Image(in));
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
        
    }
    
    private String copyImage(File originalImage) throws IOException {
//        System.out.println(originalImage.getAbsolutePath());
        File copied = null;
        
        String imageName = UUID.randomUUID().toString();
        String extention = "";
        int i = originalImage.getAbsolutePath().lastIndexOf(".");
        if(i > 0) {
            extention = originalImage.getAbsolutePath().substring(i+1);
        }
        String newFileName = imageName + "." + extention;
        String distPath = "out/product-images/" + newFileName;
//        String distPath = System.getProperty("user.dir") + "/product-images/" + newFileName;

        copied = new File(distPath);
//        System.out.println(copied.getAbsolutePath());

        ImageResizer.resizeImage(originalImage, copied, extention, Constants.PRODUCTS_IMAGES_WIDTH, Constants.PRODUCTS_IMAGES_HEIGHT);
        
        
//        FileInputStream in = null;
//        FileOutputStream out = null;
        
//        try {
//            in = new FileInputStream(originalImage);
//            out = new FileOutputStream(copied);    
//            int c;
//            
//            while ((c = in.read()) != -1) {
//                
//                out.write(c);
////                System.out.println(c);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            AlertMaker.showErrorALert(e.toString());
//        }
//        finally {
//            if (in != null) {
//                in.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//        }

        System.out.println(copied.getAbsolutePath());
         return copied.getAbsolutePath();
    }

    private void clearTextFields() {
        txtImage.setText(imagePath);
        txtName.clear();
        txtPrice.clear();
        try {
                   InputStream in = new FileInputStream(new File(imagePath));
                   imageview.setImage(new Image(in));
               } catch (Exception e) {
                   e.printStackTrace();
                   AlertMaker.showErrorALert(e.toString());
               }
    }
    
}
