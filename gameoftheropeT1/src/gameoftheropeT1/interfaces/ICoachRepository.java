package gameoftheropeT1.interfaces;

import gameoftheropeT1.state.ECoachesState;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface ICoachRepository {
    
    public void updateCoachState(int coachId, ECoachesState state);
     
}
