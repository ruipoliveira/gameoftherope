/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
import gameoftheropeT1.domain.Contestant;
import gameoftheropeT1.interfaces.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class MPlayground implements IRefereePlayground, ICoachPlayground, IContestantsPlayground{
    private int newTrial;
    private boolean newComand;
    private double strength;

    private Map<Integer, List<Integer>> coachAndTeam;
    
    private Map<Integer, List<Integer>> strengthTeam;

        
    private boolean startTrial; 
    private int numTrial; 
    private int nCoaches; // para  informReferee  os 2 treinadores tem de informar o arbtiro 
    //que as suas equipas estao prontas
    
    private int lastPlayer;
    private int pulls, cenas;
    
    private int posPull, contI; 
    
    private int seguidosA, seguidosB; 
    
    
    private boolean ultimoPuxou, fim;
    
    private int resultTeamA, resultTeamB;  
    
    
      public MPlayground(MRepository rep){
        newTrial = 0; 
        newComand = false;
        numTrial = 0; 
        strength = 0;
        contI=0;
        resultTeamA = resultTeamB = 0; 
        startTrial = false;
        fim = false; 
        
        
        coachAndTeam = new HashMap<Integer, List<Integer>>(); 
        
        strengthTeam = new HashMap<>(); 
        for(int i =1; i< 3; i++ ){
            strengthTeam.put(i, new ArrayList<Integer>()); 
        }
        
        ultimoPuxou = false;
        pulls = 0;
        
        nCoaches = 0;
        
        posPull = 0; 
        
        cenas=0; 
        
        seguidosA = seguidosB =0; 
    }
    
      ///////////       ////////////////////////////////
    /////////// REFEREE ///////////////////////////////
    
    


    @Override
    public synchronized void startTrial(int nrTrial) {
        numTrial = nrTrial; 

        
        while(newTrial != 2){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        newTrial =0; 
      
        
        startTrial = true; 
        
        fim = false; 
        notifyAll(); 
        
        
        
        
        
    }

    @Override
    public synchronized char assertTrialDecision() { // isto nao e bem assim, temos que ver melhor
        
        
        
        while(ultimoPuxou == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        //System.out.println("Trial acabou!!"); 


        
        //System.out.println(strengthTeam.get(1));
        //System.out.println(strengthTeam.get(2));

        for (int i =0; i<3; i++ ){
            resultTeamA += strengthTeam.get(1).get(i) ;
            resultTeamB += strengthTeam.get(2).get(i);
            
            contI++;
            notifyAll();
        }
        
        System.out.println("ResultA = "+resultTeamA+"; ResultB = " + resultTeamB); 

        if (resultTeamA > resultTeamB){
            posPull--;
        }
        
        else if (resultTeamA < resultTeamB){        
            posPull++;
            
        }else{
            System.out.println("Jogo Empatado!!"); 
        }
        
        //System.out.println(seguidosA +" ->"+seguidosB);
        
        System.out.println("POSIÇÃO DA CORDA: " + posPull); 
        resultTeamA = resultTeamB = 0;          
        
        
        
        while( contI % 3 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        strengthTeam.get(1).clear();
        strengthTeam.get(2).clear();
        
        
      //  System.out.println("Tudo limpo forças: "+strengthTeam.toString()); 
        

        fim = true; 
      

       // System.out.println("****************************"+numTrial); 
        
        if (numTrial == 6){  // knock out
            //seguidosA = seguidosB = 0;      
            return 'E';
        } 
        else if (posPull >= 4 || posPull <= -4){
            return 'K'; 
        }
        else{
            resultTeamA = resultTeamB = 0; 
            return 'C';
        }
    }

    ///////////       ////////////////////////////////
    /////////// COACH ///////////////////////////////
    @Override
    public synchronized void informReferee(int coachId) {
        
       // System.out.println("Coach#" +coachId+ " informa arbitro.."); 
        newTrial++;
        //System.out.println(newTrial); 
        while(newTrial % 2 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
     //   System.out.println("Tudo informado!!"); 

        notifyAll();
            
  
         
         
    }

    
    public int getPositionPull(){
        return posPull; 
    }
    
    public void setPositionPull(int posPull){
        this.posPull = posPull; 
    }
    
    
      ///////////           ////////////////////////////////
    /////////// CONTESTANTS ///////////////////////////////
    @Override
    public synchronized void getReady(int coachId, int contId) {
        
        
        while(startTrial == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    
        
         
         
         
    }

  
    @Override
    public synchronized void amDone(int coachId, int contId, int contestStrength) {
        
        while( contI % 3 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                

        strengthTeam.get(coachId).add(contestStrength);
        
        System.out.println("["+coachId+"] #"+contId + " PUXA CRLHHOOO! | Força da Equipa: "+strengthTeam.toString()); 

        
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
        }


        pulls++;
        while(pulls % 6 != 0){
            try {
                wait();
                        } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        
        ultimoPuxou = true; 

        startTrial = false; 

        cenas++; 
        
        notifyAll();
        
    }


}