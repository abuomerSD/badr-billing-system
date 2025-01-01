
package badrbillingsystem.database;

import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class DatabaseTableCreator {
    
    public static void createTables() {
        createCutomersTable();
        createProductsTable();
    }
    
    private static void createCutomersTable(){
        String sql = "CREATE TABLE  IF NOT EXISTS tbCustomer (\n" +
                            "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	name VARCHAR(100) UNIQUE, \n" +
                            "	phone VARCHAR(100)\n" +
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
        String sql = "CREATE TABLE IF NOT EXISTS tbProduct (\n" +
                            "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                            "	name VARCHAR(100) UNIQUE,\n" +
                            "	price DOUBLE, \n" +
                            "	image VARCHAR(100)\n" +
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
}
