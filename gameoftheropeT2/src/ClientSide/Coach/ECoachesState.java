package ClientSide.Coach;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public enum ECoachesState {
    
    /**
     * blocking state the coaches are waken up by the referee in 
     * operation callTrial to start selecting next team
     */
    WAIT_FOR_REFEREE_COMMAND("WFRC"),
    
    /**
     * blocking state
     */
    ASSEMBLE_TEAM("ASTM"),
    
    /**
     * the coaches are waken up in operation followCoachAdvice by the last of their
     * selected contestants to stand in position blocking state
     * the coaches are waken up in operation assertTrialDecision by the referee
     */
    WATCH_TRIAL("WCTL"); 

    private final String acronym;
    
    /**
     * 
     * @param acronym acronimo associado ao estado representado pelo enumerado
     */
    private ECoachesState(String acronym){
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
