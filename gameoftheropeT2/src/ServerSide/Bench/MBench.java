package ServerSide.Bench;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Communication.ClientComm;
import Communication.CommConst;
import Communication.Message.*;
import static java.lang.Thread.sleep;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class MBench implements ICoachBench, IContestantsBench, IRefereeBench{
    private boolean callContestant; 
    private boolean newComand; 
    private boolean teamAssemble, isEndReview; 
    private int seatedA, seatedB;
    private final int opposingTeam; 
    private final int elementInTeam; 
    private final int constestantInTrial; 
    private int numTrial; 
    private int numGame; 
    private int endReview; 
    private int nrContestant;
    private int nrPlayer;
    private int readyA;
    private int readyB;
    private final int allReady; 
    private int finished;
    private final int gamesPerMatch; 
    private final int totalPlayer; 
    private final Map<Integer, List<Integer>> coachAndTeamInBench; 
    private final Map<Integer, List<Integer>> coachAndTeamInPull; 
    
    
    public MBench( int gamesPerMatch, int constestantInTrial, int elementInTeam, int opposingTeam){
        this.gamesPerMatch = gamesPerMatch; 
        this.elementInTeam = elementInTeam;
        this.constestantInTrial = constestantInTrial; 
        this.opposingTeam = opposingTeam; 
        callContestant = false; 
        newComand = false; 
        teamAssemble = false;
        numGame = 0; 
        readyA = 0;
        readyB = 0;
        coachAndTeamInBench = new HashMap<>();
        coachAndTeamInPull = new HashMap<>();
        endReview = 0; 

        totalPlayer = 2*elementInTeam;
        
        
        for (int i =1; i<= 2; i++){
            coachAndTeamInBench.put(i, new ArrayList<>());
            coachAndTeamInPull.put(i, new ArrayList<>());
        }
        
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
        
            while(nrPlayer != totalPlayer) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*escolha de jogadores que irão jogar*/
        Collections.shuffle(coachAndTeamInBench.get(coachId));

        List<Integer> constestantInPullID  = new ArrayList<>(); 


        System.out.println("Lista de equipas + jogadores: "+coachAndTeamInBench.toString());
        
        
        for (int i =1; i<=constestantInTrial; i++){
            constestantInPullID.add(coachAndTeamInBench.get(coachId).get(i)); 
        }
        
        System.out.println("Treinador ["+coachId+"] escolheu "+constestantInPullID.toString()); 
        
        
        coachAndTeamInPull.get(coachId).addAll(constestantInPullID);

        System.out.println("Na corda estão: "+coachAndTeamInPull.toString());
          
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
                Logger.getLogger(MBench.class.getName()).log(Level.SEVERE, null, ex);
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
        removeContestantsInPull(coachId, contestId);
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
    public synchronized boolean allSittingTeams(){
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
        while(nrContestant % totalPlayer != 0){
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

    /**
     * 
     * @param c
     * @return 
     */
    @Override
    public boolean endOfTheGame(int c ){
        return numGame <= gamesPerMatch;
    }

    /**
     * 
     * @param coachId
     * @param contestId 
     */
    private void removeContestantsInPull(int coachId, int contestId) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.REMOVE_CONTESTANTS_IN_PULL, coachId, contestId);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Tipo inválido!");
            System.exit(1);
        }
        con.close();
    }
    
    
}