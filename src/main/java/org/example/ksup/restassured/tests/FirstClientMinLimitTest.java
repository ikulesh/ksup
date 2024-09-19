package org.example.ksup.restassured.tests;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.example.ksup.restassured.Config;
import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.ExcelColorChanger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.request.*;
import org.example.ksup.restassured.tests.assertions.AccessibilityAssertions;
import org.example.ksup.restassured.tests.assertions.ParamAssertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Config.*;
import static org.example.ksup.restassured.pojo.ExcelToRequestModelList.readExcelFile;

public class FirstClientMinLimitTest {
    @Test
    public void firstClientTest() throws JAXBException, IOException {
        Config.loadProperties();
        ZipSecureFile.setMinInflateRatio(0.001);
        List<ExpectedDataModel> expectedDataModelList = readExcelFile(EXCEL_FILE_PATH);
        RequestModel request = new RequestModel();
        List<ResultSetRow> result;
        List<String> params = new ArrayList<>();
        params.add("PCC000601");

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            request.initializer(expectedDataModel);
            List<String> warningsList = new ArrayList<>();
            // перебираем сначала каналы
            String channel = expectedDataModel.getChancd().get(0);
            {
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
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " "
                            + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " " + EXT_SYS_CODE + ":");

                    request.setFl1grp("GCC01"); // price group
                    CustomLogger.customLogger(Level.INFO, "GCC01 request assertion:");
                    result = RequestGCC01.requestGCC01(request);
                    if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                        AccessibilityAssertions.accessibilityAssertions(result, expectedDataModel, warningsList); //if PIPC000801 == Y |=> nextStep = true
                        ParamAssertions.paramAssertion(result, expectedDataModel, params, warningsList); // assertion for simple params
                    }
                }
            }
            //ExcelColorChanger.colorChange(expectedDataModel, warningsList);
        }
    }
}