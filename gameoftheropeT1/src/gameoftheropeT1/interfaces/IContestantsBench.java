/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.interfaces;

import static gameoftheropeT1.domain.Contestant.nrContestantsInPull;

/**
 *
 * @author gabriel
 */
public interface IContestantsBench {
    public boolean seatDown(int coachId, int contId);
    
    public void followCoachAdvice(int coachId, int contId, int nrContestantsInPull);
}
