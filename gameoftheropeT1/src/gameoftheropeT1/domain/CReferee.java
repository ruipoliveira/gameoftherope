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
import gameoftheropeT1.interfaces.*;
import newpackage.state.ERefereeState;

public class CReferee extends Thread{
    private final IRefereeBench bench;
    private final IRefereeSite site;
    private final IRefereePlayground playground;
    private final IRefereeRepository repository;
    private ERefereeState state;
    
    
    public CReferee(IRefereeBench bench, IRefereePlayground playground, IRefereeSite site, IRefereeRepository repository)
    {
        this.bench = bench;
        this.playground = playground;
        this.site = site;
        this.repository = repository;
        
        state = ERefereeState.START_OF_A_GAME;
    }
    
    @Override
    public void run() // ciclo de vida do arbitro
    {
        
    }
            // actualiza estado
    public void setState(ERefereeState state) {
        this.state = state;
    }
    
            // retorna estado actual
    public ERefereeState getCurrentState() {
        return state;
    }
}
