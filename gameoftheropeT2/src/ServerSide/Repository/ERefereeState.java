package ServerSide.Repository;

import ClientSide.Referee.*;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public enum ERefereeState {
    
    /**
     *initial state (transition)
     */
    START_OF_THE_MATCH("SOM"),
    
    /**
     * transition state 
     */
    START_OF_A_GAME("SOF"),
    
    /**
     * blocking state the referee is waken up by the last of the 
     * coaches in operation informReferee when the teams are ready to proceed
    */    
    TEAMS_READY("TRY"), 
    
    /**
     * blocking state the referee is waken up by the last of the contestants
     * in operation amDone when the trial has come to an end
     */
    WAIT_FOR_TRIAL_CONCLUSION("WTC"), 
    
    /**
     * transition state
     */
    END_OF_A_GAME("EOG"), 
    
    /**
     * final state 
     */   
    END_OF_THE_MATCH("EOM"); 
    
    private final String acronym;
    
    /**
     * 
     * @param acronym acronimo associado ao estado representado pelo enumerado
     */
    private ERefereeState(String acronym){
       this.acronym = acronym;
    }
    
    /**
     * 
     * @return acronym acronimo que identifica o estado 
     */
    public String getAcronym(){
        return acronym;
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ").toLowerCase();
    }

}
