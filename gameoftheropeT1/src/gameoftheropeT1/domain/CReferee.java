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
import gameoftheropeT1.main.CConstant;
import gameoftheropeT1.interfaces.*;
import gameoftheropeT1.state.ERefereeState;

public class CReferee extends Thread{
    private final IRefereeSite site;
    private final IRefereePlayground playground;
    private final IRefereeRepository repository;
    private ERefereeState state;
    
    
    public CReferee(IRefereePlayground playground, IRefereeSite site, IRefereeRepository repository){
        this.playground = playground;
        this.site = site;
        this.repository = repository;
        state = ERefereeState.START_OF_A_GAME;
    }
    
    @Override
    /*This function represents the life cycle of Referee.*/
    public void run() {
        int g, t;           /* trial number */ 
        char decision;      /* trial decision */

        System.out.println("Run referee..."); 

        for (g = 0; g < CConstant.GAMES_PER_MATCH; g++) {
            announceNewGame(g);         /* the referee opens a new game */
            t = 0;                      /* the referee initializes trial number */
            do{ 
                callTrial(t);           /* the referee calls the contestants to a new trial */
                startTrial();           /* the referee gives pulling command */
                decision = assertTrialDecision();   /* the referee checks trial result */
                t += 1;   /* increment trial number */
            } while (decision == CConstant.GAME_CONTINUATION);   /* is game to be continued? */
            
            declareGameWinner (decision); /* the referee announces the winner of the game */
        }
        declareMatchWinner(); /* the referee announces the winner of the match */
        
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
