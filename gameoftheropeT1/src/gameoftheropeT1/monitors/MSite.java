package gameoftheropeT1.monitors;
import gameoftheropeT1.interfaces.*;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 1.0
 */
public class MSite implements IRefereeSite, ICoachSite{
    MRepository repository; 
    private boolean newGame;
    private boolean endOfGame; 
    private boolean endOfMatch;
    private int posPull;
    private int winnerTeamA;
    private int winnerTeamB; 
    private int nrTrial, numGame; 
    private boolean endOp; 
    
    public MSite(MRepository repository ){
        this.repository = repository; 
        newGame = false; 
        endOfGame = false; 
        endOfMatch = false;
        endOp = false; 
    }
    
    /**
     * The referee starts a game. The game number should be updated. Both game header and internal state should be saved.
     * @param numGame
     * @param nrTrial 
     */
    @Override
    public synchronized void announceNewGame(int numGame, int nrTrial) {
        this.nrTrial = nrTrial; 
        this.numGame = numGame; 
    }
    
    /**
     * The referee announces which teams has won the match. Both internal state and match result should be saved.
     * @param posPull 
     */
    @Override
    public synchronized void declareGameWinner(int posPull) {

        if (posPull < 0 ){
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa A!"); 
            repository.isEnd(numGame, "A");
            winnerTeamA++; 
        }
        else if (posPull > 0){
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Vence equipa B!"); 
            repository.isEnd(numGame, "B");
            winnerTeamB++; 
        }
        else{
            System.out.println("Posicao da Corda: "+posPull+" | Game #"+numGame+" | Empatado!"); 
            repository.wasADraw(numGame);
        }
    }
    
    /**
     * The referee announces which teams has won the match. Both internal state and match result should be saved.
     */
    @Override
    public synchronized void declareMatchWinner() {

        System.out.println("***********************************************************");
        if (winnerTeamA > winnerTeamB){
            System.out.println("A Equipa A venceu o match com #" +winnerTeamA +" vitórias!");
            repository.endMatch("A", winnerTeamA, winnerTeamB);

        }
        else if (winnerTeamA < winnerTeamB){
            System.out.println("A Equipa B venceu o match com #" +winnerTeamB +" vitórias!");
            repository.endMatch("B", winnerTeamB, winnerTeamA);
        }
        else{
            System.out.println("O match ficou empatado! :(  A#" +winnerTeamA +" - B#"+winnerTeamB);
            repository.endMatch("", winnerTeamB, winnerTeamA);
        }
        System.out.println("***********************************************************");

    }
    /**
     * checks coach operation ended
     * @param id
     * @return 
     */
    @Override
    public boolean endOperCoach(int id) {
        return endOp; 
    }
    
}