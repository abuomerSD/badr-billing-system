
package badrbillingsystem.repos.companyinfo;

import badrbillingsystem.models.CompanyInfo;
import java.util.ArrayList;


public interface ICompanyInfoRepo {
    boolean save(CompanyInfo companyInfo);
    boolean update(CompanyInfo newCompanyInfo);
//    boolean delete(long id);
    CompanyInfo findById(long id);
    CompanyInfo findByName(String name);
    ArrayList<CompanyInfo> findAll();
    ArrayList<CompanyInfo> findBySearchWords(String searchWords);    
}
