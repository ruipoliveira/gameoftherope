/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Repository;

import ClientSide.Coach.ECoachesState;
import ClientSide.Contestant.EContestantsState;
import Communication.Message.Message;
import Communication.Message.MessageException;
import Communication.Message.MessageType;
import Communication.Proxy.ServerInterface;
import Communication.ServerComm;
import java.net.SocketException;

/**
 *
 * @author gabriel
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
     * @param rep
     */
    public RepositoryInterface(MRepository rep) {
        this.rep = rep;
        this.serviceEnded = false;
    }
    
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
                rep.updateContestantState(inMessage.getIdCoach(), inMessage.getIdContest(), EContestantsState.DO_YOUR_BEST);
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

    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
