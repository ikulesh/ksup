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
            input = new FileInputStream("src/main/java/org/example/properties.properties");
            prop.load(input);

            // Assign the values from the properties file
            EXCEL_FILE_PATH = prop.getProperty("excel.file.path");
            EXT_SYS_CODE = prop.getProperty("external.system.code");
            CONSTANT_ID = prop.getProperty("constant.id");
            APP_ID = prop.getProperty("application.id");
            PCC0000605 = prop.getProperty("global.minimal.limit");
            IS_SECOND_CARD = Boolean.parseBoolean(prop.getProperty("is.second.card"));
            ONLY_AVAILABLE_CARDS = Boolean.parseBoolean(prop.getProperty("only.available.cards"));
            POSITIVE_ASSERT_LOGS = Boolean.parseBoolean(prop.getProperty("positive.assert.logs"));
            CHANNEL_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("channel.list.is.limited"));
            CCBI_IS_NEED = Boolean.parseBoolean(prop.getProperty("ccbi.is.necessary"));
            CHANNEL_LIST = prop.getProperty("channel.list");
            CARD_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("card.list.is.limited"));
            CARD_LIST = prop.getProperty("card.list");
            PACKAGE_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("PCC0000605"));
            PACKAGE_LIST = prop.getProperty("package.list");

        } finally {
            if (input != null) {
                input.close();
            }
        }
    }
}