package Interfaces;

import Structures.Enumerates.ERefereeState;
import Structures.VectorClock.VectorTimestamp;
import java.rmi.RemoteException;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeRepository {
    
    public void updateRefState(ERefereeState state, VectorTimestamp vt) throws RemoteException;  

    public void updatePullPosition(int positionPull, VectorTimestamp vt) throws RemoteException;

    public void updateTrialNumber(int trialNumber, VectorTimestamp vt) throws RemoteException;

    public void updateGameNumber(int gameNumber, VectorTimestamp vt) throws RemoteException;

    public void isKnockOut(int nrGame, int nrTrial, String team, VectorTimestamp vt) throws RemoteException; 
    
    public void endMatch(String team, int resultA, int resultB) throws RemoteException; 
    
    public void isEnd(int nrGame, String team) throws RemoteException;
            
    public void wasADraw(int nrGame) throws RemoteException;
   
    
}
