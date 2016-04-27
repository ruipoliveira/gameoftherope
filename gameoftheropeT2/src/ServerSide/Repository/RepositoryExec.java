/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Repository;
import Communication.*;
import Communication.Proxy.*;
import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
/**
 *
 * @author roliveira
 */
public class RepositoryExec {
    public static void main (String [] args) throws SocketTimeoutException, FileNotFoundException{
        ServerComm scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerComm(CommConst.repServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        MRepository repository = new MRepository("", 2, 5);
        RepositoryInterface repInt = new RepositoryInterface(repository);
        System.out.println("Logging service has started!");
        System.out.println("Server is listening.");
        
        /* processamento de pedidos */
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, repInt);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                System.exit(0);
            }
        } 
    }
}
