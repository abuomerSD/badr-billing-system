package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.ReturnDocumentDetails;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.repos.returndocumentdetails.ReturnDocumentDetailsRepo;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();

        ReturnDocumentDetailsRepo repo = new ReturnDocumentDetailsRepo();
        
//         ReturnDocumentDetails d1 = new ReturnDocumentDetails();
//         d1.setDetails("details");
//         d1.setHeaderId(1);
//         d1.setProductId(100);
//         d1.setQuantity(10);
//         repo.save(d1);
//         
//         ReturnDocumentDetails d2 = new ReturnDocumentDetails();
//         d2.setDetails("details2");
//         d2.setHeaderId(1);
//         d2.setProductId(1002);
//         d2.setQuantity(102);
//         repo.save(d2);

//            ReturnDocumentDetails d2 = new ReturnDocumentDetails();
//         d2.setDetails("details2 202");
//         d2.setHeaderId(1);
//         d2.setProductId(1002);
//         d2.setQuantity(202);
//         repo.update(d2, 1002);
         
//        System.out.println(repo.delete(1, 1002));

        ArrayList<ReturnDocumentDetails> list = repo.findAllByHeaderId(1);
        System.out.println(list);
    
    }
    
}
