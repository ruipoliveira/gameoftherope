/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.sharing;
import gameoftheropeT1.interfaces.*;

/**
 *
 * @author gabriel
 */
public class CPlayground implements IRefereePlayground, ICoachPlayground, IContestantsPlayground{

      ///////////       ////////////////////////////////
    /////////// REFEREE ///////////////////////////////
    @Override
    public synchronized void callTrial(int numTrial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void startTrial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized char assertTrialDecision() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    ///////////       ////////////////////////////////
    /////////// COACH ///////////////////////////////
    @Override
    public synchronized void informReferee(int coachId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
      ///////////           ////////////////////////////////
    /////////// CONTESTANTS ///////////////////////////////
    @Override
    public synchronized void getReady(int coachId, int contId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public synchronized void amDone(int coachId, int contId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
