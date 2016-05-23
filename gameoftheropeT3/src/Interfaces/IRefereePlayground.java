package Interfaces;

import Structures.VectorClock.VectorTimestamp;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereePlayground {
    
    public VectorTimestamp startTrial(int nrGame,int numTrial, VectorTimestamp vt);
    
    public Object [] assertTrialDecision(VectorTimestamp vt);
    
    public int getPositionPull(); 
    
    public void setPositionPull (int posPull); 
    
}
