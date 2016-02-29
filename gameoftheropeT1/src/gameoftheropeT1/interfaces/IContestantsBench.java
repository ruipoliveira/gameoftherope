/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.interfaces;

/**
 *
 * @author gabriel
 */
public interface IContestantsBench {
    public boolean seatDown(int coachId, int contestId);
    
    public void followCoachAdvice(int coachId, int contestId);
}
