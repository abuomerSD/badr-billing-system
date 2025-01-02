
package badrbillingsystem.repos.user;

import badrbillingsystem.models.User;
import java.util.ArrayList;

public interface IUserRepo {
    boolean save(User user);
    boolean update(User newUser);
    boolean delete(long id);
    User findById(long id);
    User findByName(String name);
    ArrayList<User> findAll();
    ArrayList<User> findBySearchWords(String searchWords);
    
}
