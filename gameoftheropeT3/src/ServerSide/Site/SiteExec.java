package ServerSide.Site;

import Interfaces.SiteInterface;
import Structures.Constants.RegistryConfig;
import java.io.IOException;
import java.net.SocketException;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class SiteExec {
    
    public static void main (String [] args) throws SocketException, IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
                 
        String rmiRegHostName;                      // nome do sistema onde está localizado o serviço de registos RMI
        int rmiRegPortNumb;                         // port de escuta do serviço
        
        RegistryConfig rc = new RegistryConfig("../../config.ini");
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        
        /*MSite site = new MSite(); 
        SiteInterface siteInt = new SiteInterface(site);
        System.out.println("******************************************************************\nSite service has started!");
        System.out.println("Server is listening.\n******************************************************************");*/
        
        
    }
    
}
