/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide.Coach;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author roliveira
 */
public class CoachExec {
    private final static int OPPOSING_TEAMS = 2; 
    public static void main(String [] args)  throws IOException{

        ArrayList<Coach> coach = new ArrayList<>(OPPOSING_TEAMS);
    
        for (int idc = 1; idc <= OPPOSING_TEAMS ; idc++){
            coach.add(new Coach(idc, (ICoachBench) bench, (ICoachPlayground) playground,
                    (ICoachSite) site, repository));
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
