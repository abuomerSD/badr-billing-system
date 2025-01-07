
package badrbillingsystem.repos.salesinvoicedetails;

import badrbillingsystem.models.SalesInvoiceDetails;
import java.util.ArrayList;

public interface ISalesInvoiceDetailsRepo {
    boolean save(SalesInvoiceDetails details);
    boolean update(SalesInvoiceDetails newDetails);
    boolean delete(long productId, long headerId);
    ArrayList<SalesInvoiceDetails> findAllByHeaderId(long id);
//    SalesInvoiceDetails findByName(String name);
//    ArrayList<SalesInvoiceDetails> findAll();
//    ArrayList<SalesInvoiceDetails> findBySearchWords(String searchWords);
}
