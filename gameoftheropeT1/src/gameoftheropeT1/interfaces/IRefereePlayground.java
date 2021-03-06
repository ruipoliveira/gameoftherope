package gameoftheropeT1.interfaces;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface IRefereePlayground {
    
    public void startTrial(int nrGame,int numTrial);
    
    public char assertTrialDecision();
    
    public int getPositionPull(); 
    
    public void setPositionPull (int posPull); 
    
}
