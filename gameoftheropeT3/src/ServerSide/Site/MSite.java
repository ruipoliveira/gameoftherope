package ServerSide.Site;

import Interfaces.RepositoryInterface;
import Interfaces.SiteInterface;
import Structures.Constants.ConstConfigs;
import Structures.VectorClock.VectorTimestamp;

import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class MSite implements SiteInterface{
    private int winnerTeamA;
    private int winnerTeamB; 
    private int nrTrial, numGame; 
    private final boolean endOp; 
    private final RepositoryInterface repository; 
    private final VectorTimestamp clocks;
    
    public MSite(RepositoryInterface repository){
        endOp = false; 
        this.clocks = new VectorTimestamp(ConstConfigs.ELEMENTS_IN_TEAM + ConstConfigs.OPPOSING_TEAMS + 1, 0);
        this.repository = repository; 
    }
    
    /**
     * The referee starts a game. The game number should be updated. Both game header and internal state should be saved.
     * @param numGame
     * @param nrTrial 
     */
    @Override
    public synchronized VectorTimestamp announceNewGame(int numGame, int nrTrial, VectorTimestamp vt) throws RemoteException{
        
        clocks.update(vt);
        this.nrTrial = nrTrial; 
        this.numGame = numGame;
        
        return clocks.clone();
    }
    
    /**
     * The referee announces which teams has won the match. Both internal state and match result should be saved.
     * @param posPull 
     */
    @Override
    public synchronized VectorTimestamp declareGameWinner(int posPull, VectorTimestamp vt) throws RemoteException {
        clocks.update(vt);
        if (posPull < 0 ){            
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa A!"); 
            isEnd(numGame, "A");
            winnerTeamA++; 
            return clocks.clone();
        }
        else if (posPull > 0){
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa B!"); 
            isEnd(numGame, "B");
            winnerTeamB++;
            return clocks.clone();
        }
        else{
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Empatado!"); 
            wasADraw(numGame);
            return clocks.clone();
        }
        
    }
    
    /**
     * The referee announces which teams has won the match. Both internal state and match result should be saved.
     */
    @Override
    public synchronized VectorTimestamp declareMatchWinner(VectorTimestamp vt) throws RemoteException{
        
        clocks.update(vt);
        System.out.println("***********************************************************");
        if (winnerTeamA > winnerTeamB){
            System.out.println("A Equipa A venceu o match com #" +winnerTeamA +" vitórias!");
            endMatch("A", winnerTeamA, winnerTeamB);
        }
        else if (winnerTeamA < winnerTeamB){
            System.out.println("A Equipa B venceu o match com #" +winnerTeamB +" vitórias!");
            endMatch("B", winnerTeamB, winnerTeamA);
        }
        else{
            System.out.println("O match ficou empatado! :(  A#" +winnerTeamA +" - B#"+winnerTeamB);
            endMatch("", winnerTeamB, winnerTeamA);
        }
        System.out.println("***********************************************************");
        return clocks.clone();

    }
    /**
     * checks coach operation ended
     * @param id
     * @return 
     */
    @Override
    public boolean endOperCoach(int id) throws RemoteException {
        return endOp; 
    }
    
    public int getNrTrial(){
        return nrTrial; 
    }

    private void isEnd(int numGame, String team) throws RemoteException{
       repository.isEnd(numGame, team);
    }

    private void wasADraw(int numGame) throws RemoteException {
       repository.wasADraw(numGame);
    }

    private void endMatch(String team, int winnerTeamA, int winnerTeamB) throws RemoteException {
       repository.endMatch(team, winnerTeamA, winnerTeamB);
    }
    
}