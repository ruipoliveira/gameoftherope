/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Playground;

import Communication.Message.Message;
import Communication.Message.MessageException;
import Communication.Message.MessageType;
import Communication.Proxy.ServerInterface;
import Communication.ServerComm;
import ServerSide.Bench.MBench;
import java.net.SocketException;

/**
 *
 * @author roliveira
 */
public class PlaygroundInterface implements ServerInterface{

    private final MPlayground playground;
    private boolean serviceEnded;
    
    public PlaygroundInterface(MPlayground playground){
        this.playground = playground; 
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
                
            case START_TRIAL:
                playground.startTrial(0, 0);
                break; 
            case ASSERT_TRIAL_DECISION: 
                playground.assertTrialDecision(); 
                break; 
            case INFORM_REFEREE:
                playground.informReferee(0);
                break; 
            case GET_READY:
                playground.getReady(0, 0);
                break; 
            case AM_DONE:
                playground.amDone(0, 0, 0);
                break; 
        
        }
        return outMessage;
    }

    @Override
    public boolean serviceEnded() {
        return serviceEnded;
    }
    
}
