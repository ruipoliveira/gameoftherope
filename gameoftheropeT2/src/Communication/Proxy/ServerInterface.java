/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication.Proxy;

import Communication.Message.Message;
import Communication.Message.MessageException;
import Communication.ServerComm;
import java.net.SocketException;

/**
 * This file defines the server interface.
 * @author roliveira
 */
public interface ServerInterface {
    /**
     * Processes the received messages and replies to the entity that sent it.
     * 
     * @param inMessage The received message.
     * @param scon Server communication.
     * @return Returns the reply to the received message.
     * @throws MessageException
     * @throws SocketException 
     */
    public Message processAndReply (Message inMessage, ServerComm scon) throws MessageException, SocketException;
    
    /**
     * Tell the service if it is allowed to end or not.
     * @return True if the system can terminate, false otherwise.
     */
    public boolean serviceEnded();
}
