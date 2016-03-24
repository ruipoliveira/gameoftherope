package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class MSite implements IRefereeSite, ICoachSite{

    private boolean newGame;
    private boolean endOfGame; 
    private boolean endOfMatch;
    private int posPull;
    private int winnerTeamA;
    private int winnerTeamB; 
    private int nrTrial, numGame; 
    private boolean cenas; 
    
    public MSite(MRepository rep ){
        newGame = false; 
        endOfGame = false; 
        endOfMatch = false;
        cenas = false; 
    }
    
    
    @Override
    public synchronized void announceNewGame(int numGame, int nrTrial) {
        this.nrTrial = nrTrial; 
        this.numGame = numGame; 
        
    }

 
    
    
    @Override
    public synchronized void declareGameWinner(int posPull) {

        if (posPull < 0 ){
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa A!"); 
            winnerTeamA++; 

        }
        else if (posPull > 0){
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa B!"); 
            winnerTeamB++; 
        }
        else{
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Empatado!"); 

        }

        
        
    }
    
    @Override
    public synchronized void declareMatchWinner() {

        System.out.println("***********************************************************");
        if (winnerTeamA > winnerTeamB)
            System.out.println("A Equipa A venceu o match com #" +winnerTeamA +" vitórias!");
        else if (winnerTeamA < winnerTeamB) 
            System.out.println("A Equipa B venceu o match com #" +winnerTeamB +" vitórias!");
        else
            System.out.println("O match ficou empatado! :(  A#" +winnerTeamA +" - B#"+winnerTeamB);
        System.out.println("***********************************************************");

    }

    @Override
    public boolean endOperCoach(int cia) {
        return cenas; 
    }
    
}