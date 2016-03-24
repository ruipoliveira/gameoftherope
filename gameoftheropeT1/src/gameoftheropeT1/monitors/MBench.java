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
    private boolean teamAssemble, olaTa; 
    private boolean endOfGame, acabou;  // para os jogadores se sentarem
    private int sentadosA, sentadosB;
   
    private int numTrial; 
    private int numGame; 
    private int nrEquipas, acabouReview; 
    
    private int lastPlayer, nrJogadores;
    
    private int nrPlayer;
    private int readyA;
    private int readyB;
   
    private int feito; 
   
    private int terminados;

    
    private Map<Integer, List<Integer>> coachAndTeamInBench; 
    
    private Map<Integer, List<Integer>> coachAndTeamInPull; 

    private List<Integer> constestantInPullID;
    
    public MBench(MRepository rep){
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        
        endOfGame = false;
        numGame = 0; 
        readyA = 0;
        readyB = 0;
        coachAndTeamInBench = new HashMap<>();
        coachAndTeamInPull = new HashMap<>();
        acabouReview = 0; 
        
        
        for (int i =1; i<= 2; i++){
            coachAndTeamInBench.put(i, new ArrayList<Integer>());
            coachAndTeamInPull.put(i, new ArrayList<Integer>());
        }
        
        constestantInPullID = new ArrayList<Integer>();
        
        
        nrEquipas = 0; 
        nrJogadores = 0; 
        numTrial = 0; 
        feito =6; 
        nrPlayer = 0;
        sentadosA = 0;
        sentadosB = 0;
        olaTa = false; 
        terminados = 0;
        acabou= false; 

    }
    
    
    /***********/
    /** COACH **/
    /***********/
    
   
    @Override
    public synchronized void callContestants(int coachId ) {

        
        if (numTrial > 1 || numGame > 1){
 
            while(sentadosA != 3){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            while(sentadosB != 3){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        
        
        while(newComand == false){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        
        
        
        
       // System.out.println("***********Treinador #"+coachId+" chama jogadores***********"); 

        
        callContestant = true; 
        notifyAll();
        
        
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
        
        
        System.out.println("Treinador ["+coachId+"] escolheu "+constestantInPullID.toString()); 
        
        
        coachAndTeamInPull.get(coachId).addAll(constestantInPullID);
        

        System.out.println("Na corda estão: "+coachAndTeamInPull.toString());
        
        
        
        if(coachId == 1)
            readyA = 5;
        
        else if(coachId == 2)
            readyB = 5;
        
        
        notifyAll();  // equipas prontas
        
      

        
        teamAssemble = false;
        
        while(teamAssemble == false ){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

            sentadosA =0; 
            sentadosB = 0;
        

        
        
        System.out.println("***********************Equipa #"+coachId+" formada***********************"); 

             
       
        
        
    }
    
        
    @Override
    public synchronized void callTrial(int numGame, int numTrial) {
        
        this.numGame = numGame; 
        this.numTrial = numTrial; 
        
 
        if (numTrial > 1 || numGame > 1){
            
            while(olaTa == false){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        

        
            while(feito % 6 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
            
            
            
        
        }
        

        System.out.println("\n**************************************************************\nGAME: "+numGame+"- TRIAL: "+numTrial +"\n**************************************************************\n"); 
        
     
        // System.out.println("In bench: "+coachAndTeamInBench.toString());
        //System.out.println("In pull: "+coachAndTeamInPull.toString());
        
        
        
        
       
        newComand = true; 
        notifyAll();
       
        
        
       
       

        
        
        
        
        
        
    }
    
    
    public boolean jogadorEEscolhido(int coachId, int contestId){
        
        return coachAndTeamInPull.get(coachId).contains(contestId); 
    }
    
    
    @Override
    public synchronized void reviewNotes(int coachId) {
        
      
        while(sentadosA % 3 != 0 || sentadosB % 3 != 0){
        }
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
       


        //System.out.println(coachId +" retirou notas!!!"); 
        

        
        
              
        coachAndTeamInPull.get(1).clear(); // ir retirando do campo os jogadores
        coachAndTeamInPull.get(2).clear(); // ir retirando do campo os jogadores
        
        
        
        acabouReview++; 
        while(acabouReview % 2 != 0){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        olaTa = true; 
        
        notifyAll();

    }
    

    /*****************/
    /** CONTESTANTS **/
    /*****************/
    
    @Override
    public synchronized void seatDown(int coachId, int contestId) {
      
       
        terminados++; // espera por todos
        
        
        while(terminados % 6 != 0){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        nrPlayer = 0 ;
        readyB = 0;
        readyA = 0; 

        
        if (coachId ==1)
            sentadosA++; 
        else if (coachId == 2)
            sentadosB++; 
        
        System.out.println("Player #"+contestId+" of team #"+coachId+" is seat down  | A:"+sentadosA+ "; B: "+sentadosB ); 


        teamAssemble = false; 
 
        notifyAll();  
        
    }

    
    public boolean estatudosentado(){
        return sentadosA + sentadosB == 6; 
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
        
        
        if (numTrial ==1 && numGame ==1){
            
                coachAndTeamInBench.get(coachId).add(contestId);
                nrPlayer++;
        }else{
            
                nrPlayer++; 
        }

        notifyAll();
        
  
        
        if(coachId == 1) {

           while(readyA == 0){
               try {
                   wait();
               } catch (InterruptedException ex) {
                   Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           readyA--;
        }
              
        else if(coachId == 2){

           while(readyB == 0){
               try {
                   wait();
               } catch (InterruptedException ex) {
                   Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           readyB--;
        }
        
        
        nrJogadores++; 
        while(nrJogadores % 10 != 0){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                 
        teamAssemble = true;

        newComand = false; 

        
        olaTa = false; 
        
        callContestant = false; 
        notifyAll();             
        
        
        
                
       
        
    }


    
    
    @Override
    public boolean endOfTheGame(){

        if (numGame <= 3){
            return true; 
        }else{
            return false; 
        }
        
    }
    
    
}