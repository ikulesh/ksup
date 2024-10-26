package org.example.ksup.tests;

import org.example.ksup.Config;
import org.example.ksup.log.CustomLogger;
import org.example.ksup.pojo.ExcelColorChanger;
import org.example.ksup.pojo.ExcelFileReader;
import org.example.ksup.pojo.RequestModel;
import org.example.ksup.pojo.outparms.ExpectedDataModel;
import org.example.ksup.request.*;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.Config.*;

public class EachClientTest {
    @Test
    public void eachClientTest() throws JAXBException, IOException {
        Config.loadProperties();
        List<ExpectedDataModel> expectedDataModelList = ExcelFileReader.readExcelFile(EXCEL_FILE_PATH);
        RequestModel request = new RequestModel();
        HashMap<Integer, List<String>> warningsListMap = new HashMap<>();
        List<String> warningsList = new ArrayList<>();

        for (ExpectedDataModel expectedDataModel : expectedDataModelList) {
            request.initialize(expectedDataModel);
            //перебираем сначала каналы
            for (String channel : expectedDataModel.getChannelList()) {

                request.setChancd(channel);
                //для каждого канала перебираем клиентов
                for (String client : expectedDataModel.getClientList()) {

                    if (!expectedDataModel.needToTest(channel, client)) {
                        continue;
                    }
                    request.setFllpfl(client);

                    // для комбинации канал - клиент перебираем уровни риска
                    for (String riskLevel : expectedDataModel.getRiskLevelRpp()) {
                        CustomLogger.customLogger(Level.INFO, "");
                        CustomLogger.customLogger(Level.INFO, "");
                        CustomLogger.customLogger(Level.INFO, "Assertions for " + expectedDataModel.getPackageCode() + " " + channel + " "
                                + expectedDataModel.getProductCode() + " " + client + " " + expectedDataModel.getCardCode() + " "
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
                            request.setFl1pro(expectedDataModel.getCardCode());
                            request.setFl1grp(expectedDataModel.getFl1grp());

                            //request IPC
                            RequestIPC1st.execution(request, expectedDataModel, warningsList);

                            //reset params
                            request.reset();
                        }
                    }
                }
            }
            warningsListMap.put(expectedDataModel.getIndex(), new ArrayList<>(warningsList));
            warningsList.clear();
        }
        ExcelColorChanger.colorChange(warningsListMap);
    }
}
