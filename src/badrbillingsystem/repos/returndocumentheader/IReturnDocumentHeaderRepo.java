
package badrbillingsystem.repos.returndocumentheader;


import badrbillingsystem.models.ReturnDocumentHeader;
import java.util.ArrayList;


public interface IReturnDocumentHeaderRepo {
    long save(ReturnDocumentHeader header);
    boolean update(ReturnDocumentHeader newHeader);
    boolean delete(long id);
    ReturnDocumentHeader findById(long id);
    ArrayList<ReturnDocumentHeader> findAll();
    ArrayList<ReturnDocumentHeader> findByCustomerId(long customerId);
    ArrayList<ReturnDocumentHeader> findAllById(long id);
}
