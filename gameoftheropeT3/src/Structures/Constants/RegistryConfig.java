/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures.Constants;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains all the constants related with the Registry.
 * 
 **/
public class RegistryConfig {
    /**
     * Repository name entry on the registry.
     */
    public static String repNameEntry = "RepositoryInt";

    /**
     * Bench name entry on the registry.
     */
    public static String benchNameEntry = "BenchInt";

    /**
     * Playground name entry on the registry.
     */
    public static String playgroundNameEntry = "PlaygroundInt";

    /**
     * Site name entry on the registry.
     */
    public static String siteNameEntry = "SiteInt";

    /**
     * RegisterHandler name entry on the registry.
     */
    public static String registerHandler = "RegisterHandler";
    /**
     * Bash property of the file.
     */
    private Properties prop;
    /**
     * Constructor that receives the file with the configurations.
     * @param filename path for the configuration file
     */
    public RegistryConfig(String filename) {
        prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream(filename);
            prop.load(in);
            in.close();
        } catch (IOException ex) {
            Logger.getLogger(RegistryConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(RegistryConfig.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        }
    }
    /**
     * Loads a parameter from the bash.
     * @param param parameter name
     * @return parameter value
     */
    public String loadParam(String param) {
        return prop.getProperty(param);
    }
    
    /** 
     * Loads the parameter REGISTER_HOST from the configuration file.
     * @return parameter value
     */
    public String registryHost() {
        return loadParam("REGISTER_HOST");
    }
    /** 
     * Loads the parameter REGISTER_PORT from the configuration file.
     * @return parameter value
     */
    public int registryPort() {
        return Integer.parseInt(loadParam("REGISTER_PORT"));
    }
    /** 
     * Loads the parameter REGISTER_OBJECT_PORT from the configuration file.
     * @return parameter value
     */
    public int objectPort() {
        return Integer.parseInt(loadParam("REGISTER_OBJECT_PORT"));
    }
    /** 
     * Loads the parameter REPOSITORY_PORT from the configuration file.
     * @return parameter value
     */
    public int repositoryPort() {
        return Integer.parseInt(loadParam("REPOSITORY_PORT"));
    }
    /** 
     * Loads the parameter BENCH_PORT from the configuration file.
     * @return parameter value
     */
    public int benchPort() {
        return Integer.parseInt(loadParam("BENCH_PORT"));
    }
    /** 
     * Loads the parameter PLAYGROUND_PORT from the configuration file.
     * @return parameter value
     */
    public int playgroundPort() {
        return Integer.parseInt(loadParam("PLAYGROUND_PORT"));
    }
    /** 
     * Loads the parameter SITE_PORT from the configuration file.
     * @return parameter value
     */
    public int sitePort() {
        return Integer.parseInt(loadParam("SITE_PORT"));
    }
}