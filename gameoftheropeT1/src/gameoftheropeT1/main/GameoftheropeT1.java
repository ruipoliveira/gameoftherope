package gameoftheropeT1.main;

import gameoftheropeT1.domain.*; 
import gameoftheropeT1.interfaces.*; 
import gameoftheropeT1.monitors.MBench;
import gameoftheropeT1.monitors.MPlayground;
import gameoftheropeT1.monitors.MRepository;
import gameoftheropeT1.monitors.MSite;
import gameoftheropeT1.state.ERefereeState;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class GameoftheropeT1 {

    public final static int OPPOSING_TEAMS = 2; 
    public final static int GAMES_PER_MATCH = 3; 
    public final static char GAME_CONTINUATION = 'C';
    public final static char GAME_END = 'E'; // constant created
    public final static int CONTESTANTS_IN_TRIAL = 3; 
    public final static int MAX_TRAILS_PER_GAME = 6; 
    public final static int KNOCK_OUT = 4;  
    public final static int ELEMENTS_IN_TEAM = 5; 
    
    public final static int NUM_OF_COACHES = 2;  // num de treinadores
    
    
    
    /**
     * Ficheiro main!
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //String logname = "logging"+hour()+".txt";
        String logname = "cenas.log";

        MRepository repository = new MRepository(logname, OPPOSING_TEAMS ,ELEMENTS_IN_TEAM );
        
        
        // This hook allows the simulation always close the logging file even 
        // when the simulation is forced to close.
        //ShutdownHook shutdownHook = new ShutdownHook(repository);
        //Runtime.getRuntime().addShutdownHook(shutdownHook);
        
        
        MBench bench = new MBench(repository);
        
        MPlayground playground = new MPlayground(repository);
        
        MSite site = new MSite(repository);
        
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, (IRefereeBench) bench, (IRefereeRepository) repository);

        
        ArrayList<Coach> coach = new ArrayList<>(OPPOSING_TEAMS);

        ArrayList<Contestant> contestant = new ArrayList<>(ELEMENTS_IN_TEAM);
        
        for (int idc = 1; idc <= 2 ; idc++){
            coach.add(new Coach(idc, (ICoachBench) bench, (ICoachPlayground) playground,
                    (ICoachSite) site, repository));
            
            for (int idct = 1; idct <= 5; idct++){
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
        
        repository.endWriting();
        
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
// terminar com isto de uma vez!!!!!!!!!!!!
class ShutdownHook extends Thread {
    MRepository log;
    ShutdownHook(MRepository log) {
        this.log = log;
    }
    @Override
    public void run() {
        log.endWriting();
    }
}
