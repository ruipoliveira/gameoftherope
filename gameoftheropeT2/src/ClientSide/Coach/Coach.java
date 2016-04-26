package ClientSide.Coach;

import Communication.ClientComm;
import Communication.CommConst;
import Communication.Message.Message;
import Communication.Message.MessageType;
import static java.lang.Thread.sleep;
import java.util.Arrays;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class Coach extends Thread{
    
    private ECoachesState state; 
    private final int idCoach;

    public Coach(int idCoach){
        this.setName("Coach "+idCoach);
        this.idCoach = idCoach; 
        state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
    }
    
    /**
     * Esta função representada o ciclo de vida de um treinador.
     */
    @Override
    public void run(){
        do {
            switch(this.state){
                case WAIT_FOR_REFEREE_COMMAND:
                    if (!endOfTheGame(idCoach)) break; 
                    callContestants(idCoach);
                    state = ECoachesState.ASSEMBLE_TEAM;
                //    updateCoachState(idCoach, state);
                    break; 

                case ASSEMBLE_TEAM:      
                    informReferee(idCoach); 
                    state = ECoachesState.WATCH_TRIAL;
                    //repository.updateCoachState(idCoach, state);
                    break; 
                    
                case WATCH_TRIAL:
                    reviewNotes(idCoach);
                    state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
               //     updateCoachState(idCoach, state);
                    break;
            }
        }while (endOfTheGame(idCoach));
        
        System.out.println("Fim do treinador "+idCoach); 
    
    }
    
    private void callContestants(int idCoach){
        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.CALL_CONTESTANTS, idCoach);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK ) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close(); 
        //bench.callContestants(idCoach);
        
    }
    
    private void informReferee(int idCoach){
         ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.INFORM_REFEREE, idCoach);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK ) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        //playground.informReferee(idCoach);
    }
    
    private void reviewNotes(int idCoach){
        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.REVIEW_NOTES, idCoach);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK ) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        ///bench.reviewNotes(idCoach);
    }
    
    
     private boolean endOfTheGame(int idCoach) {
       ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.END_OF_THE_GAME, idCoach);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();        
        if (type == MessageType.POSITIVE)
            return true;
        else if(type == MessageType.NEGATIVE)
            return false;
        else{
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        return false;
    }
     
    
    
    public void setState(ECoachesState state){
        this.state = state;
    }
    
   
    
    /**
     * Permite aceder ao estado atual do treinador. 
     * @return ECoachesState estado atual do treinador 
     */
    public ECoachesState getCurrentState() {
        return state;
    }
    
    /**
     * Permite aceder ao identificador do treinador.
     * @return idCoach 
     */
    public int getIdCoach(){
        return idCoach;
    }

   

    private void updateCoachState(int idCoach, ECoachesState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}