
package badrbillingsystem.main;

import badrbillingsystem.database.DatabaseTableCreator;
import badrbillingsystem.ui.Home;
import java.awt.Component;
import java.awt.ComponentOrientation;

public class Main {

    public static void main(String[] args) {
        Home home = new Home();
        DatabaseTableCreator.createTables();
        home.show();
    }
    
}
