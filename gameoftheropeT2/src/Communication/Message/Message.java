/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication.Message;

import ClientSide.Coach.ECoachesState;
import ClientSide.Contestant.EContestantsState;
import ClientSide.Referee.ERefereeState;
import java.io.Serializable;

/**
 *
 * @author roliveira
 */
public class Message implements Serializable {
    public static final int ERROR_INT = Integer.MIN_VALUE;
   
    public static final char ERROR_CHAR = 0xFFFE;
    
    private static final long serialVersionUID = 1001L;

    private MessageType type;

    private int id;

    private ERefereeState refState;
    
    private ECoachesState coachState;
    
    private EContestantsState contestState;

    private String filename;
    
    
    
    // duvida aqui.... achas que vale a pena ter 2 variaveis para aquele de uma ser no inicio e outra 
    // ser depois quando se desenrola o jogo???
    /**
     * Variable that holds the strength of players, in case the message requires it.
     */
    private int contestStrength;
    
    public MessageType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public ERefereeState getRefState() {
        return refState;
    }

    public ECoachesState getCoachState() {
        return coachState;
    }

    public EContestantsState getContestState() {
        return contestState;
    }

    public String getFilename() {
        return filename;
    }

    public int getContestStrength() {
        return contestStrength;
    }

    public int getPullPosition() {
        return pullPosition;
    }

    public int getTrialNumber() {
        return trialNumber;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public boolean isIsKnockOut() {
        return isKnockOut;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    /**
     * Variable that holds the pull position (defined by referee), in case the message requires it.
     */
    public boolean isLastPulled() {
        return lastPulled;
    }

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
    
     /******************
     ** CONSTRUCTORS **
     ******************/
    
    /**
     * Empty constructor for the message that initializes the default values for the previous variables.
     */
    private Message(){
        id = ERROR_INT;
        
        refState = null;
        coachState = null;
        contestState = null;
  

        filename = null;

        contestStrength = ERROR_INT;
        pullPosition = ERROR_INT;
        trialNumber = ERROR_INT;
        gameNumber = ERROR_INT;

        isKnockOut = false;
        lastPulled = false;

        totalPlayer = ERROR_INT;
    }
    
    // constructor used in all methods
    public Message(MessageType type) {
        this();
        this.type = type;
    }
   
    
    
    // mensagem com o tipo e o estado de cada cliente / entidade
    public Message(MessageType type, ERefereeState refState) {
        this();
        this.type = type;
        this.refState = refState;
    }
    
    public Message(MessageType type, ECoachesState coachState) {
        this();
        this.type = type;
        this.coachState = coachState;
    }
    
    public Message(MessageType type, EContestantsState contestState) {
        this();
        this.type = type;
        this.contestState = contestState;
    }
    
    
    
   
}
