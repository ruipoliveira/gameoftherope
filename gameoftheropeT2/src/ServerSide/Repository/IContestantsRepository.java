package ServerSide.Repository;

import ClientSide.Contestant.EContestantsState;
import java.util.List;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface IContestantsRepository {
    public void updateContestantState(int idTeam, int contestId, EContestantsState state);
    
    public void updateStrength(int idTeam, int contestId, int contestStrength); 
    
    public void updateStrengthAndWrite(int idTeam, int contestId, int strength);
    
    public void addContestantsInPull(int coachId, List<Integer> inPull);
    
    public void removeContestantsInPull(int coachId);
    
}
