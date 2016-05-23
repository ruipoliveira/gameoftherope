package ClientSide.Referee; 

import Structures.Enumerates.ERefereeState;
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
public class Referee extends Thread{
    public final static int PULL_CENTER = 0;
    public final static char GAME_CONTINUATION = 'C';
    public final static char GAME_END = 'E';
    public final static char KNOCK_OUT_A = 'A';
    public final static char KNOCK_OUT_B = 'B';
    
    
    private final SiteInterface site;
    private final PlaygroundInterface playground;
    private final RepositoryInterface repository;
    private final BenchInterface bench; 
    
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    private ERefereeState state;
    private final int nrGamesMax; 
    
    public Referee(RepositoryInterface repository, PlaygroundInterface playground, BenchInterface bench, SiteInterface site , int nrGamesMax){
        this.repository = repository;
        this.playground = playground;
        this.bench = bench;
        this.site = site;
        this.nrGamesMax = nrGamesMax; 
        state = ERefereeState.START_OF_THE_MATCH;
        
        myClock = new VectorTimestamp(ConstConfigs.ELEMENTS_IN_TEAM + ConstConfigs.OPPOSING_TEAMS + 1, 0);

    }
    
    /**
     * Esta função representada o ciclo de vida do arbitro.
    */
    @Override
    public void run() {
        int nrGame =0, nrTrial = 0;
        boolean endOp = true; 
        
        Object[] res = null;

        do{
            char decision = 0; 

            switch(state){
                case START_OF_THE_MATCH:
                    myClock.increment(); // added                  
                    nrGame++;
                    updateGameNumber(nrGame);
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    nrTrial++;
                    updateTrialNumber(nrTrial);
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    receivedClock = announceNewGame(nrGame,nrTrial, myClock.clone());
                    myClock.update(receivedClock); // added
                    
                    myClock.increment(); // added
                    state = ERefereeState.START_OF_A_GAME;
                    myClock.update(receivedClock); // added
                    break; 

                case START_OF_A_GAME:
                    myClock.increment();
                    receivedClock = callTrial(nrGame,nrTrial, myClock.clone()); 
                    myClock.update(receivedClock); // added
                    
                    myClock.increment();
                    state = ERefereeState.TEAMS_READY;
                    updateRefState(state);
                    myClock.update(receivedClock); // added
                    break;

                case TEAMS_READY:
                    myClock.increment(); // added
                    receivedClock = startTrial(nrGame,nrTrial, myClock.clone());
                    myClock.update(receivedClock); // added
                    
                    myClock.increment();
                    state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    updateRefState(state);
                    myClock.update(receivedClock); // added
                    break; 

                case WAIT_FOR_TRIAL_CONCLUSION:
                    if (allSittingTeams()){
                        myClock.increment(); // added
                        res = assertTrialDecision(myClock.clone());
                        decision = (char)res[1];
                        myClock.update(VectorTimestamp)res[0])); // added
                    }

                    if(res[0] == GAME_CONTINUATION ){
                        myClock.increment(); // added
                        System.out.println("Jogo vai continuar");
                        nrTrial++; 
                        state = ERefereeState.START_OF_A_GAME;
                        updateRefState(state);
                        myClock.update(receivedClock); // added
                    }
                    else if(decision == GAME_END || decision == KNOCK_OUT_A || decision == KNOCK_OUT_B ){
                        myClock.increment(); // added
                        switch (decision) {
                            case GAME_END:
                                System.out.println("Jogo acaba! - excedeu numero de trials! ");
                                break;
                            case KNOCK_OUT_A:
                                System.out.println("Jogo acaba! - knock out! Ganha A");
                                isKnockOut(nrGame, nrTrial, "A");
                                break;
                            case KNOCK_OUT_B:
                                System.out.println("Jogo acaba! - knock out! Ganha B");
                                isKnockOut(nrGame, nrTrial, "B");
                                break;
                            default:
                                break;
                        }
                        
                        int posPull = getPositionPull(); 

                        declareGameWinner(posPull); 
                        setPositionPull(PULL_CENTER);

                        state = ERefereeState.END_OF_A_GAME;
                        updateRefState(state);  // actualiza no repositorio
                        myClock.update(receivedClock); // added
                    }
                    break; 

                case END_OF_A_GAME:
                    if(nrGame < nrGamesMax){
                        myClock.increment(); // added
                        nrTrial=0;
                        state = ERefereeState.START_OF_THE_MATCH;
                        updateRefState(state);
                        myClock.update(receivedClock); // added
                        
                    }
                    else{
                        myClock.increment(); // added
                        state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                        updateRefState(state);
                        myClock.update(receivedClock); // added
                        
                        myClock.increment(); // added
                        declareMatchWinner();
                        myClock.update(receivedClock); // added
                    } 
                    break;

                case END_OF_THE_MATCH: 
                    myClock.increment(); // added
                    System.out.println("Fim do match!");
                    endOp = false; 
                    myClock.update(receivedClock); // added
                    break; 
            }
        }while(endOp);
    }
    

    /**
     * Permite atualizar o estado actual do arbitro
     * @param state estado do cenas
     */
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
    /**
     * Permite aceder ao estado actual do arbitro
     * @return ERefereeState retorna enumarado que representa o estado atual do arbitro.
     */
    public ERefereeState getCurrentState() {
        return state;
    }
    
    /**
     * referee announces a new trial
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     */
    private VectorTimestamp callTrial(int nrGame, int nrTrial, VectorTimestamp vt){
     
        return bench.callTrial(nrGame, nrTrial, vt);
    }
    
    /**
     * sets the position of the pull to the center
     * 
     * @param positionCenter is the center's position pull 
     */
    private void setPositionPull(int positionCenter){
        
    }
    
    /**
     * referee start's the trial
     * 
     * @param nrGame is the number of the game
     * @param numTrial is the number of the trial
     */
    private VectorTimestamp startTrial(int nrGame,int numTrial, VectorTimestamp vt){    
        
        return playground.startTrial(nrGame,numTrial, vt);
    }
    
    /**
     * referee makes a decision in the end of the trial or game
     * 
     * @return A Team A wins
     * @return B Team B wins
     * @return C the game will continue
     * @return E the game is over
     */
    private Object[] assertTrialDecision(VectorTimestamp vt){
 
        return playground.assertTrialDecision(VectorTimestamp vt); 
    }
    
    /**
     * referee declares the winner of the game
     * 
     * @param posPull is the position of the pull
     */
    private void declareGameWinner(int posPull){
        
        site.declareGameWinner(posPull);
    }
    
    /**
     * referee decides the winner of the match
     * 
     */
    private void declareMatchWinner(){
         
        site.declareMatchWinner();
    }
    
    /**
     * referee announces a new game
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     */
    private VectorTimestamp announceNewGame(int nrGame, int nrTrial, VectorTimestamp vt){       
        return site.announceNewGame(nrGame, nrTrial, vt);
    }
    
  
    /**
     * updates referee current state
     * 
     * @param state is the current referee state
     */
    private void updateRefState(ERefereeState state) {

    }

    /**
     * gets the postion of the pull
     * 
     * @return the position of the pull
     */
    private int getPositionPull() {
        return playground.getPositionPull();
    }
    
    /**
     * Say if the a game was ended by a knock out
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     * @param team is the name of the team
     */
    private void isKnockOut(int nrGame, int nrTrial, String team) {
        repository.isKnockOut(nrGame, nrTrial, team);
    }
    
    /**
     * verifies if all contestants of each team are sitting down at the bench
     * 
     * @return true if all contestants are sitting donw
     * @return false, otherwise
     */
    private boolean allSittingTeams() {
        return bench.allSittingTeams();
    }
    
    /**
     * updates the number of the game
     * 
     * @param nrGame is the number of the game
     */
    private void updateGameNumber(int nrGame) {
        repository.updateGameNumber(nrGame);
    }
    
    /**
     * updates the number of the trial
     * 
     * @param nrTrial is the number of the trial
     */
    private void updateTrialNumber(int nrTrial) {
        repository.updateTrialNumber(nrTrial);
    }
    
}