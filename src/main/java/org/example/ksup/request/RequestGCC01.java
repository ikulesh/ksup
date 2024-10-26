package org.example.ksup.request;

import org.example.ksup.log.CustomLogger;
import org.example.ksup.pojo.RequestModel;
import org.example.ksup.pojo.outparms.ExpectedDataModel;
import org.example.ksup.pojo.outparms.ResultSetRow;
import org.example.ksup.tests.assertions.AccessibilityAssertion;
import org.example.ksup.tests.assertions.ParamAssertions;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class RequestGCC01 {
    /**
     * Method sends GCC01 request
     *
     * @param requestModel instance with request info
     * @return list of ResultSetRow
     */
    public static List<ResultSetRow> requestGCC01(RequestModel requestModel) throws JAXBException {
        return Request.request(null, requestModel, setAttrList(), false, "W");
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAttrList() {
        //ChooseCardRequest
        List<String> attrList = new ArrayList<>();
        attrList.add("PAPPTYPE");

        attrList.add("PIPC000002");
        attrList.add("PIPC000004");
        attrList.add("PIPC000302");
        //accessibility
        attrList.add("PIPC000801");

        attrList.add("PIPC000001");
        attrList.add("PIPC000003");
        attrList.add("PIPC000209");
        attrList.add("PIPC000219");
        attrList.add("PIPC000501");
        attrList.add("PIPC000511");
        attrList.add("PIPC000504");
        attrList.add("PIPC000503");
        attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        attrList.add("PIPC000615");
        attrList.add("PIPC000618");
        attrList.add("PIPC000628");
        attrList.add("PIPC000807");
        attrList.add("PIPC000808");
        attrList.add("PIPC000809");
        attrList.add("PIPC000007");
        attrList.add("PIPC000303");
        attrList.add("PIPC000812");
        attrList.add("PIPC000904");
        attrList.add("PIPC000905");
        attrList.add("PIPC000601");
        attrList.add("PIPC000602");

        return attrList;
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000002");
        attrList.add("PIPC000004");
        attrList.add("PIPC000302");
        //accessibility
        attrList.add("PIPC000801");
        attrList.add("PIPC000812");
        attrList.add("PIPC000601");
        attrList.add("PIPC000602");

        return attrList;
    }

    /**
     * Method sends req and assert response
     *
     * @param expectedDataModel info for comparing
     * @param request           req info
     * @param warningsList      wrong params
     */
    public static boolean execution(RequestModel request, ExpectedDataModel expectedDataModel, List<String> warningsList) throws JAXBException {
        CustomLogger.customLogger(Level.INFO, "GCC01 request assertion:");
        request.setFl1grp("GCC01"); // price group
        request.setFl1pro(expectedDataModel.getCardCode());
        request.setFl8pck(null);
        List<ResultSetRow> result = requestGCC01(request);
        boolean nextStep = AccessibilityAssertion.accessibilityAssertion(result, expectedDataModel, warningsList); //if PIPC000801 == Y |=> nextStep = true
        if (nextStep && ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.cardNameAssertion(result, expectedDataModel, warningsList); // cardName assertion
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestGCC01.setAssertList(), warningsList); // assertion for simple params
        }
        request.setFl1grp(null);
        request.setFl1pro(null);
        request.setFl8pck(expectedDataModel.getPackageCode());
        return nextStep;
    }
}
