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
    private final IContestantsSite site;
    private final IContestantsRepository repository;
    
    private EContestantsState state; 
    private final int id;
    
    public CContestant(int id, IContestantsBench bench, IContestantsPlayground playground, IContestantsSite site, IContestantsRepository repository)
    {
        this.bench = bench;
        this.playground = playground;
        this.site = site;
        this.repository = repository;
        this.id = id;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        
    }
    
    @Override
    public void run()
    {
        int id = 1;
        followCoachAdvice(id);
    }
    
    
    
    public void setState(EContestantsState state) {
        this.state = state;
    }
    
    public EContestantsState getCurrentState() {
        return state;
    }
    
    private void followCoachAdvice(int coachId){
        bench.followCoachAdvice(coachId, coachId);
    }
}
