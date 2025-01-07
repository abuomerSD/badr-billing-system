
package badrbillingsystem.repos.salesinvoiceheader;

import badrbillingsystem.models.SalesInvoiceHeader;
import java.util.ArrayList;

public interface ISalesInvoiceHeaderRepo {
    long save(SalesInvoiceHeader header);
    boolean update(SalesInvoiceHeader newHeader);
    boolean delete(long id);
    SalesInvoiceHeader findById(long id);
//    SalesInvoiceHeader findByName(String name);
    ArrayList<SalesInvoiceHeader> findAll();
    ArrayList<SalesInvoiceHeader> findByCutomerId(long id);
//    ArrayList<User> findBySearchWords(String searchWords);
    
}
