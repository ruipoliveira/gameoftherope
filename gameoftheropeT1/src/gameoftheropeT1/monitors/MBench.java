/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
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
    private boolean endOfGame;  // para os jogadores se sentarem
    private boolean sentados;
    private int nrContestantsInPull; 
    private int nrContestantsInTrial; // para verificar se todos jogadores estão no banco
    
    private int lastPlayer;
   
    public MBench(MRepository rep){
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        nrContestantsInPull = 0; 
        nrContestantsInTrial = Constant.CONTESTANTS_IN_TRIAL; 
        
        endOfGame = false;
        sentados = false;
        
    }
    
    
    /***********/
    /** COACH **/
    /***********/
    
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
    

    /*****************/
    /** CONTESTANTS **/
    /*****************/
    
    @Override
    public synchronized boolean seatDown(int coachId, int contestId) {
        
        while(endOfGame == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        nrContestantsInTrial--;  // vao saindo do campo
        if(nrContestantsInTrial == 0) {
            sentados = true;
            notifyAll();
            nrContestantsInTrial = Constant.CONTESTANTS_IN_TRIAL; 
        }
        
        return sentados;
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
        
        /*apenas o ultimo jogador avisa o treinador que a equipa está */
        nrContestantsInPull++; 
        if (nrContestantsInPull == Constant.CONTESTANTS_IN_TRIAL){
            nrContestantsInPull=0; 
            teamAssemble = true; 
            notifyAll();
        }
        
    }




}
