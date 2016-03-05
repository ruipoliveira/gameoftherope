/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain;

import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.ECoachesState;

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
        System.out.println("Run coach #"+this.idCoach); 
        do {
            reviewNotes (this.idCoach);     /* the coach reviews his notes */
            callContestants(this.idCoach);  /* the coach calls contestants to a trial */
            informReferee(this.idCoach);    /* the coach informs the referee the team is ready */
        }while (false); // endOperCoach(this.idCoach) ?? 


    }
    
    private void callContestants(int idCoach){
        bench.callContestants(idCoach);
    }
    
    private void informReferee(int idCoach){
        site.informReferee(idCoach);
    }
    
    private void reviewNotes(int idCoach){
        playground.reviewNotes(idCoach);
    }
    
    public void setState(ECoachesState state){
        this.state = state;
    }
    
    public ECoachesState getCurrentState() {
        return state;
    }
    
}
