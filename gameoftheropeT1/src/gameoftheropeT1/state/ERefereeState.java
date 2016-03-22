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
public enum ERefereeState {
    /*initial state (transition)*/
    START_OF_THE_MATCH("SOM"),
    
    /* transition state */
    START_OF_A_GAME("SOG"),
    
    /* blocking state the referee is waken up by the last of the 
    coaches in operation informReferee when the teams are ready to proceed*/    
    TEAMS_READY("TR"), 
    
    /*blocking state the referee is waken up by the last of the contestants
    in operation amDone when the trial has come to an end */
    WAIT_FOR_TRIAL_CONCLUSION("WTC"), 
    
    /*transition state*/
    END_OF_A_GAME("EOF"), 
    
    /*final state */   
    END_OF_THE_MATCH("EOM"); 
    
   
    private final String acronym;
    private ERefereeState(String acronym) 
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
