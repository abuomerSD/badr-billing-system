package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.Customer;
import badrbillingsystem.repos.customer.CustomerRepo;
import badrbillingsystem.ui.Home;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();
        
        CustomerRepo repo = new CustomerRepo();
        
//        Customer c1 = new Customer();
//        c1.setName("الطيب");
//        c1.setPhone("876723671");
//        repo.save(c1);

        Customer c = repo.findById(3);
        System.out.println(c.getPhone());

//        ArrayList<Customer> list = repo.findAll();
//        System.out.println(list.size());
//        for(Customer c : list) {
//            System.out.println(c.getName());
//        }
        
    }
    
}
