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
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class GameoftheropeT1 {

    public final static int OPPOSING_TEAMS = 2; 
    public final static int CONTESTANTS_IN_TRIAL = 3; 
    public final static int KNOCK_OUT = 4;  
    public final static int NUM_OF_COACHES = 2;
    
    public final static int MAX_TRAILS_PER_GAME = 20; 
    public final static int ELEMENTS_IN_TEAM = 6; 
    public final static int GAMES_PER_MATCH = 100; 


    public static void main(String[] args) throws IOException {
        
        String logname = "gameoftherope_"+hour()+".log";
        MRepository repository = new MRepository(logname, OPPOSING_TEAMS ,ELEMENTS_IN_TEAM );
        
        MBench bench = new MBench(repository, GAMES_PER_MATCH, CONTESTANTS_IN_TRIAL, ELEMENTS_IN_TEAM, OPPOSING_TEAMS);
        
        MPlayground playground = new MPlayground(repository, MAX_TRAILS_PER_GAME);
        
        MSite site = new MSite(repository);
        
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, 
                (IRefereeBench) bench, (IRefereeRepository) repository, GAMES_PER_MATCH);

        ArrayList<Coach> coach = new ArrayList<>(OPPOSING_TEAMS);

        ArrayList<Contestant> contestant = new ArrayList<>(ELEMENTS_IN_TEAM);
        
        for (int idc = 1; idc <= 2 ; idc++){
            coach.add(new Coach(idc, (ICoachBench) bench, (ICoachPlayground) playground,
                    (ICoachSite) site, repository));
            
            for (int idct = 1; idct <= ELEMENTS_IN_TEAM; idct++){
                contestant.add(new Contestant(idct, idc, (IContestantsBench) bench,
                    (IContestantsPlayground) playground, repository, site));
            }
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

    }

    /**
     * 
     * @return Hora atual em formato HH:mm:ss
     */
    public static String hour(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime();
        return sdf.format(hora);
    }

}

