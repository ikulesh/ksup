package org.example.ksup.pojo.outparms;

import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.ksup.log.CustomLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.ksup.Config.*;
import static org.example.ksup.Config.CHANNEL_LIST;
import static org.example.ksup.pojo.outparms.ExcelMethods.*;

/**
 * Main data source which exported from excel file
 */
@Data
public class ExpectedDataModel {
    private String packageCode;
    private String productCode;
    private String fl1grp;
    private List<String> regionList;
    private List<String> channelList;
    private String cardCode;
    private String cardName;
    private String accessibility;
    private List<String> riskLevelRpp;
    private List<CreditParams> creditParams;
    boolean isAClub;
    private List<String> clientList;
    private List<String> clientCatList;
    private Map<String, String> cardParams;
    private int index;

    @Data
    public class CreditParams {
        private String key;
        private Map<String, String> values;

        /**
         * Method creates new instance.
         * Removes letter "G" in the end of param.
         *
         * @param key   name of param
         * @param value value of param
         */
        public CreditParams(String key, String value) {
            if (key.contains("G")) {
                key = key.replace("G", "");
            }
            this.key = key;
            this.values = convertStringToMap(value);
        }
    }

    public ExpectedDataModel() {
        this.creditParams = new ArrayList<>();
        this.channelList = new ArrayList<>();
        this.clientCatList = new ArrayList<>();
        this.clientList = new ArrayList<>();
        this.riskLevelRpp = new ArrayList<>();
    }

    /**
     * Method gets region field from the Excel requirements, splits it and sets as a list
     *
     * @param regionList full region info from the Excel requirements
     */
    public void setRegionList(String regionList) {
        this.regionList = new ArrayList<>();
        String[] elements = regionList.split(",");
        for (String element : elements) {
            if (element.equals("RF")) {
                this.regionList.add("77");
                break;
            }
            this.regionList.add(element.trim());
        }
    }

    /**
     * Converts string of params into Map (could be refactored, but format of params in excel file too stange)
     *
     * @param input param values string (which name like PCC00....)
     */
    public static Map<String, String> convertStringToMap(String input) {
        // Initialize the map to store the key-value pairs
        Map<String, String> map = new HashMap<>();

        input = input.trim();

        // Regular expression to split by the key-value pattern
        String regex = "\\s+";

        // Split the input string based on the pattern
        String[] pairs = input.split(regex);
        if (pairs.length % 2 == 0) {

            // Iterate over each key-value pair
            for (int i = 0; i < pairs.length - 1; i += 2) {
                String key = pairs[i];
                String value = pairs[i + 1];
                map.put(key, value);
            }
        } else {
            regex = "(?<=\\d)(?=[A-Z])";

            // Split the input string based on the pattern
            pairs = input.split(regex);

            // Iterate over each key-value pair
            for (String pair : pairs) {
                // Split each pair by space to separate key and value
                String[] keyValue = pair.split(" ");
                // Format as "key -> value" and add to the map
                if (keyValue.length == 2) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }
        }

        return map;
    }

    /**
     * Method adds channels in ExpectedDataModel.
     *
     * @param fullChannels contains all channels from excel file
     */
    public void addChannel(String fullChannels) {
        String[] elements = fullChannels.split(", ");
        for (String element : elements) {
            channelList.add(ExpectedDataModelMapper.channelMapper(element.trim()));
        }
    }


    /**
     * Method adds clients in to ExpectedDataModel
     *
     * @param fullClients all clients
     */
    public void addClient(String fullClients) {
        String[] elements = fullClients.split(", ");
        for (String element : elements) {
            clientList.add(ExpectedDataModelMapper.clientMapper(element.trim()));
        }

    }

    /**
     * Method adds all risk level from excel file
     *
     * @param riskLevelString contains all risks (param PCC0003152)
     */
    public void addRiskLevelRpp(String riskLevelString) {
        String[] elements = riskLevelString.split(",");
        for (String element : elements) {
            riskLevelRpp.add(element.trim());
        }
    }

    /**
     * Method adds new instance of AttrMap in to creditParams
     *
     * @param key   name of param
     * @param value value of param
     */
    public void addCreditParam(String key, String value) {
        CreditParams nextCreditParam = new CreditParams(key, value);
        creditParams.add(nextCreditParam);
    }

    /**
     * Method adds all client categories
     *
     * @param clientCatFull string of all client categories
     */
    public void addClientCat(String clientCatFull) {
        String[] elements = clientCatFull.split(",");
        for (String element : elements) {
            clientCatList.add(element.trim());
        }
    }

    /**
     * Method adds new instance of AttrMap in to creditParams
     *
     * @param key   name of param ("G" removed)
     * @param value value of param (without formatting)
     */
    public void addSingleParam(String key, String value) {
        if (cardParams == null) {
            setCardParams(new HashMap<>());
        }
        if (key.contains("G")) {
            key = key.replace("G", "");
        }
        if (this.isAClub && value.contains("LM1")) {
            // Regular expression to capture numbers with decimal places
            Pattern pattern = Pattern.compile("\\d+\\.\\d+");
            Matcher matcher = pattern.matcher(value);

            // List to store extracted numbers as strings
            List<String> numbers = new ArrayList<>();

            // Extract all matching numbers
            while (matcher.find()) {
                numbers.add(matcher.group());
            }
            // Check if all numbers are equal
            boolean allEqual = numbers.stream().distinct().count() == 1;

            if (allEqual) {
                cardParams.put(key, numbers.get(0));
            } else {
                CustomLogger.customLogger(Level.WARNING, "Different multi params in the Excel table: " + key + ": " + numbers);
            }
        } else {
            cardParams.put(key, value);
        }
    }

    /**
     * Method for limiting the number of tests
     *
     * @param channel RequestModel.channelList
     * @return skip ExpectedDataModel for testing or not.
     */
    public boolean needToTest(String channel, String fllpfl) {
        boolean necessity = true;
        if (CARD_LIST_IS_LIMITED && !CARD_LIST.contains(getCardCode())) {
            necessity = false;
        }
        if (CHANNEL_LIST_IS_LIMITED && !CHANNEL_LIST.contains(channel)) {
            necessity = false;
        }
        if (CLIENT_LIST_IS_LIMITED && !CLIENT_LIST.contains(fllpfl)) {
            necessity = false;
        }
        return necessity;
    }

    /**
     * Method gets all elements from currentRow and sets it into ExcelDataModel
     *
     * @param currentRow executed row of the Excel requirements
     * @param headerRow  header row of the Excel requirements
     */
    public void setNewPackage(Row headerRow, Row currentRow) {
        Map<String, Consumer<String>> paramActions = paramActionsMainParams();
        this.isAClub = false;
        for (Cell cell : currentRow) {
            int paramNumber = cell.getColumnIndex();
            if (cellIsEmpty(headerRow.getCell(paramNumber))) {
                break;
            }
            //strange thing
            if (headerRow.getCell(cell.getColumnIndex()).getStringCellValue().equals("fllpfl")) {
                if (cell.getStringCellValue().equals("A-Club")) {
                    this.isAClub = true;
                }
            }
            String paramName = getParamName(headerRow, cell.getColumnIndex());
            String paramValue = getParamValue(cell);
            // Handle parameter using the map
            Consumer<String> action = paramActions.get(paramName);
            if (action != null) {
                action.accept(paramValue);
                continue;
            }

            if (!cellIsEmpty(cell)) {
                if (isAClub) {
                    paramName = ExpectedDataModelMapper.aClubParamsMapping(paramName);
                    if (GLOBAL_SINGLE_PARAM_LIST_A_CLUB.contains(paramName)) {
                        addSingleParam(paramName, paramValue);
                    }
                } else {
                    // Handle GLOBAL_SINGLE_PARAM_LIST
                    if (GLOBAL_SINGLE_PARAM_LIST.contains(paramName)) {
                        addSingleParam(paramName, paramValue);
                        continue;
                    }
                    // Handle GLOBAL_MULTI_PARAM_LIST
                    if (GLOBAL_MULTI_PARAM_LIST.contains(paramName)) {
                        addCreditParam(paramName, paramValue);
                    }
                }
            }
        }
    }

    /**
     * Creates a map that associates parameter names with actions to be performed on them.
     * Each entry in the map maps a string (the parameter name) to a corresponding action,
     * which is a method reference or a lambda function that takes a string argument.
     *
     * <p>Each action corresponds to a specific field or operation related to the parameter name:
     * <ul>
     *   <li>"fl1pck" - sets the package code using {@link #setPackageCode(String)}</li>
     *   <li>"flenoc" - sets the region list using {@link #setRegionList(String)}</li>
     *   <li>"gpvdsc" - adds a channel using {@link #addChannel(String)}</li>
     *   <li>"clientList" - adds a client using {@link #addClient(String)}</li>
     *   <li>"clientCatList" - adds a client category using {@link #addClientCat(String)}</li>
     *   <li>"fl5dsc" - sets the card name using {@link #setCardName(String)}</li>
     *   <li>"cardCode" - sets either the product code or the card code, depending on whether
     *       the parameter value consists of exactly four capital letters. If it does,
     *       {@link #setProductCode(String)} is called; otherwise, {@link #setCardCode(String)} is called.</li>
     * </ul>
     *
     * @return A map associating parameter names with their corresponding actions (method references or lambdas).
     */

    private Map<String, Consumer<String>> paramActionsMainParams() {
        Map<String, Consumer<String>> paramActions = new HashMap<>();

        // Define mappings of paramName to corresponding action
        paramActions.put("fl1pck", this::setPackageCode);
        paramActions.put("flenoc", this::setRegionList);
        paramActions.put("gpvdsc", this::addChannel);
        paramActions.put("fllpfl", this::addClient);
        paramActions.put("flkval", this::addClientCat);
        paramActions.put("fl5dsc", this::setCardName);
        paramActions.put("fl1proc", this::setProductCode);
        paramActions.put("fl1prod", this::setCardCode);
        paramActions.put("PCC0002053", this::addRiskLevelRpp);
        paramActions.put("fl1pro", (paramValue) -> {
            if (paramValue.matches("^[A-Z|\\d]{4}$")) {
                this.setProductCode(paramValue);
            } else {
                this.setCardCode(paramValue);
            }
        });
        return paramActions;
    }
}
