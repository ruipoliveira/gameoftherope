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
       
        int[] solutionArray = { 1, 2, 3, 4, 5};

        shuffleArray(solutionArray);
        for (int i = 0; i < solutionArray.length; i++)
        {
          System.out.print(solutionArray[i] + " ");
        }
        System.out.println();
    
  */  
    
        
  /*      
        Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>(); 

                
        List<Integer> c = new ArrayList<Integer>();
        
        c.add(1); 
        c.add(2); 

        List<Integer> c1 = new ArrayList<Integer>();
        
        c1.add(1); 
        c1.add(2); 
        
        System.out.println(c.toString()); 
        
        
        map.put(1, c); 
        map.put(3, c1); 
        //System.out.println(map.toString()); 
        
        
        c1.add(2);
        //System.out.println(map.toString()); 
        
        System.out.println("--->"+map.toString());
        for (int c3 : map.keySet()){
            for(List j : map.values()){

                System.out.println(c3 +"->"+j.toString());
        }
        
        }
*/
        
        
        
        
        
        MBench bench = new MBench(repository);
        MPlayground playground = new MPlayground(repository);
        
        MSite site = new MSite(repository);
        
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, (IRefereeBench) bench, (IRefereeRepository) repository);


        
        ArrayList<Coach> coach = new ArrayList<>(1);

        ArrayList<Contestant> contestant = new ArrayList<>(Constant.ELEMENTS_IN_TEAM);
        
        for (int idc = 1; idc <= 1 ; idc++){
            coach.add(new Coach(idc, (ICoachBench) bench, (ICoachPlayground) playground, (ICoachSite) site, repository));
            
            for (int idct = 1; idct <= 2; idct++)
                contestant.add(new Contestant(idct, idc, (IContestantsBench) bench,
        (IContestantsPlayground) playground, repository));          
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
        
        /*
        
       
            
    
        referee.start();
        
        for (Coach c : coach)
            c.start();
        for (Contestant c : contestant)
            c.start();


  
          */      
        
    }
    
    
    public static String hour(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        return sdf.format(hora);
    }
    
    public static void shuffleArray(int[] ar)
    {
    // If running on Java 6 or older, use `new Random()` on RHS here
    Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          // Simple swap
          int a = ar[index];
          ar[index] = ar[i];
          ar[i] = a;
        }
    }
     
}
