package Interfaces;

import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface ICoachBench {
    
    public VectorTimestamp callContestants(int coachId, VectorTimestamp vt) throws RemoteException;
    
    public VectorTimestamp reviewNotes(int coachId, VectorTimestamp vt) throws RemoteException;
    
    public boolean endOfTheGame(int c) throws RemoteException; 
}
