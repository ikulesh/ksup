package org.example.ksup.log;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.*;

import static org.example.ksup.Config.*;

public class CustomLogger {

    private static final Logger logger = Logger.getLogger(CustomLogger.class.getName());

    static {
        try {
            // The path to the log file
            //String logFilePath = LOG_FOLDER_PATH + "logfile_" + LocalDate.now() + "_" + EXT_SYS_CODE.substring(4) + ".txt";
            String logFilePath = LOG_FOLDER_PATH + "log_file.txt";

            // Create a file handler to write log messages to the file
            FileHandler fileHandler = new FileHandler(logFilePath, true);

            // Apply the custom formatter to remove date, time, and package info
            fileHandler.setFormatter(new SimpleLogFormatter());

            // Add the file handler to the logger
            logger.addHandler(fileHandler);

            // Set the default log level to INFO or higher
            logger.setLevel(LOG_LEVEL);

            // Optionally remove console output (optional)
            Logger rootLogger = Logger.getLogger("");
            Handler[] handlers = rootLogger.getHandlers();
            if (handlers[0] instanceof ConsoleHandler) {
                rootLogger.removeHandler(handlers[0]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to log messages with a specified log level
    public static void customLogger(Level logLevel, String logMessage) {
        logger.log(logLevel, logMessage);
    }

    // Optional: Set log level dynamically
    public static void setLogLevel(Level level) {
        logger.setLevel(level);
    }
}
