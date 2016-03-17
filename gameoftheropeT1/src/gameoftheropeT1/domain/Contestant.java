/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain;

import gameoftheropeT1.interfaces.*;
import static gameoftheropeT1.monitors.MBench.generateStrength;
import gameoftheropeT1.state.EContestantsState;

/**
 *
 * @author roliveira
 */

public class Contestant extends Thread{
    private final IContestantsBench bench;
    private final IContestantsPlayground playground;
    private final IContestantsRepository repository;
    
    private int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    
    public Contestant(int contId, int coachId, IContestantsBench bench, IContestantsPlayground playground, IContestantsRepository repository)
    {
        this.bench = bench;
        this.playground = playground;
        this.repository = repository;
        
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        
        contestStrength = generateStrength();
        this.coachId = coachId;
        
    }
    
    @Override
    public void run() {
        
  
        System.out.println("Run Contestant #"+ this.contId); 
        do {
            switch(this.state)
            {                             
                case SEAT_AT_THE_BENCH:
                    followCoachAdvice (coachId, contId); /* the contestant complies to coach decision */
                    state = EContestantsState.STAND_IN_POSITION;

                break;
                
                case STAND_IN_POSITION:
                    getReady(contId,coachId); /* the contestant takes place at his end of the rope */
                    state = EContestantsState.DO_YOUR_BEST;
                break;
                
                case DO_YOUR_BEST:
                    amDone (contId, coachId, contestStrength); /* the contestant ends his effort */                    
                    if (seatDown (contId,coachId)) break; /* the contestant goes to the bench to rest a little bit */
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                break;    
            }         
        } while (true); // acaba o jogo.... 
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
    
    private void amDone(int coachId, int contId, int contestStrength){
        playground.amDone(coachId, contId, contestStrength);
    }
    
    private void getReady(int coachId, int contestId){
        playground.getReady(coachId, contestId);
    }

    private boolean endOperContestants(int contId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }
        
    
}
