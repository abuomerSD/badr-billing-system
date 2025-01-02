
package badrbillingsystem.repos.salesinvoiceheader;

import badrbillingsystem.models.SalesInvoiceHeader;
import java.util.ArrayList;

public interface ISalesInvoiceHeaderRepo {
    boolean save(SalesInvoiceHeader header);
    boolean update(SalesInvoiceHeader newHeader);
    boolean delete(long id);
    SalesInvoiceHeader findById(long id);
//    SalesInvoiceHeader findByName(String name);
    ArrayList<SalesInvoiceHeader> findAll();
//    ArrayList<User> findBySearchWords(String searchWords);
    
}
