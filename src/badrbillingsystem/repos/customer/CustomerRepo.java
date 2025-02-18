
package badrbillingsystem.repos.customer;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class CustomerRepo implements ICustomerRepo{

    @Override
    public boolean save(Customer customer) {
        String sql = "INSERT INTO TB_CUSTOMER (NAME, PHONE) VALUES (?,?)";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPhone());
            int flag = ps.executeUpdate();
            if (flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(Customer newCustomer) {
        String sql = "UPDATE TB_CUSTOMER SET NAME = ?, PHONE = ? WHERE ID  = ?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newCustomer.getName());
            ps.setString(2, newCustomer.getPhone());
            ps.setLong(3, newCustomer.getId());
            int flag = ps.executeUpdate();
            if (flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM TB_CUSTOMER WHERE ID = ?";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            int flag = ps.executeUpdate();
            if (flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public Customer findById(long id) {
        String sql = "SELECT * FROM TB_CUSTOMER WHERE ID = " + id + ";";
        Customer customer = new Customer();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            customer.setName(rs.getString("NAME"));
            customer.setPhone(rs.getString("PHONE"));
            customer.setId(id);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return customer;
    }

    @Override
    public Customer findByName(String name) {
        String sql = "SELECT * FROM TB_CUSTOMER WHERE NAME = ?";
        Customer customer = new Customer();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            customer.setId(rs.getLong("ID"));
            customer.setPhone(rs.getString("PHONE"));
            customer.setName(name);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return customer;
    }

    @Override
    public ArrayList<Customer> findAll() {
        String sql = "SELECT * FROM TB_CUSTOMER";
        ArrayList<Customer> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int colNumber = 1;
            while (rs.next()) {                
                Customer c = new Customer();
                c.setColNumber(colNumber);
                c.setId(rs.getInt("ID"));
                c.setName(rs.getString("NAME"));
                c.setPhone(rs.getString("PHONE"));
                list.add(c);
                colNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }

    @Override
    public ArrayList<Customer> findBySearchWords(String searchWords) {
        ArrayList<Customer> list = new ArrayList<>();
        
        String sql = "SELECT * FROM TB_CUSTOMER WHERE NAME LIKE '%" + searchWords +"%'";
        
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            
            System.out.println(ps.toString());
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getLong("ID"));
                c.setName(rs.getString("NAME"));
                c.setPhone(rs.getString("PHONE"));
                
                list.add(c);
                        
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Error", 0);
        }
        
        return list;
    }
    
}
