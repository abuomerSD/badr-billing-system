package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
        launch(args);
//        Home home = new Home();
//        home.show();


    
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
