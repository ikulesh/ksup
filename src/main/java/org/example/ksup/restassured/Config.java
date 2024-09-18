package org.example.ksup.restassured;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static String EXCEL_FILE_PATH;
    public static String EXT_SYS_CODE;
    public static String CONSTANT_ID;
    public static String APP_ID;
    public static String PCC0000605;
    public static boolean IS_SECOND_CARD;
    public static boolean ONLY_AVAILABLE_CARDS;
    public static boolean POSITIVE_ASSERT_LOGS;
    public static boolean CHANNEL_LIST_IS_LIMITED;
    public static boolean CCBI_IS_NEED;
    public static String CHANNEL_LIST;
    public static boolean CARD_LIST_IS_LIMITED;
    public static String CARD_LIST;
    public static boolean PACKAGE_LIST_IS_LIMITED;
    public static String PACKAGE_LIST;

    // Method to load properties from the file
    public static void loadProperties() throws IOException {
        Properties prop = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream("src/main/java/org/example/ksup/restassured/properties.properties");
            prop.load(input);

            // Assign the values from the properties file
            EXCEL_FILE_PATH = prop.getProperty("EXCEL_FILE_PATH");
            EXT_SYS_CODE = prop.getProperty("EXT_SYS_CODE");
            CONSTANT_ID = prop.getProperty("CONSTANT_ID");
            APP_ID = prop.getProperty("APP_ID");
            PCC0000605 = prop.getProperty("PCC0000605");
            IS_SECOND_CARD = Boolean.parseBoolean(prop.getProperty("IS_SECOND_CARD"));
            ONLY_AVAILABLE_CARDS = Boolean.parseBoolean(prop.getProperty("ONLY_AVAILABLE_CARDS"));
            POSITIVE_ASSERT_LOGS = Boolean.parseBoolean(prop.getProperty("POSITIVE_ASSERT_LOGS"));
            CHANNEL_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("CHANNEL_LIST_IS_LIMITED"));
            CCBI_IS_NEED = Boolean.parseBoolean(prop.getProperty("CCBI_IS_NEED"));
            CHANNEL_LIST = prop.getProperty("CHANNEL_LIST");
            CARD_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("CARD_LIST_IS_LIMITED"));
            CARD_LIST = prop.getProperty("CARD_LIST");
            PACKAGE_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("PACKAGE_LIST_IS_LIMITED"));
            PACKAGE_LIST = prop.getProperty("PACKAGE_LIST");

        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}