package ServerSide.Repository;

import Communication.Message.Message;
import Communication.Message.MessageException;
import Communication.Message.MessageType;
import Communication.Proxy.ServerInterface;
import Communication.ServerComm;
import java.net.SocketException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class RepositoryInterface implements ServerInterface {
    
    /**
     * Logger file.
     */
    private final MRepository rep;
    
    /**
     * Tells the service if it can end or not.
     */
    public boolean serviceEnded;
    
    /**
     * Constructor for the logger server.
     * @param rep MRepository to store information 
     */
    public RepositoryInterface(MRepository rep) {
        this.rep = rep;
        this.serviceEnded = false;
    }
    
    /**
     * Processes the received messages and replies to the entity that sent it.
     * 
     * @param inMessage The received message.
     * @param scon Server communication.
     * @return Returns the reply to the received message.
     * @throws MessageException
     * @throws SocketException 
     */
    @Override
    public Message processAndReply(Message inMessage, ServerComm scon) throws MessageException, SocketException {
        Message outMessage = null;
        
        switch (inMessage.getType()) {
            case TERMINATE:
                outMessage = new Message(MessageType.ACK);

                break;
                
            case UPDATE_REF_STATE:
                rep.updateRefState(inMessage.getRefState());
                outMessage = new Message(MessageType.ACK);
                break; 

            case UPDATE_COACH_STATE: 
                rep.updateCoachState(inMessage.getIdCoach(), inMessage.getCoachState());
                outMessage = new Message(MessageType.ACK);
                break; 
    

            case UPDATE_CONTESTANT_STATE:
                rep.updateContestantState(inMessage.getIdCoach(), inMessage.getIdContest(), inMessage.getContestState());
                outMessage = new Message(MessageType.ACK);
                break; 
    

            case UPDATE_STRENGTH: 
                rep.updateStrength(inMessage.getIdCoach(), inMessage.getIdContest(), inMessage.getContestStrength());
                outMessage = new Message(MessageType.ACK);
                break; 
    

            case UPDATE_AND_WRITE_STRENGTH: 
                rep.updateStrengthAndWrite(inMessage.getIdCoach(), inMessage.getIdContest(), inMessage.getContestStrength());
                outMessage = new Message(MessageType.ACK);
                break; 


            case UPDATE_PULL_POSITION: 
                rep.updatePullPosition(inMessage.getPullPosition());
                outMessage = new Message(MessageType.ACK);
                break; 
            case UPDATE_TRIAL_NUMBER: 
                rep.updateTrialNumber(inMessage.getTrialNumber());
                outMessage = new Message(MessageType.ACK);
                break; 

            case UPDATE_GAME_NUMBER: 
                rep.updateGameNumber(inMessage.getGameNumber());
                outMessage = new Message(MessageType.ACK);
                break; 

            case IS_KNOCK_OUT: 
                rep.isKnockOut(inMessage.getGameNumber(), inMessage.getTrialNumber() , inMessage.getTeam());
                outMessage = new Message(MessageType.ACK);
                break; 

            case IS_END: 
                rep.isEnd(inMessage.getGameNumber(), inMessage.getTeam());
                outMessage = new Message(MessageType.ACK);
                break; 

            case WAS_A_DRAW:
                rep.wasADraw(inMessage.getGameNumber());
                outMessage = new Message(MessageType.ACK);
                break; 

            case END_MATCH:
                rep.endMatch(inMessage.getTeam(), inMessage.getResultA(), inMessage.getResultB());
                outMessage = new Message(MessageType.ACK);
                break; 

            case ADD_CONTESTANTS_IN_PULL: 
                rep.addContestantsInPull(inMessage.getIdCoach(), inMessage.getIdContest());
                outMessage = new Message(MessageType.ACK);
                break; 

            case REMOVE_CONTESTANTS_IN_PULL:
                rep.removeContestantsInPull(inMessage.getIdCoach(), inMessage.getIdContest());
                outMessage = new Message(MessageType.ACK);
                break; 

            default:
                System.out.println("Mensagem inválida recebida: " + inMessage);
                break;
        }
        return outMessage;
    }

     
    /**
     * Tell the service if it is allowed to end or not.
     * @return True if the system can terminate, false otherwise.
     */    
    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
