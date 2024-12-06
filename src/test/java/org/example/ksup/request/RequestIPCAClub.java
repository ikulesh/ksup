package org.example.ksup.request;

import org.example.ksup.log.CustomLogger;
import org.example.ksup.pojo.RequestModel;
import org.example.ksup.pojo.outparms.ExpectedDataModel;
import org.example.ksup.pojo.outparms.ResultSetRow;
import org.example.ksup.tests.assertions.ParamAssertions;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.Config.IS_SECOND_CARD;

public class RequestIPCAClub {
    /**
     * Method sends IPC1st request
     *
     * @param requestModel instance with request info
     * @return list of ResultSetRow
     */
    public static List<ResultSetRow> requestForDifGCC(RequestModel requestModel) throws JAXBException {
        return Request.request("IPC", requestModel, setAttrList(), false, "W");
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000001");
        attrList.add("PIPC000209");
        attrList.add("PIPC000501");
        attrList.add("PIPC000504");
        attrList.add("PIPC000604");
        attrList.add("PIPC000503");
        attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        attrList.add("PIPC000614");
        attrList.add("PIPC000615");
        attrList.add("PIPC000703");
        attrList.add("PIPC000003");
        attrList.add("PIPC000807");
        attrList.add("PIPC000808");
        attrList.add("PIPC000709");
        attrList.add("PIPC000809");
        attrList.add("PIPC000811");
        attrList.add("PIPC000608");
        attrList.add("PIPC000611");
        attrList.add("PIPC000505");
        attrList.add("PIPC000610");
        attrList.add("PIPC000618");
        attrList.add("PIPC000210");


        return attrList;
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000001");
        attrList.add("PIPC000209");
        attrList.add("PIPC000501");
        //attrList.add("PIPC000503");
        //attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        //attrList.add("PIPC000609");
        attrList.add("PIPC000703");
        attrList.add("PIPC000003");
        attrList.add("PIPC000807");
        attrList.add("PIPC000709");
        attrList.add("PIPC000809");
        attrList.add("PIPC000811");
        //attrList.add("PIPC000608");
        //attrList.add("PIPC000611");
        //attrList.add("PIPC000505");
        attrList.add("PIPC000610");
        attrList.add("PIPC000618");


        return attrList;
    }

    /**
     * Method sends req and assert response
     *
     * @param expectedDataModel info for comparing
     * @param request           req info
     * @param warningsList      wrong params
     */
    public static void execution(RequestModel request, ExpectedDataModel expectedDataModel, List<String> warningsList) throws JAXBException {
        //отправляем запрос для проверки основных параметров для первой карты
        request.setFl1grp("GCC1");
        request.setFl1pro(expectedDataModel.getCardCode());
        request.setRiskLevel(null);
        List<ResultSetRow> result = requestForDifGCC(request);
        CustomLogger.customLogger(Level.INFO, "IPC A-Club request assertion:");
        if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestIPCAClub.setAssertList(), warningsList);
        }
    }
}
