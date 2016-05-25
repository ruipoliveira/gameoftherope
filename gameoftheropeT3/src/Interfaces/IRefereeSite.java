package Interfaces;

import Structures.VectorClock.VectorTimestamp;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeSite {
    
    public VectorTimestamp announceNewGame(int numGame, int nrTrial, VectorTimestamp vt);
    
    public VectorTimestamp declareGameWinner(int posPull, VectorTimestamp vt);
    
    public VectorTimestamp declareMatchWinner(VectorTimestamp vt);
        
}
