/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.main.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
public class MBench implements ICoachBench, IContestantsBench, IRefereeBench{
    
    private boolean callContestant; 
    private boolean newComand; 
    private boolean teamAssemble; 
    private boolean endOfGame;  // para os jogadores se sentarem
    private int sentados;
   
    private int numTrial; 
    
    private int nrEquipas; 
    
    private int lastPlayer;
    
    private int nrPlayer;
    private int readyA;
    private int readyB;
   
    private int feito; 
   
    private int terminados;

    
    Map<Integer, List<Integer>> coachAndTeamInBench; 
    
    Map<Integer, List<Integer>> coachAndTeamInPull; 

    
    public MBench(MRepository rep){
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        
        endOfGame = false;
        
        readyA = 0;
        readyB = 0;
        coachAndTeamInBench = new HashMap<>();
        coachAndTeamInPull = new HashMap<>();

        for (int i =1; i<= 2; i++){
            coachAndTeamInBench.put(i, new ArrayList<Integer>(5));
            coachAndTeamInPull.put(i, new ArrayList<Integer>(3));

        }


        nrEquipas = 0; 
        
        numTrial = 0; 
        feito =6; 
        nrPlayer = 0;
        sentados = 0;
        terminados = 0;

    }
    
    
    /***********/
    /** COACH **/
    /***********/
    
   
    @Override
    public synchronized void callContestants(int coachId ) {

        
        
        while(newComand == false){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        
        
        System.out.println("***********Treinador #"+coachId+" vamos jogar novo trial!***********"); 

        
        callContestant = true; 
        notifyAll();
        
        
        System.out.println("-------------------"+coachId); 
        while(nrPlayer != 10) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        

        // depois de todos estarem prontos
        Collections.shuffle(coachAndTeamInBench.get(coachId));

        List<Integer> constestantInPullID  = new ArrayList<Integer>(); 

        System.out.println("Lista de equipas + jogadores: "+coachAndTeamInBench.toString());



        for (int i =1; i<=3; i++){
            constestantInPullID.add(coachAndTeamInBench.get(coachId).get(i)); 
        }
        
        
        System.out.println(coachId+" escolheu "+constestantInPullID.toString()); 
        
        
        coachAndTeamInPull.get(coachId).addAll(constestantInPullID);
        

        System.out.println(" In pull: "+coachAndTeamInPull.toString());
        
        
        
        if(coachId == 1)
            readyA = 5;
        
        else if(coachId == 2)
            readyB = 5;
        
        
        notifyAll();  // equipas prontas
        
      
        System.out.println("Em espera da equipa #"+coachId); 
        //wait até que os jogadores fiquem posicionados 
       // teamAssemble = false; 
        teamAssemble = false;
        
        while(teamAssemble == false ){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        
        
        System.out.println("***********************Equipa #"+coachId+" formada***********************"); 

     
    }
    
        
    @Override
    public synchronized void callTrial(int numTrial) {
        
        while(feito != 6){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        feito = 0; 
        
        this.numTrial = numTrial; 
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Trial #" + numTrial+" %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        
        

        System.out.println("In bench: "+coachAndTeamInBench.toString());
        System.out.println("In pull: "+coachAndTeamInPull.toString());
        
        
        
        
        
        
       /** if(numTrial > 1){
            while(sentados == false){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sentados = false;
            newComand = true;
            notifyAll();
        }*/
        
        newComand = true; 
        notifyAll();
        
        
    }
    
    
    public boolean jogadorEEscolhido(int coachId, int contestId){
        
        return coachAndTeamInPull.get(coachId).contains(contestId); 
    }
    
    
    @Override
    public synchronized void reviewNotes(int coachId) {
        while(sentados != 6){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        sentados =0; 
        System.out.println("Estão todos sentados, bom jogo equipa!");
        notifyAll();
    }
    

    /*****************/
    /** CONTESTANTS **/
    /*****************/
    
    @Override
    public synchronized void seatDown(int coachId, int contestId) {
      
        /*
        terminados++; // espera por todos
        System.out.println("Player #"+contestId+" of team #"+coachId+" is seat down");
        

        
        while(terminados != 6){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }

   */
        //System.out.println("Total in bench: "+coachAndTeamInBench.toString()); 
       // System.out.println("In pull: "+coachAndTeamInPull.toString()); 
        
        
        //System.out.println("Player #"+contestId+" of team #"+coachId+" is seat down");
        coachAndTeamInPull.get(1).clear(); // ir retirando do campo os jogadores
        coachAndTeamInPull.get(2).clear(); // ir retirando do campo os jogadores

        
        nrPlayer = 0 ;
        readyB = 0;
        readyA = 0; 
        terminados = 0;
        sentados++;
        teamAssemble = false; 
        feito++; 
        notifyAll();
  
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
        
        
        if (numTrial ==1){
            
                System.out.println("chamar jogadores.. "+coachId+"-"+contestId); 
                coachAndTeamInBench.get(coachId).add(contestId);
                nrPlayer++;// junta os jogadores nas equipas 
        }else{
            
                //System.out.println(">>>>>>>>><<"+nrPlayer);
                nrPlayer++; 
        }

        notifyAll();
        
  
        
        /// espera que o coach faça shuffle ///////////////////
        if(coachId == 1)
        {

           while(readyA == 0){
               try {
                   wait();
               } catch (InterruptedException ex) {
                   Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           readyA--;
        }
              
        else if(coachId == 2)
        {

           while(readyB == 0){
               try {
                   wait();
               } catch (InterruptedException ex) {
                   Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           

           readyB--;
        }
        
        
 
                 
        teamAssemble = true;

        newComand = false; 

        callContestant = false; 
        notifyAll();             
        
    }

}
