package Interfaces;

import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IContestantsBench {
    
    public VectorTimestamp seatDown(int coachId, int contId, VectorTimestamp vt) throws RemoteException;
    
    public VectorTimestamp followCoachAdvice(int coachId, int contId, VectorTimestamp vt) throws RemoteException;
    
    public boolean isPlayerSelected(int coachId, int contestId) throws RemoteException; 
    
}
