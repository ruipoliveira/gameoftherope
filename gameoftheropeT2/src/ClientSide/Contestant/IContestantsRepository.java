package ClientSide.Contestant;

import java.util.List;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IContestantsRepository {
    public void updateContestantState(int idTeam, int contestId, EContestantsState state);
    
    public void updateStrength(int idTeam, int contestId, int contestStrength); 
    
    public void updateStrengthAndWrite(int idTeam, int contestId, int strength);
    
    public void addContestantsInPull(int coachId, int idPlayer);
    
    public void removeContestantsInPull(int coachId , int idPlayer);
    
}
