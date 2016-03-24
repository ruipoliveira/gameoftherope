package gameoftheropeT1.interfaces;

import gameoftheropeT1.state.EContestantsState;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface IContestantsRepository {
    public void updateTeamAContestState(int contestId, EContestantsState state);
    public void updateTeamBContestState(int contestId, EContestantsState state);
    
    public void updateStrengthTeamA(int contestId, int strength);
    public void updateStrengthTeamB(int contestId, int strength);
    
    public void contestantsInPullTeamA(int coachId, int contestId);
    public void contestantsInPullTeamB(int coachId, int contestId);
    
}
