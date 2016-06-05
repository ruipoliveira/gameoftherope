package Interfaces;

import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereePlayground {
    
    public VectorTimestamp startTrial(int nrGame,int numTrial, VectorTimestamp vt) throws RemoteException;
    
    public Object [] assertTrialDecision(VectorTimestamp vt) throws RemoteException;
    
    public int getPositionPull() throws RemoteException; 
    
    public VectorTimestamp setPositionPull (int posPull, VectorTimestamp vt) throws RemoteException; 
    
}
