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
 * @author gabriel
 */
public class MPlayground implements IRefereePlayground, ICoachPlayground, IContestantsPlayground{

    private boolean newTrial;
    private boolean newComand;
    private double strength;
    
    private int nCoaches; // para  informReferee  os 2 treinadores tem de informar o arbtiro 
    //que as suas equipas estao prontas
    
    private int lastPlayer;
    
    public MPlayground(MRepository rep){
        newTrial = false; 
        newComand = false;
        
        strength = 0;
        
        
        nCoaches = 0;
    }
    
      ///////////       ////////////////////////////////
    /////////// REFEREE ///////////////////////////////
    
    
    
    @Override
    public synchronized void callTrial(int numTrial) {
        
        newComand = true; 
        notifyAll();
                
    }

    @Override
    public synchronized void startTrial() {
        
        while(newTrial == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized char assertTrialDecision() { // isto nao e bem assim, temos que ver melhor
        
        char decision;
        
        strength = generateStrength();
        
        if(strength <= 0){
            decision = Constant.GAME_END;
            return decision;
        }
        
        else{
            decision = Constant.GAME_CONTINUATION;
            return decision;
        }
            
        
    }

    ///////////       ////////////////////////////////
    /////////// COACH ///////////////////////////////
    @Override
    public synchronized void informReferee(int coachId, boolean teamAssemble) {
        
        while(teamAssemble == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        nCoaches++;
        if(nCoaches == Constant.NUM_OF_COACHES){           
            newTrial = true; 
            nCoaches = 0;
            notify(); 
        }       
    }

    
      ///////////           ////////////////////////////////
    /////////// CONTESTANTS ///////////////////////////////
    @Override
    public synchronized void getReady(int coachId, int contId) {
        
        strength = 0; 
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

  
    @Override
    public void amDone(int coachId, int contId, int contestStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    private double generateStrength(){
        return Math.random()*100; 
    }


}
