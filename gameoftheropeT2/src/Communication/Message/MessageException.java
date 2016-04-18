/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication.Message;


/**
 * This date type defines an exception that is thrown if the message 
 * type is invalid.
 * 
 * @author Prof.Rui Borges
 */

public class MessageException extends Exception {

    /**
     * Message that originated the exception.
     */
    private final Message msg;

    /**
     * Message instantiation.
     *
     * @param errorMessage string signaling an error situation
     * @param msg origin message
     */
    public MessageException(String errorMessage, Message msg) {
        super(errorMessage);
        this.msg = msg;
    }

    /**
     * Get the message that originated the exception.
     *
     * @return message
     */
    public Message getMessageVal() {
        return (msg);
    }
}