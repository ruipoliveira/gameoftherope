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
    public synchronized void declareGameWinner(char winner) {

        System.out.println("E o vencedor deste jogo foi...."); 
    }
    
    @Override
    public synchronized void declareMatchWinner() {
        cenas = true; 
        System.out.println("Jogador do match foi...."); 
    }

    @Override
    public boolean endOperCoach(int idCoach) {
        return cenas; 
    }
    
}