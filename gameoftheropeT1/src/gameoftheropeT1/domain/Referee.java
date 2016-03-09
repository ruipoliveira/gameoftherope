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
    private ERefereeState state;
    
    
    public Referee(IRefereePlayground playground, IRefereeSite site, IRefereeRepository repository){
        this.playground = playground;
        this.site = site;
        this.repository = repository;
        state = ERefereeState.START_OF_THE_MATCH;
    }
    
    @Override
    /*This function represents the life cycle of Referee.*/
    public void run() {
        int g, t = 0;           /* trial number */ 
        char decision;      /* trial decision */

        System.out.println("Run referee..."); 

        for (g = 1; g <= Constant.GAMES_PER_MATCH; g++) { // jogo 1, 2 e jogo 3
            switch(state)
            {
                case START_OF_THE_MATCH:
                     t = 0;
                     announceNewGame(g);
                     state = ERefereeState.START_OF_A_GAME;
                break; 
                
                case START_OF_A_GAME:
                     callTrial(t);
                     state = ERefereeState.TEAMS_READY;
                break;
                
                case TEAMS_READY:
                     startTrial();
                     state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION; 
                break;
                
                case WAIT_FOR_TRIAL_CONCLUSION:
                    decision = assertTrialDecision(); // go to the correct state agreed by the char decisoin
                    t += 1;
                    if(decision == Constant.GAME_CONTINUATION){ // se receber continuacao do jogo, faz calltrial
                        callTrial(t);
                        state = ERefereeState.TEAMS_READY;
                    }
                    else if(decision == Constant.GAME_END){
                        declareGameWinner(decision); // transitar no fim do metodo para o estado END_OF_A_GAME
                        state = ERefereeState.END_OF_A_GAME;
                    }
                    
                    else
                        state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                break; 
                 
                case END_OF_A_GAME:
                    if(g < Constant.GAMES_PER_MATCH)
                        state = ERefereeState.START_OF_THE_MATCH; // comeca novamente o ciclo
                    
                    else{
                        declareMatchWinner();
                        state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                    } 
                break;    
            }
       
        }
      
        
    }
    /* actualiza estado */
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
    /* retorna estado actual */
    public ERefereeState getCurrentState() {
        return state;
    }
    
    
    
    private void callTrial(int numTrial){
        playground.callTrial(numTrial);
    }
    
    private void startTrial(){
        playground.startTrial();
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
    
    private void announceNewGame(int numGame){
        site.announceNewGame(numGame);
    }
    
    
    
}
