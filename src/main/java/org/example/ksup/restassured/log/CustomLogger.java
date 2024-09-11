package org.example.ksup.restassured.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

public class CustomLogger {
    public static void customLogger(Level logLevel, String logMessage) {

        // The path to the log file
        String logFilePath = "/Users/admin2/Desktop/RPP/logfile_" + LocalDate.now() + ".txt";

        // Write the string to the log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(logLevel.toString() + "  " + logMessage);
            writer.newLine();  // Adds a new line after the log entry
            //System.out.println("Log entry saved.");
        } catch (IOException e) {
            //System.out.println("An error occurred while writing to the log file.");
            e.printStackTrace();
        }
    }
}
