package Interfaces;

import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeSite {
    
    public VectorTimestamp announceNewGame(int numGame, int nrTrial, VectorTimestamp vt) throws RemoteException;
    
    public VectorTimestamp declareGameWinner(int posPull, VectorTimestamp vt) throws RemoteException;
    
    public VectorTimestamp declareMatchWinner(VectorTimestamp vt) throws RemoteException;
        
}
