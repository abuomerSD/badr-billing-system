package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
        
        ProductMovementRepo repo = new ProductMovementRepo();
        ProductMovement m = new ProductMovement();
        m.setDetails("details");
        m.setProductId(1);
        m.setReturnInvoiceId(0);
        m.setReturnQuantity(0);
        m.setSalesInvoiceId(1);
        m.setSalesQuantity(10);
        repo.save(m);
        
        ProductMovement m1 = new ProductMovement();
        m1.setDetails("details1");
        m1.setProductId(1);
        m1.setReturnInvoiceId(2);
        m1.setReturnQuantity(20);
        m1.setSalesInvoiceId(0);
        m1.setSalesQuantity(0);
        repo.save(m1);
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/home.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("الواجهة الرئيسية");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
