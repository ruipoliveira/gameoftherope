package ClientSide.Coach;

import Communication.ConstConfigs;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class CoachExec {
    public static void main(String [] args) throws IOException{
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        System.out.println("******************************************************************\nEntity coach has started!");
        System.out.println("******************************************************************");

        ArrayList<Coach> coach = new ArrayList<>(ConstConfigs.OPPOSING_TEAMS);
    
        for (int idc = 1; idc <= ConstConfigs.OPPOSING_TEAMS ; idc++){
            coach.add(new Coach(idc));
        }
        
        for (Coach c : coach)
            c.start();
        
        for(Coach c : coach){
            try{
                c.join();
            } catch (InterruptedException e){}
        }
       
    }
}
