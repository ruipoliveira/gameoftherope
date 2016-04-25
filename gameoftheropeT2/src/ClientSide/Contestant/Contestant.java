package ClientSide.Contestant;

import Communication.ClientComm;
import Communication.CommConst;
import Communication.Message.Message;
import Communication.Message.MessageType;
import ServerSide.Site.MSite;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import ClientSide.Coach.*;



/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */

public class Contestant extends Thread{
    
    private final int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    Coach ch; //  usado para o endOperCoach
    public Contestant(int contId, int coachId){
        this.coachId = coachId;
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        contestStrength = generateStrength();
        updateStrength(coachId,contId,contestStrength);      
    }
    
    @Override
    public void run() {
       
        boolean endOp = true; 
        
        do {
            switch(this.state){                             
                case SEAT_AT_THE_BENCH:         
                    followCoachAdvice (coachId, contId);                    
                    if (endOperCoach(coachId)){
                        endOp = false; 
                        break;
                    } 
                    
                    state = EContestantsState.STAND_IN_POSITION;
                    updateContestantState(coachId, contId, state);
                 
                break;
                
                case STAND_IN_POSITION:
                    
                    if (isPlayerSelected(coachId,contId) ){
                        getReady(coachId, contId);  
                        state = EContestantsState.DO_YOUR_BEST;
                        updateContestantState(coachId, contId, state);
                    }
                    else{
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                        updateContestantState(coachId, contId, state);
                        contestStrength++;
                        updateStrengthAndWrite(coachId, contId, contestStrength);
                    }

                break;
                
                case DO_YOUR_BEST:
                    
                    amDone(coachId, contId, contestStrength); 
                    seatDown(coachId,contId); 
                    contestStrength--;
                    updateStrengthAndWrite(coachId,contId, contestStrength);
                    state = EContestantsState.SEAT_AT_THE_BENCH;
                    updateContestantState(coachId, contId, state);

                break;    
            }
            
        } while (endOp); 
        
        System.out.println("Fim jogador #"+coachId); 
    }
   
    
    private void seatDown(int coachId, int contestId){
        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.SEAT_DOWN, coachId, contestId);
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
        //bench.seatDown(coachId, contestId); 
    }
    
    private boolean isPlayerSelected(int coachId, int contestId){
        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.IS_PLAYER_SELECTED, coachId, contestId);
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
        return false ;//bench.isPlayerSelected(coachId,contId); 
    }
    
    private void followCoachAdvice(int coachId, int contestId){
        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.FOLLOW_COACH_ADVICE, coachId, contestId);
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
       // bench.followCoachAdvice(coachId, contestId);
       
    }
    
    private void amDone(int coachId, int contId, int contestStrength){
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.AM_DONE, coachId, contId, contestStrength);
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
        //playground.amDone(coachId, contId, contestStrength);
    }
    
    private void getReady(int coachId, int contestId){
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.GET_READY, coachId, contestId);
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
        //playground.getReady(coachId, contestId);
    }


    private boolean endOperCoach(int idCoach){
        ClientComm con = new ClientComm(CommConst.siteServerName, CommConst.siteServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.END_OPER_COACH, idCoach);
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

        //return true ;//site.endOperCoach(idCoach); 
    }
    
    /**
     * Permite aceder à força do respectivo jogador. 
     * @return contestStrength 
     */
    public int getStrength(){
        return contestStrength; 
    }
    
    /**
     * Permite gerar de forma aleatoria a força associada a cada jogador.
     * @return int - valor inteiro entre 10 e 20 
     */
    private int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }

    private void updateStrength(int coachId, int contId, int contestStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Permite atualizar o estado actual do jogador
     * @param state 
     */
    public void setState(EContestantsState state) {
        this.state = state;
    }
    
    /**
     * Permite aceder ao estado actual do jogador 
     * @return state
     */
    public EContestantsState getCurrentState() {
        return state;
    }
    
    
    private void updateContestantState(int coachId, int contId, EContestantsState state) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void updateStrengthAndWrite(int coachId, int contId, int contestStrength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}