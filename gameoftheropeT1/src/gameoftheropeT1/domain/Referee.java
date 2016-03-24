/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.domain; 

/**
 *
 * @author roliveira
 */
import gameoftheropeT1.main.Constant;
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.ERefereeState;

public class Referee extends Thread{
    private final IRefereeSite site;
    private final IRefereePlayground playground;
    private final IRefereeRepository repository;
    private final IRefereeBench bench; 
    private ERefereeState state;
    
    
    public Referee(IRefereePlayground playground, IRefereeSite site, IRefereeBench bench, IRefereeRepository repository){
        this.playground = playground;
        this.site = site;
        this.bench = bench; 
        this.repository = repository;
        state = ERefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    /*This function represents the life cycle of Referee.*/
    public void run() {
        int nrGame =0, nrTrial = 0;
            boolean cenas = true; 

            do{
                char decision = 'F'; 
                
                switch(state)
                {
                    case START_OF_THE_MATCH:
                        nrTrial++; nrGame++; 
                        announceNewGame(nrGame,nrTrial);
                        state = ERefereeState.START_OF_A_GAME;
                        break; 
                        
                    case START_OF_A_GAME:
                        callTrial(nrGame,nrTrial );
                        state = ERefereeState.TEAMS_READY;
                        break;

                    case TEAMS_READY:
                        startTrial(nrTrial);
                        state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION; 
                        break; 

                    case WAIT_FOR_TRIAL_CONCLUSION:
                        
                        
                        if (bench.estatudosentado()){
                            decision = assertTrialDecision();
                        }
                        
                        
                        if(decision == Constant.GAME_CONTINUATION){ 
                            System.out.println("Jogo vai continuar");
                            nrTrial++; 
                            state = ERefereeState.START_OF_A_GAME;
                        }
                        else if(decision == Constant.GAME_END){
                            System.out.println("Jogo acaba!");
                            
                            declareGameWinner(decision);  
                            state = ERefereeState.END_OF_A_GAME;
                        }

                        break; 

                    case END_OF_A_GAME:
                        if(nrGame < 3){
                            nrTrial=0;
                            state = ERefereeState.START_OF_THE_MATCH;
                        }

                        else{
                            declareMatchWinner();
                            state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                        } 
                        break;
                    
                    case END_OF_THE_MATCH: 
                        
                        System.out.println("FIM do match!"); 
                        cenas = false; 
                        break; 
                        
                }
               
            }while(cenas); 
            
            System.out.println("Foi-se"); 

                                       
      
        
    }
    /* actualiza estado */
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
    /* retorna estado actual */
    public ERefereeState getCurrentState() {
        return state;
    }
    
    
    
    private void callTrial(int nrGame, int nrTrial){
        bench.callTrial(nrGame, nrTrial);
    }
    
    private void startTrial(int numTrial){
        playground.startTrial(numTrial);
    }
    
    private char assertTrialDecision(){
        return playground.assertTrialDecision(); 
    }
    
    
    private void declareGameWinner(char winner){
        site.declareGameWinner(winner);
    }
    
    private void declareMatchWinner(){
        site.declareMatchWinner();
    }
    
    private void announceNewGame(int numGame, int nrTria){
        site.announceNewGame(numGame, nrTria);
    }
    
    
    
}