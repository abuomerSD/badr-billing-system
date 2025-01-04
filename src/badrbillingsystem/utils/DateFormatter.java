
package badrbillingsystem.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatter {
    
//    public static SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
    
    DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    public String format(LocalDateTime date) {
        String formatedDate = date.format(df);
        return formatedDate;
    }
    
    
}
