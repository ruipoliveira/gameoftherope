package ClientSide.Contestant;

import Communication.ClientComm;
import Communication.CommConst;
import Communication.Message.Message;
import Communication.Message.MessageType;
import static java.lang.Thread.sleep;
import java.util.Arrays;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class Contestant extends Thread{
    
    private final int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    public Contestant(int contId, int coachId){
        this.coachId = coachId;
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        contestStrength = generateStrength();
        updateStrength(coachId,contId,contestStrength);      
    }
    
    /**
     * Esta função representada o ciclo de vida de um jogador.
     */
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
   
    /**
     * players go to seat down at the bench
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     */
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
    
    /**
     * verify if the player is selected to pull the rope
     * return true if is selected
     * return false, otherwise
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     * @return 
     */
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
        if (null != type)
            switch (type) {
            case POSITIVE:
                return true;
            case NEGATIVE:
                return false;
            default:
                System.out.println("Thread " + getName() + ": Tipo inválido!");
                System.out.println("Message:"+ inMessage.toString());
                System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
                System.exit(1);
        }
        con.close();
        return false ;
        //bench.isPlayerSelected(coachId,contId); 
    }
    
    /**
     * contestants follow the coach instructions for the game
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId is the contestant identifier (ID)
     */
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
    
    /**
     * players are playing the game
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the contestant strength 
     */
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
    
    /**
     * players that will play the game, positioning into the field
     * 
     * @param coachId is the coach identifier (ID)
     * @param contestId  is the contestant identifier (ID)
     */
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

    /**
     * verify if the coach operation was terminated
     * return true if the coach operation is terminated
     * return false, otherwise
     * 
     * @param idCoach is the coach identifier (ID)
     * @return 
     */
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
        if (null != type)
            switch (type) {
            case POSITIVE:
                return true;
            case NEGATIVE:
                return false;
            default:
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

    /**
     * updates the strength of the contestants 
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the contestant strength
     */
    private void updateStrength(int coachId, int contId, int contestStrength) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_STRENGTH, coachId,contId, contestStrength );
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        
        con.close(); 
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
    
    /**
     * updates the contestants current state
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param state is the contestant state
     */
    private void updateContestantState(int coachId, int contId, EContestantsState state) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_CONTESTANT_STATE, coachId,contId,state );
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        
        con.close();
    }
    
    /**
     * updates and write the strength of contestants
     * 
     * @param coachId is the coach identifier (ID)
     * @param contId is the contestant identifier (ID)
     * @param contestStrength is the strength of contestants
     */
    private void updateStrengthAndWrite(int coachId, int contId, int contestStrength) {

        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_AND_WRITE_STRENGTH, coachId,contId, contestStrength );
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        
        con.close();
        
    }

}