package org.example.ksup.restassured.tests;

import org.example.ksup.restassured.Config;
import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.request.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Config.*;
import static org.example.ksup.restassured.pojo.ExcelToRequestModelList.readExcelFile;

public class EachCatClientTest {
    @Test
    public void eachCatClientTest() throws JAXBException, IOException {
        Config.loadProperties();
        List<ExpectedDataModel> expectedDataModelList = readExcelFile(EXCEL_FILE_PATH);
        RequestModel request = new RequestModel();

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            request.initializer(expectedDataModel);
            //перебираем сначала каналы
            for (String channel : expectedDataModel.getChancd()) {
                request.setChancd(channel);
                //для каждого канала перебираем клиентов
                for (String catClient : expectedDataModel.getFlkval()) {
                    String clientWithCat = "";
                    for (String client : expectedDataModel.getFllpfl()) {
                        String cat = ExpectedDataModel.determineCategoryOfClient(client);
                        assert cat != null;
                        if (cat.equals(catClient)) {
                            clientWithCat = client;
                            break;
                        }
                    }
                    request.setFllpfl(clientWithCat);

                    // для комбинации канал - клиент перебираем уровни риска
                    for (String riskLevel : expectedDataModel.getRiskLevelRpp()) {
                        CustomLogger.customLogger(Level.INFO, "");
                        CustomLogger.customLogger(Level.INFO, "");
                        CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " "
                                + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " "
                                + riskLevel + " " + EXT_SYS_CODE + ":");

                        //вызов GCC01
                        boolean nextStep = RequestGCC01.execution(request, expectedDataModel);

                        if (nextStep) {
                            //сетим уровень риска и AppID
                            request.setRiskLevel(riskLevel);
                            request.setApplicationID(APP_ID);

                            //request for getting main PCC params with alternative
                            RequestGraceLGP.execution(request, expectedDataModel);

                            //request for other PCC params
                            RequestGrace4th.execution(request, expectedDataModel);

                            //WOW! Assert for one param PCC0000605
                            RequestCCBI3rd.execution(request,expectedDataModel);

                            //сетим код карты и ценовую группу
                            request.setFl1pro(expectedDataModel.getFl1pro());
                            request.setFl1grp(expectedDataModel.getFl1grp());

                            //request IPC
                            RequestIPC1st.execution(request,expectedDataModel);

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
}
