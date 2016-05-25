
package Interfaces;

import Structures.VectorClock.VectorTimestamp;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IContestantsPlayground {
    
    public VectorTimestamp getReady(int coachId, int contId, VectorTimestamp vt);

    public VectorTimestamp amDone(int coachId, int contId, int contestStrength, VectorTimestamp vt);
}
