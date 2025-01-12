
package badrbillingsystem.repos.customeraccount;

import badrbillingsystem.models.CustomerAccount;
import java.util.ArrayList;

public interface ICustomerAccountRepo {
    boolean save(CustomerAccount account) ;
    boolean updateBySalesInvoiceId(CustomerAccount account);
    boolean updateByReturnDocumentId(CustomerAccount account);
    boolean deleteBySalesInvoiceId(long headId, long customerId);
    boolean deleteByReturnDocumentId(long headId, long customerId);
    ArrayList<CustomerAccount> findByCutomerId(long id);
    ArrayList<CustomerAccount> findByDates(String fromDate, String toDate);
}
