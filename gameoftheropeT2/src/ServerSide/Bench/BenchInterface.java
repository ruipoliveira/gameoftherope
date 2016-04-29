package ServerSide.Bench;

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
public class BenchInterface implements ServerInterface {

    private final MBench bench;
    private boolean serviceEnded;
    
    public BenchInterface(MBench bench){
        this.bench = bench; 
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
            
            case CALL_CONTESTANTS:
                bench.callContestants(inMessage.getIdCoach());
                outMessage = new Message(MessageType.ACK);
                break; 

            case CALL_TRIAL: 
                if (inMessage.getGameNumber() == Message.ERROR_INT ||
                        inMessage.getTrialNumber() == Message.ERROR_INT)
                    throw new MessageException("Id do cliente inválido,", inMessage);
                
                bench.callTrial(inMessage.getGameNumber(), inMessage.getTrialNumber());               
                outMessage = new Message(MessageType.ACK);
                //bench.callTrial(0, 0);
                break;
                
            case IS_PLAYER_SELECTED:
                
                boolean isSelected = bench.isPlayerSelected(inMessage.getIdCoach(), inMessage.getIdContest()); 
                if (isSelected)
                    outMessage = new Message(MessageType.POSITIVE);
                else
                    outMessage = new Message(MessageType.NEGATIVE);
                
                break;
                
            case REVIEW_NOTES:
                bench.reviewNotes(inMessage.getIdCoach());
                outMessage = new Message(MessageType.ACK);
                break;
                
            case SEAT_DOWN:
                bench.seatDown(inMessage.getIdCoach(), inMessage.getIdContest());
                outMessage = new Message(MessageType.ACK);
                break; 
               
            case ALL_SITTING_TEAMS:
                
                boolean allSitting = bench.allSittingTeams();
                
                if (allSitting)
                    outMessage = new Message(MessageType.POSITIVE);
                else 
                    outMessage = new Message(MessageType.NEGATIVE);
                
                break; 

            case FOLLOW_COACH_ADVICE:
                bench.followCoachAdvice(inMessage.getIdCoach(), inMessage.getIdContest());
                outMessage = new Message(MessageType.ACK);
                break;
                
            case END_OF_THE_GAME:
                boolean endGame = bench.endOfTheGame(inMessage.getIdCoach());
                if (endGame)
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
     * @return True if the system can terminate, false otherwise.
     */    
    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
