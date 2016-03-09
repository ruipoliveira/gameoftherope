/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.sharing;
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.main.Constant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roliveira
 */
public class MBench implements ICoachBench, IContestantsBench{
    
    private int totalContestantsBench; // numero total de jogadores no banco 
    private boolean callContestant; 
    private boolean newComand; 
    private boolean teamAssemble; 
    private int nrContestantInPull; 
   
    public MBench(){
        callContestant = false; 
        newComand = false; 
        nrContestantInPull= 0; 
    }
    
    
    /**************/
        /** COACH **/
        /**
     * @param coachId************/ 
    
    @Override
    public synchronized void callContestants(int coachId) {
        
        while(newComand == false){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        callContestant = true; 
        notify();  
        
        
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
        
        while (callContestant ==false){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        nrContestantInPull++; 
        if (nrContestantInPull == Constant.CONTESTANTS_IN_TRIAL){
            teamAssemble = true; 
            notifyAll();
        }
        

        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }




}
