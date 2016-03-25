package gameoftheropeT1.state;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public enum EContestantsState {
    /**
     * blocking state the contestants are waken up in operation 
     * callContestants by their coaches if
     * they are selected to join the next trial
     */
    SEAT_AT_THE_BENCH("SAB"),

    /**
     * blocking state
     * the contestants are waken up in operation startTrial by the referee
     */
    STAND_IN_POSITION("SIP"), 
    
    /**
     * independent state with blocking the contestants are made to sleep 
     * for a random time interval in the simulation they block next and are 
     * waken up in operation assertTrialDecision by the referee
     */
    DO_YOUR_BEST("DYB"); 

    private final String acronym;
    
    /**
     * 
     * @param acronym acronimo associado ao estado representado pelo enumerado
     */
    private EContestantsState(String acronym){
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
