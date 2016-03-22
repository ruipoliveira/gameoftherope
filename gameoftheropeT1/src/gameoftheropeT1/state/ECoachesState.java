/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.state;

/**
 *
 * @author roliveira
 */
public enum ECoachesState {
    
    /*blocking state the coaches are waken up by the referee in 
    operation callTrial to start selecting next team*/
    WAIT_FOR_REFEREE_COMMAND("WRC"),
    
    /*blocking state*/
    ASSEMBLE_TEAM("AT"),
    
    /* the coaches are waken up in operation followCoachAdvice by the last of their
    selected contestants to stand in position blocking state
    the coaches are waken up in operation assertTrialDecision by the referee */
    WATCH_TRIAL("WT"); 

    
    private final String acronym;
    private ECoachesState(String acronym) 
    {
       this.acronym = acronym;
    }

    public String getAcronym() {
        return acronym;
    }

    @Override
    public String toString() {
        return this.name().replace("_", " ").toLowerCase();
    }
}
