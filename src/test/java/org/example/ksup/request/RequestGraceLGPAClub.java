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

import static org.example.ksup.Config.APP_ID;


public class RequestGraceLGPAClub {
    /**
     * Method sends LGP request
     *
     * @param requestModel instance with request info
     * @return list of ResultSetRow
     */
    public static List<ResultSetRow> requestGRC(RequestModel requestModel) throws JAXBException {
        return Request.request("GRC", requestModel, setAttrList(), false, "W");
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request LGP
        attrList.add("PCC0002011");
        attrList.add("PCC0001056");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0002013");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
        attrList.add("PCC0002014");
        attrList.add("PCC0002015");
        attrList.add("PCC0002016");
        attrList.add("PCC0002017");
        attrList.add("PCC0002034");
        attrList.add("PCC0002035");
        attrList.add("PCC0002041");
        attrList.add("PCC0002042");
        attrList.add("PCC0002043");
        attrList.add("PCC0002044");
        attrList.add("PCC0002051");

        return attrList;
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        //Request LGP
        attrList.add("PCC0002011");
        attrList.add("PCC0001056");
        attrList.add("PCC0002021");
        attrList.add("PCC0002022");
        attrList.add("PCC0002031");
        attrList.add("PCC0002032");
        attrList.add("PCC0002013");
        attrList.add("PCC0002023");
        attrList.add("PCC0002033");
        attrList.add("PCC0002041");
        attrList.add("PCC0002042");
        attrList.add("PCC0002043");
        attrList.add("PCC0002044");

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
        request.setApplicationID(APP_ID);
        List<ResultSetRow> result = requestGRC(request);
        CustomLogger.customLogger(Level.INFO, "GRC LGP A-Club request assertion:");
        if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.productAssertion(result, expectedDataModel, warningsList); // product code assertion
            ParamAssertions.attrAssertions(result, expectedDataModel, RequestGraceLGPAClub.setAssertList(), warningsList); // assertion for complex params
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestGraceLGPAClub.setAssertList(), warningsList);
        }
    }
}
