
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
        String sql = "INSERT INTO TB_PRODUCT (NAME, PRICE, IMAGE) VALUES (?,?,?);";
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
        String sql = "UPDATE TB_PRODUCT SET NAME = ?, PRICE = ?, IMAGE = ? WHERE ID = ?;";
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
        String sql = "DELETE FROM TB_PRODUCT WHERE ID = ?;";
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
        String sql = "SELECT * FROM TB_PRODUCT WHERE ID = ?;";
        Product p = new Product();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            p.setName(rs.getString("NAME"));
            p.setPrice(rs.getDouble("PRICE"));
            p.setImage(rs.getString("IMAGE"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return p;
    }

    @Override
    public Product findByName(String name) {
        String sql = "SELECT * FROM TB_PRODUCT WHERE NAME = ?;";
        Product p = new Product();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            p.setName(name);
            p.setId(rs.getLong("ID"));
            p.setImage(rs.getString("IMAGE"));
            p.setPrice(rs.getDouble("PRICE"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return p;
    }

    @Override
    public ArrayList<Product> findAll() {
        String sql = "SELECT * FROM TB_PRODUCT";
        ArrayList<Product> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("ID"));
                p.setImage(rs.getString("IMAGE"));
                p.setName(rs.getString("NAME"));
                p.setPrice(rs.getDouble("PRICE"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }

    @Override
    public ArrayList<Product> findBySearchWords(String searchWords) {
        String sql = "SELECT * FROM TB_PRODUCT WHERE NAME LIKE '%" + searchWords +"%'";
        ArrayList<Product> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Product p = new Product();
                p.setId(rs.getLong("ID"));
                p.setImage(rs.getString("IMAGE"));
                p.setName(rs.getString("NAME"));
                p.setPrice(rs.getDouble("PRICE"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return list;
    }
    
    
}
