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
    public static List<String> GLOBAL_SINGLE_PARAM_LIST_A_CLUB;

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
            GLOBAL_SINGLE_PARAM_LIST_A_CLUB = jsonMap.get("globalSingleParamListAClub");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("properties.properties")) {
            prop.load(input);

            // Assign values from system properties or fall back to properties file
            EXCEL_FILE_PATH = System.getProperty("EXCEL_FILE_PATH") != null
                    ? System.getProperty("EXCEL_FILE_PATH")
                    : prop.getProperty("excel.file.path");

            LOG_FOLDER_PATH = System.getProperty("LOG_FOLDER_PATH") != null
                    ? System.getProperty("LOG_FOLDER_PATH")
                    : prop.getProperty("log.folder.path");

            EXT_SYS_CODE = System.getProperty("EXT_SYS_CODE") != null
                    ? System.getProperty("EXT_SYS_CODE")
                    : prop.getProperty("external.system.code");

            CONSTANT_ID = prop.getProperty("constant.id");
            APP_ID = prop.getProperty("application.id");

            IS_SECOND_CARD = Boolean.parseBoolean(
                    System.getProperty("IS_SECOND_CARD") != null
                            ? System.getProperty("IS_SECOND_CARD")
                            : prop.getProperty("is.second.card"));

            ONLY_AVAILABLE_CARDS = Boolean.parseBoolean(
                    System.getProperty("ONLY_AVAILABLE_CARDS") != null
                            ? System.getProperty("ONLY_AVAILABLE_CARDS")
                            : prop.getProperty("only.available.cards"));

            CARD_LIST_IS_LIMITED = Boolean.parseBoolean(
                    System.getProperty("CARD_LIST_IS_LIMITED") != null
                            ? System.getProperty("CARD_LIST_IS_LIMITED")
                            : prop.getProperty("card.list.is.limited"));

            CHANNEL_LIST_IS_LIMITED = Boolean.parseBoolean(
                    System.getProperty("CHANNEL_LIST_IS_LIMITED") != null
                            ? System.getProperty("CHANNEL_LIST_IS_LIMITED")
                            : prop.getProperty("channel.list.is.limited"));

            PACKAGE_LIST_IS_LIMITED = Boolean.parseBoolean(
                    System.getProperty("PACKAGE_LIST_IS_LIMITED") != null
                            ? System.getProperty("PACKAGE_LIST_IS_LIMITED")
                            : prop.getProperty("package.list.is.limited"));

            CLIENT_LIST_IS_LIMITED = Boolean.parseBoolean(
                    System.getProperty("CLIENT_LIST_IS_LIMITED") != null
                            ? System.getProperty("CLIENT_LIST_IS_LIMITED")
                            : prop.getProperty("client.list.is.limited"));

            CARD_LIST = propertyToList(
                    System.getProperty("CARD_LIST") != null
                            ? System.getProperty("CARD_LIST")
                            : prop.getProperty("card.list"));

            CHANNEL_LIST = propertyToList(
                    System.getProperty("CHANNEL_LIST") != null
                            ? System.getProperty("CHANNEL_LIST")
                            : prop.getProperty("channel.list"));

            PACKAGE_LIST = propertyToList(
                    System.getProperty("PACKAGE_LIST") != null
                            ? System.getProperty("PACKAGE_LIST")
                            : prop.getProperty("package.list"));

            CLIENT_LIST = propertyToList(
                    System.getProperty("CLIENT_LIST") != null
                            ? System.getProperty("CLIENT_LIST")
                            : prop.getProperty("client.list"));

            LOG_LEVEL = Level.parse(
                    System.getProperty("LOG_LEVEL") != null
                            ? System.getProperty("LOG_LEVEL")
                            : prop.getProperty("log.level"));
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
    public static List<String> getRequestParamList(String requestName) {
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