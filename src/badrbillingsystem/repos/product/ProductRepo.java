
package badrbillingsystem.repos.product;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProductRepo implements IProductRepo{

    @Override
    public boolean save(Product product) {
        String sql = "INSERT INTO tbProduct (name, price, image) VALUES (?,?,?);";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getImage());
            int flag = ps.executeUpdate();
            if (flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(Product newProduct) {
        String sql = "UPDATE tbProduct SET name = ?, price = ?, image = ? WHERE id = ?;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newProduct.getName());
            ps.setDouble(2, newProduct.getPrice());
            ps.setString(3, newProduct.getImage());
            ps.setLong(4, newProduct.getId());
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
        String sql = "DELETE FROM tbProduct WHERE id = ?;";
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
    public Product findById(long id) {
        String sql = "SELECT * FROM tbProduct WHERE id = ?;";
        Product p = new Product();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            p.setName(rs.getString("name"));
            p.setPrice(rs.getDouble("price"));
            p.setImage(rs.getString("image"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return p;
    }

    @Override
    public Product findByName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Product> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Product> findBySearchWords(String searchWords) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
