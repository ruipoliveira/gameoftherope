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
     
     /** *  @param type Message type for the created message.
     *
     * @param value Possible value for the message.
     */
    /*public Message(MessageType type, int value) {
        this();
        this.type = type;
        switch(type)
        {
            case ENTER_SHOP:
            case EXIT_SHOP:
            case GO_SHOPPING:
            case TRY_AGAIN_LATER:
            case PRIME_MATERIALS_NEEDED:
            case BACK_TO_WORK:
            case COLLECTING_MATERIALS:
            case PREPARE_TO_PRODUCE:
            case BATCH_READY_FOR_TRANSFER:
            case GO_TO_STORE:
            case SAY_GOODBYE_TO_CUSTOMER:
                this.id = value;
                break;
                
            case REPLENISH_STOCK:
                this.nMaterials = value;
                break;
            case ACK:
            case RETURN_TO_SHOP:
            case GO_TO_WORKSHOP:
                this.nProducts = value;
                break;
            default:
                System.err.println(type + ", wrong message type!");
                this.type = MessageType.ERROR;
                break;
        }
    } */
    
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
