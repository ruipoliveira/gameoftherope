/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Structures.Enumerates.ECoachesState;
import Structures.Enumerates.EContestantsState;
import Structures.Enumerates.ERefereeState;
import java.rmi.Remote;

/**
 *
 * @author gabriel
 */
public interface RepositoyInterface extends Remote{
    
    public void updateCoachState(int coachId, ECoachesState state);
    
    public void updateRefState(ERefereeState state);  

    public void updatePullPosition(int positionPull);

    public void updateTrialNumber(int trialNumber);

    public void updateGameNumber(int gameNumber);

    public void isKnockOut(int nrGame, int nrTrial, String team);
    
    public void updateContestantState(int idTeam, int contestId, EContestantsState state);
    
    public void updateStrength(int idTeam, int contestId, int contestStrength); 
    
    public void updateStrengthAndWrite(int idTeam, int contestId, int strength);
    
    public void addContestantsInPull(int coachId, int idPlayer);
    
    public void removeContestantsInPull(int coachId , int idPlayer);
    
}
