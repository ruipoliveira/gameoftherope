/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain;

import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.EContestantsState;

/**
 *
 * @author roliveira
 */
public class CContestant extends Thread{
    private final IContestantsBench bench;
    private final IContestantsPlayground playground;
    private final IContestantsRepository repository;
    
    private EContestantsState state; 
    private final int contId;
    
    public CContestant(int contId, IContestantsBench bench, IContestantsPlayground playground, IContestantsRepository repository)
    {
        this.bench = bench;
        this.playground = playground;
        this.repository = repository;
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        
    }
    
    @Override
    public void run() {
        int cId =1; /* coach identification */
        int nId = 2;  /* contestant identification */
  
        System.out.println("Run Contestant #"+ this.contId); 
        do {
            switch(this.state)
            {
                case SEAT_AT_THE_BENCH:
                    followCoachAdvice (cId,nId); /* the contestant complies to coach decision */
                    state = EContestantsState.STAND_IN_POSITION;
                break;
                
                case STAND_IN_POSITION:
                    getReady(cId,nId); /* the contestant takes place at his end of the rope */
                    state = EContestantsState.DO_YOUR_BEST;
                break;
                
                case DO_YOUR_BEST:
                    amDone (cId,nId); /* the contestant ends his effort */                    
                    if (seatDown (cId,nId)) break; /* the contestant goes to the bench to rest a little bit */
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                break;    
            }         
        } while (endOperContestants(this.contId)); // acaba o jogo.... 

    }
    
    
    public void setState(EContestantsState state) {
        this.state = state;
    }
    
    public EContestantsState getCurrentState() {
        return state;
    }
    
    private boolean seatDown(int coachId, int contestId){
        return bench.seatDown(coachId, contestId); 
    }
    
    private void followCoachAdvice(int coachId, int contestId){
        bench.followCoachAdvice(coachId, contestId);
    }
    
    private void amDone(int coachId, int contId){
        playground.amDone(coachId, contId);
    }
    
    private void getReady(int coachId, int contestId){
        playground.getReady(coachId, contestId);
    }

    private boolean endOperContestants(int contId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
