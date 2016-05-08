package ServerSide.Bench;

import Interfaces.BenchInterface;
import Communication.ConstConfigs;
import Communication.CommConst;
import Communication.Proxy.ClientProxy;
import Communication.ServerComm;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class BenchExec {
        
    public static void main (String [] args) throws SocketException, IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        ServerComm scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerComm(CommConst.benchServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        
        MBench bench = new MBench( ConstConfigs.GAMES_PER_MATCH, ConstConfigs.CONTESTANTS_IN_TRIAL, ConstConfigs.ELEMENTS_IN_TEAM, ConstConfigs.OPPOSING_TEAMS); 
        BenchInterface shopInt = new BenchInterface(bench);
        System.out.println("******************************************************************\nBench service has started!");
        System.out.println("Server is listening.\n******************************************************************");
        
        /* processamento de pedidos */
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept(); // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, shopInt); // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                System.exit(0);
            }
        }
        
        
        
    }
}
