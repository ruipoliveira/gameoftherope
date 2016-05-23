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
     */
    public RegistryConfig() {
    }

    /** 
     * Loads the parameter REGISTER_HOST from the configuration file.
     * @return parameter value
     */
    public String registryHost() {
        return "l040101-ws07.ua.pt";
    }
    /** 
     * Loads the parameter REGISTER_PORT from the configuration file.
     * @return parameter value
     */
    public int registryPort() {
        return 22150;
    }
    /** 
     * Loads the parameter REGISTER_OBJECT_PORT from the configuration file.
     * @return parameter value
     */
    public int objectPort() {
        return 22151;
    }
    /** 
     * Loads the parameter REPOSITORY_PORT from the configuration file.
     * @return parameter value
     */
    public int repositoryPort() {
        return 22152;
    }
    /** 
     * Loads the parameter BENCH_PORT from the configuration file.
     * @return parameter value
     */
    public int benchPort() {
        return 22154;
    }
    /** 
     * Loads the parameter PLAYGROUND_PORT from the configuration file.
     * @return parameter value
     */
    public int playgroundPort() {
        return 22152;
    }
    /** 
     * Loads the parameter SITE_PORT from the configuration file.
     * @return parameter value
     */
    public int sitePort() {
        return 22153;
    }
}