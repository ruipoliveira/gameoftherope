/*
 * This file defines the main method to run the Logging server
 */
package ServerSide.Repository;
import Interfaces.Register;
import Interfaces.RepositoryInterface;
import Structures.Constants.ConstConfigs;
import Structures.Constants.RegistryConfig;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */
public class RepositoryExec {
    public static void main (String [] args){

        System.out.print("\033[H\033[2J");
        System.out.flush();
        
        String rmiRegHostName;                      // nome do sistema onde está localizado o serviço de registos RMI
        int rmiRegPortNumb;                         // port de escuta do serviço

        RegistryConfig rc = new RegistryConfig();
        rmiRegHostName = rc.registryHost();
        rmiRegPortNumb = rc.registryPort();
        
        /* instanciação e instalação do gestor de segurança */
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        /* instanciação do objecto remoto que representa a barbearia e geração de um stub para ele */
        MRepository rep = null;                              // barbearia (representa o objecto remoto)
        RepositoryInterface repInterface = null;             // interface da barbearia

        try {
            rep = new MRepository(ConstConfigs.NAME_FILE, ConstConfigs.OPPOSING_TEAMS, ConstConfigs.ELEMENTS_IN_TEAM);
        } catch (IOException e) {
            System.out.println("Failed to open file for repository: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        try {
            repInterface = (RepositoryInterface) UnicastRemoteObject.exportObject(rep, rc.repositoryPort());
        } catch (RemoteException e) {
            System.out.println("Excepção na geração do stub para o repositorio: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O stub para o repository foi gerado!");

        /* seu registo no serviço de registo RMI */
        String nameEntryBase = RegistryConfig.registerHandler;
        String nameEntryObject = RegistryConfig.repNameEntry;
        Registry registry = null;
        Register reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            System.out.println("Excepção na criação do registo RMI: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O registo RMI foi criado!");

        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        try {
            reg.bind(nameEntryObject, repInterface);
        } catch (RemoteException e) {
            System.out.println("Excepção no registo do logging: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("O logging já está registado: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("O repository foi registado!");
    }
}
