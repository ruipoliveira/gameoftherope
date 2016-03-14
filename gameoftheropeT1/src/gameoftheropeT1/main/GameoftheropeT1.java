package gameoftheropeT1.main;

import gameoftheropeT1.domain.*; 
import gameoftheropeT1.interfaces.*; 
import gameoftheropeT1.monitors.MBench;
import gameoftheropeT1.monitors.MPlayground;
import gameoftheropeT1.monitors.MRepository;
import gameoftheropeT1.monitors.MSite;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author roliveira
 */
public class GameoftheropeT1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        String logname = "logging"+hour()+".txt";
        
        MRepository repository = new MRepository(logname, Constant.OPPOSING_TEAMS ,Constant.ELEMENTS_IN_TEAM );

        /*
        
        
        MBench bench = new MBench(repository);
        MPlayground playground = new MPlayground(repository);
        
        MSite site = new MSite(repository);
        
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, (IRefereeRepository) repository);
        
        
        ArrayList<Coach> coach = new ArrayList<>(Constant.OPPOSING_TEAMS);

        ArrayList<Contestant> contestant = new ArrayList<>(Constant.ELEMENTS_IN_TEAM);


        
        for (int idc = 1; idc <= Constant.OPPOSING_TEAMS ; idc++){
            coach.add(new Coach(idc, repository, shop));
            
            for (int idct = 1; idct <= Constant.ELEMENTS_IN_TEAM; idct++)
                contestant.add(new Contestant(idct, idc, repository));          
        }
            
    
        referee.start();
        
        for (Coach c : coach)
            c.start();
        for (Contestant c : contestant)
            c.start();


        for(Coach c : coach){
            try {
                c.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GameoftheropeT1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (Contestant c : contestant){
            try {
                c.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(GameoftheropeT1.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }     
          */      
        
    }
    
    
    public static String hour(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        return sdf.format(hora);
    }
}
