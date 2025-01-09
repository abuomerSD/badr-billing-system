package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.reports.ReportMaker;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.salesinvoicedetails.SalesInvoiceDetailsRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import java.util.ArrayList;



public class Main {

    public static void main(String[] args)  {
        DatabaseTableCreator.createTables();    
        
        SalesInvoiceHeaderRepo headerRepo = new SalesInvoiceHeaderRepo();
        SalesInvoiceDetailsRepo detailsRepo = new SalesInvoiceDetailsRepo();
        CustomerRepo customerRepo = new CustomerRepo();
        
        SalesInvoiceHeader header = headerRepo.findById(1);
        ArrayList<SalesInvoiceDetails> details = detailsRepo.findAllByHeaderId(1);
        Customer c = customerRepo.findById(header.getCustomerId());
        
        ReportMaker reportMaker = new ReportMaker();
        reportMaker.showSalesInvoice(1, header, details, c);

        
        
//        launch(args);
    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/badrbillingsystem/ui/home.fxml"));
//            Scene scene = new Scene(root);
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("الواجهة الرئيسية");
//            primaryStage.show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
}
