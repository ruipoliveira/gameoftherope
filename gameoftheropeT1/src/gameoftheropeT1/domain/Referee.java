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
        int g = 1;           /* trial number */ 
        char decision;      /* trial decision */

        System.out.println("Run referee..."); 

        //for (g = 1; g <= Constant.GAMES_PER_MATCH; g++) { // jogo 1, 2 e jogo 3
            int t = 0;
            while(true){ // verificar se o jogo acabo... 

                switch(state)
                {
                    case START_OF_THE_MATCH:
                        announceNewGame(g);
                        state = ERefereeState.START_OF_A_GAME;
                        repository.UpdateRefState(state); // actualiza no repositorio
                        break; 

                    case START_OF_A_GAME:
                        t++; 
                        callTrial(t,g);
                         
                         state = ERefereeState.TEAMS_READY;
                         //repository.UpdateRefState(state);  // actualiza no repositorio
                        break;

                    case TEAMS_READY:
                         startTrial(t);
                         state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                         //repository.UpdateRefState(state); // actualiza no repositorio
                        break; 

                    case WAIT_FOR_TRIAL_CONCLUSION:

                        decision = assertTrialDecision(); // go to the correct state agreed by the char decisoin
                        
                        if(decision == Constant.GAME_CONTINUATION){ // se receber continuacao do jogo, faz calltrial
                            //callTrial(t);
                            System.out.println("-----------------------JOGO nãoACABOU "+t+"!!!!-------------------");
                            state = ERefereeState.START_OF_A_GAME;
                            //repository.UpdateRefState(state);  // actualiza no repositorio
                        }
                        else if(decision == Constant.GAME_END){
                            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4JOGO ACABOU "+t+" !!!!$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"); 
                            declareGameWinner(decision); // transitar no fim do metodo para o estado END_OF_A_GAME
                            state = ERefereeState.END_OF_A_GAME;
                            //repository.UpdateRefState(state);  // actualiza no repositorio
                        }

                        else{
                            state = ERefereeState.WAIT_FOR_TRIAL_CONCLUSION;
                            //repository.UpdateRefState(state);  // actualiza no repositorio
                        }
                        break; 

                    case END_OF_A_GAME:
                        if(g <= Constant.GAMES_PER_MATCH){
                            t = 0; // mete trial a 0
                            g++;      // incrementa numero do jogo                      
                            state = ERefereeState.START_OF_THE_MATCH; // comeca novamente o ciclo
                            //repository.UpdateRefState(state);  // actualiza no repositorio
                        }            
                        else{
                            declareMatchWinner();
                            state = ERefereeState.END_OF_THE_MATCH; // termina o encontro
                            //repository.UpdateRefState(state);   // actualiza no repositorio
                        } 
                        break;
                    
                    case END_OF_THE_MATCH:
                        //repository.UpdateRefState(state);  // actualiza no repositorio
                        break;
                }
            //}
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
    
    
    
    private void callTrial(int numTrial, int numGame){
        bench.callTrial(numTrial, numGame);
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
    
    private void announceNewGame(int numGame){
        site.announceNewGame(numGame);
    }
    
    
    
}
