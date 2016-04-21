package ClientSide.Contestant;

import ServerSide.Site.MSite;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */

public class Contestant extends Thread{
    
    private final int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    
    public Contestant(int contId, int coachId){

        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        contestStrength = generateStrength();
 
        updateStrength(coachId,contId,contestStrength);
        
        this.coachId = coachId;
        
    }
    
    @Override
    public void run() {
       
        boolean endOp = true; 
        
        do {
            switch(this.state){                             
                case SEAT_AT_THE_BENCH:         
                    followCoachAdvice (coachId, contId);                    
                    if (endOperCoach(coachId)){
                        endOp = false; 
                        break;
                    } 
                    
                    state = EContestantsState.STAND_IN_POSITION;
                    updateContestantState(coachId, contId, state);
                 
                break;
                
                case STAND_IN_POSITION:
                    
                    if (isPlayerSelected(coachId,contId) ){
                        getReady(coachId, contId);  
                        state = EContestantsState.DO_YOUR_BEST;
                        updateContestantState(coachId, contId, state);
                    }
                    else{
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                        updateContestantState(coachId, contId, state);
                        contestStrength++;
                        updateStrengthAndWrite(coachId, contId, contestStrength);
                    }

                break;
                
                case DO_YOUR_BEST:
                    
                    amDone(coachId, contId, contestStrength); 
                    seatDown(coachId,contId); 
                    contestStrength--;
                    updateStrengthAndWrite(coachId,contId, contestStrength);
                    state = EContestantsState.SEAT_AT_THE_BENCH;
                    updateContestantState(coachId, contId, state);

                break;    
            }
            
        } while (endOp); 
        
        System.out.println("Fim jogador #"+coachId); 
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
    
    private void seatDown(int coachId, int contestId){
        //bench.seatDown(coachId, contestId); 
    }
    
    private boolean isPlayerSelected(int coachId, int contestId){
        return true ;//bench.isPlayerSelected(coachId,contId); 
    }
    
    private void followCoachAdvice(int coachId, int contestId){
       // bench.followCoachAdvice(coachId, contestId);
       
    }
    
    private void amDone(int coachId, int contId, int contestStrength){
        //playground.amDone(coachId, contId, contestStrength);
    }
    
    private void getReady(int coachId, int contestId){
        //playground.getReady(coachId, contestId);
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
    public int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }

    private void updateStrength(int coachId, int contId, int contestStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean endOperCoach(int coachId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateContestantState(int coachId, int contId, EContestantsState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateStrengthAndWrite(int coachId, int contId, int contestStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}