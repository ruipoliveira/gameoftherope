/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameoftheropeT1.interfaces;

import gameoftheropeT1.state.EContestantsState;

/**
 *
 * @author roliveira
 */
public interface IContestantsRepository {
    public void UpdateContestState(int contestId, EContestantsState state);
}
