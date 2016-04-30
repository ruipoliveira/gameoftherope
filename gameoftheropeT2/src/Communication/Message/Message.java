package Communication.Message;

import ClientSide.Coach.ECoachesState;
import ClientSide.Contestant.EContestantsState;
import ClientSide.Referee.ERefereeState;
import java.io.Serializable;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
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
    
    /**
     * messageType used only for positionPull
     * 
     * @param posP is the position of the pull
     */
    public Message(int posP){
        this();
    }
    
    /**
     * 
     * @param type is the type of message
     */
    public Message(MessageType type) {
        this();
        this.type = type;
    }
   
     /**
      * constructor used to say the of message and associate a value
      * 
      * @param type is the type of message 
      * @param value is the possible value for the message
      */
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
    
    /**
     * constructor used for the type of message and associate some values
     * 
     * @param type is the type of message
     * @param value1 is one of the possible values of message
     * @param value2 is one of the possible values of message
     */
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
    
    /**
     * constructor used for the type of message and associate some values and the contestStrength
     * 
     * @param type is the type of message
     * @param value1 is one of the possible values of message
     * @param value2 is one of the possible values of message
     * @param contestStrength is the strength of contestant 
     */
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
    
    
    /**
     * constructor used for the type of message and the state of referee 
     * 
     * @param type is the type of message
     * @param refState is the state of referee
     */
    public Message(MessageType type, ERefereeState refState) {
        this();
        this.type = type;
        this.refState = refState;
    }
    
    /**
     * constructor used for the type of message, coach ID and state of coach
     * 
     * @param type is the type of message
     * @param coach is the coach identifier (ID)
     * @param coachState is the state of coach
     */
    public Message(MessageType type, int coach, ECoachesState coachState) {
        this();
        this.type = type;
        this.idCoach = coach; 
        this.coachState = coachState;
    }
    
    /**
     * constructor used for the type of message, coach ID, contestant identifier and state of contestant
     * 
     * @param type is the type of message
     * @param idCoach is the coach identifier (ID)
     * @param idContestant is the contestant identifier (ID)
     * @param contestState is the state of contestant
     */
    public Message(MessageType type, int idCoach, int idContestant, EContestantsState contestState) {
        this();
        this.type = type;
        this.idCoach = idCoach; 
        this.idContestant = idContestant; 
        this.contestState = contestState;
    }
    
    /**
     * constructor used for the tyoe of message, name of the team, result of team A and result of team B
     * 
     * @param type is the type of message
     * @param team is the name of the team
     * @param resultA is the result of team A
     * @param resultB is the result of team B
     */
    public Message(MessageType type, String team, int resultA, int resultB){
        this();
        this.type = type;
        this.team = team; 
        this.resultA = resultA; 
        this.resultB = resultB; 
    }
    
    /**
     * constructor used for the type of message, number of the game, number of the trial and name of the team
     * 
     * @param type is the type of message
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     * @param team is the name of the team
     */
    public Message(MessageType type, int nrGame, int nrTrial, String team){
        this();
        this.type = type;
        this.team = team;
        this.trialNumber = nrTrial; 
        this.gameNumber = nrGame; 
    }
    
    /**
     * constructor used for the type of message, name of the team and numbe rof the game
     * 
     * @param type is type of message
     * @param team is the name of the team
     * @param numGame is the number of the game
     */
    public Message(MessageType type, String team, int numGame){
        this();
        this.type = type;
        this.team = team; 
        this.gameNumber = numGame; 
    }
    
    /**
     * Get the current message type 
     * 
     * @return Message type
     */
    public MessageType getType() {
        return type;
    }
    
    /**
     * Get the id of the coach
     * 
     * @return idCoach
     */
    public int getIdCoach() {
        return idCoach;
    }
    
    /**
     * Get the id of the contestant
     * 
     * @return idContestant
     */
    public int getIdContest() {
        return idContestant;
    }
    
    /**
     * Get the information about the Referee state present in the current message
     * 
     * @return EReferee refState
     */
    public ERefereeState getRefState() {
        return refState;
    }
    
    /**
     * Get the information about the Coach state present in the current message
     * 
     * @return ECoaches coachState
     */
    public ECoachesState getCoachState() {
        return coachState;
    }
    
    /**
     * Get the information about the Contestant state present in the current message
     * 
     * @return EContestants contestState
     */
    public EContestantsState getContestState() {
        return contestState;
    }
    
    /**
     * Get the name of the logger file
     * 
     * @return String filename
     */
    public String getFilename() {
        return filename;
    }
    
    /**
     * Get the strength of contestants
     * 
     * @return contestStrength
     */
    public int getContestStrength() {
        return contestStrength;
    }
    
    /**
     * Get the position of the pull
     * 
     * @return pullPosition
     */
    public int getPullPosition() {
        return pullPosition;
    }
    
    /**
     * Get the number of the trial
     * 
     * @return trialNumber
     */
    public int getTrialNumber() {
        return trialNumber;
    }
    
    /**
     * Get the number of game
     * 
     * @return gameNumber 
     */
    public int getGameNumber() {
        return gameNumber;
    }
    
    /**
     * Return if the game was terminated by knockOut
     * 
     * @return isKnockOut
     */
    public boolean isIsKnockOut() {
        return isKnockOut;
    }
    
    /**
     * Get total number of players
     * 
     * @return totalPlayer
     */
    public int getTotalPlayer() {
        return totalPlayer;
    }
    
    /**
     * Get the name of team
     * 
     * @return team
     */
    public String getTeam(){
        return team; 
    }
    
    /**
     * Get the result of team A
     * 
     * @return resultA
     */
    public int getResultA(){
        return resultA; 
    }
    
    /**
     * Get the result of team B
     * 
     * @return resultB
     */
    public int getResultB(){
        return resultB; 
    }
    
    /**
     * Return if the last player pulled the rope
     * 
     * @return lastPulled
     */
    public boolean isLastPulled() {
        return lastPulled;
    }
    
    /**
     * Set the position of pull
     * 
     * @param pullPosition is the position of pull
     */
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
