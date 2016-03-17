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
    
    private int totalContestantsBench; // numero total de jogadores no banco 
    private boolean callContestant; 
    private boolean newComand; 
    private boolean teamAssemble; 
    private boolean endOfGame;  // para os jogadores se sentarem
    private boolean sentados;
    private int nrContestantsInPull; 
    private int nrContestantsInTrial; // para verificar se todos jogadores estão no banco
    private int numTrial; 
    
    
    private int nrEquipas; 
    
    private int lastPlayer;
    private int strength;
    
    Map<Integer, List<Integer>> coachAndTeamInBench; 

    
    public MBench(MRepository rep){
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        nrContestantsInPull = 0; 
        nrContestantsInTrial = Constant.CONTESTANTS_IN_TRIAL; 
        
        endOfGame = false;
        sentados = false;
        
        coachAndTeamInBench = new HashMap<>();
        for (int i =1; i<= 2; i++){
            coachAndTeamInBench.put(i, new ArrayList<Integer>());
        }


        strength = 0; 
        
        nrEquipas = 0; 
        
        
        numTrial = 0; 
    }
    
    
    /***********/
    /** COACH **/
    /***********/
    
   
    @Override
    public synchronized void callContestants(int coachId ) {
        System.out.println("Treinador #"+coachId+" espera por um novo jogo"); 
        
        while(newComand == false){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       

        
       // List<Integer> 
        
        //for(coachAndTeamInBench)
            
            
        callContestant = true; 
        notifyAll();  
        teamAssemble = false; 
        
        
        System.out.println("Em espera da equipa"); 
        //wait até que os jogadores fiquem posicionados 
        while(teamAssemble == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        System.out.println("Equipa #"+coachId+" formada"); 
        teamAssemble = false; 
        
    }
    
        
    @Override
    public synchronized void callTrial(int numTrial) {
        this.numTrial = numTrial; 
        
        System.out.println("Trial" + numTrial);
        newComand = true; 
        notifyAll();
                
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
        
        
        System.out.println("chamar jogadores.."); 

        coachAndTeamInBench.get(coachId).add(contestId); 
    
        /*apenas o ultimo jogador avisa o treinador que a equipa está */
        
        //nrContestantsInPull++;  
        //System.out.println(nrContestantsInPull +" -->" + coachAndTeamInBench.toString()); 
        
        
        
        if (coachAndTeamInBench.get(coachId).size() == 5){
            
            List<Integer> constestantInPullID = new ArrayList<Integer>(); 

            System.out.println("Lista de equipas + jogadores: "+coachAndTeamInBench.toString());

            Collections.shuffle(coachAndTeamInBench.get(coachId)); 
                
            for (int i =1; i<=3; i++){
                constestantInPullID.add(coachAndTeamInBench.get(coachId).get(i)); 
            }
            
            
            System.out.println("Equipa #"+coachId+" vão jogar: "+ constestantInPullID.toString());
            
            teamAssemble = true; 
            notifyAll();
            
            
        }
        
    }


    public static int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }


    
    private void shuffleArray(int[] ar){
        Random rnd = ThreadLocalRandom.current();
        
        for (int i = ar.length - 1; i > 0; i--){
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    
    private int[] contestantInTrial(int[] arrayIdContestant){
        String s = "";
        
        for (int i =0; i<arrayIdContestant.length; i++)
            arrayIdContestant[i] = i+1; 
         
        shuffleArray(arrayIdContestant);
        for (int i = 0; i < 2; i++)
            s += arrayIdContestant[i]+",";
        
        return Arrays.stream(s.split(",")).filter(word -> !word.isEmpty()).mapToInt(Integer::parseInt).toArray();

        
    }


}
