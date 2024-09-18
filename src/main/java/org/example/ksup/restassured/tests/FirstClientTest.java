package org.example.ksup.restassured.tests;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.example.ksup.restassured.Config;
import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.ExcelColorChanger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.request.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Config.*;
import static org.example.ksup.restassured.pojo.ExcelToRequestModelList.readExcelFile;

public class FirstClientTest {
    @Test
    public void firstClientTest() throws JAXBException, IOException {
        Config.loadProperties();
        ZipSecureFile.setMinInflateRatio(0.0001); // Set to a lower ratio, if needed
        RequestModel request = new RequestModel();
        List<ExpectedDataModel> expectedDataModelList = readExcelFile(EXCEL_FILE_PATH);

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            List<String> warningsList = new ArrayList<>();
            //инициализация общей части запроса
            request.initializer(expectedDataModel);
            //перебираем сначала каналы
            //for (String channel : expectedDataModel.getChancd())
            String channel = expectedDataModel.getChancd().get(0);
            {
                //ограничены ли проверки наборами каналов и карт
                if (CARD_LIST_IS_LIMITED) {
                    if (!CARD_LIST.contains(expectedDataModel.getFl1pro())) {
                        break;
                    }
                }
                if (CHANNEL_LIST_IS_LIMITED) {
                    if (!CHANNEL_LIST.contains(channel)) {
                        continue;
                    }
                }
                request.setChancd(channel); // channel set
                //сетим первого клиента из списка
                String clientWithCat = expectedDataModel.getFllpfl().get(0);
                request.setFllpfl(clientWithCat);
                // для комбинации канал - клиент перебираем уровни риска
                for (String riskLevel : expectedDataModel.getRiskLevelRpp()) {
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "");
                    CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getFl8pck() + " " + channel + " "
                            + expectedDataModel.getFl1proCat() + " " + clientWithCat + " " + expectedDataModel.getFl1pro() + " "
                            + riskLevel + " " + EXT_SYS_CODE + ":");

                    //вызов GCC01
                    boolean nextStep = RequestGCC01.execution(request, expectedDataModel, warningsList);

                    if (nextStep) {
                        //сетим уровень риска и AppID
                        request.setRiskLevel(riskLevel);
                        request.setApplicationID(APP_ID);

                        //request for getting main PCC params with alternative
                        RequestGraceLGP.execution(request, expectedDataModel, warningsList);

                        //request for other PCC params
                        RequestGrace4th.execution(request, expectedDataModel, warningsList);

                        //WOW! Assert for one param PCC0000605
                        RequestCCBI3rd.execution(request, expectedDataModel, warningsList);

                        //сетим код карты и ценовую группу
                        request.setFl1pro(expectedDataModel.getFl1pro());
                        request.setFl1grp(expectedDataModel.getFl1grp());

                        //request IPC
                        RequestIPC1st.execution(request, expectedDataModel, warningsList);

                        //reset params
                        request.setApplicationID(null);
                        request.setFl1pro(null);
                        request.setFl1grp(null);
                    }
                }
            }
            ExcelColorChanger.colorChange(expectedDataModel, warningsList);
        }
    }
}