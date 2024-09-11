package org.example.ksup.restassured.tests;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;


import java.util.*;
import java.util.logging.Level;

import static org.example.ksup.restassured.Properties.POSITIVE_ASSERT_LOGS;


public abstract class AttrAssertions {
    public static void attrAssertions(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, RequestModel requestModel, List<String> expectedAttr) {
        boolean contains = false;
        Map<String, String> rowMap;
        List<ExpectedDataModel.AttrMap> attrList = expectedDataModel.getAttrList();
        if (resultSetRowList == null || resultSetRowList.isEmpty()) {
            CustomLogger.customLogger(Level.WARNING, "Empty Response for " + expectedDataModel.getFl8pck() + " " + requestModel.getChancd() + " " + expectedDataModel.getFl1proCat() + " " + requestModel.getFllpfl() + " " + expectedDataModel.getFl1pro() + " " + requestModel.getRiskLevel() + ":");
        } else {
            //Проверка на нули в ответе
            for (ExpectedDataModel.AttrMap map : expectedDataModel.getAttrList()) {
                String expectedNameParam = map.getKey();
                Collection<String> expectedKeys = map.getValues().keySet();
                for (String element : expectedKeys) {
                    for (ResultSetRow row : resultSetRowList) {
                        if (row.getFl3prm().trim().equals(expectedNameParam.trim()) && row.getFl4val().contains(element)) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains && expectedAttr.contains(expectedNameParam)) {
                        CustomLogger.customLogger(Level.WARNING, expectedNameParam + " " + element + " not contains in response");
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
                            if (contains && POSITIVE_ASSERT_LOGS) {
                                CustomLogger.customLogger(Level.INFO, "SUCCESS: " + row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is correct");
                            }
                            break;
                        }
                    }
                    if (!contains) {
                        if (!expectedValues.isEmpty()) {
                            CustomLogger.customLogger(Level.WARNING, row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be in: " + expectedValues);
                        } else {
                            CustomLogger.customLogger(Level.WARNING, row.getFl3prm() + " " + rowMap + " (fl1ppr = " + row.getFl1ppr() + ") is incorrect, should be empty");
                        }
                    } else {
                        contains = false;
                    }
                }
            }
        }

    }

    public static void paramAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, RequestModel requestModel, List<String> expectedAttr) {
        if (resultSetRowList == null || resultSetRowList.isEmpty()) {
            CustomLogger.customLogger(Level.WARNING, "Empty response for " + expectedDataModel.getFl8pck() + " " + requestModel.getChancd() + " " + expectedDataModel.getFl1proCat() + " " + requestModel.getFllpfl() + " " + expectedDataModel.getFl1pro() + " " + requestModel.getRiskLevel() + ":");
        } else {
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
                    if (expectedAttr.contains(fl3prm.trim())) {
                        if (value != null) {
                            //if (fl3prm.trim().equals("PIPC000601"))
                            {
                                if (!value.equals(fl4val)) {
                                    CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " is incorrect(fl1ppr = " + row.getFl1ppr() + "), should be: " + value);
                                } else if (POSITIVE_ASSERT_LOGS) {
                                    CustomLogger.customLogger(Level.INFO, fl3prm + " " + fl4val + " (fl1ppr = " + row.getFl1ppr() + ") is correct");
                                }
                            }
                        } else if (fl4val != null) {
                            if (!fl4val.isEmpty()) {
                                if (!fl4val.equals("N")) {
                                    CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " is incorrect(fl1ppr = " + row.getFl1ppr() + "), should be empty ");
                                } else if (POSITIVE_ASSERT_LOGS) {
                                    CustomLogger.customLogger(Level.INFO, fl3prm + " " + fl4val + " (fl1ppr = " + row.getFl1ppr() + ") could be empty, but value is correct");
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void productAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel
            expectedDataModel, RequestModel requestModel) {
        if (resultSetRowList == null || resultSetRowList.isEmpty()) {
            CustomLogger.customLogger(Level.WARNING, "Empty response for " + expectedDataModel.getFl8pck() + " " + requestModel.getChancd() + " " + expectedDataModel.getFl1proCat() + " " + requestModel.getFllpfl() + " " + expectedDataModel.getFl1pro() + " " + requestModel.getRiskLevel() + ":");
        } else {
            for (ResultSetRow row : resultSetRowList) {
                {
                    String fl3prm = row.getFl3prm();
                    String fl4val = row.getFl4val();
                    String value = expectedDataModel.getFl1proCat();
                    if (fl3prm.equals("PCC0001001")) {
                        if (!value.equals(fl4val)) {
                            CustomLogger.customLogger(Level.WARNING, fl3prm + " " + fl4val + " is incorrect, should be: " + value);
                        } else if (POSITIVE_ASSERT_LOGS) {
                            CustomLogger.customLogger(Level.INFO, fl3prm + " " + fl4val + " is correct");
                        }
                    }
                }
            }
        }
    }


    public static Map<String, String> convertToMap(String fl4val) {
        if (fl4val.contains(" ")) {
            // Split the string based on whitespace
            String[] parts = fl4val.trim().split("\\s+");
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
}
