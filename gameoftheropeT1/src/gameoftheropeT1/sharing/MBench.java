/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.sharing;
import gameoftheropeT1.interfaces.*;

/**
 *
 * @author roliveira
 */
public class MBench implements ICoachBench, IContestantsBench{
    
    private int totalContestantsBench; // numero total de jogadores no banco 
    
   
    public MBench(){
        
    }
    
    
    /**************/
        /** COACH **/
        /**
     * @param coachId************/ 
    
    @Override
    public synchronized void callContestants(int coachId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public synchronized void reviewNotes(int coachId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
/**************/
        /** CONTESTANTS **/
        /**
     * @param coachId* 
     * @param contestId* 
     * @return **********/ 
    
    @Override
    public synchronized boolean seatDown(int coachId, int contestId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void followCoachAdvice(int coachId, int contestId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
