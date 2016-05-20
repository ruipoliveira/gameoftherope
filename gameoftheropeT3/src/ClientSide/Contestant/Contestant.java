package ClientSide.Contestant;

import Structures.Enumerates.EContestantsState;
import Interfaces.*;
import Structures.Constants.ConstConfigs;
import Structures.VectorClock.VectorTimestamp;
import static java.lang.Thread.sleep;
import java.util.Arrays;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class Contestant extends Thread{
    private final BenchInterface bench;
    private final PlaygroundInterface playground;
    private final RepositoryInterface repository;
    
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    private final int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    public Contestant(int contId, int coachId, RepositoryInterface repository, PlaygroundInterface playground, 
            BenchInterface bench){
        this.bench = bench;
        this.playground = playground;
        this.repository = repository;
        this.coachId = coachId;
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        contestStrength = generateStrength();
        updateStrength(coachId,contId,contestStrength);  
        
        myClock = new VectorTimestamp(ConstConfigs.ELEMENTS_IN_TEAM + ConstConfigs.OPPOSING_TEAMS + 1, contId + ConstConfigs.OPPOSING_TEAMS + 1);

    }
    
    /**
     * Esta função representada o ciclo de vida de um jogador.
     */
    @Override
    public void run() {
       
        boolean endOp = true; 
        
        do {
            switch(this.state){                             
                case SEAT_AT_THE_BENCH:
                    myClock.increment(); // added
                    followCoachAdvice (coachId, contId);  
                    myClock.update(receivedClock);
                    
                    if (endOperCoach(coachId)){
                        myClock.increment(); // added
                        endOp = false;
                        myClock.update(receivedClock); // added
                        break;
                    } 
                    myClock.increment(); // added
                    state = EContestantsState.STAND_IN_POSITION;
                    updateContestantState(coachId, contId, state);
                    myClock.update(receivedClock);
                break;
                
                case STAND_IN_POSITION:
                    if (isPlayerSelected(coachId,contId) ){
                        myClock.increment(); // added
                        getReady(coachId, contId);
                        myClock.update(receivedClock); // added
                        
                        myClock.increment(); // added
                        state = EContestantsState.DO_YOUR_BEST;
                        updateContestantState(coachId, contId, state);
                        myClock.update(receivedClock); // added
                    }
                    else{
                        myClock.increment(); // added
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                        updateContestantState(coachId, contId, state);
                        myClock.update(receivedClock); // added
                        
                        myClock.increment();
                        contestStrength++;
                        updateStrengthAndWrite(coachId, contId, contestStrength);
                        myClock.update(receivedClock); // added
                    }
                break;
                
                case DO_YOUR_BEST:
                    myClock.increment(); // added
                    amDone(coachId, contId, contestStrength); 
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    seatDown(coachId,contId); 
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    contestStrength--;
                    updateStrengthAndWrite(coachId,contId, contestStrength);
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    state = EContestantsState.SEAT_AT_THE_BENCH;
                    updateContestantState(coachId, contId, state);
                    myClock.update(receivedClock); // added
                break;    
            }
            
        } while (endOp); 
        
        System.out.println("Fim jogador #"+coachId); 
    }
   
    /**
     * players go to seat down at the bench
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     */
    private void seatDown(int coachId, int contestId){
        
        bench.seatDown(coachId, contestId); 
    }
    
    /**
     * verify if the player is selected to pull the rope
     * return true if is selected
     * return false, otherwise
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     * @return 
     */
    private boolean isPlayerSelected(int coachId, int contestId){
        
        return bench.isPlayerSelected(coachId,contId); 
    }
    
    /**
     * contestants follow the coach instructions for the game
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     */
    private void followCoachAdvice(int coachId, int contestId){
       
       bench.followCoachAdvice(coachId, contestId);
       
    }
    
    /**
     * players are playing the game
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the contestant strength 
     */
    private void amDone(int coachId, int contId, int contestStrength){
        
        playground.amDone(coachId, contId, contestStrength);
    }
    
    /**
     * players that will play the game, positioning into the field
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId  is the contestant identifier (ID)
     */
    private void getReady(int coachId, int contestId){
        
        playground.getReady(coachId, contestId);
    }

    /**
     * verify if the coach operation was terminated
     * return true if the coach operation is terminated
     * return false, otherwise
     * 
     * @param idCoach is the coach identifier (ID)
     * @return 
     */
    private boolean endOperCoach(int idCoach){
        
        //return true ;//site.endOperCoach(idCoach); 
    }
    
    /**
     * Permite aceder à força do respectivo jogador. 
     * @return contestStrength 
     */
    public int getStrength(){
        return contestStrength; 
    }
    
    /**
     * Permite gerar de forma aleatoria a força associada a cada jogador.
     * @return int - valor inteiro entre 10 e 20 
     */
    private int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }

    /**
     * updates the strength of the contestants 
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the contestant strength
     */
    private void updateStrength(int coachId, int contId, int contestStrength) {
        
    }

    
    /**
     * Permite atualizar o estado actual do jogador
     * @param state 
     */
    public void setState(EContestantsState state) {
        this.state = state;
    }
    
    /**
     * Permite aceder ao estado actual do jogador 
     * @return state
     */
    public EContestantsState getCurrentState() {
        return state;
    }
    
    /**
     * updates the contestants current state
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param state is the contestant state
     */
    private void updateContestantState(int coachId, int contId, EContestantsState state) {
        
    }
    
    /**
     * updates and write the strength of contestants
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the strength of contestants
     */
    private void updateStrengthAndWrite(int coachId, int contId, int contestStrength) {

       
    }

}