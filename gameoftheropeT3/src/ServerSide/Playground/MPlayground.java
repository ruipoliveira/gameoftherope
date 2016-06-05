package ServerSide.Playground;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Interfaces.PlaygroundInterface;
import Interfaces.RepositoryInterface;
import Structures.Constants.ConstConfigs;
import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class MPlayground implements PlaygroundInterface{
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
    private final int contestantInTrial;
    private VectorTimestamp clocks;
    private final RepositoryInterface repository; 
    
    public MPlayground(int maxTrials, int contestantInTrial, RepositoryInterface repository){
        newTrial = 0; 
        numTrial = 0; 
        playerInPull=0;
        resultTeamA = 0;
        resultTeamB = 0; 
        startTrial = false;
        this.repository = repository; 
        nrGame =0; 
        strengthTeam = new HashMap<>(); 
        for(int i =1; i< 3; i++ ){
            strengthTeam.put(i, new ArrayList<>()); 
        }
        
        lastPulled = false;
        allPulled = 0;
        posPull = 0; 
        
        this.maxTrials = maxTrials;
        this.contestantInTrial = contestantInTrial;
        
        this.clocks = new VectorTimestamp(ConstConfigs.OPPOSING_TEAMS*ConstConfigs.ELEMENTS_IN_TEAM + ConstConfigs.OPPOSING_TEAMS + 1, 0);
    }

    /**
     * 
     * @param nrGame
     * @param nrTrial 
     */
    @Override
    public synchronized VectorTimestamp startTrial(int nrGame,int nrTrial, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
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
        
        return clocks.clone();
    }
    
    /**
     * The referee announces which team has won the game. An error message should be generated if decision is not 'end of the game'. The game result should be updated. Both internal state and game result should be saved.
     * @param vt
     * @return decision trial decision 
     */
    @Override
    public synchronized Object[] assertTrialDecision(VectorTimestamp vt) throws RemoteException { 
        
        clocks.update(vt);
        Object[] res = new Object[2];
        res[0] = clocks.clone();
        
        while(lastPulled == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for (int i =0; i<contestantInTrial; i++ ){
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

        while( playerInPull % contestantInTrial != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        strengthTeam.get(1).clear();
        strengthTeam.get(2).clear();

        if (numTrial == maxTrials){  
            res[1] = 'E';
        } 
        else if (posPull >= 4 ){
            res[1] = 'B'; 
        }
        else if (posPull <= -4){
            res[1] = 'A'; 
        }
        else{
            resultTeamA = resultTeamB = 0; 
            res[1] = 'C';
        }
        
        return res; // not sure about this
    }

    /**
     * 
     * @param coachId 
     * @param vt 
     * @return  
     */
    @Override
    public synchronized VectorTimestamp informReferee(int coachId, VectorTimestamp vt) throws RemoteException{
        clocks.update(vt);
        newTrial++;
        while(newTrial % 2 != 0){
            try { 
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyAll();
        return clocks.clone();
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getPositionPull(){
        return posPull;
    }
    
    /**
     * 
     * @param posPull 
     * @param vt 
     * @return  
     */
    @Override
    public synchronized VectorTimestamp setPositionPull(int posPull, VectorTimestamp vt) throws RemoteException{
        clocks.update(vt);
        this.posPull = posPull; 
        return clocks.clone();
    }
    
    /**
     * 
     * @param coachId
     * @param contId 
     * @param vt 
     * @return  
     */
    @Override
    public synchronized VectorTimestamp getReady(int coachId, int contId, VectorTimestamp vt) throws RemoteException{
        clocks.update(vt);
        while(startTrial == false){
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return clocks.clone();
    }

    /**
     * The contestant asserts if the end of operations has arrived.
     * @param coachId
     * @param contId
     * @param contestStrength 
     * @param vt 
     * @return  
     */
    @Override
    public synchronized VectorTimestamp amDone(int coachId, int contId, int contestStrength, VectorTimestamp vt) throws RemoteException{
        clocks.update(vt);
        while( playerInPull % contestantInTrial != 0){
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
        int todos = contestantInTrial*2;
        while(allPulled % todos != 0){
            try {
                wait();
                        } catch (InterruptedException ex) {
                Logger.getLogger(MPlayground.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        lastPulled = true; 
        startTrial = false; 

        notifyAll();
        return clocks.clone();
        
    }
    /**
     * 
     * @return 
     */
    public int getNrGame(){
        return nrGame; 
    }

    /**
     * 
     * @param numTrial 
     */
    private void updateTrialNumber(int numTrial) throws RemoteException{
       repository.updateTrialNumber(numTrial, clocks.clone());
    }
    /**
     * 
     * @param posPull 
     */
    private void updatePullPosition(int posPull) throws RemoteException {
       repository.updatePullPosition(posPull, clocks.clone());
    }
    
    /**
     * 
     * @param coachId
     * @param contId 
     */
    private void addContestantsInPull(int coachId, int contId) throws RemoteException {
        repository.addContestantsInPull(coachId, contId, clocks.clone()); 
    }
    
 
}