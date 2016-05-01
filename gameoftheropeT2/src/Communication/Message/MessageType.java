package Communication.Message;

/**
 * This file defines the message types.
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */

public enum MessageType {
    /**
     * This message type allows the sender of a previous message to make sure the message was received
     */   
    ACK,
    
    /**
     * Simulates the boolean functionality for the messages (like TRUE return)
     */   
    POSITIVE,
    
    /**
     * Simulates the boolean functionality for the messages (like FALSE return)
     */
    NEGATIVE,
    
    /**
     * Alerts that the message is an error message
     */
    ERROR,
    
    /**
     * Message to send position of pull
     */
    SEND_POS_PULL, 
   
    /**
     * Alerts the logger that the clients are finishing
     */
    TERMINATE,
    

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
     * Message to know if all teams are seated down
     */
    ALL_SITTING_TEAMS,
 
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
    
    /**
     * Message to get position of pull
     */
    GET_POSITION_PULL,
    
    /**
     * Message to set the position of pull
     */
    SET_POSITION_PULL,

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

    /**
     * Message used to say that the game is ended .
     */
    DECISION_E,
    
    /**
     * Message used to say that team A won the game.
     */
    DECISION_A,
    
    /**
     * Message used to say that team B won the game.
     */
    DECISION_B,
    
    /**
     * Message used to say that the game will continue.
     */
    DECISION_C,
    
    /**
     * Message used to know if the game is ended.
     */
    END_OF_THE_GAME, 
    
    /**
     * Message used when the referee declares the winner of the game.
     */
    DECLARE_GAME_WINNER,
    
    /**
     * Message used when the referee declares the winner of the match.
     */
    DECLARE_MATCH_WINNER,
    
    /**
     * Message used to update the referee state in logger's information.
     */
    UPDATE_REF_STATE,
    
    /**
     * Message used to update the COACH state in logger's information.
     */
    UPDATE_COACH_STATE,
    
    /**
     * Message used to update the COACH state in logger's information.
     */
    UPDATE_CONTESTANT_STATE,
    
    /**
     * Message used to update the strength of contestant (only used initially).
     */
    UPDATE_STRENGTH,
    
    /**
     * Message used to update and write strength of contestant .
     */
    UPDATE_AND_WRITE_STRENGTH,
    
    /**
     * Message used to update pull position .
     */
    UPDATE_PULL_POSITION,
    
    /**
     * Message used to update trial number .
     */
    UPDATE_TRIAL_NUMBER,
    
    /**
     * Message used to update game number .
     */
    UPDATE_GAME_NUMBER,
        
    /**
     * Message used to know if a team wins a game by knock out .
     */
    IS_KNOCK_OUT,
    
    /**
     * Message used to know if the game is ended.
     */   
    IS_END,
    
    /**
     * Message used to know if the result of the game was a draw.
     */
    WAS_A_DRAW,
    
    /**
     * Message used to know if the match is ended.
     */
    END_MATCH,
    
    /**
     * Message used to notify that constestants must be ADDED to the pull
     */
    ADD_CONTESTANTS_IN_PULL,
    
    /**
     * Message used to notify that constestants must be moved out to the pull
     */
    REMOVE_CONTESTANTS_IN_PULL,  

}
