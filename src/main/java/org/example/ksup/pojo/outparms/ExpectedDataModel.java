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
            chancd.add(replaceValueOfChannel(element.trim()));
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
            fllpfl.add(replaceValueOfClient(element.trim()));
        }

    }

    /**
     * Method for replacing client name and code
     *
     * @param element client name
     * @return client code
     */
    public String replaceValueOfClient(String element) {
        Map<String, String> clientMap = new HashMap<>();

        clientMap.put("Satis", "SATISCAT");
        clientMap.put("Satis_O", "SATISCATO");
        clientMap.put("IZK_Satis", "IZK1CAT");
        clientMap.put("IZK_Satis_O", "IZK1CATO");
        clientMap.put("N-Payroll_Satis", "PRLNW1CAT");
        clientMap.put("N-Payroll_Satis_O", "PRLNW1CATO");
        clientMap.put("Tender_Satis", "TDRSTSCAT");
        clientMap.put("Tender_Satis_O", "TDRSTSCATO");

        clientMap.put("Payroll", "PROLLCAT");
        clientMap.put("Payroll_O", "PROLLCATO");
        clientMap.put("Tender_Payroll", "TDRPRLCAT");
        clientMap.put("Tender_Payroll_O", "TDRPRLCATO");

        clientMap.put("Preferred", "PREFERCAT");
        clientMap.put("Preferred_O", "PREFERCATO");
        clientMap.put("IZK_Pref", "IZK3CAT");
        clientMap.put("IZK_Pref_O", "IZK3CATO");
        clientMap.put("N-Payroll_Pref", "PRLNW3CAT");
        clientMap.put("N-Payroll_Pref_O", "PRLNW3CATO");
        clientMap.put("Tender_Pref", "TDRPRFCAT");
        clientMap.put("Tender_Pref_O", "TDRPRFCATO");

        clientMap.put("Pref-Prem", "PREFPRMCAT");
        clientMap.put("Pref-Prem_O", "PRFPRMCATO");
        clientMap.put("IZK_Pref-Prem", "IZK4CAT");
        clientMap.put("IZK_Pref-Prem_O", "IZK4CATO");
        clientMap.put("N-Payroll_Pref-Prem", "PRLNW4CAT");
        clientMap.put("N-Payroll_Pref-Prem_O", "PRLNW4CATO");
        clientMap.put("Tender_Pref-Prem", "TDRPRMCAT");
        clientMap.put("Tender_Pref-Prem_O", "TDRPRMCATO");

        clientMap.put("Staff", "STAFFCAT");
        clientMap.put("Staff_O", "STAFFCATO");
        clientMap.put("Staff_ALFA", "STAFFALFA");
        clientMap.put("Staff_ALFA_O", "STAFFALFAO");

        clientMap.put("A-Club", "APRVTCATO");

        clientMap.put("Prime_O", "PRMCONCATO");

        return clientMap.get(element.trim());
    }

    /**
     * Method defines client category
     *
     * @param client client code
     * @return client category
     */
    public static String determineCategoryOfClient(String client) {
        List<String> firstCatClient = new ArrayList<>();
        firstCatClient.add("SATISCAT");
        firstCatClient.add("SATISCATO");
        firstCatClient.add("IZK1CAT");
        firstCatClient.add("IZK1CATO");
        firstCatClient.add("PRLNW1CAT");
        firstCatClient.add("PRLNW1CATO");
        firstCatClient.add("TDRSTSCAT");
        firstCatClient.add("TDRSTSCATO");
        if (firstCatClient.contains(client)) {
            return "01";
        }

        List<String> secondCatClient = new ArrayList<>();
        secondCatClient.add("PROLLCAT");
        secondCatClient.add("PROLLCATO");
        secondCatClient.add("TDRPRLCAT");
        secondCatClient.add("TDRPRLCATO");
        if (secondCatClient.contains(client)) {
            return "02";
        }

        List<String> thirdCatClient = new ArrayList<>();
        thirdCatClient.add("PREFERCAT");
        thirdCatClient.add("PREFERCATO");
        thirdCatClient.add("IZK3CAT");
        thirdCatClient.add("IZK3CATO");
        thirdCatClient.add("PRLNW3CAT");
        thirdCatClient.add("PRLNW3CATO");
        thirdCatClient.add("TDRPRFCAT");
        thirdCatClient.add("TDRPRFCATO");
        if (thirdCatClient.contains(client)) {
            return "03";
        }

        List<String> fourthCatClient = new ArrayList<>();
        fourthCatClient.add("PREFPRMCAT");
        fourthCatClient.add("PRFPRMCATO");
        fourthCatClient.add("IZK4CAT");
        fourthCatClient.add("IZK4CATO");
        fourthCatClient.add("PRLNW4CAT");
        fourthCatClient.add("PRLNW4CATO");
        fourthCatClient.add("TDRPRMCAT");
        fourthCatClient.add("TDRPRMCATO");
        if (fourthCatClient.contains(client)) {
            return "04";
        }

        List<String> fifthCatClient = new ArrayList<>();
        fifthCatClient.add("STAFFCAT");
        fifthCatClient.add("STAFFCATO");
        fifthCatClient.add("STAFFALFA");
        fifthCatClient.add("STAFFALFAO");
        if (fifthCatClient.contains(client)) {
            return "05";
        }

        List<String> sixthCatClient = new ArrayList<>();
        sixthCatClient.add("APRVTCATO");
        if (sixthCatClient.contains(client)) {
            return "06";
        }

        List<String> seventhCatClient = new ArrayList<>();
        seventhCatClient.add("PRMCONCATO");
        if (seventhCatClient.contains(client)) {
            return "07";
        }
        return null;
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
     * @param key name of param
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
     * @param key name of param ("G" removed)
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
     * @param channel RequestModel.chancd
     * @return skip ExpectedDataModel for testing or not.
     * */
    public boolean needToTest(String channel) {
        boolean necessity = true;
        if (CARD_LIST_IS_LIMITED && !CARD_LIST.contains(getFl1pro())) {
            necessity = false;
        }
        if (CHANNEL_LIST_IS_LIMITED && !CHANNEL_LIST.contains(channel)) {
            necessity = false;
        }
        return necessity;
    }
}
