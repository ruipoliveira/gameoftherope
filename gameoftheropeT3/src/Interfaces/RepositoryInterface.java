/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Structures.Enumerates.ECoachesState;
import Structures.Enumerates.EContestantsState;
import Structures.Enumerates.ERefereeState;
import java.rmi.Remote;

/**
 *
 * @author gabriel
 */
public interface RepositoryInterface extends Remote, ICoachRepository, IContestantsRepository, IRefereeRepository{

    
}
