
package badrbillingsystem.repos.productmovement;

import badrbillingsystem.models.ProductMovement;
import java.util.ArrayList;

public interface IProductMovementRepo {
    boolean save(ProductMovement productMovement);
    boolean updateBySalesInvoiceId(ProductMovement newProductMovement, long invoiceId);
    boolean updateByReturnInvoiceId(ProductMovement newProductMovement, long invoiceId);
    boolean deleteBySalesInvoiceId(long productId, long invoiceId);
    boolean deleteByReturnInvoiceId(long productId, long invoiceId);
    ArrayList<ProductMovement> findAllById(long id); 
}
