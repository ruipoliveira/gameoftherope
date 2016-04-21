package ServerSide.Bench;

import Communication.CommConst;
import Communication.Proxy.ClientProxy;
import Communication.ServerComm;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author roliveira
 */
public class BenchExec {
    public static void main (String [] args) throws SocketException {
        ServerComm scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerComm(CommConst.benchServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        
        MBench bench = new MBench(repository, 0, 0, 0, 0); 
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
