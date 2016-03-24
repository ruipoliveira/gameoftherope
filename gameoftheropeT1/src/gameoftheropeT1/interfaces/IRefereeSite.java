package gameoftheropeT1.interfaces;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public interface IRefereeSite {
    
    public void announceNewGame(int numGame, int nrTrial);
    
    public void declareGameWinner(int posPull);
    
    public void declareMatchWinner();
        
}
