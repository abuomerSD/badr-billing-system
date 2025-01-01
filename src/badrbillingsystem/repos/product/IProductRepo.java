
package badrbillingsystem.repos.product;

import badrbillingsystem.models.Customer;
import badrbillingsystem.models.Product;
import java.util.ArrayList;

public interface IProductRepo {
    boolean save(Product product);
    boolean update(Product newProduct);
    boolean delete(long id);
    Product findById(long id);
    Product findByName(String name);
    ArrayList<Product> findAll();
    ArrayList<Product> findBySearchWords(String searchWords);     
}
