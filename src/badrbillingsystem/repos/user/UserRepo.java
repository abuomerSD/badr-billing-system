
package badrbillingsystem.repos.user;

import badrbillingsystem.database.DBConnection;
import badrbillingsystem.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UserRepo implements IUserRepo{

    @Override
    public boolean save(User user) {
        String sql = "INSERT INTO TB_USER (USERNAME, PASSWORD) VALUES (?,?);";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean update(User newUser) {
        String sql = "UPDATE TB_USER SET USERNAME = ?, PASSWORD = ?  WHERE ID = ? ;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword());
            ps.setLong(3, newUser.getId());
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public boolean delete(long id) {
        String sql = "DELETE FROM TB_USER WHERE ID = ? ;";
        boolean status = false;
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            int flag = ps.executeUpdate();
            if(flag == 1) status = true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return status;
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT * FROM TB_USER WHERE ID = ?";
        User u = new User();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            u.setId(id);
            u.setPassword(rs.getString("PASSWORD"));
            u.setUsername(rs.getString("USERNAME"));
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return u;
    }

    @Override
    public User findByName(String name) {
        String sql = "SELECT * FROM TB_USER WHERE USERNAME = ?";
        User u = new User();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            u.setId(rs.getLong("ID"));
            u.setPassword(rs.getString("PASSWORD"));
            u.setUsername(name);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return u;
    }

    @Override
    public ArrayList<User> findAll() {
        String sql = "SELECT * FROM TB_USER;";
        ArrayList<User> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = new User(rs.getLong("ID"),
                                rs.getString("USERNAME"),
                                rs.getString("PASSWORD"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list;
    }

    @Override
    public ArrayList<User> findBySearchWords(String searchWords) {
        String sql = "SELECT * FROM TB_USER WHERE USERNAME LIKE '%" + searchWords +"%'";
        ArrayList<User> list = new ArrayList<>();
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                User u = new User(rs.getLong("ID"),
                                rs.getString("USERNAME"),
                                rs.getString("PASSWORD"));
                list.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
        return list;
    }
    
}
