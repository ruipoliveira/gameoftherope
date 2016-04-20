/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication.Message;

import ClientSide.Coach.ECoachesState;
import ClientSide.Contestant.EContestantsState;
import ClientSide.Referee.ERefereeState;

/**
 *
 * @author roliveira
 */
public class Message {
    public static final int ERROR_INT = Integer.MIN_VALUE;
    
    /**
     * Variable that defines the character value for an error.
     */
    public static final char ERROR_CHAR = 0xFFFE;
    
    /**
     * Variable that defines the serial version identifier.
     */
    private static final long serialVersionUID = 1001L;
    
    /**
     * Variable that defines the type of the current message.
     */
    private MessageType type;
    
    /**
     * Variable that defines the identifier, if the message requires one.
     */
    private int id;
    
     /**
     * Variable that holds the Referee state, in case the message requires it.
     */
    private ERefereeState refState;
    
     /**
     * Variable that holds the Coach state, in case the message requires it.
     */
    private ECoachesState coachState;
    
    /**
     * Variable that holds the Contestant state, in case the message requires it.
     */
    private EContestantsState contestState;
        
     /**
     * Variable that holds the logging file name, in case the message requires it.
     */
    private String filename;
    
    
    // duvida aqui.... achas que vale a pena ter 2 variaveis para aquele de uma ser no inicio e outra 
    // ser depois quando se desenrola o jogo???
    /**
     * Variable that holds the strength of players, in case the message requires it.
     */
    private int contestStrength;
    
    /**
     * Variable that holds the pull position (defined by referee), in case the message requires it.
     */
    private int pullPosition;
    
    /**
     * Variable that holds the trialNumber, in case the message requires it.
     */
    private int trialNumber;
    
    /**
     * Variable that holds the gameNumber, in case the message requires it.
     */
    private int gameNumber;
    
    /**
     * Variable that holds the if is knock out, in case the message requires it.
     */
    private boolean isKnockOut;

    /**
     * Variable that holds the total number of players, in case the message requires it.
     */
    private int totalPlayer;
    
    /**
     * Variable that holds if the last pulled the rope, in case the message requires it.
     */
    private boolean lastPulled;
    
    
    
   
}
