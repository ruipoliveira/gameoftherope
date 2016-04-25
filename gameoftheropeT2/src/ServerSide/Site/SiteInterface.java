/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Site;

import Communication.Message.Message;
import Communication.Message.MessageException;
import Communication.Message.MessageType;
import Communication.Proxy.ServerInterface;
import Communication.ServerComm;
import java.net.SocketException;

/**
 *
 * @author roliveira
 */
public class SiteInterface implements ServerInterface {
    private final MSite site;
    private boolean serviceEnded;
    
    public SiteInterface(MSite site){
        this.site = site; 
        this.serviceEnded = false;
    }
    
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

    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
