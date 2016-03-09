package gameoftheropeT1.main;

import gameoftheropeT1.sharing.*;
import gameoftheropeT1.domain.*; 
import gameoftheropeT1.interfaces.*; 
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author roliveira
 */
public class GameoftheropeT1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String logname = "";
    
        logname = "logging"+hour()+".txt";
        
        System.out.print(logname);
        
            
        
    }
    
    
    public static String hour(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        return sdf.format(hora);
    }
}
