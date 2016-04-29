
package ServerSide.Site;

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
public class SiteInterface implements ServerInterface {
    private final MSite site;
    private boolean serviceEnded;
    
    public SiteInterface(MSite site){
        this.site = site; 
        this.serviceEnded = false;
    }
    
    /**
     * Processes the received messages and replies to the entity that sent it.
     * 
     * @param inMessage The received message.
     * @param scon Server communication
     * @return Returns the reply to the received message
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
                
            case ANNOUNCE_NEW_GAME:
                site.announceNewGame(inMessage.getGameNumber(), inMessage.getTrialNumber());
                outMessage = new Message(MessageType.ACK); 
                break; 
                
            case DECLARE_GAME_WINNER: 
                site.declareGameWinner(inMessage.getPullPosition());
                outMessage = new Message(MessageType.ACK);
                break;
                
            case DECLARE_MATCH_WINNER: 
                site.declareMatchWinner();
                outMessage = new Message(MessageType.ACK); 
                break; 
                
            case END_OPER_COACH:
                boolean endOper = site.endOperCoach(inMessage.getIdCoach()); 
                if (endOper)
                    outMessage = new Message(MessageType.POSITIVE); 
                else
                    outMessage = new Message(MessageType.NEGATIVE); 
                
                break; 
                
            default:
                System.out.println("Mensagem inválida recebida: " + inMessage);
                break;    

        }
        return outMessage;
    }
    
    /**
     * Tell the service if it is allowed to end or not.
     * 
     * @return True if the system can terminate, false otherwise
     */
    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
