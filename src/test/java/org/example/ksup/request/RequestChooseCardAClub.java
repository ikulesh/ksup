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

/**
 * Old class (could be used, but this req has no sense)
 */
public class RequestChooseCardAClub {
    public static List<ResultSetRow> requestChooseCard(RequestModel requestModel) throws JAXBException {
        return Request.request(null, requestModel, setAttrList(), false, "O");
    }

    private static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PAPPTYPE");
        attrList.add("PIPC000002");
        attrList.add("PIPC000001");
        attrList.add("PCC0001046");
        attrList.add("PIPC000302");
        attrList.add("PIPC000007");
        attrList.add("PIPC000011");
        attrList.add("PIPC000303");
        attrList.add("PIPC000501");
        attrList.add("PIPC000503");
        attrList.add("PCC0002011");
        attrList.add("PCC0001056");
        attrList.add("PIPC000601");
        attrList.add("PIPC000602");
        attrList.add("PCC0001001");
        attrList.add("PIPC000614");
        attrList.add("PIPC000801");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0001150");
        attrList.add("PCC0002013");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
        attrList.add("PCC0001151");
        attrList.add("PIPC000811");
        attrList.add("PIPC000812");
        attrList.add("PIPC000608");
        attrList.add("PCC0002051");

        return attrList;
    }

    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000002");
        attrList.add("PIPC000001");
        attrList.add("PIPC000302");
        attrList.add("PIPC000501");
        attrList.add("PIPC000503");
        attrList.add("PCC0002011");
        attrList.add("PCC0001056");
        attrList.add("PIPC000601");
        attrList.add("PIPC000602");
        attrList.add("PIPC000801");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
        attrList.add("PIPC000811");
        attrList.add("PIPC000812");
        attrList.add("PIPC000608");


        return attrList;
    }

    public static boolean execution(RequestModel request, ExpectedDataModel expectedDataModel, List<String> warningsList) throws JAXBException {
        CustomLogger.customLogger(Level.INFO, "Choose Card A-Club request assertion:");
        request.setFl1grp(null);
        request.setFl1pro(null);
        request.setFl8pck(null);
        List<ResultSetRow> result = requestChooseCard(request);
        boolean nextStep = AccessibilityAssertion.accessibilityAssertion(result, expectedDataModel, warningsList); //if PIPC000801 == Y |=> nextStep = true
        if (nextStep && ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.cardNameAssertion(result, expectedDataModel, warningsList); // cardName assertion
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestGCC01.setAssertList(), warningsList); // assertion for simple params
        }
        request.setFl8pck(expectedDataModel.getPackageCode());
        return nextStep;
    }
}
