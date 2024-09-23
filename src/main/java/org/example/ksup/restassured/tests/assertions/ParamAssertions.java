package org.example.ksup.restassured.tests.assertions;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;


import java.util.*;
import java.util.logging.Level;


public abstract class ParamAssertions {
    public static void attrAssertions(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, List<String> expectedAttr, List<String> warningsList) {
        boolean contains = false;
        Map<String, String> rowMap;
        List<ExpectedDataModel.AttrMap> attrList = expectedDataModel.getAttrList();

        //Проверка на нули в ответе
        for (ExpectedDataModel.AttrMap map : expectedDataModel.getAttrList()) {
            String expectedNameParam = map.getKey();
            Collection<String> expectedKeys = map.getValues().keySet();
            for (String element : expectedKeys) {
                for (ResultSetRow row : resultSetRowList) {
                    if (row.getFl3prm().equals(expectedNameParam) && row.getFl4val().contains(element)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains && expectedAttr.contains(expectedNameParam)) {
                    CustomLogger.customLogger(Level.WARNING, expectedNameParam + " " + element + " not contains in response");
                    warningsList.add(expectedNameParam);
                }
                contains = false;
            }
        }

        //Перебираем ответ поэлементно
        for (ResultSetRow row : resultSetRowList) {
            //конвертим fl4val типа LM2    29.99 в мапу (либо возвращаем null)
            String fl4val = row.getFl4val();
            rowMap = convertToMap(fl4val);
            Map<String, String> expectedValues = new HashMap<>();
            //String fl3prm = row.getFl3prm();
            //если fl4val типа LM2    29.99
            if (rowMap != null) {
                //ищем параметр в ожидаемых результатах
                for (ExpectedDataModel.AttrMap expectedRow : attrList) {
                    //ищем ключ-значение в ожидамемом результате для текущего fl3prm
                    if (expectedRow.getKey().equals(row.getFl3prm())) {
                        expectedValues = expectedRow.getValues();
                        contains = findMap(rowMap, expectedRow.getValues());
                        if (contains) {
                            CustomLogger.customLogger(Level.FINE, row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is correct");
                        }
                        break;
                    }
                }
                if (!contains) {
                    if (!expectedValues.isEmpty()) {
                        CustomLogger.customLogger(Level.WARNING, row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be in: " + expectedValues);
                        warningsList.add(row.getFl3prm());
                    } else {
                        CustomLogger.customLogger(Level.WARNING, row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be empty");
                        warningsList.add(row.getFl3prm());
                    }
                } else {
                    contains = false;
                }
            }
        }
    }


    public static void paramAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, List<String> expectedAttr, List<String> warningsList) {
        //кусочек с проверкой на нули
        Collection<String> params = expectedDataModel.getParamsPIPC().keySet();
        boolean contains = false;
        for (String param : params) {
            if (!expectedAttr.contains(param)) {
                continue;
            }
            for (ResultSetRow row : resultSetRowList) {
                if (row.getFl3prm().equals(param)) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                CustomLogger.customLogger(Level.WARNING, param + " is empty, should be: " + expectedDataModel.getParamsPIPC().get(param));
                warningsList.add(param);
            } else {
                contains = false;
            }
        }
        //основные проверки
        for (ResultSetRow row : resultSetRowList) {
            if (!(row.getFl4val().contains("LM") || row.getFl4val().contains("HR"))) {
                String fl3prm = row.getFl3prm();
                String fl4val = row.getFl4val();
                String value = expectedDataModel.getParamsPIPC().get(fl3prm);
                if (expectedAttr.contains(fl3prm)) {
                    if (value != null) {
                        //if (fl3prm.equals("PIPC000601"))
                        {
                            if (!value.equals(fl4val)) {
                                CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " is incorrect(fl1ppr = " + row.getFl1ppr() + "), should be: " + value);
                                warningsList.add(fl3prm);
                            } else {
                                CustomLogger.customLogger(Level.FINE, fl3prm + " " + fl4val + " (fl1ppr = " + row.getFl1ppr() + ") is correct");
                            }
                        }
                    } else if (fl4val != null) {
                        if (!fl4val.isEmpty()) {
                            if (!fl4val.equals("N")) {
                                CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " is incorrect(fl1ppr = " + row.getFl1ppr() + "), should be empty ");
                                warningsList.add(fl3prm);
                            } else {
                                CustomLogger.customLogger(Level.FINE, fl3prm + " " + fl4val + " (fl1ppr = " + row.getFl1ppr() + ") could be empty, but value is correct");
                            }
                        }
                    }
                }
            }
        }
    }


    public static void productAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, List<String> warningsList) {
        for (ResultSetRow row : resultSetRowList) {
            {
                String fl3prm = row.getFl3prm();
                String fl4val = row.getFl4val();
                String value = expectedDataModel.getFl1proCat();
                //productName assertion
                if (fl3prm.equals("PCC0001001")) {
                    if (!value.equals(fl4val)) {
                        CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be: " + value);
                        warningsList.add(fl3prm);
                    } else {
                        CustomLogger.customLogger(Level.FINE, fl3prm + " " + fl4val + " is correct");
                    }
                }
            }

        }
    }

    public static void cardNameAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, List<String> warningsList) {
        ResultSetRow row = resultSetRowList.get(0);
        String fl5dsc = row.getFl5dsc();
        //cardName assertion
        String value = expectedDataModel.getCardName();
        if (value.equals(fl5dsc)) {
            CustomLogger.customLogger(Level.FINE, "fl5dsc " + fl5dsc + " is correct");
        } else {
            CustomLogger.customLogger(Level.WARNING, "fl5dsc " + fl5dsc + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be: " + value);
            warningsList.add("fl5dsc");
        }
    }


    public static Map<String, String> convertToMap(String fl4val) {
        if (fl4val.contains(" ")) {
            // Split the string based on whitespace
            String[] parts = fl4val.split("\\s+");
            // Create a map and add the key-value pair
            Map<String, String> map = new HashMap<>();
            map.put(parts[0], parts[1]);
            // Output the map to check the result
            System.out.println(map);
            return map;
        } else return null;
    }

    public static boolean findMap(Map<String, String> currentMap, Map<String, String> expectedMap) {
        return expectedMap.entrySet().containsAll(currentMap.entrySet());
    }

    public static boolean responseIsNotEmpty(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, RequestModel requestModel) {
        if (resultSetRowList == null || resultSetRowList.isEmpty()) {
            CustomLogger.customLogger(Level.WARNING, "Empty response for " + expectedDataModel.getFl8pck() + " " + requestModel.getChancd() + " " + expectedDataModel.getFl1proCat() + " " + requestModel.getFllpfl() + " " + expectedDataModel.getFl1pro() + " " + requestModel.getRiskLevel() + ":");
            return false;
        } else return true;
    }
}
