package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.SalesInvoiceHeader;
import badrbillingsystem.models.User;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.product.ProductRepo;
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
        
        SalesInvoiceHeaderRepo repo = new SalesInvoiceHeaderRepo();
        
//        SalesInvoiceHeader header = new SalesInvoiceHeader();
//        header.setCustomerId(1);
//        header.setUserId(2);
//        header.setDate("2-1-2025");
//        header.setDiscount(500);
//        header.setTax(200);
//        header.setTotal(10000);
//        repo.save(header);

//        SalesInvoiceHeader header = repo.findById(1);
//        System.out.println(header);
//        SalesInvoiceHeader header = new SalesInvoiceHeader();
//        header.setCustomerId(10);
//        header.setUserId(20);
//        header.setDate("20-1-2025");
//        header.setDiscount(2000);
//        header.setTax(1000);
//        header.setTotal(100000);
//        header.setId(1);
//        repo.update(header);

        repo.delete(1);
        
        ArrayList<SalesInvoiceHeader> list = repo.findAll();
        System.out.println(list);

    
    }
    
}
