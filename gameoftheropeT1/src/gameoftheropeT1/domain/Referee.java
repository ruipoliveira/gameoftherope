package gameoftheropeT1.domain; 

/**
 *
 * @author roliveira
 */
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.ERefereeState;

public class Referee extends Thread{
    private final IRefereeSite site;
    private final IRefereePlayground playground;
    private final IRefereeRepository repository;
    private final IRefereeBench bench; 
    private ERefereeState state;
    
    
    public Referee(IRefereePlayground playground, IRefereeSite site, IRefereeBench bench, IRefereeRepository repository){
        this.playground = playground;
        this.site = site;
        this.bench = bench; 
        this.repository = repository;
        state = ERefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    /*This function represents the life cycle of Referee.*/
    public void run() {
        int nrGame =0, nrTrial = 0;
            boolean cenas = true; 

            do{
                char decision = 'F'; 
                
                switch(state)
                {
                    case START_OF_THE_MATCH:
                        nrTrial++; nrGame++;
                        repository.updateGameNumber(nrGame);// incrementa numero do jogo                      
                        announceNewGame(nrGame,nrTrial);
                        state = ERefereeState.START_OF_A_GAME;
                        repository.updateRefState(state); // actualiza no repositorio
                        break; 
                        
                    case START_OF_A_GAME:
                        callTrial(nrGame,nrTrial );
                        repository.updateTrialNumber(nrTrial);
                        state = ERefereeState.TEAMS_READY;
                        repository.updateRefState(state);  // actualiza no repositorio
                        break;

                    case TEAMS_READY:
                        startTrial(nrTrial);
                        repository.updatePullPosition(playground.getPositionPull());
                        state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                        repository.updateRefState(state); // actualiza no repositorio
                        break; 

                    case WAIT_FOR_TRIAL_CONCLUSION:
                        
                        
                        if (bench.allSittingTeams()){
                            decision = assertTrialDecision();
                        }
                        
                        if(decision == 'C'){ 
                            System.out.println("Jogo vai continuar");
                            nrTrial++; 
                            state = ERefereeState.START_OF_A_GAME;
                            repository.updateRefState(state);  // actualiza no repositorio
                        }
                        else if(decision == 'E' || decision == 'K'){
                            
                            if (decision == 'E')
                                System.out.println("Jogo acaba! - excedeu numero de trials! ");
                            else if (decision == 'K')
                                System.out.println("Jogo acaba! - knock out!");
                            
                            
                            int posPull = playground.getPositionPull(); 

                            declareGameWinner(posPull); 
                            
                            playground.setPositionPull(0);
                            repository.updatePullPosition(playground.getPositionPull());
                            
                            state = ERefereeState.END_OF_A_GAME;
                            repository.updateRefState(state);  // actualiza no repositorio
                        }

                        break; 

                    case END_OF_A_GAME:
                        if(nrGame < 3){
                            nrTrial=0;
                            state = ERefereeState.START_OF_THE_MATCH;
                            repository.updateRefState(state);
                        }

                        else{
                            declareMatchWinner();
                            state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                            repository.updateRefState(state);
                        } 
                        break;
                    
                    case END_OF_THE_MATCH: 
                        
                        System.out.println("FIM do match!");
                        repository.updateRefState(state);
                        cenas = false; 
                        break; 
                        
                }
               
            }while(cenas); 
            
            System.out.println("Foi-se"); 


    }
    /* actualiza estado */
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
    /* retorna estado actual */
    public ERefereeState getCurrentState() {
        return state;
    }
    
    
    
    private void callTrial(int nrGame, int nrTrial){
        bench.callTrial(nrGame, nrTrial);
    }
    
    private void startTrial(int numTrial){
        playground.startTrial(numTrial);
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
    
    
    
}