package Interfaces;

import Structures.Enumerates.ERefereeState;
import Structures.VectorClock.VectorTimestamp;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeRepository {
    
    public void updateRefState(ERefereeState state, VectorTimestamp vt);  

    public void updatePullPosition(int positionPull, VectorTimestamp vt);

    public void updateTrialNumber(int trialNumber, VectorTimestamp vt);

    public void updateGameNumber(int gameNumber, VectorTimestamp vt);

    public void isKnockOut(int nrGame, int nrTrial, String team, VectorTimestamp vt); 
              
}
