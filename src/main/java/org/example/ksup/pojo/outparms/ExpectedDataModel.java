package org.example.ksup.pojo.outparms;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.ksup.Config.*;
import static org.example.ksup.Config.CHANNEL_LIST;

/**
 * Main data source which exported from excel file
 */
@Data
public class ExpectedDataModel {
    private String fl8pck;
    private String fl1proCat;
    private String fl1grp;
    private String regcd;
    private List<String> chancd;
    private String fl1pro;
    private String cardName;
    private String accessibility;
    private List<String> riskLevelRpp;
    private List<AttrMap> attrList;

    private List<String> fllpfl;
    private List<String> flkval;
    private Map<String, String> paramsPIPC;
    private int index;

    @Data
    public class AttrMap {
        private String key;
        private Map<String, String> values;

        /**
         * Method creates new instance.
         * Removes letter "G" in the end of param.
         *
         * @param key   name of param
         * @param value value of param
         */
        public AttrMap(String key, String value) {
            if (key.contains("G")) {
                key = key.replace("G", "");
            }
            this.key = key;
            this.values = convertStringToMap(value);
        }
    }

    /**
     * Sets region 77 as a default value (need to refactor)
     */
    public void setRegcd(String regcd) {
        if (regcd.equals("RF")) {
            this.regcd = "77";
        } else if (regcd.contains("77")) {
            this.regcd = "77";
        } else {
            this.regcd = regcd;
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
        if (pairs.length == 6) {

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
    public void addChancd(String fullChannels) {
        String[] elements = fullChannels.split(", ");
        for (String element : elements) {
            chancd.add(ExpectedDataModelMapper.channelMapper(element.trim()));
        }
    }

    /**
     * Method for replacing channel name and code
     *
     * @param element channel name
     * @return channel code
     */
    public String replaceValueOfChannel(String element) {
        Map<String, String> channelMap = new HashMap<>();

        channelMap.put("DSA", "CH10");
        channelMap.put("DO", "CH16");
        channelMap.put("KKO", "CH22");
        channelMap.put("VIP", "CH23");
        channelMap.put("DCCC_Front", "CH92");
        channelMap.put("DCCC_IA", "CH93");
        channelMap.put("DCCC_AZON", "CH94");
        channelMap.put("DCCC_UKD", "CH95");
        channelMap.put("Combo", "CH96");
        channelMap.put("Svyaznoy_FM", "CH51");
        channelMap.put("Partners_FM", "CH53");
        channelMap.put("Beeline_FM", "CH54");
        channelMap.put("IA", "CH6");
        channelMap.put("IA_Beeline", "CH64");
        channelMap.put("PIL_CC", "CH65");
        channelMap.put("Svyaznoy_LM", "CH34");
        channelMap.put("Beeline_LM", "CH36");
        channelMap.put("Partners_LM", "CH35");
        channelMap.put("Partners_ALL", "CH57");
        channelMap.put("Branch", "CH9");

        return channelMap.get(element.trim());
    }

    /**
     * Method adds clients in to ExpectedDataModel
     *
     * @param fullClients all clients
     */
    public void addFllpfl(String fullClients) {
        String[] elements = fullClients.split(", ");
        for (String element : elements) {
            fllpfl.add(ExpectedDataModelMapper.clientMapper(element.trim()));
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
     * Method adds new instance of AttrMap in to attrList
     *
     * @param key   name of param
     * @param value value of param
     */
    public void addAttrList(String key, String value) {
        AttrMap nextAttr = new AttrMap(key, value);
        attrList.add(nextAttr);
    }

    /**
     * Method adds all client categories
     *
     * @param flkvalFull string of all client categories
     */
    public void addFlkval(String flkvalFull) {
        String[] elements = flkvalFull.split(",");
        for (String element : elements) {
            flkval.add(element.trim());
        }
    }

    /**
     * Method adds new instance of AttrMap in to attrList
     *
     * @param key   name of param ("G" removed)
     * @param value value of param (without formatting)
     */
    public void addParamPIPC(String key, String value) {
        if (paramsPIPC == null) {
            setParamsPIPC(new HashMap<>());
        }
        if (key.contains("G")) {
            key = key.replace("G", "");
        }
        paramsPIPC.put(key, value);
    }

    /**
     * Method for limiting the number of tests
     *
     * @param channel RequestModel.chancd
     * @return skip ExpectedDataModel for testing or not.
     */
    public boolean needToTest(String channel, String fllpfl) {
        boolean necessity = true;
        if (CARD_LIST_IS_LIMITED && !CARD_LIST.contains(getFl1pro())) {
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
}
