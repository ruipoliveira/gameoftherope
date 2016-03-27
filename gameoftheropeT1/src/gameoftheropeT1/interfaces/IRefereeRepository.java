package gameoftheropeT1.interfaces;

import gameoftheropeT1.state.ERefereeState;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface IRefereeRepository {
    
    public void updateRefState(ERefereeState state);  

    public void updatePullPosition(int positionPull);

    public void updateTrialNumber(int trialNumber);

    public void updateGameNumber(int gameNumber);

    public void isKnockOut(int nrGame, int nrTrial, String team); 
              
}
