package ClientSide.Coach;

import Structures.Enumerates.ECoachesState;
import Interfaces.*;
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
    
    private final int idCoach;

    public Coach(int idCoach, RepositoryInterface repository, PlaygroundInterface playground, BenchInterface bench, SiteInterface site){
        this.setName("Coach "+idCoach);
        this.idCoach = idCoach;
        this.bench = bench;
        this.site = site; 
        this.playground = playground;
        this.repository = repository;
        state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
    }
    
    /**
     * Esta função representada o ciclo de vida de um treinador.
     */
    @Override
    public void run(){
        do {
            switch(this.state){
                case WAIT_FOR_REFEREE_COMMAND:
                    if (!endOfTheGame(idCoach)) break;
                    callContestants(idCoach);
                    state = ECoachesState.ASSEMBLE_TEAM;
                    updateCoachState(idCoach, state);
                    break; 

                case ASSEMBLE_TEAM:      
                    informReferee(idCoach); 
                    state = ECoachesState.WATCH_TRIAL;
                    updateCoachState(idCoach, state);
                    break; 
                    
                case WATCH_TRIAL:
                    reviewNotes(idCoach);
                    state = ECoachesState.WAIT_FOR_REFEREE_COMMAND; 
                    updateCoachState(idCoach, state);
                    break;
            }
        }while (endOfTheGame(idCoach));
        
        System.out.println("Fim do treinador "+idCoach); 
    
    }
    
    /**
     * coach call the contestants to play the game
     * 
     * @param idCoach is the coach identifier (ID)
     */
    private void callContestants(int idCoach){
        //bench.callContestants(idCoach);    
    }
    
    /**
     * coach informs referee that his team is ready to play the game
     * 
     * @param idCoach 
     */
    private void informReferee(int idCoach){   
        //playground.informReferee(idCoach);
    }
    
    /**
     * after each trial, coach review his notes
     * 
     * @param idCoach 
     */
    private void reviewNotes(int idCoach){
        //bench.reviewNotes(idCoach);
    }
    
    /**
     * verify if the game is over
     * return true if the game is over
     * return false, otherwise
     * 
     * @param idCoach
     * @return 
     */
    private boolean endOfTheGame(int idCoach) {
       
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

   
    /**
     * updates the coach current state
     * 
     * @param idCoach is the coach identifier (ID)
     * @param state is the coach current state
     */
    private void updateCoachState(int idCoach, ECoachesState state) {
    }
    
}