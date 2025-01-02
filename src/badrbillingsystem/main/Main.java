package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.User;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.repos.user.UserRepo;
import badrbillingsystem.ui.Home;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();

        CompanyInfoRepo repo = new CompanyInfoRepo();

        
//        CompanyInfo c1 = new CompanyInfo();
//        c1.setAddress("address1");
//        c1.setLogo("logo1");
//        c1.setName("name1");
//        c1.setPhone("phone1");
//        
//        repo.save(c1);
//        
//        CompanyInfo c2 = new CompanyInfo();
//        c2.setAddress("address2");
//        c2.setLogo("logo2");
//        c2.setName("name2");
//        c2.setPhone("phone2");
//        System.out.println(repo.save(c1));
        
//        CompanyInfo c1 = new CompanyInfo();
//        c1.setId(1);
//        c1.setAddress("address1 u");
//        c1.setLogo("logo1 u");
//        c1.setName("name1 u");
//        c1.setPhone("phone1 u");
//        
//        repo.update(c1);
        CompanyInfo c = repo.findByName("name du");
        System.out.println(c);
        
        ArrayList<CompanyInfo> list = repo.findBySearchWords("1 u");
        System.out.println(list);
        
        
    
    }
    
}
