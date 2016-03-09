/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.sharing;
import gameoftheropeT1.interfaces.*;

/**
 *
 * @author gabriel
 */
public class MSite implements IRefereeSite, ICoachSite{

    private boolean newGame;
    private boolean endOfGame; 
    
    public MSite(){
        newGame = false; 
        endOfGame = false; 
    }
    
    
    @Override
    public synchronized void announceNewGame(int numGame) {
        
        newGame = true; 
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void declareGameWinner(char winner) {
        endOfGame = true; 
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public synchronized void declareMatchWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean endOperCoach(int idCoach) {
        return endOfGame; 
    }
    
}
