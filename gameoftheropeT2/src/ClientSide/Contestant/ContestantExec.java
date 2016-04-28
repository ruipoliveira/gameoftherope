/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide.Contestant;

import Communication.ConstConfigs;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author roliveira
 */
public class ContestantExec { 
    public static void main(String [] args) throws IOException{
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        System.out.println("******************************************************************\nEntity contestant has started!");
        System.out.println("******************************************************************");
        
        ArrayList<Contestant> contestant = new ArrayList<>(ConstConfigs.ELEMENTS_IN_TEAM);
        
        for (int idc = 1; idc <= ConstConfigs.OPPOSING_TEAMS ; idc++){
            for (int idct = 1; idct <= ConstConfigs.ELEMENTS_IN_TEAM; idct++){
                contestant.add(new Contestant(idct, idc));
            }
        }
        
        System.out.println("Number of contestant: " + contestant.size());
        
        for (Contestant c : contestant)
            c.start();
        
        for (Contestant c : contestant) { 
            try { 
                c.join ();
            }catch (InterruptedException e) {}
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
