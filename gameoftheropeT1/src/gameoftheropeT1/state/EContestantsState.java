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
public enum EContestantsState {
    /*blocking state the contestants are waken up in operation 
    callContestants by their coaches if
    they are selected to join the next trial*/
    SEAT_AT_THE_BENCH("SATB"),

    /*blocking state
    the contestants are waken up in operation startTrial by the referee*/
    STAND_IN_POSITION("SIP"), 
    
    /*independent state with blocking the contestants are made to sleep 
    for a random time interval in the simulation they block next and are 
    waken up in operation assertTrialDecision by the referee*/
    DO_YOUR_BEST("DYB"); 


    private final String acronym;
    private EContestantsState(String acronym) 
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
