package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.SalesInvoiceDetails;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.models.User;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.repos.salesinvoicedetails.SalesInvoiceDetailsRepo;
import badrbillingsystem.repos.salesinvoiceheader.SalesInvoiceHeaderRepo;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.ui.Home;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();
        
        SalesInvoiceDetailsRepo repo = new SalesInvoiceDetailsRepo();
        
//        SalesInvoiceDetails details = new SalesInvoiceDetails();
//        details.setDetails("details");
//        details.setHeaderId(1);
//        details.setPrice(100);
//        details.setProductId(10);
//        details.setQuantity(5);
//        details.setTotal(500);
//        repo.save(details);
//        
//        SalesInvoiceDetails details2 = new SalesInvoiceDetails();
//        details2.setDetails("details2");
//        details2.setHeaderId(1);
//        details2.setPrice(200);
//        details2.setProductId(20);
//        details2.setQuantity(10);
//        details2.setTotal(2000);
//        repo.save(details2);
//        
//        SalesInvoiceDetails details3 = new SalesInvoiceDetails();
//        details3.setDetails("details3");
//        details3.setHeaderId(1);
//        details3.setPrice(300);
//        details3.setProductId(30);
//        details3.setQuantity(20);
//        details3.setTotal(6000);
//        repo.save(details3);


//    SalesInvoiceDetails details = new SalesInvoiceDetails();
//        details.setDetails("details ");
//        details.setHeaderId(11);
//        details.setPrice(1000);
//        details.setProductId(10);
//        details.setQuantity(50);
//        details.setTotal(50000);
//        repo.update(details);

//        repo.delete(10);
        
        ArrayList<SalesInvoiceDetails> list = repo.findAllByHeaderId(1);
        System.out.println(list);
    
    }
    
}
