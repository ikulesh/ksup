package org.example.ksup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.openxml4j.util.ZipSecureFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;


public class Config {
    public static String EXCEL_FILE_PATH;
    public static String LOG_FOLDER_PATH;
    public static String EXT_SYS_CODE;
    public static String CONSTANT_ID;
    public static String APP_ID;
    public static boolean IS_SECOND_CARD;
    public static boolean ONLY_AVAILABLE_CARDS;
    public static boolean CHANNEL_LIST_IS_LIMITED;
    public static boolean CARD_LIST_IS_LIMITED;
    public static boolean PACKAGE_LIST_IS_LIMITED;
    public static boolean CLIENT_LIST_IS_LIMITED;
    public static List<String> CHANNEL_LIST;
    public static List<String> CARD_LIST;
    public static List<String> PACKAGE_LIST;
    public static List<String> CLIENT_LIST;
    public static Level LOG_LEVEL;
    public static List<String> GLOBAL_MULTI_PARAM_LIST;
    public static List<String> GLOBAL_SINGLE_PARAM_LIST;

    /**
     * Method to load properties from the file
     *
     * @throws IOException when properties file doesn't exist
     */
    public static void loadProperties() throws IOException {
        ZipSecureFile.setMinInflateRatio(0.0001); // Prevent potential ZIP bomb attacks

        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON from the file into a Map
            Map<String, List<String>> jsonMap = objectMapper.readValue(new File("json/params_mapping.json"), Map.class);

            GLOBAL_MULTI_PARAM_LIST = jsonMap.get("globalMultiParamList");
            GLOBAL_SINGLE_PARAM_LIST = jsonMap.get("globalSingleParamList");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("properties.properties")) {
            prop.load(input);

            // Assign values from system properties or fall back to properties file
            EXCEL_FILE_PATH = System.getenv("EXCEL_FILE_PATH") != null
                    ? System.getenv("EXCEL_FILE_PATH")
                    : prop.getProperty("excel.file.path");

            LOG_FOLDER_PATH = System.getenv("LOG_FOLDER_PATH") != null
                    ? System.getenv("LOG_FOLDER_PATH")
                    : prop.getProperty("log.folder.path");

            EXT_SYS_CODE = prop.getProperty("external.system.code");
            CONSTANT_ID = prop.getProperty("constant.id");
            APP_ID = prop.getProperty("application.id");
            IS_SECOND_CARD = Boolean.parseBoolean(prop.getProperty("is.second.card"));
            ONLY_AVAILABLE_CARDS = Boolean.parseBoolean(prop.getProperty("only.available.cards"));
            CARD_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("card.list.is.limited"));
            CHANNEL_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("channel.list.is.limited"));
            PACKAGE_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("package.list.is.limited"));
            CARD_LIST = propertyToList(prop.getProperty("card.list"));
            CHANNEL_LIST = propertyToList(prop.getProperty("channel.list"));
            PACKAGE_LIST = propertyToList(prop.getProperty("package.list"));
            CLIENT_LIST = propertyToList(prop.getProperty("client.list"));
            CLIENT_LIST_IS_LIMITED = Boolean.parseBoolean(prop.getProperty("client.list.is.limited"));
            LOG_LEVEL = Level.parse(prop.getProperty("log.level"));
            System.out.println(LOG_FOLDER_PATH);
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

    @Deprecated
    public static List<String> getRequestParamList(String requestName){
        try {
            // Create an ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            // Read JSON from the file into a Map
            Map<String, List<String>> jsonMap = objectMapper.readValue(new File("json/properties.json"), Map.class);
            return jsonMap.get(requestName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}