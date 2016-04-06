package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;
import static gameoftheropeT1.main.GameoftheropeT1.GAMES_PER_MATCH;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class MBench implements ICoachBench, IContestantsBench, IRefereeBench{
    private MRepository repository;
    private boolean callContestant; 
    private boolean newComand; 
    private boolean teamAssemble, isEndReview; 
    private boolean endOfGame;  // para os jogadores se sentarem
    private int seatedA, seatedB;
    private int opposingTeam; 
    private int elementInTeam; 
    private int constestantInTrial; 
    private int numTrial; 
    private int numGame; 
    private int nrTeams, endReview; 
    private int lastPlayer, nrContestant;
    private int nrPlayer;
    private int readyA;
    private int readyB;
    private int allReady; 
    private int finished;
    private int gamesPerMatch; 
    
    private Map<Integer, List<Integer>> coachAndTeamInBench; 
    
    private Map<Integer, List<Integer>> coachAndTeamInPull; 

    private List<Integer> constestantInPullID;
    
    public MBench(MRepository repository, int gamesPerMatch, int constestantInTrial, int elementInTeam, int opposingTeam){
        this.gamesPerMatch = gamesPerMatch; 
        this.elementInTeam = elementInTeam;
        this.repository = repository; 
        this.constestantInTrial = constestantInTrial; 
        this.opposingTeam = opposingTeam; 
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        endOfGame = false;
        numGame = 0; 
        readyA = 0;
        readyB = 0;
        coachAndTeamInBench = new HashMap<>();
        coachAndTeamInPull = new HashMap<>();
        endReview = 0; 

        for (int i =1; i<= 2; i++){
            coachAndTeamInBench.put(i, new ArrayList<Integer>());
            coachAndTeamInPull.put(i, new ArrayList<Integer>());
        }
        
        constestantInPullID = new ArrayList<Integer>();

        nrTeams = 0; 
        nrContestant = 0; 
        numTrial = 0; 
        allReady =6; 
        nrPlayer = 0;
        seatedA = 0;
        seatedB = 0;
        isEndReview = false; 
        finished = 0;

    }
    
    
    /**
     * The coach of the last team to become ready informs the referee. The coach waits for the trial to take place. The internal state should be saved.
     * @param coachId 
     */
    @Override
    public synchronized void callContestants(int coachId ) {

        if (numTrial > 1 || numGame > 1){
            while(seatedA != constestantInTrial){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            while(seatedB != constestantInTrial){
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

        callContestant = true; 
        notifyAll();

        while(nrPlayer != 2*elementInTeam) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*escolha de jogadores que irão jogar*/
        Collections.shuffle(coachAndTeamInBench.get(coachId));

        List<Integer> constestantInPullID  = new ArrayList<Integer>(); 


        System.out.println("Lista de equipas + jogadores: "+coachAndTeamInBench.toString());
        
        
        for (int i =1; i<=constestantInTrial; i++){
            constestantInPullID.add(coachAndTeamInBench.get(coachId).get(i)); 
        }
        
        System.out.println("Treinador ["+coachId+"] escolheu "+constestantInPullID.toString()); 
        
        
        coachAndTeamInPull.get(coachId).addAll(constestantInPullID);

        System.out.println("Na corda estão: "+coachAndTeamInPull.toString());
        
        repository.addContestantsInPull(coachId, coachAndTeamInPull.get(coachId));
  
        if(coachId == 1)
            readyA = elementInTeam;
        
        else if(coachId == 2)
            readyB = elementInTeam;

        notifyAll();

        teamAssemble = false;
        
        while(teamAssemble == false ){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        seatedA =0; 
        seatedB = 0;
        
        System.out.println("***********************Equipa #"+coachId+" formada***********************"); 
        
    }

    /**
     * The referee starts a trial and waits for its conclusion. The contestants at the ends of the rope have to be alerted for the fact. The internal state should be saved.
     * @param numGame
     * @param numTrial 
     */
    @Override
    public synchronized void callTrial(int numGame, int numTrial) {
        this.numGame = numGame; 
        this.numTrial = numTrial; 

        if (numTrial > 1 || numGame > 1){
            while(isEndReview == false){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            while(allReady % 2*constestantInTrial != 0){
                try { 
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println("\n**************************************************************\nGAME: "+numGame+"- TRIAL: "+numTrial +"\n**************************************************************\n"); 

        newComand = true; 
        notifyAll();
         
    }
    
    /**
     * verifies if the player has been chosen
     * @param coachId
     * @param contestId
     * @return isSelected
     */
    @Override
    public boolean isPlayerSelected(int coachId, int contestId){
        return coachAndTeamInPull.get(coachId).contains(contestId); 
    }

    /**
     * The coach asserts if the end of operations has arrived.
     * @param coachId 
     */
    @Override
    public synchronized void reviewNotes(int coachId) {

        while(seatedA % constestantInTrial != 0 || seatedB % constestantInTrial != 0){
        }
        try {
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        coachAndTeamInPull.get(1).clear(); 
        coachAndTeamInPull.get(2).clear();
        
        repository.removeContestantsInPull(1);
        repository.removeContestantsInPull(2);

        endReview++;
        while(endReview % opposingTeam != 0){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        isEndReview = true; 
        
        notifyAll();

    }
    
    /**
     * The contestant seats at the bench and waits to be called by the coach. The internal state should be saved.
     * @param coachId
     * @param contestId 
     */
    @Override
    public synchronized void seatDown(int coachId, int contestId) {

        finished++;

        while(finished % 2*constestantInTrial != 0){
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
            seatedA++; 
        else if (coachId == 2)
            seatedB++; 
        
        System.out.println("Player #"+contestId+" of team #"+coachId+" is seat down  | A:"+seatedA+ "; B: "+seatedB ); 

        teamAssemble = false; 
 
        notifyAll();  
        
    }

    /**
     * checks all players sitting
     * @return isSitting
     */
    @Override
    public boolean allSittingTeams(){
        return seatedA + seatedB == 2*constestantInTrial; 
    }
    
    /**
     * The contestant join the trial team if requested by the coach and waits for the referee's command to start pulling. The last contestant to join his end of the rope should alert the coach. The internal state should be saved.
     * @param coachId
     * @param contestId 
     */
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
        
        nrContestant++; 
        while(nrContestant % 40 != 0){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                 
        teamAssemble = true;
        newComand = false; 
        isEndReview = false; 
        callContestant = false; 
        notifyAll();
    }

    
    @Override
    public boolean endOfTheGame(int c ){
        if (numGame <= gamesPerMatch){
            return true; 
        }else{
            return false; 
        }
        
    }
    
    
}