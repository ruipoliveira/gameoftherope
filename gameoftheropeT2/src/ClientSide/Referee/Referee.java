package ClientSide.Referee; 


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class Referee extends Thread{
    public final static int PULL_CENTER = 0;
    public final static char GAME_CONTINUATION = 'C';
    public final static char GAME_END = 'E';
    public final static char KNOCK_OUT_A = 'A';
    public final static char KNOCK_OUT_B = 'B';

    private ERefereeState state;
    private final int nrGamesMax; 
    
    public Referee(int nrGamesMax){

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

                        if (decision == GAME_END)
                            System.out.println("Jogo acaba! - excedeu numero de trials! ");
                        else if (decision == KNOCK_OUT_A){
                            System.out.println("Jogo acaba! - knock out! Ganha A");
                            isKnockOut(nrGame, nrTrial, "A");
                        }
                        else if (decision == KNOCK_OUT_B){
                            System.out.println("Jogo acaba! - knock out! Ganha B");
                            isKnockOut(nrGame, nrTrial, "B");
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

    private void callTrial(int nrGame, int nrTrial){
        //bench.callTrial(nrGame, nrTrial);
    }
    
    private void startTrial(int nrGame,int numTrial){
        //playground.startTrial(nrGame,numTrial);
    }
    
    private char assertTrialDecision(){
        return '2'; //playground.assertTrialDecision(); 
    }
    
    private void declareGameWinner(int posPull){
        //site.declareGameWinner(posPull);
    }
    
    private void declareMatchWinner(){
        //site.declareMatchWinner();
    }
    
    private void announceNewGame(int numGame, int nrTria){
        //site.announceNewGame(numGame, nrTria);
    }
    
    private void setPositionPull(int pos){
        //playground.setPositionPull(pos); 
    }

    private void updateRefState(ERefereeState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int getPositionPull() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void isKnockOut(int nrGame, int nrTrial, String b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean allSittingTeams() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateGameNumber(int nrGame) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateTrialNumber(int nrTrial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}