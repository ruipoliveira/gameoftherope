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

    private int idCoach;
    
    private int idContestant;
    
    private int contestStrength;

    private ERefereeState refState;
    
    private ECoachesState coachState;
    
    private EContestantsState contestState;

    private String filename;

    /**
     * Variable that holds the pull position (defined by referee), in case the message requires it.
     */


    public int pullPosition;
    
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
    
    
    private String team; 
    
    private int resultA; 
    private int resultB; 
    
     /******************
     ** CONSTRUCTORS **
     ******************/
    
    /**
     * Empty constructor for the message that initializes the default values for the previous variables.
     */
    private Message(){
        idCoach = ERROR_INT;
        idContestant = ERROR_INT;
        
        refState = null;
        coachState = null;
        contestState = null;
  

        filename = null;

        contestStrength = ERROR_INT;
        pullPosition = 0;
        trialNumber = ERROR_INT;
        gameNumber = ERROR_INT;

        isKnockOut = false;
        lastPulled = false;

        totalPlayer = ERROR_INT;
    }
    
    public Message(int posP){
        this();
    }
    
    // constructor used in all methods
    public Message(MessageType type) {
        this();
        this.type = type;
    }
   
    public Message(MessageType type, int value) {
        this();
        this.type = type;
        
        switch(type){    // executa de igual modo para ambos
            case CALL_CONTESTANTS: 
            case REVIEW_NOTES: 
            case END_OF_THE_GAME: 
            case END_OPER_COACH: 
            case INFORM_REFEREE: 
                this.idCoach = value;
                break; 
            
            case ACK:
            case SEND_POS_PULL: 
            case SET_POSITION_PULL: 
            case DECLARE_GAME_WINNER:
                this.pullPosition = value; 
                break; 
            case WAS_A_DRAW: 
                this.gameNumber = value; 
                break; 
                
            case UPDATE_TRIAL_NUMBER:
                this.trialNumber = value; 
                break; 
            case UPDATE_GAME_NUMBER: 
                this.gameNumber = value; 
                break;
                
            case UPDATE_PULL_POSITION: 
                this.pullPosition = value;
                break; 
            
            default:
                System.err.println(type + ", wrong message type!");
                this.type = MessageType.ERROR;
                break;
        }

    }
   
    public Message(MessageType type, int value1, int value2) {
        this();
        this.type = type;

        switch(type){
            case START_TRIAL:
            case CALL_TRIAL:
            case ANNOUNCE_NEW_GAME: 
                this.gameNumber = value1; 
                this.trialNumber = value2; 
                break;
            case GET_READY: 
            case IS_PLAYER_SELECTED: 
            case REVIEW_NOTES: 
            case SEAT_DOWN: 
            case FOLLOW_COACH_ADVICE:
            case ADD_CONTESTANTS_IN_PULL: 
            case REMOVE_CONTESTANTS_IN_PULL: 
                this.idCoach = value1; 
                this.idContestant = value2;
                break; 
            default:
                System.err.println(type + ", wrong message type!");
                this.type = MessageType.ERROR;
                break;
                
        }
        
    }
    
    public Message(MessageType type, int value1, int value2, int contestStrength) {
        this();
        this.type = type;
        
        switch(type){
            case UPDATE_AND_WRITE_STRENGTH: 
            case UPDATE_STRENGTH: 
            case AM_DONE : 
                this.idCoach = value1; 
                this.idContestant = value2; 
                this.contestStrength = contestStrength; 
                break; 
            default:
                System.err.println(type + ", wrong message type!");
                this.type = MessageType.ERROR;
                break; 
        }

    }
    
    
    // mensagem com o tipo e o estado de cada cliente / entidade
    public Message(MessageType type, ERefereeState refState) {
        this();
        this.type = type;
        this.refState = refState;
    }
    
    public Message(MessageType type, int coach, ECoachesState coachState) {
        this();
        this.type = type;
        this.idCoach = coach; 
        this.coachState = coachState;
    }
    
    public Message(MessageType type, int idCoach, int idContestant, EContestantsState contestState) {
        this();
        this.type = type;
        this.idCoach = idCoach; 
        this.idContestant = idContestant; 
        this.contestState = contestState;
    }
    
    public Message(MessageType type, String team, int resultA, int resultB){
        this();
        this.type = type;
        this.team = team; 
        this.resultA = resultA; 
        this.resultB = resultB; 
    }
    
    
    public Message(MessageType type, int nrGame, int nrTrial, String team){
        this();
        this.type = type;
        this.team = team;
        this.trialNumber = nrTrial; 
        this.gameNumber = nrGame; 
    }
    
    public Message(MessageType type, String team, int numGame){
        this();
        this.type = type;
        this.team = team; 
        this.gameNumber = numGame; 
    }
    
    
    public MessageType getType() {
        return type;
    }

    public int getIdCoach() {
        return idCoach;
    }

    public int getIdContest() {
        return idContestant;
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
    
    public String getTeam(){
        return team; 
    }
    
    public int getResultA(){
        return resultA; 
    }
    
    public int getResultB(){
        return resultB; 
    }

    public boolean isLastPulled() {
        return lastPulled;
    }
    
    public void setPosPull(int pullPosition){
        this.pullPosition = pullPosition; 
    }
    
    /**
     * Convert the message type to a readable/writable format.
     * @return Message type as a string.
     */
    @Override
    public String toString() {
        return this.type.toString();
    }
    
}
