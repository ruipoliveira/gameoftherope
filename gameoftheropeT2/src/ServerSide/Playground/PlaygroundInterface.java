package ServerSide.Playground;

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
public class PlaygroundInterface implements ServerInterface{

    private final MPlayground playground;
    private boolean serviceEnded;
    
    public PlaygroundInterface(MPlayground playground){
        this.playground = playground; 
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
                this.serviceEnded = true;
                break;
                
            case START_TRIAL:               
                if (inMessage.getGameNumber() == Message.ERROR_INT ||
                        inMessage.getTrialNumber() == Message.ERROR_INT)
                    throw new MessageException("Id do cliente inválido,", inMessage);
                
                playground.startTrial(inMessage.getGameNumber(), inMessage.getTrialNumber());
                
                outMessage = new Message(MessageType.ACK); 
                
                break; 
            case ASSERT_TRIAL_DECISION: 
                char decision = playground.assertTrialDecision(); 
                
                switch (decision) {
                    case 'A':
                        outMessage = new Message(MessageType.DECISION_A);
                        break;
                    case 'B':
                        outMessage = new Message(MessageType.DECISION_B);
                        break;
                    case 'C':
                        outMessage = new Message(MessageType.DECISION_C);
                        break;
                    case 'E':
                        outMessage = new Message(MessageType.DECISION_E);
                        break;
                    default:
                        break;
                }               
                break;  
                
            case INFORM_REFEREE:
                playground.informReferee(inMessage.getIdCoach());
                outMessage = new Message(MessageType.ACK);
                break; 
                
            case GET_READY:
                playground.getReady(inMessage.getIdCoach(), inMessage.getIdContest());
                outMessage = new Message(MessageType.ACK);
                break; 
                
            case AM_DONE:
                playground.amDone(inMessage.getIdCoach(), inMessage.getIdContest(), inMessage.getContestStrength());
                outMessage = new Message(MessageType.ACK);
                break; 
                
            case SET_POSITION_PULL:
                playground.setPositionPull(inMessage.getPullPosition());
                outMessage = new Message(MessageType.ACK);
                break; 
                
                
            case GET_POSITION_PULL: 
                int posPull = playground.getPositionPull(); 
                outMessage = new Message(MessageType.ACK,posPull); 
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
