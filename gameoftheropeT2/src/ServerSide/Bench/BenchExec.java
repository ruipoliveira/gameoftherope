package ServerSide.Bench;

import Communication.CommConst;
import Communication.Proxy.ClientProxy;
import Communication.ServerComm;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author roliveira
 */
public class BenchExec {
    public final static int ELEMENTS_IN_TEAM = 5; 
    public final static int GAMES_PER_MATCH = 3; 
    public final static int OPPOSING_TEAMS = 2; 
    public final static int CONTESTANTS_IN_TRIAL = 3; 
        
    public static void main (String [] args) throws SocketException, IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        ServerComm scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerComm(CommConst.benchServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        
        MBench bench = new MBench( GAMES_PER_MATCH, CONTESTANTS_IN_TRIAL, ELEMENTS_IN_TEAM, OPPOSING_TEAMS); 
        BenchInterface shopInt = new BenchInterface(bench);
        System.out.println("Bench service has started!");
        System.out.println("Server is listening.");
        

        /* processamento de pedidos */
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, shopInt);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                System.exit(0);
            }
        }
        
        
        
    }
}
