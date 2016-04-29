package ClientSide.Referee; 

import Communication.ClientComm;
import Communication.CommConst;
import static java.lang.Thread.sleep;
import Communication.Message.Message;
import Communication.Message.MessageType;
import java.util.Arrays;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class Referee extends Thread{
    public final static int PULL_CENTER = 0;
    public final static char GAME_CONTINUATION = 'C';
    public final static char GAME_END = 'E';
    public final static char KNOCK_OUT_A = 'A';
    public final static char KNOCK_OUT_B = 'B';

    private ERefereeState state;
    private final int nrGamesMax; 
    
    public Referee(int nrGamesMax){

        this.nrGamesMax = nrGamesMax; 
        state = ERefereeState.START_OF_THE_MATCH;

    }
    
    /**
     * Esta função representada o ciclo de vida do arbitro.
    */
    @Override
    public void run() {
        int nrGame =0, nrTrial = 0;
        boolean endOp = true; 

        do{
            char decision = 0; 

            switch(state){
                case START_OF_THE_MATCH:
                    nrTrial++;
                    nrGame++;
                    updateGameNumber(nrGame);
                    updateTrialNumber(nrTrial);
                    announceNewGame(nrGame,nrTrial);
                    state = ERefereeState.START_OF_A_GAME;
                    break; 

                case START_OF_A_GAME:
                    callTrial(nrGame,nrTrial); 
                    state = ERefereeState.TEAMS_READY;
                    updateRefState(state);
                    break;

                case TEAMS_READY:
                    startTrial(nrGame,nrTrial);
                    state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                    updateRefState(state); 
                    break; 

                case WAIT_FOR_TRIAL_CONCLUSION:
                    if (allSittingTeams()){
                        decision = assertTrialDecision();
                    }

                    if(decision == GAME_CONTINUATION ){ 
                        System.out.println("Jogo vai continuar");
                        nrTrial++; 
                        state = ERefereeState.START_OF_A_GAME;
                        updateRefState(state);
                    }
                    else if(decision == GAME_END || decision == KNOCK_OUT_A || decision == KNOCK_OUT_B ){

                        if (decision == GAME_END)
                            System.out.println("Jogo acaba! - excedeu numero de trials! ");
                        else if (decision == KNOCK_OUT_A){
                            System.out.println("Jogo acaba! - knock out! Ganha A");
                            isKnockOut(nrGame, nrTrial, "A");
                        }
                        else if (decision == KNOCK_OUT_B){
                            System.out.println("Jogo acaba! - knock out! Ganha B");
                            isKnockOut(nrGame, nrTrial, "B");
                        }
                        
                        int posPull = getPositionPull(); 

                        declareGameWinner(posPull); 
                        setPositionPull(PULL_CENTER);

                        state = ERefereeState.END_OF_A_GAME;
                        updateRefState(state);  // actualiza no repositorio
                    }
                    break; 

                case END_OF_A_GAME:
                    if(nrGame < nrGamesMax){
                        nrTrial=0;
                        state = ERefereeState.START_OF_THE_MATCH;
                        updateRefState(state);
                    }
                    else{
                        state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                        updateRefState(state);
                        declareMatchWinner();
                    } 
                    break;

                case END_OF_THE_MATCH: 

                    System.out.println("Fim do match!");
                    endOp = false; 
                    break; 
            }
        }while(endOp);
    }
    

    /**
     * Permite atualizar o estado actual do arbitro
     * @param state estado do cenas
     */
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
    /**
     * Permite aceder ao estado actual do arbitro
     * @return ERefereeState retorna enumarado que representa o estado atual do arbitro.
     */
    public ERefereeState getCurrentState() {
        return state;
    }
    
    /**
     * referee announces a new trial
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     */
    private void callTrial(int nrGame, int nrTrial){

        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.CALL_TRIAL, nrGame, nrTrial);
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
        
        //bench.callTrial(nrGame, nrTrial);
    }
    
    /**
     * sets the position of the pull to the center
     * 
     * @param positionCenter is the center's position pull 
     */
    private void setPositionPull(int positionCenter){
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.SET_POSITION_PULL, positionCenter);
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
     * referee start's the trial
     * 
     * @param nrGame is the number of the game
     * @param numTrial is the number of the trial
     */
    private void startTrial(int nrGame,int numTrial){
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.START_TRIAL, nrGame, numTrial);
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
        
        //playground.startTrial(nrGame,numTrial);
    }
    
    /**
     * referee makes a decision in the end of the trial or game
     * 
     * @return A Team A wins
     * @return B Team B wins
     * @return C the game will continue
     * @return E the game is over
     */
    private char assertTrialDecision(){
        
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.ASSERT_TRIAL_DECISION);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        
        if (type == MessageType.DECISION_A)
            return 'A';
        else if(type == MessageType.DECISION_B)
            return 'B';
        else if(type == MessageType.DECISION_C)
            return 'C';
        else if(type == MessageType.DECISION_E)
            return 'E';        
        else {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        
        con.close();
        return ' '; 
        //playground.assertTrialDecision(); 
    }
    
    /**
     * referee declares the winner of the game
     * 
     * @param posPull is the position of the pull
     */
    private void declareGameWinner(int posPull){
        
        ClientComm con = new ClientComm(CommConst.siteServerName, CommConst.siteServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.DECLARE_GAME_WINNER, posPull);
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
        
        
        //site.declareGameWinner(posPull);
    }
    
    /**
     * referee decides the winner of the match
     * 
     */
    private void declareMatchWinner(){
        
        ClientComm con = new ClientComm(CommConst.siteServerName, CommConst.siteServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.DECLARE_MATCH_WINNER);
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
        
        //site.declareMatchWinner();
    }
    
    /**
     * referee announces a new game
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     */
    private void announceNewGame(int nrGame, int nrTrial){
        
        ClientComm con = new ClientComm(CommConst.siteServerName, CommConst.siteServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.ANNOUNCE_NEW_GAME, nrGame, nrTrial);
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
        
        //site.announceNewGame(numGame, nrTria);
    }
    
  
    /**
     * updates referee current state
     * 
     * @param state is the current referee state
     */
    private void updateRefState(ERefereeState state) {

        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_REF_STATE, state);
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
     * gets the postion of the pull
     * 
     * @return the position of the pull
     */
    private int getPositionPull() {
        
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.GET_POSITION_PULL);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        
        MessageType type = inMessage.getType();
        int posPull = inMessage.getPullPosition(); 
        if (type != MessageType.ACK || posPull == Message.ERROR_INT ) {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        
        con.close();
        return posPull; 
    }
    
    /**
     * Say if the a game was ended by a knock out
     * 
     * @param nrGame is the number of the game
     * @param nrTrial is the number of the trial
     * @param team is the name of the team
     */
    private void isKnockOut(int nrGame, int nrTrial, String team) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.IS_KNOCK_OUT, nrGame, nrTrial, team);
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
     * verifies if all contestants of each team are sitting down at the bench
     * 
     * @return true if all contestants are sitting donw
     * @return false, otherwise
     */
    private boolean allSittingTeams() {

        ClientComm con = new ClientComm(CommConst.benchServerName, CommConst.benchServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.ALL_SITTING_TEAMS);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        
        if (type == MessageType.POSITIVE)
            return true;
        else if(type == MessageType.NEGATIVE)
            return false;
        else
        {
            System.out.println("Thread " + getName() + ": Tipo inválido!");
            System.out.println("Message:"+ inMessage.toString());
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        con.close();
        return false;

    }
    
    /**
     * updates the number of the game
     * 
     * @param nrGame is the number of the game
     */
    private void updateGameNumber(int nrGame) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_GAME_NUMBER, nrGame);
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
     * updates the number of the trial
     * 
     * @param nrTrial is the number of the trial
     */
    private void updateTrialNumber(int nrTrial) {
        ClientComm con = new ClientComm(CommConst.repServerName, CommConst.repServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_TRIAL_NUMBER, nrTrial);
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