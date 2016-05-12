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
public interface PlaygroundInterface extends Remote{
    public void getReady(int coachId, int contId);

    public void amDone(int coachId, int contId, int contestStrength);
    
    public void informReferee(int coachId);
    
    public void startTrial(int nrGame,int numTrial);
    
    public char assertTrialDecision();
    
    public int getPositionPull(); 
    
    public void setPositionPull (int posPull);
}
