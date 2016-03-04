/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain;

import gameoftheropeT1.interfaces.*;
import newpackage.state.ECoachesState;

/**
 *
 * @author roliveira
 */
public class CCoach extends Thread{
    
    private ECoachesState state; 
    private final ICoachBench bench; 
    private final ICoachPlayground playground; 
    private final ICoachRepository repository;
    private final ICoachSite site; 
    private final int idCoach; 
    
    public CCoach(int idCoach, ICoachBench bench, ICoachPlayground playground, ICoachSite site, 
            ICoachRepository repository ){
        this.setName("Coach "+idCoach);
        this.idCoach = idCoach; 
        this.bench = bench;
        this.playground = playground;
        this.site = site; 
        this.repository = repository;
        state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
    }
    
    @Override
    public void run(){
        callContestants(); 
        
        
    }
    
    private void callContestants()
    {
        bench.callContestants(idCoach);
    }
    
    private void informReferre()
    {
        site.informReferee(idCoach);
    }
    
    private void reviewNotes(){
        playground.reviewNotes(idCoach);
    }
    
    public void setState(ECoachesState state) {
        this.state = state;
    }
    
    public ECoachesState getCurrentState() {
        return state;
    }
    
}
