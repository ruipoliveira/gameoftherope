package Communication;

/**
 * This file stores the communication constants so that it is easier to change the values, if needed.
 * @author Gabriel Vieira (68021) gabriel.vieira@ua.pt
 * @author Rui Oliveira (68779) ruipedrooliveira@ua.pt
 * @version 2.0
 */

public class CommConst {
    // ssh sd0105@l040101-wsXX.ua.pt
    // qualquercoisa
    
    /**
     * Variable that holds the address for the repository server.
     */
    //public final static String repServerName = "127.0.0.1";
    public final static String repServerName = "l040101-ws01.ua.pt";
    
    /**
     * Variable that holds the port number for the repository server.
     */
    public final static int repServerPort = 22151;
    
    /**
     * Variable that holds the address for the playground server.
     */
    //public final static String playServerName = "127.0.0.1";
    public final static String playServerName = "l040101-ws05.ua.pt";
    
    /** 
     * Variable that holds the port number for the shop server.
     */
    public final static int playServerPort = 22152;
        
    /**
     * Variable that holds the address for the site server.
     */
    //public final static String siteServerName = "127.0.0.1";
    public final static String siteServerName = "l040101-ws04.ua.pt";
    
    /**
     * Variable that holds the port number for the site server.
     */
    public final static int siteServerPort = 22153;
    
    /**
     * Variable that holds the address for the bench server.
     */
    //public final static String benchServerName = "127.0.0.1";
    public final static String benchServerName = "l040101-ws03.ua.pt";
    
    /**
     * Variable that holds the port number for the bench server.
     */
    public final static int benchServerPort = 22154;
    
    /**
     * Variable that holds the timeout value for the server sockets.
     */
    public final static int socketTimeout = 500;

    // client l040101-ws09.ua.pt
    
}
