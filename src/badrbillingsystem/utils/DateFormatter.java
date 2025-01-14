
package badrbillingsystem.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    
//    public static SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter df1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public String format(LocalDate date) {
        String formatedDate = date.format(df1);
        return formatedDate;
    }
    
    
}
