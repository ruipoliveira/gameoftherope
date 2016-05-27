package ClientSide.Coach;

import Structures.Enumerates.ECoachesState;
import Interfaces.*;
import Structures.Constants.ConstConfigs;
import Structures.VectorClock.VectorTimestamp;
import static java.lang.Thread.sleep;
import java.util.Arrays;
 


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class Coach extends Thread{
    
    private ECoachesState state; 
    
    private final BenchInterface bench; 
    //private final ICoachSite site; 
    private final PlaygroundInterface playground;
    private final RepositoryInterface repository;
    private final SiteInterface site; 
    
    private final VectorTimestamp myClock;
    private VectorTimestamp receivedClock;
    
    private final int idCoach;

    public Coach(int idCoach, RepositoryInterface repository, PlaygroundInterface playground, BenchInterface bench, SiteInterface site){
        this.setName("Coach "+idCoach);
        this.idCoach = idCoach;
        this.bench = bench;
        this.site = site; 
        this.playground = playground;
        this.repository = repository;
        state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
        
        myClock = new VectorTimestamp(ConstConfigs.ELEMENTS_IN_TEAM + ConstConfigs.OPPOSING_TEAMS + 1, idCoach);
    }
    
    /**
     * Esta função representada o ciclo de vida de um treinador.
     */
    @Override
    public void run(){
        do {
            switch(this.state){
                case WAIT_FOR_REFEREE_COMMAND:
                    if (!bench.endOfTheGame(idCoach)) break;
                    
                    myClock.increment(); // added    // ver melhor isto
                    receivedClock = callContestants(idCoach, myClock.clone());
                    myClock.update(receivedClock); // added
                    
                    state = ECoachesState.ASSEMBLE_TEAM;
                    repository.updateCoachState(idCoach, state, myClock.clone() ); // ver depois
                    break; 

                case ASSEMBLE_TEAM:
                    myClock.increment(); // added
                    receivedClock = informReferee(idCoach, myClock.clone());
                    myClock.update(receivedClock); // added
                    
                    state = ECoachesState.WATCH_TRIAL;
                    repository.updateCoachState(idCoach, state, myClock.clone());
                    break; 
                    
                case WATCH_TRIAL:
                    myClock.increment(); // added
                    receivedClock = reviewNotes(idCoach, myClock.clone());
                    myClock.update(receivedClock); // added
                    
                    state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
                    repository.updateCoachState(idCoach, state, myClock.clone()); // fazer mais tarde
                    break;
            }
        }while (bench.endOfTheGame(idCoach));
        
        System.out.println("Fim do treinador "+idCoach); 
    
    }
    
    /**
     * coach call the contestants to play the game
     * 
     * @param idCoach is the coach identifier (ID)
     */
    private VectorTimestamp callContestants(int idCoach, VectorTimestamp vt){
        return bench.callContestants(idCoach, vt);    
    }
    
    /**
     * coach informs referee that his team is ready to play the game
     * 
     * @param idCoach 
     */
    private VectorTimestamp informReferee(int idCoach, VectorTimestamp vt){   
        return playground.informReferee(idCoach, vt);
    }
    
    /**
     * after each trial, coach review his notes
     * 
     * @param idCoach 
     */
    private VectorTimestamp reviewNotes(int idCoach, VectorTimestamp vt){
        return bench.reviewNotes(idCoach, vt);
    }
    
  
     
    /**
     * Permite atualizar o estado o treiandor. 
     * @param state 
     */
    public void setState(ECoachesState state){
        this.state = state;
    }
    
   
    
    /**
     * Permite aceder ao estado atual do treinador. 
     * @return ECoachesState estado atual do treinador 
     */
    public ECoachesState getCurrentState() {
        return state;
    }
    
    /**
     * Permite aceder ao identificador do treinador.
     * @return idCoach 
     */
    public int getIdCoach(){
        return idCoach;
    }    
}