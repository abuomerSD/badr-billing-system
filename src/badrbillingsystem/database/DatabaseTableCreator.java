
package badrbillingsystem.database;

import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DatabaseTableCreator {
    
    public static void createTables() {
        createCutomersTable();
        createProductsTable();
        createUsersTable();
        insertAdminUser();
    }
    
    private static void createCutomersTable(){
        String sql = "CREATE TABLE  IF NOT EXISTS TB_CUSTOMER (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	NAME VARCHAR(100) UNIQUE, \n" +
                            "	PHONE VARCHAR(100)\n" +
                            ") ;";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    private static void createProductsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_PRODUCT (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	NAME VARCHAR(100) UNIQUE,\n" +
                            "	PRICE DOUBLE, \n" +
                            "	IMAGE VARCHAR(100)\n" +
                            ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
        
    }

    private static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS TB_USER (\n" +
                            "	ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	USERNAME VARCHAR(30) UNIQUE,\n" +
                            "	PASSWORD VARCHAR(50)\n" +
                            ");";
        
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString());
        }
    }

    private static void insertAdminUser() {
        
    }
}
