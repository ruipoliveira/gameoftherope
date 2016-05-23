
package ClientSide.Referee;

import Structures.Constants.ConstConfigs;
import Interfaces.BenchInterface;
import Interfaces.PlaygroundInterface;
import Interfaces.RepositoryInterface;
import Interfaces.SiteInterface;
import Structures.Constants.RegistryConfig;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class RefereeExec {

    public static void main(String [] args) throws IOException{
        System.out.print("\033[H\033[2J");
        System.out.flush();       
        System.out.println("******************************************************************\nEntity referee has started!");
        System.out.println("******************************************************************");
        
        
        String rmiRegHostName;                      // nome do sistema onde está localizado o serviço de registos RMI
        int rmiRegPortNumb;                         // port de escuta do serviço

        RegistryConfig rc = new RegistryConfig();
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        RepositoryInterface repInt = null;
        PlaygroundInterface playInt = null;
        BenchInterface benchInt = null;
        SiteInterface siteInt = null;
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            repInt = (RepositoryInterface) registry.lookup (RegistryConfig.repNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating repository: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Repository is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            playInt = (PlaygroundInterface) registry.lookup (RegistryConfig.playgroundNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating playground: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Playground is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            benchInt = (BenchInterface) registry.lookup (RegistryConfig.benchNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating bench: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Bench is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        try
        { 
            Registry registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
            siteInt = (SiteInterface) registry.lookup (RegistryConfig.siteNameEntry);
        }
        catch (RemoteException e)
        { 
            System.out.println("Exception thrown while locating site: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Site is not registered: " + e.getMessage () + "!");
            e.printStackTrace ();
            System.exit(1);
        }
        
        
        Referee referee = new Referee(repInt, playInt, benchInt, siteInt, ConstConfigs.GAMES_PER_MATCH);
        
        referee.start();
       
        try {
            referee.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RefereeExec.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Sending TERMINATE message to the repository");
      
    }
    
}
