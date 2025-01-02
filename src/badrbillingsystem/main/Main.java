package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import badrbillingsystem.models.User;
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
        

        
    
    }
    
}
