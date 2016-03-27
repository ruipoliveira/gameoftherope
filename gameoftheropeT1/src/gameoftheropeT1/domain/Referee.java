package gameoftheropeT1.domain; 

import gameoftheropeT1.interfaces.IRefereeBench;
import gameoftheropeT1.interfaces.IRefereePlayground;
import gameoftheropeT1.interfaces.IRefereeRepository;
import gameoftheropeT1.interfaces.IRefereeSite;
import gameoftheropeT1.state.ERefereeState;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class Referee extends Thread{
    public final static int PULL_CENTER = 0;
    private final IRefereeSite site;
    private final IRefereePlayground playground;
    private final IRefereeRepository repository;
    private final IRefereeBench bench; 
    private ERefereeState state;
    private int nrGamesMax; 
    
    public Referee(IRefereePlayground playground, IRefereeSite site, 
            IRefereeBench bench, IRefereeRepository repository, int nrGamesMax){
        this.playground = playground;
        this.site = site;
        this.bench = bench; 
        this.repository = repository;
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
                    repository.updateGameNumber(nrGame);
                    announceNewGame(nrGame,nrTrial);
                    state = ERefereeState.START_OF_A_GAME;
                    break; 

                case START_OF_A_GAME:
                    callTrial(nrGame,nrTrial);
                   // repository.updateTrialNumber(nrTrial);
                    state = ERefereeState.TEAMS_READY;
                    repository.updateRefState(state);
                    break;

                case TEAMS_READY:
                    startTrial(nrGame,nrTrial);
                  //  repository.updatePullPosition(playground.getPositionPull());
                    state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    repository.updateRefState(state); 
                    break; 

                case WAIT_FOR_TRIAL_CONCLUSION:

                    if (bench.allSittingTeams()){
                        decision = assertTrialDecision();
                    }

                    
                    if(decision == 'C'){ 
                        System.out.println("Jogo vai continuar");
                        nrTrial++; 
                  //      repository.updateTrialNumber(nrTrial);
                        state = ERefereeState.START_OF_A_GAME;
                        repository.updateRefState(state);  // actualiza no repositorio
                    }
                    else if(decision == 'E' || decision == 'A' || decision == 'B' ){

                        if (decision == 'E')
                            System.out.println("Jogo acaba! - excedeu numero de trials! ");
                        else if (decision == 'A'){
                            System.out.println("Jogo acaba! - knock out! Ganha A");
                            repository.isKnockOut(nrGame, nrTrial, "A");
                        }
                            
                            
                        else if (decision == 'B'){
                            System.out.println("Jogo acaba! - knock out! Ganha B");
                            repository.isKnockOut(nrGame, nrTrial, "B");
                        }
                        
                        int posPull = playground.getPositionPull(); 

                        declareGameWinner(posPull); 

                        setPositionPull(PULL_CENTER);


                        state = ERefereeState.END_OF_A_GAME;
                        repository.updateRefState(state);  // actualiza no repositorio
                    }
                    break; 

                case END_OF_A_GAME:
                    if(nrGame < nrGamesMax){
                        nrTrial=0;
                        state = ERefereeState.START_OF_THE_MATCH;
                        repository.updateRefState(state);
                    }

                    else{
                        state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                        repository.updateRefState(state);
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

    private void callTrial(int nrGame, int nrTrial){
        bench.callTrial(nrGame, nrTrial);
    }
    
    private void startTrial(int nrGame,int numTrial){
        playground.startTrial(nrGame,numTrial);
    }
    
    private char assertTrialDecision(){
        return playground.assertTrialDecision(); 
    }
    
    private void declareGameWinner(int posPull){
        site.declareGameWinner(posPull);
    }
    
    private void declareMatchWinner(){
        site.declareMatchWinner();
    }
    
    private void announceNewGame(int numGame, int nrTria){
        site.announceNewGame(numGame, nrTria);
    }
    
    private void setPositionPull(int pos){
        playground.setPositionPull(pos); 
    }
    
}