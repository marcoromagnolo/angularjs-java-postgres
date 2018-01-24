package dmt.server.component;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class Logging {

    private static final Logger LOGGER = Logger.getLogger(Logging.class.getName());
    private static final LogManager logManager = LogManager.getLogManager();
    
    private static final String log_path = System.getProperty("user.dir");

    public Logging() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            File directory = new File(log_path + "\\logs");
            if(!directory.exists()){
            	directory.mkdir();
            }else{
            	LOGGER.log(Level.INFO, "[" + log_path + "\\logs] - logs directory already exists");
            }
            logManager.readConfiguration(classLoader.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }
}
