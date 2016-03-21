/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.interfaces;

/**
 *
 * @author roliveira
 */

// numTrial -> numero do trial

public interface IRefereePlayground {
    
    public void startTrial(int numTrial);
    
    public char assertTrialDecision();
}
