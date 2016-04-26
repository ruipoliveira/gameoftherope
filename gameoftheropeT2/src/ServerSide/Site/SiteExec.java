/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide.Site;

import Communication.CommConst;
import Communication.Proxy.ClientProxy;
import Communication.ServerComm;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author roliveira
 */
public class SiteExec {
    
    public static void main (String [] args) throws SocketException {
        ServerComm scon, sconi;                             // canais de comunicação
        ClientProxy cliProxy;                               // thread agente prestador do serviço

        /* estabelecimento do servico */
        scon = new ServerComm(CommConst.siteServerPort);    // criação do canal de escuta e sua associação
        scon.start();                                       // com o endereço público
        
        MSite site = new MSite(); 
        SiteInterface siteInt = new SiteInterface(site);
        System.out.println("Playground service has started!");
        System.out.println("Server is listening.");
        

        /* processamento de pedidos */
        while (true) {
            //scon.setTimeout(500);
            try {
                sconi = scon.accept();                         // entrada em processo de escuta
                cliProxy = new ClientProxy(scon, sconi, siteInt);    // lançamento do agente prestador do serviço
                cliProxy.start();
            } catch (SocketTimeoutException ex) {
                System.exit(0);
            }
        }
    }
    
}
