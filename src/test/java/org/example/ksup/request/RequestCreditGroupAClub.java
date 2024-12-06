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


public class RequestCreditGroupAClub {
    /**
     * Method sends CCBI request
     *
     * @param requestModel instance with request info
     * @return list of ResultSetRow
     */
    public static List<ResultSetRow> requestCreditGroupAClub(RequestModel requestModel) throws JAXBException {
        return Request.request(null, requestModel, setAttrList(), false, "W");
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request #3
        attrList.add("PCC0001038");
        attrList.add("PCC0001034");
        attrList.add("PCC0001033");
        attrList.add("PCC0002011");
        attrList.add("PCC0001138");
        attrList.add("PCC0001139");
        attrList.add("PCC0001057");
        attrList.add("PCC0001056");
        attrList.add("PCC0001061");
        attrList.add("PCC0001062");
        attrList.add("PCC0001049");
        attrList.add("PCC0000605");
        attrList.add("PBI0000001");
        attrList.add("PCC0001066");
        attrList.add("PCC0001024");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0001150");
        attrList.add("PCC0002013");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
        attrList.add("PCC0001151");
        attrList.add("PCC0002051");
        attrList.add("PCC0002053");

        return attrList;
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        //Request #3
        attrList.add("PCC0002011");
        attrList.add("PCC0001056");
        attrList.add("PCC0000605");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0002013");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
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
        CustomLogger.customLogger(Level.INFO, "Credit Group A-Club request assertion:");
        request.setPcc0000605(expectedDataModel.getCardParams().get("PIPC000602"));
        if (request.getPcc0000605() == null || request.getPcc0000605().isEmpty()) {
            request.setPcc0000605(expectedDataModel.getCardParams().get("PIPC000601"));
        }
        if ((request.getPcc0000605() == null || request.getPcc0000605().isEmpty())) {
            CustomLogger.customLogger(Level.WARNING, "Empty parameter PCC0000605, check your excel file (string number = "
                    + expectedDataModel.getIndex() + 1 + ", parameters PIPC000601 and PIPC000602)");
        } else {
            List<ResultSetRow> result = RequestCreditGroupAClub.requestCreditGroupAClub(request);
            if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                ParamAssertions.paramAssertion(result, expectedDataModel, RequestCreditGroupAClub.setAssertList(), warningsList);
                ParamAssertions.attrAssertions(result, expectedDataModel, RequestCreditGroupAClub.setAssertList(), warningsList);
            }
        }
        request.setRiskLevel(null);
    }
}
