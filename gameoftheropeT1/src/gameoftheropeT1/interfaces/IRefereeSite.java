/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.interfaces;

/**
 *
 * @author gabriel
 */
public interface IRefereeSite {
    public void announceNewGame(int numGame);
    
    public void declareGameWinner(char winner);
    
    public void declareMatchWinner();
}
