package ClientSide.Coach;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class Coach extends Thread{
    
    private ECoachesState state; 
    private final int idCoach;

    public Coach(int idCoach){
        this.setName("Coach "+idCoach);
        this.idCoach = idCoach; 
        state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
    }
    
    /**
     * Esta função representada o ciclo de vida de um treinador.
     */
    @Override
    public void run(){
        do {
            switch(this.state){
                case WAIT_FOR_REFEREE_COMMAND:
                    if (!endOfTheGame(idCoach)) break; 
                    callContestants(idCoach);
                    state = ECoachesState.ASSEMBLE_TEAM;
                    updateCoachState(idCoach, state);
                    break; 

                case ASSEMBLE_TEAM:      
                    informReferee(idCoach); 
                    state = ECoachesState.WATCH_TRIAL;
                    //repository.updateCoachState(idCoach, state);
                    break; 
                    
                case WATCH_TRIAL:
                    reviewNotes(idCoach);
                    state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
                    updateCoachState(idCoach, state);
                    break;
            }
        }while (endOfTheGame(idCoach));
        
        System.out.println("Fim do treinador "+idCoach); 
    
    }
    
    private void callContestants(int idCoach){
        //bench.callContestants(idCoach);
    }
    
    private void informReferee(int idCoach){
        //playground.informReferee(idCoach);
    }
    
    private void reviewNotes(int idCoach){
        ///bench.reviewNotes(idCoach);
    }
    
    public void setState(ECoachesState state){
        this.state = state;
    }
    
    public boolean endOperCoach(int idCoach){
        return true ;//site.endOperCoach(idCoach); 
    }
    
    /**
     * Permite aceder ao estado atual do treinador. 
     * @return ECoachesState estado atual do treinador 
     */
    public ECoachesState getCurrentState() {
        return state;
    }
    
    /**
     * Permite aceder ao identificador do treinador.
     * @return idCoach 
     */
    public int getIdCoach(){
        return idCoach;
    }

    private boolean endOfTheGame(int idCoach) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateCoachState(int idCoach, ECoachesState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}