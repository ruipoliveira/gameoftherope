/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;

/**
 *
 * @author gabriel
 */
public interface BenchInterface extends Remote{
    
    public void callContestants(int coachId);
    
    public void reviewNotes(int coachId);
    
    public boolean endOfTheGame(int c);
    
    public void seatDown(int coachId, int contId);
    
    public void followCoachAdvice(int coachId, int contId);
    
    public boolean isPlayerSelected(int coachId, int contestId); 
    
    public void callTrial(int nrTrial, int numGame);

    public boolean allSittingTeams(); 
}
