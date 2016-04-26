package ServerSide.Playground;
import Communication.ClientComm;
import Communication.CommConst;
import Communication.Message.Message;
import Communication.Message.MessageType;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class MPlayground implements IRefereePlayground, ICoachPlayground, IContestantsPlayground{
    private int newTrial;
    private final Map<Integer, List<Integer>> strengthTeam;
    private boolean startTrial; 
    private int numTrial; 
    private int allPulled;
    private int posPull, playerInPull; 
    private boolean lastPulled;
    private int resultTeamA, resultTeamB;  
    private int nrGame; 
    private final int maxTrials; 
    
    
    public MPlayground(int maxTrials){
        newTrial = 0; 
        numTrial = 0; 
        playerInPull=0;
        resultTeamA = 0;
        resultTeamB = 0; 
        startTrial = false;
        nrGame =0; 
        strengthTeam = new HashMap<>(); 
        for(int i =1; i< 3; i++ ){
            strengthTeam.put(i, new ArrayList<Integer>()); 
        }
        
        lastPulled = false;
        allPulled = 0;
        posPull = 0; 
        
        this.maxTrials = maxTrials;
    }


    @Override
    public synchronized void startTrial(int nrGame,int nrTrial) {
        this.nrGame = nrGame; 
        numTrial = nrTrial; 

        while(newTrial != 2){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        newTrial =0;
        startTrial = true; 
        notifyAll(); 
    }
    
    /**
     * The referee announces which team has won the game. An error message should be generated if decision is not 'end of the game'. The game result should be updated. Both internal state and game result should be saved.
     * @return decision trial decision 
     */
    @Override
    public synchronized char assertTrialDecision() { 
        
        while(lastPulled == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i =0; i<3; i++ ){
            resultTeamA += strengthTeam.get(1).get(i) ;
            resultTeamB += strengthTeam.get(2).get(i);
            
            playerInPull++;
            notifyAll();
        }
        
        System.out.println("ResultA = "+resultTeamA+"; ResultB = " + resultTeamB); 

        if (resultTeamA > resultTeamB){
            posPull--;
        }
        
        else if (resultTeamA < resultTeamB){        
            posPull++;
            
        }else{
            System.out.println("Jogo Empatado!!"); 
        }
        
        updateTrialNumber(numTrial);
        updatePullPosition(posPull);

        System.out.println("POSIÇÃO DA CORDA: " + posPull); 

        resultTeamA = resultTeamB = 0;          

        while( playerInPull % 3 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        strengthTeam.get(1).clear();
        strengthTeam.get(2).clear();

        if (numTrial == maxTrials){  
            return 'E';
        } 
        else if (posPull >= 4 ){
            return 'B'; 
        }
        else if (posPull <= -4){
            return 'A'; 
        }
        else{
            resultTeamA = resultTeamB = 0; 
            return 'C';
        }
    }

    
    @Override
    public synchronized void informReferee(int coachId) {
        newTrial++;
        while(newTrial % 2 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyAll();
    }

    
    @Override
    public synchronized int getPositionPull(){
        return posPull; 
    }
    
    @Override
    public synchronized void setPositionPull(int posPull){
        this.posPull = posPull; 
    }
    
    
    @Override
    public synchronized void getReady(int coachId, int contId) {
        while(startTrial == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * The contestant asserts if the end of operations has arrived.
     * @param coachId
     * @param contId
     * @param contestStrength 
     */
    @Override
    public synchronized void amDone(int coachId, int contId, int contestStrength) {
        
        while( playerInPull % 3 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        addContestantsInPull(coachId, contId);
        
        strengthTeam.get(coachId).add(contestStrength);
        
        System.out.println("["+coachId+"] #"+contId + " PUXA A CORDA!! | Força da Equipa: "+strengthTeam.toString()); 
/*
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
        }
*/
        allPulled++;
        while(allPulled % 6 != 0){
            try {
                wait();
                        } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        lastPulled = true; 
        startTrial = false; 

        notifyAll();
        
    }
    
    public int getNrGame(){
        return nrGame; 
    }

            // communication messages
    private void updateTrialNumber(int numTrial) {
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_TRIAL_NUMBER, numTrial);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Tipo inválido!");
            System.exit(1);
        }
        con.close();
    }

    private void updatePullPosition(int posPull) {
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.UPDATE_PULL_POSITION, posPull);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Tipo inválido!");
            System.exit(1);
        }
        con.close();
    }

    private void addContestantsInPull(int coachId, int contId) {
        ClientComm con = new ClientComm(CommConst.playServerName, CommConst.playServerPort);
        Message inMessage, outMessage;

        while (!con.open())
        {
            try {
                sleep((long) (10));
            } catch (InterruptedException e) {
            }
        }
        outMessage = new Message(MessageType.ADD_CONTESTANTS_IN_PULL, coachId, contId);
        con.writeObject(outMessage);
        
        inMessage = (Message) con.readObject();
        MessageType type = inMessage.getType();
        if (type != MessageType.ACK) {
            System.out.println("Tipo inválido!");
            System.exit(1);
        }
        con.close();
    }

}