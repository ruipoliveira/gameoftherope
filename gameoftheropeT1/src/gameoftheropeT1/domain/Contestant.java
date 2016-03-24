/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain;

import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.monitors.MPlayground;
import gameoftheropeT1.monitors.MSite;
import gameoftheropeT1.state.EContestantsState;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roliveira
 */

public class Contestant extends Thread{
    private final IContestantsBench bench;
    private final IContestantsPlayground playground;
    private final IContestantsRepository repository;
    private final MSite site; 
    
    private int coachId;
    private EContestantsState state; 
    private final int contId;
    
    private int contestStrength;
    
    public Contestant(int contId, int coachId, IContestantsBench bench, IContestantsPlayground playground, IContestantsRepository repository, MSite site)
    {
        this.bench = bench;
        this.playground = playground;
        this.repository = repository;
        this.site = site; 
        
        this.contId = contId;
        state = EContestantsState.SEAT_AT_THE_BENCH;
        
        contestStrength = generateStrength();
        this.coachId = coachId;
        
    }
    
    @Override
    public void run() {
        
        
        boolean cenas = true; 
      //  System.out.println("Run Contestant #"+ this.contId); 
        do {
            switch(this.state)
            {                             
                case SEAT_AT_THE_BENCH:         
                    
                    followCoachAdvice (coachId, contId); /* the contestant complies to coach decision */
                    
                    if (site.endOperCoach(coachId)){
                        cenas = false; 
                        break;
                    } 

                    state = EContestantsState.STAND_IN_POSITION;
                    
                    if(coachId == 1)
                        repository.updateTeamAContestState(contId, state);
                    
                    else if(coachId == 2){
                        repository.updateTeamBContestState(contId, state);
                    }

                break;
                
                case STAND_IN_POSITION:
                    
                    
                    if (isPlayerSelected(coachId,contId) ){
                        getReady(coachId, contId); /* the contestant takes place at his end of the rope */
                        
                        state = EContestantsState.DO_YOUR_BEST;
                        
                        if(coachId == 1){
                            repository.updateTeamAContestState(contId, state);
                            repository.contestantsInPullTeamA(coachId, contId);
                         }
                        else if(coachId == 2){
                            repository.updateTeamBContestState(contId, state); 
                            repository.contestantsInPullTeamB(coachId, contId);
                    }
                        
                        
                    }
                    else{
                        
                        state = EContestantsState.SEAT_AT_THE_BENCH;
                       
                        if(coachId == 1)
                            repository.updateTeamAContestState(contId, state);
                        
                        else if(coachId == 2)
                            repository.updateTeamBContestState(contId, state);

 
                        // System.out.println(coachId+"-"+contId+" -> NÃO JOGA!"); 
                       contestStrength++;
                       
                        if(coachId == 1)
                            repository.updateStrengthTeamA(contId, contestStrength);
                       else if(coachId == 2)
                            repository.updateStrengthTeamB(contId, contestStrength);

                        

                    }

                break;
                
                case DO_YOUR_BEST:
                    amDone(coachId, contId, contestStrength);  /* the contestant ends his effort */  
                    
                    seatDown (coachId,contId); /* the contestant goes to the bench to rest a little bit */
                    
                    contestStrength--;
                    //System.out.print("contest: #"+contId+" strength: #"+contestStrength);
                    
                    if(coachId == 1)
                        repository.updateStrengthTeamA(contId, contestStrength);
                    
                    else if(coachId == 2)
                        repository.updateStrengthTeamB(contId, contestStrength);

                    
                    
                    
                    
                    
                    state = EContestantsState.SEAT_AT_THE_BENCH;
                
                    if(coachId == 1)
                            repository.updateTeamAContestState(contId, state);
                        
                    else if(coachId == 2)
                            repository.updateTeamBContestState(contId, state);

                
                
                break;    
            }
            
            //System.out.println(">>>JOGO ACABOU!"+bench.endOfTheGame());
        } while (cenas); // acaba o jogo.... 
        
        System.out.println("Fim jogador #"+coachId); 
        
    }
    
    
    public void setState(EContestantsState state) {
        this.state = state;
    }
    
    public EContestantsState getCurrentState() {
        return state;
    }
    
    private void seatDown(int coachId, int contestId){
        bench.seatDown(coachId, contestId); 
    }
    
    private boolean isPlayerSelected(int coachId, int contestId){
        return bench.isPlayerSelected(coachId,contId); 
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
    
    
    private static int generateStrength(){
        return 10 + (int)(Math.random() * ((20 - 10) + 1)); 
    }
    
    
        
    
}