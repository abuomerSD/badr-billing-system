/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package badrbillingsystem.repos.customer;

import badrbillingsystem.models.Customer;
import java.util.ArrayList;

/**
 *
 * @author asdf
 */
public interface ICustomerRepo {
    boolean save(Customer customer);
    boolean update(Customer newCustomer);
    boolean delete(long id);
    Customer findById(long id);
    Customer findByName(String name);
    ArrayList<Customer> findAll();
    ArrayList<Customer> findBySearchWords(String searchWords);    
}
