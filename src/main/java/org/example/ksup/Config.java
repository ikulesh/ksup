package org.example.ksup;

import org.apache.poi.openxml4j.util.ZipSecureFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;


public class Config {
    public static String EXCEL_FILE_PATH;
    public static String EXT_SYS_CODE;
    public static String CONSTANT_ID;
    public static String APP_ID;
    public static boolean IS_SECOND_CARD;
    public static boolean ONLY_AVAILABLE_CARDS;
    public static boolean CCBI_IS_NEED;
    public static boolean CHANNEL_LIST_IS_LIMITED;
    public static boolean CARD_LIST_IS_LIMITED;
    public static boolean PACKAGE_LIST_IS_LIMITED;
    public static List<String> CHANNEL_LIST;
    public static List<String> CARD_LIST;
    public static List<String> PACKAGE_LIST;
    public static Level LOG_LEVEL;

    /**
     * Method to load properties from the file
     *
     * @throws IOException when properties file doesn't exist
     */
    public static void loadProperties() throws IOException {
        ZipSecureFile.setMinInflateRatio(0.0001); // Set to a lower ratio, if needed
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/java/org/example/properties.properties")) {
            prop.load(input);

            // Assign the values from the properties file
            EXCEL_FILE_PATH = prop.getProperty("excel.file.path");
            EXT_SYS_CODE = prop.getProperty("external.system.code");
            CONSTANT_ID = prop.getProperty("constant.id");
            APP_ID = prop.getProperty("application.id");
            IS_SECOND_CARD = Boolean.parseBoolean(prop.getProperty("is.second.card"));
            ONLY_AVAILABLE_CARDS = Boolean.parseBoolean(prop.getProperty("only.available.cards"));
            CCBI_IS_NEED = Boolean.parseBoolean(prop.getProperty("ccbi.is.necessary"));
            CARD_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("card.list.is.limited"));
            CHANNEL_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("channel.list.is.limited"));
            PACKAGE_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("package.list.is.limited"));
            CARD_LIST = propertyToList(prop.getProperty("card.list"));
            CHANNEL_LIST = propertyToList(prop.getProperty("channel.list"));
            PACKAGE_LIST = propertyToList(prop.getProperty("package.list"));
            LOG_LEVEL = Level.parse(prop.getProperty("log.level"));

        }
    }

    private static List<String> propertyToList(String property) {
        List<String> propertiesList = new ArrayList<>();
        String[] elements = property.split(",");
        for (String element : elements) {
            propertiesList.add(element.trim());
        }
        return propertiesList;
    }
}