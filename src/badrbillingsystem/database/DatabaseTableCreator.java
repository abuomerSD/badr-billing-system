
package badrbillingsystem.database;

import java.sql.Connection;
import java.sql.Statement;


public class DatabaseTableCreator {
    
    public static void createTables() {
        createCutomersTable();
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
}
