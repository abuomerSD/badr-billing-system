
package badrbillingsystem.repos.returndocumentheader;


import badrbillingsystem.models.ReturnDocumentHeader;
import java.util.ArrayList;


public interface IReturnDocumentHeaderRepo {
    boolean save(ReturnDocumentHeader header);
    boolean update(ReturnDocumentHeader newHeader);
    boolean delete(long id);
    ReturnDocumentHeader findById(long id);
    ArrayList<ReturnDocumentHeader> findAll();
}
