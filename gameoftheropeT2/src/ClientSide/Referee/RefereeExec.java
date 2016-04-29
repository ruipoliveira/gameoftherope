
package ClientSide.Referee;

import Communication.ConstConfigs;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class RefereeExec {

    public static void main(String [] args) throws IOException{
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        System.out.println("******************************************************************\nEntity referee has started!");
        System.out.println("******************************************************************");
        
        Referee referee = new Referee(ConstConfigs.GAMES_PER_MATCH);
        
        referee.start();
       
        try {
            referee.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RefereeExec.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Sending TERMINATE message to the repository");

            
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
