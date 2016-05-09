package Interfaces;


import Structures.Enumerates.ERefereeState;
import Structures.Enumerates.EContestantsState;
import Structures.Enumerates.ECoachesState;
import Structures.VectorClock.VectorTimestamp;
import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public interface RepositoryInterface extends Remote {
    
    public void writeLine(VectorTimestamp vt) throws RemoteException;
    
    public void endWriting() throws RemoteException;
    
    public void writeLineGame() throws RemoteException;
    
    public void updateRefState(ERefereeState state) throws RemoteException;
    
    public void updateCoachState(int idCoach, ECoachesState state) throws RemoteException;
    
    public void updateContestantState(int idTeam, int idContest, EContestantsState state) throws RemoteException;
    
    public void updateStrength(int idTeam, int idContest, int contestStrength) throws RemoteException;
    
    public void updateStrengthAndWrite(int idTeam,int contestId, int contestStrength) throws RemoteException;
    
    public void updatePullPosition(int posPull) throws RemoteException;
    
    public void updateTrialNumber(int nrTrial) throws RemoteException;
    
    public void updateGameNumber(int nrGame) throws RemoteException;
    
    public void isKnockOut(int nrGame, int nrTrial, String team ) throws RemoteException;
    
    public void isEnd(int nrGame, String team) throws RemoteException;
    
    public void wasADraw(int nrGame) throws RemoteException;
    
    public void endMatch(String team, int resultA, int resultB) throws RemoteException;
    
    public void addContestantsInPull(int idTeam, int idPlayer) throws RemoteException;
    
    public void removeContestantsInPull(int idTeam, int idPlayer) throws RemoteException;   
}
