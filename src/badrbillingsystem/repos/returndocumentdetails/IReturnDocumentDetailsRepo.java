
package badrbillingsystem.repos.returndocumentdetails;

import badrbillingsystem.models.ReturnDocumentDetails;
import java.util.ArrayList;

public interface IReturnDocumentDetailsRepo {
    boolean save(ReturnDocumentDetails details);
    boolean update(ReturnDocumentDetails newDetails, long productId);
    boolean delete(long headerId, long productId);
//    ReturnDocumentDetails findAllByHeaderId(long id);
    ArrayList<ReturnDocumentDetails> findAllByHeaderId(long id);
}
