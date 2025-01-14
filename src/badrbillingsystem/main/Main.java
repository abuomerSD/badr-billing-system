package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.Product;
import badrbillingsystem.repos.product.ProductRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{

    public static void main(String[] args)  {
        DatabaseTableCreator.createTables();            
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("تسجيل الدخول");
            primaryStage.show();
            
            
//            ProductRepo repo = new ProductRepo();
//            for (int i = 10; i < 100; i++) {
//                Product p = new Product();
//                p.setImage("/Users/asdf/Documents/Eltayeb/Code/BadrBillingSystem/out/product-images/0da2405b-6003-469a-b569-3c602512647a.jpg");
//                p.setName("p"+i);
//                p.setPrice(i+100);
//                repo.save(p);
//                System.out.println("p"+i+" saved");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
