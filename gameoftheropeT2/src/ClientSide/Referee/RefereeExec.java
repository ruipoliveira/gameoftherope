/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide.Referee;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roliveira
 */
public class RefereeExec {
    
    public static void main(String [] args){
        Referee referee = new Referee((IRefereePlayground) playground, (IRefereeSite) site, 
                (IRefereeBench) bench, (IRefereeRepository) repository, GAMES_PER_MATCH);
        
        referee.start();
       
        try {
            referee.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RefereeExec.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            /*
            Message inMessage, outMessage;
            ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
            while (!con.open()) {
            try {
            sleep((long) (10));
            } catch (InterruptedException e) {
            }
            }
            outMessage = new Message(MessageType.TERMINATE);
            con.writeObject(outMessage);
            inMessage = (Message) con.readObject();
            if (inMessage.getType() != MessageType.ACK) {
            System.out.println("Tipo Inválido. Message:" + inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
            }
            con.close();
            */

                
    }
    
}
