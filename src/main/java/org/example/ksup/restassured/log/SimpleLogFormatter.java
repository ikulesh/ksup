package org.example.ksup.restassured.log;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SimpleLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        // Return only the log level and message
        return record.getLevel() + ": " + record.getMessage() + System.lineSeparator();
    }
}
