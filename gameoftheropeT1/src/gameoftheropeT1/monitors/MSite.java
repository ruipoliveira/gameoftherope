/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;

/**
 *
 * @author gabriel
 */
public class MSite implements IRefereeSite, ICoachSite{

    private boolean newGame;
    private boolean endOfGame; 
    private boolean endOfMatch;
    
    public MSite(MRepository rep ){
        newGame = false; 
        endOfGame = false; 
        endOfMatch = false;
    }
    
    
    @Override
    public synchronized void announceNewGame(int numGame) {
        
        System.out.printf("Game number: ",numGame); // apenas para ter a certeza que actualiza o numero do jogo
        newGame = true;
    }

    @Override
    public synchronized void declareGameWinner(char winner) {
        endOfGame = true; 
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public synchronized void declareMatchWinner() {
        
        endOfMatch = true;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean endOperCoach(int idCoach) {
        return endOfGame; 
    }
    
}
