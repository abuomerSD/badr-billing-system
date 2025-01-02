package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.repos.product.ProductRepo;
import badrbillingsystem.ui.Home;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();
        
//        Product p = new Product();
        ProductRepo repo = new ProductRepo();
//        
//        p.setImage("image path4");
//        p.setName("p4");
//        p.setPrice(400);
//        
//        Product p2 = new Product();
//        p2.setImage("image path5");
//        p2.setName("p10");
//        p2.setPrice(500);
//        p2.setId(1);
        
//        repo.save(p);
//        repo.save(p2);
//        repo.update(p2);
//        boolean status = repo.delete(3);
//        System.out.println(status);

//        Product product4 = repo.findById(4);
//        Product product5 = repo.findById(5);
//        
//        System.out.println(product4);
//        System.out.println(product5);

//        CustomerRepo customerRepo = new CustomerRepo();
//        Customer c = customerRepo.findByName("الطيب");
//        System.out.println(c);

        
        Product p = repo.findByName("p4");
        List<Product> list = repo.findBySearchWords("");
        System.out.println(list);
        
    }
    
}
