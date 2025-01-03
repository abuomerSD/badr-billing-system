package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.models.ProductMovement;
import badrbillingsystem.models.ReturnDocumentHeader;
import badrbillingsystem.repos.productmovement.ProductMovementRepo;
import badrbillingsystem.repos.returndocumentheader.ReturnDocumentHeaderRepo;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        DatabaseTableCreator.createTables();
//        Home home = new Home();
//        home.show();

        ReturnDocumentHeaderRepo repo = new ReturnDocumentHeaderRepo();
        
//        ReturnDocumentHeader header = new ReturnDocumentHeader();
//        header.setId(1);
//        header.setDate("1-1-2025");
//        header.setDetails("details 1");
//        header.setSalesInvoiceId(2025010001);
//        repo.save(header);
//        
//        ReturnDocumentHeader header2 = new ReturnDocumentHeader();
//        header2.setId(1);
//        header2.setDate("3-1-2025");
//        header2.setDetails("details 2");
//        header2.setSalesInvoiceId(2025010002);
//        repo.save(header2);

//        ReturnDocumentHeader header = new ReturnDocumentHeader();
//        header.setId(1);
//        header.setDate("1-1-2026");
//        header.setDetails("details 1 u");
//        header.setSalesInvoiceId(2025011);
//        System.out.println(repo.update(header));

//        System.out.println(repo.delete(1));

//        System.out.println(repo.findById(2));
        
        ArrayList<ReturnDocumentHeader> list = repo.findAll();
        System.out.println(list);
    
    }
    
}
