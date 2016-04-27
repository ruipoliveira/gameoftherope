/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Repository;

import Communication.Message.Message;
import Communication.Message.MessageException;
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
     * @param log Logging to store information.
     */
    public RepositoryInterface(MRepository rep) {
        this.rep = rep;
        this.serviceEnded = false;
    }
    
    @Override
    public Message processAndReply(Message inMessage, ServerComm scon) throws MessageException, SocketException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean serviceEnded() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
