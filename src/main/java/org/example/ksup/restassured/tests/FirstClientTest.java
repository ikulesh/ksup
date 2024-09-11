package org.example.ksup.restassured.tests;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.request.*;
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

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            System.out.println(expectedDataModel);
        }

        List<ResultSetRow> result;
        boolean nextStep;

        RequestModel request = new RequestModel();
        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            // сетим юнит и ConstantID
            request.setExtSysCode(EXT_SYS_CODE); // constant
            request.setConstantID(CONSTANT_ID); // constant
            // сетим пакет и регион (регион только один будет для каждого пакета)
            request.setFl8pck(expectedDataModel.getFl8pck());
            request.setRegcd(expectedDataModel.getRegcd());
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
                request.setChancd(channel);
                //сетим первого клиента из списка
                request.setFllpfl(expectedDataModel.getFllpfl().get(0));
                String clientWithCat = request.getFllpfl();
                // для комбинации канал - клиент перебираем уровни риска
                for (String riskLevel : expectedDataModel.getRiskLevelRpp()) {

                    request.setFl1grp("GCC01");
                    result = RequestGCC01.requestGCC01(request);
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " " + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " " + riskLevel + ":");
                    CustomLogger.customLogger(Level.INFO, "GCC01 request assertion:");
                    nextStep = AccessibilityAssertions.accessibilityAssertions(result, expectedDataModel);
                    //nextStep = false;
                    AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestGCC01.setAssertList());
                    request.setFl1grp(null);

                    if (nextStep) {
                        //сетим уровень риска
                        request.setRiskLevel(riskLevel);

                        request.setApplicationID(APP_ID);
                        //request for getting main PCC params with alternative
                        result = RequestGraceLGP.requestGRC(request);
                        CustomLogger.customLogger(Level.INFO, "GRC LGP request assertion:");
                        AttrAssertions.productAssertion(result, expectedDataModel, request);
                        AttrAssertions.attrAssertions(result, expectedDataModel, request, RequestGraceLGP.setAssertList());
                        AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestGraceLGP.setAssertList());

                        //request for other PCC params
                        result = RequestGrace4th.requestGRCOther(request);
                        CustomLogger.customLogger(Level.INFO, "GRC 4th request assertion:");
                        AttrAssertions.attrAssertions(result, expectedDataModel, request, RequestGrace4th.setAssertList());
                        AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestGrace4th.setAssertList());

                        //skip CCBI request (it could be here, but I'm too lazy)
                        /*
                        result = RequestCCBI3rd.requestCCBI(request);
                        CustomLogger.customLogger(Level.INFO, "CCBI 3rd request assertion:");
                        AttrAssertions.attrAssertions(result, expectedDataModel, request, RequestCCBI3rd.setAttrList());
                        AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestCCBI3rd.setAttrList());
                        */
                        //сетим ценовую группу
                        SetPriceGrp.setPriceGrp(result, expectedDataModel);

                        //сетим код карточки для запроса на доступность
                        request.setFl1pro(expectedDataModel.getFl1pro());
                        request.setFl1grp(expectedDataModel.getFl1grp());

                        //отправляем запрос для проверки основных параметров для первой карты
                        result = RequestIPC1st.requestForDifGCC(request, false);
                        CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = false) assertion:");
                        AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestIPC1st.setAssertList(false));

                        //отправляем запрос для проверки альтернативных параметров для второй карты
                        if (IS_SECOND_CARD) {
                            result = RequestIPC1st.requestForDifGCC(request, true);
                            CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = true) assertion:");
                            AttrAssertions.paramAssertion(result, expectedDataModel, request, RequestIPC1st.setAssertList(true));
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