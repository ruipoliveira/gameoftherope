/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide.Referee;

/**
 *
 * @author roliveira
 */
public class RefereeExec {
    
    public static void main(String [] args){
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, 
                (IRefereeBench) bench, (IRefereeRepository) repository, GAMES_PER_MATCH);
        
        referee.start();
    }
    
}
