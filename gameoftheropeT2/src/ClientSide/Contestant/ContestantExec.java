/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide.Contestant;

import java.util.ArrayList;

/**
 *
 * @author roliveira
 */
public class ContestantExec {
    public final static int ELEMENTS_IN_TEAM = 5; 
    private final static int OPPOSING_TEAMS = 2; 
    public static void main(String [] args){
        
        ArrayList<Contestant> contestant = new ArrayList<>(ELEMENTS_IN_TEAM);
        
        for (int idc = 1; idc <= OPPOSING_TEAMS ; idc++){
            for (int idct = 1; idct <= ELEMENTS_IN_TEAM; idct++){
                contestant.add(new Contestant(idct, idc, (IContestantsBench) bench,
                    (IContestantsPlayground) playground, repository, site));
            }
        }
        
        for (Contestant c : contestant)
            c.start();
        
        for (Contestant c : contestant) { 
            try { 
                c.join ();
            }catch (InterruptedException e) {}
        }
        
    }
}
