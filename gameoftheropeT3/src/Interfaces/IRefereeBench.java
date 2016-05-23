package Interfaces;

import Structures.VectorClock.VectorTimestamp;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeBench {
    
    public VectorTimestamp callTrial(int nrTrial, int numGame, VectorTimestamp vt);

    public boolean allSittingTeams(); 
}
