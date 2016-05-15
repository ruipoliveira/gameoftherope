package ClientSide.Referee; 

import Structures.Enumerates.ERefereeState;
import Interfaces.*;
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
    private ERefereeState state;
    private final int nrGamesMax; 
    
    public Referee(RepositoryInterface repository, PlaygroundInterface playground, BenchInterface bench, SiteInterface site , int nrGamesMax){
        this.repository = repository;
        this.playground = playground;
        this.bench = bench;
        this.site = site;
        this.nrGamesMax = nrGamesMax; 
        state = ERefereeState.START_OF_THE_MATCH;

    }
    
    /**
     * Esta função representada o ciclo de vida do arbitro.
    */
    @Override
    public void run() {
        int nrGame =0, nrTrial = 0;
        boolean endOp = true; 

        do{
            char decision = 0; 

            switch(state){
                case START_OF_THE_MATCH:
                    nrTrial++;
                    nrGame++;
                    updateGameNumber(nrGame);
                    updateTrialNumber(nrTrial);
                    announceNewGame(nrGame,nrTrial);
                    state = ERefereeState.START_OF_A_GAME;
                    break; 

                case START_OF_A_GAME:
                    callTrial(nrGame,nrTrial); 
                    state = ERefereeState.TEAMS_READY;
                    updateRefState(state);
                    break;

                case TEAMS_READY:
                    startTrial(nrGame,nrTrial);
                    state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    updateRefState(state); 
                    break; 

                case WAIT_FOR_TRIAL_CONCLUSION:
                    if (allSittingTeams()){
                        decision = assertTrialDecision();
                    }

                    if(decision == GAME_CONTINUATION ){ 
                        System.out.println("Jogo vai continuar");
                        nrTrial++; 
                        state = ERefereeState.START_OF_A_GAME;
                        updateRefState(state);
                    }
                    else if(decision == GAME_END || decision == KNOCK_OUT_A || decision == KNOCK_OUT_B ){

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
                    }
                    break; 

                case END_OF_A_GAME:
                    if(nrGame < nrGamesMax){
                        nrTrial=0;
                        state = ERefereeState.START_OF_THE_MATCH;
                        updateRefState(state);
                    }
                    else{
                        state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                        updateRefState(state);
                        declareMatchWinner();
                    } 
                    break;

                case END_OF_THE_MATCH: 

                    System.out.println("Fim do match!");
                    endOp = false; 
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
    private void callTrial(int nrGame, int nrTrial){
     
        bench.callTrial(nrGame, nrTrial);
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
    private void startTrial(int nrGame,int numTrial){    
        
        //playground.startTrial(nrGame,numTrial);
    }
    
    /**
     * referee makes a decision in the end of the trial or game
     * 
     * @return A Team A wins
     * @return B Team B wins
     * @return C the game will continue
     * @return E the game is over
     */
    private char assertTrialDecision(){
 
        //playground.assertTrialDecision(); 
    }
    
    /**
     * referee declares the winner of the game
     * 
     * @param posPull is the position of the pull
     */
    private void declareGameWinner(int posPull){
        
        //site.declareGameWinner(posPull);
    }
    
    /**
     * referee decides the winner of the match
     * 
     */
    private void declareMatchWinner(){
         
        //site.declareMatchWinner();
    }
    
    /**
     * referee announces a new game
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     */
    private void announceNewGame(int nrGame, int nrTrial){
        
        //site.announceNewGame(numGame, nrTria);
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
        
    }
    
    /**
     * Say if the a game was ended by a knock out
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     * @param team is the name of the team
     */
    private void isKnockOut(int nrGame, int nrTrial, String team) {
      
    }
    
    /**
     * verifies if all contestants of each team are sitting down at the bench
     * 
     * @return true if all contestants are sitting donw
     * @return false, otherwise
     */
    private boolean allSittingTeams() {

    }
    
    /**
     * updates the number of the game
     * 
     * @param nrGame is the number of the game
     */
    private void updateGameNumber(int nrGame) {
     
    }
    
    /**
     * updates the number of the trial
     * 
     * @param nrTrial is the number of the trial
     */
    private void updateTrialNumber(int nrTrial) {
 
    }
    
}