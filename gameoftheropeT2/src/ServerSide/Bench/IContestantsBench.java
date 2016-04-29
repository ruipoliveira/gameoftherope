package ServerSide.Bench;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IContestantsBench {
    
    public void seatDown(int coachId, int contId);
    
    public void followCoachAdvice(int coachId, int contId);
    
    public boolean isPlayerSelected(int coachId, int contestId); 
}
