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
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Properties.*;
import static org.example.ksup.restassured.pojo.ExcelToRequestModelList.readExcelFile;

public class FirstClientTest {
    @Test
    public void firstClientTest() throws JAXBException {
        List<ExpectedDataModel> expectedDataModelList = readExcelFile(EXCEL_FILE_PATH);
        RequestModel request = new RequestModel();
        List<ResultSetRow> result;
        boolean nextStep;

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
                for (String riskLevel : expectedDataModel.getRiskLevelRpp()) {

                    request.setFl1grp("GCC01"); // price group
                    result = RequestGCC01.requestGCC01(request);
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " " + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " " + riskLevel + " " + EXT_SYS_CODE + ":");
                    CustomLogger.customLogger(Level.INFO, "GCC01 request assertion:");
                    nextStep = AccessibilityAssertions.accessibilityAssertions(result, expectedDataModel); //if PIPC000801 == Y |=> nextStep = true
                    if (nextStep && AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                        AttrAssertions.cardNameAssertion(result, expectedDataModel); // cardName assertion
                        AttrAssertions.paramAssertion(result, expectedDataModel, RequestGCC01.setAssertList()); // assertion for simple params
                    } else {
                        nextStep = false;
                    }
                    request.setFl1grp(null);

                    if (nextStep) {
                        //сетим уровень риска и AppID
                        request.setRiskLevel(riskLevel);
                        request.setApplicationID(APP_ID);

                        //request for getting main PCC params with alternative
                        result = RequestGraceLGP.requestGRC(request);
                        CustomLogger.customLogger(Level.INFO, "GRC LGP request assertion:");
                        if (AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                            AttrAssertions.productAssertion(result, expectedDataModel); // product code assertion
                            AttrAssertions.attrAssertions(result, expectedDataModel, RequestGraceLGP.setAssertList()); // assertion for complex params
                            AttrAssertions.paramAssertion(result, expectedDataModel, RequestGraceLGP.setAssertList());
                        }

                        //request for other PCC params
                        result = RequestGrace4th.requestGRCOther(request);
                        CustomLogger.customLogger(Level.INFO, "GRC 4th request assertion:");
                        if (AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                            AttrAssertions.attrAssertions(result, expectedDataModel, RequestGrace4th.setAssertList());
                            AttrAssertions.paramAssertion(result, expectedDataModel, RequestGrace4th.setAssertList());
                        }

                        //сетим ценовую группу
                        SetPriceGrp.setPriceGrp(result, expectedDataModel);

                        //WOW! Assert for one param PCC0000605
                        if (CCBI_IS_NEED && AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                            result = RequestCCBI3rd.requestCCBI(request);
                            CustomLogger.customLogger(Level.INFO, "CCBI 3rd request assertion:");
                            //AttrAssertions.attrAssertions(result, expectedDataModel, RequestCCBI3rd.setAssertList());
                            AttrAssertions.paramAssertion(result, expectedDataModel, RequestCCBI3rd.setAssertList());
                        }


                        request.setFl1pro(expectedDataModel.getFl1pro());
                        request.setFl1grp(expectedDataModel.getFl1grp());

                        //отправляем запрос для проверки основных параметров для первой карты
                        result = RequestIPC1st.requestForDifGCC(request, false);
                        CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = false) assertion:");
                        if (AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                            AttrAssertions.paramAssertion(result, expectedDataModel, RequestIPC1st.setAssertList(false));
                        }

                        //отправляем запрос для проверки альтернативных параметров для второй карты
                        if (IS_SECOND_CARD) {
                            result = RequestIPC1st.requestForDifGCC(request, true);
                            CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = true) assertion:");
                            if (AttrAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                                AttrAssertions.paramAssertion(result, expectedDataModel, RequestIPC1st.setAssertList(true));
                            }
                        }
                        //reset params
                        request.setApplicationID(null);
                        request.setFl1pro(null);
                        request.setFl1grp(null);
                    }
                }
            }
        }
    }
}