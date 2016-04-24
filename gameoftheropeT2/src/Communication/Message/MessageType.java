
package Communication.Message;

/**
 * This file defines the message types.
 * @author roliveira
 */
public enum MessageType {
    ACK,
    
    POSITIVE,
    
    NEGATIVE,
    
    ERROR,
    
    TERMINATE,
    
    /*### Meter tudo o que é feito nos monitores ###*/
    
    // ### CONTESTANTS ##### messages types ( I guess)
    
    /**
     * Message used when the Contestant is seating at the bench.
     */
    SEAT_DOWN,
    
    /**
     * Message used for Contestant knows if he is selected to compete.
     */
    IS_PLAYER_SELECTED,
    
    /**
     * Message used when the Contestant is following coach advice.
     */
    FOLLOW_COACH_ADVICE,
    
    /**
     * Message used when the Contestant is pulling the rope.
     */
    AM_DONE,
    
    /**
     * Message used when the Contestant is getting ready.
     */
    GET_READY,
    
    /**
     * 
     */
    ALL_SITTING_TEAMS,
 
    
    
    // ### COACH ##### messages types ( I guess)
    
    /**
     * Message used when the Coach calls the contestants.
     */
    CALL_CONTESTANTS,
    
    /**
     * Message used when the Coach informs referee to start the trial.
     */   
    INFORM_REFEREE,
    
    /**
     * Message used when the Coach is reviewing notes.
     */ 
    REVIEW_NOTES,
    
    /**
     * Message used when the Coach finishes your duty.
     */ 
    END_OPER_COACH,
    
    GET_POSITION_PULL,
    SET_POSITION_PULL,

    
    // ### REFEREE ##### messages types ( I guess)
    
    /**
     * Message used when the referee says that another game will start.
     */
    ANNOUNCE_NEW_GAME,
    
    /**
     * Message used when the referee calls another trial.
     */
    CALL_TRIAL,
    
     /**
     * Message used when the referee starts a new trial.
     */
    START_TRIAL,
    
     /**
     * Message used when the referee waits for trial conclusion and make a decision.
     */
    ASSERT_TRIAL_DECISION,

    DECISION_E,
    DECISION_A,
    DECISION_B,
    DECISION_C,
    
    /**
     * Message used when the referee declares the winner of the game.
     */
    DECLARE_GAME_WINNER,
    
    /**
     * Message used when the referee declares the winner of the match.
     */
    DECLARE_MATCH_WINNER,
    
    /*### REPOSITORY ###*/
    /**
     * Message used to update the referee state in logger's information
     */
    UPDATE_REF_STATE,
    
    /**
     * Message used to update the COACH state in logger's information
     */
    UPDATE_COACH_STATE,
    
    /**
     * Message used to update the COACH state in logger's information
     */
    UPDATE_CONTESTANT_STATE,
    
    /**
     * Message used to update the strength of contestant (only used initially)
     */
    UPDATE_STRENGTH,
    
    /**
     * Message used to update and write strength of contestant 
     */
    UPDATE_AND_WRITE_STRENGTH,
    
    /**
     * Message used to update pull position 
     */
    UPDATE_PULL_POSITION,
    
    /**
     * Message used to update trial number 
     */
    UPDATE_TRIAL_NUMBER,
    
    /**
     * Message used to update game number 
     */
    UPDATE_GAME_NUMBER,
        
    /**
     * Message used to know if a team wins a game by knock out 
     */
    IS_KNOCK_OUT,
    
    /**
     * Message used to notify that constestants must be ADDED to the pull
     */
    ADD_CONTESTANTS_IN_PULL,
    
    /**
     * Message used to notify that constestants must be moved out to the pull
     */
    REMOVE_CONTESTANTS_IN_PULL,  
    
    
    
    
}
