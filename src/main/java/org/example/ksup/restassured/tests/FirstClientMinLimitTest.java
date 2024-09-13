package org.example.ksup.restassured.tests;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.request.*;
import org.example.ksup.restassured.tests.assertions.AccessibilityAssertions;
import org.example.ksup.restassured.tests.assertions.AttrAssertions;
import org.example.ksup.restassured.tests.assertions.SetPriceGrp;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Properties.*;
import static org.example.ksup.restassured.pojo.ExcelToRequestModelList.readExcelFile;

public class FirstClientMinLimitTest {
    @Test
    public void firstClientTest() throws JAXBException {
        List<ExpectedDataModel> expectedDataModelList = readExcelFile(EXCEL_FILE_PATH);
        RequestModel request = new RequestModel();
        List<ResultSetRow> result;
        List<String> params = new ArrayList<>();
        params.add("PCC000601");

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            request.setExtSysCode(EXT_SYS_CODE); // unit
            request.setConstantID(CONSTANT_ID); // constantId
            // сетим пакет и регион (регион только один для каждого пакета)
            request.setFl8pck(expectedDataModel.getFl8pck()); // package
            request.setRegcd(expectedDataModel.getRegcd()); // region
            // сетим валюту и pipc000812
            request.setCurrency(expectedDataModel.getParamsPIPC().get("PIPC000004"));
            request.setPipc000812(expectedDataModel.getParamsPIPC().get("PIPC000812"));
            // перебираем сначала каналы
            for (String channel : expectedDataModel.getChancd()) {
                //ограничены ли проверки наборами каналов и карт
                if (CARD_LIST_IS_LIMITED) {
                    if (!CARD_LIST.contains(expectedDataModel.getFl1pro().trim())) {
                        break;
                    }
                }
                if (CHANNEL_LIST_IS_LIMITED) {
                    if (!CHANNEL_LIST.contains(channel.trim())) {
                        continue;
                    }
                }
                request.setChancd(channel); // channel set
                //сетим первого клиента из списка
                request.setFllpfl(expectedDataModel.getFllpfl().get(0));
                String clientWithCat = request.getFllpfl();
                // для комбинации канал - клиент перебираем уровни риска
                {
                    request.setFl1grp("GCC01"); // price group
                    result = RequestGCC01.requestGCC01(request);
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " " + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " " + ":");
                    CustomLogger.customLogger(Level.INFO, "GCC01 request assertion:");
                    if (AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                        AccessibilityAssertions.accessibilityAssertions(result, expectedDataModel); //if PIPC000801 == Y |=> nextStep = true
                        AttrAssertions.paramAssertion(result, expectedDataModel, params); // assertion for simple params
                    }
                }
            }
        }
    }
}