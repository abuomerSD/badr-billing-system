package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.CustomerAccount;
import badrbillingsystem.repos.customeraccount.CutomerAccountRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application{

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();   
        
        
        CutomerAccountRepo repo = new CutomerAccountRepo();
//        CustomerAccount a = new CustomerAccount();
//        a.setBalance(0);
//        a.setCustomerId(3);
//        a.setDate("7-1-2025");
//        a.setIncoming(500);
//        a.setInfo("Info");
//        a.setOutgoing(0);
//        a.setReturnDocumentId(1);
//        a.setSalesInvoiceId(0);
//        a.setTotalBalance(10000);
//        a.setBalance(0);
//        
//        repo.save(a);


//        CustomerAccount a = new CustomerAccount();
//        a.setBalance(0);
//        a.setCustomerId(2);
//        a.setDate("7-1-2025 u");
//        a.setIncoming(0);
//        a.setInfo("Info u ");
//        a.setOutgoing(5000);
//        a.setReturnDocumentId(0);
//        a.setSalesInvoiceId(1);
//        a.setTotalBalance(20000);
//        a.setBalance(0);
//        
//        repo.updateBySalesInvoiceId(a);

//        CustomerAccount a = new CustomerAccount();
//        a.setBalance(0);
//        a.setCustomerId(3);
//        a.setDate("7-1-2025 uu");
//        a.setIncoming(700);
//        a.setInfo("Info u u");
//        a.setOutgoing(0);
//        a.setReturnDocumentId(1);
//        a.setSalesInvoiceId(0);
//        a.setTotalBalance(7000);
//        a.setBalance(0322);
//        
//        System.out.println("dsd");
//        repo.updateByReturnDocumentId(a);
        
        repo.deleteByReturnDocumentId(1, 3);
        
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
