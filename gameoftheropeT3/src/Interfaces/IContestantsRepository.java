package Interfaces;

import Structures.Enumerates.EContestantsState;
import Structures.VectorClock.VectorTimestamp;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IContestantsRepository {
    public void updateContestantState(int idTeam, int contestId, EContestantsState state, VectorTimestamp vt);
    
    public void updateStrength(int idTeam, int contestId, int contestStrength, VectorTimestamp vt); 
    
    public void updateStrengthAndWrite(int idTeam, int contestId, int strength, VectorTimestamp vt);
    
    public void addContestantsInPull(int coachId, int idPlayer, VectorTimestamp vt);
    
    public void removeContestantsInPull(int coachId , int idPlayer, VectorTimestamp vt);
    
}
