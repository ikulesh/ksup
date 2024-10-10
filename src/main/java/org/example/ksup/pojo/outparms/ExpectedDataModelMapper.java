package org.example.ksup.pojo.outparms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ExpectedDataModelMapper {

    private Map<String, String> excelToDatabaseMap;

    // Constructor to load the JSON mapping
    public ExpectedDataModelMapper(String jsonFilePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Load the JSON file into the excelToDatabaseMap
            excelToDatabaseMap = objectMapper.readValue(new File(jsonFilePath), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load client mapping from JSON file");
        }
    }

    /**
     * Method for replacing client name and code
     *
     * @param excelValue element in Excel file
     * @return database value
     */
    public String replaceExcelValue(String excelValue) {
        return excelToDatabaseMap.get(excelValue.trim());
    }

    /**
     * Method gets client value from the Excel file and returns db value
     *
     * @param client client from the Excel file
     * @return db client value
     */
    public static String clientMapper(String client) {
        ExpectedDataModelMapper clientMapper = new ExpectedDataModelMapper("json/client_mapping.json");
        return clientMapper.replaceExcelValue(client);
    }

    /**
     * Method gets channel value from the Excel file and returns db value
     *
     * @param channel channel from the Excel file
     * @return db channel value
     */
    public static String channelMapper(String channel) {
        ExpectedDataModelMapper channelMapper = new ExpectedDataModelMapper("json/channel_mapping.json");
        return channelMapper.replaceExcelValue(channel);
    }

    /**
     * Method gets client value from the ExpectedDataModel and returns category of client
     *
     * @param clientCategory client from the ExcelDataModel file
     * @return client category value
     */
    public static String channelCategoryMapper(String clientCategory) {
        ExpectedDataModelMapper channelMapper = new ExpectedDataModelMapper("json/client_category_mapping.json");
        return channelMapper.replaceExcelValue(clientCategory);
    }
}

