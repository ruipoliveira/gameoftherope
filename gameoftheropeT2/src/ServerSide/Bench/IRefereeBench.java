package ServerSide.Bench;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface IRefereeBench {
    
    public void callTrial(int nrTrial, int numGame);

    public boolean allSittingTeams(); 
}
