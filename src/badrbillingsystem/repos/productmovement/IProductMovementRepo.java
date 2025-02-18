
package badrbillingsystem.repos.productmovement;

import badrbillingsystem.models.ProductMovement;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public interface IProductMovementRepo {
    boolean save(ProductMovement productMovement);
    boolean updateBySalesInvoiceId(ProductMovement newProductMovement, long invoiceId);
    boolean updateByReturnInvoiceId(ProductMovement newProductMovement, long invoiceId);
    boolean deleteBySalesInvoiceId(long productId, long invoiceId);
    boolean deleteByReturnInvoiceId(long productId, long invoiceId);
    ArrayList<ProductMovement> findAllById(long id); 
//    ArrayList<ProductMovement> findAllByKeywords(String fromDate, String toDate, long customerId);
    ArrayList<ProductMovement> findAllByDateRange(String fromDate, String toDate, long productId);
    ArrayList<ProductMovement> findAllByKeywords(long productId, String fromDate, String toDate, long customerId);
    ArrayList<ProductMovement> findAllByCustomerId(long customerId, long productId);
}
