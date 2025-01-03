package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();
        
        ProductMovementRepo repo = new ProductMovementRepo();
        
//        ProductMovement movement = new ProductMovement();
//        movement.setProductId(1);
//        movement.setReturnInvoiceId(0);
//        movement.setReturnQuantity(0);
//        movement.setSalesInvoiceId(20240001);
//        movement.setSalesQuantity(10);        
//        repo.save(movement);
//        
//        ProductMovement movement2 = new ProductMovement();
//        movement2.setProductId(2);
//        movement2.setReturnInvoiceId(20240002);
//        movement2.setReturnQuantity(50);
//        movement2.setSalesInvoiceId(0);
//        movement2.setSalesQuantity(0);        
//        repo.save(movement2);

        
                
//        ProductMovement movement2 = new ProductMovement();
//        movement2.setProductId(2);
//        movement2.setReturnInvoiceId(20240002);
//        movement2.setReturnQuantity(100);
//        movement2.setSalesInvoiceId(0);
//        movement2.setSalesQuantity(0);        
//        repo.updateByReturnInvoiceId(movement2, 20240002);

//        ProductMovement movement = new ProductMovement();
//        movement.setProductId(1);
//        movement.setReturnInvoiceId(0);
//        movement.setReturnQuantity(0);
//        movement.setSalesInvoiceId(20240001);
//        movement.setSalesQuantity(100);        
//        repo.updateBySalesInvoiceId(movement, 20240001);
        repo.deleteByReturnInvoiceId(2, 20240002);
        
        ArrayList<ProductMovement> list = repo.findAllById(1);
        System.out.println(list);
//        

    
    }
    
}
