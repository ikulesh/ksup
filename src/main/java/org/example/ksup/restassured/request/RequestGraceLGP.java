package org.example.ksup.restassured.request;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.tests.assertions.ParamAssertions;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class RequestGraceLGP {
    /**
     * Method sends LGP request
     *
     * @param requestModel instance with request info
     * @return list of ResultSetRow
     */
    public static List<ResultSetRow> requestGRC(RequestModel requestModel) throws JAXBException {
        return Request.request("GRC", requestModel, setAttrList(), false, "8");
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request LGP
        attrList.add("PCC0001001");
        attrList.add("PCC0001056");
        attrList.add("PCC0002151");
        attrList.add("PCC0002152");
        attrList.add("PCC0002113");
        attrList.add("PCC0002214");
        attrList.add("PCC0002053");
        attrList.add("PCC0002042");
        attrList.add("PCC0002121");
        attrList.add("PCC0002122");
        attrList.add("PCC0002123");
        attrList.add("PCC0002124");

        attrList.add("PCC0003152");
        attrList.add("PCC0003122");
        attrList.add("PCC0003132");
        attrList.add("PCC0003161");
        attrList.add("PCC0003113");
        attrList.add("PCC0003123");
        attrList.add("PCC0003133");
        attrList.add("PCC0003142");

        return attrList;
    }

    /**
     * Method creating assertion params list
     */
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        //Request LGP
        attrList.add("PCC0001056");
        attrList.add("PCC0002151");
        attrList.add("PCC0002152");
        attrList.add("PCC0002113");
        attrList.add("PCC0002214");
        attrList.add("PCC0002042");
        attrList.add("PCC0002121");
        attrList.add("PCC0002122");
        attrList.add("PCC0002123");
        attrList.add("PCC0002124");
        attrList.add("PCC0003152");
        attrList.add("PCC0003122");
        attrList.add("PCC0003132");
        attrList.add("PCC0003161");
        attrList.add("PCC0003113");
        attrList.add("PCC0003123");
        attrList.add("PCC0003133");
        attrList.add("PCC0003142");

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
        List<ResultSetRow> result = requestGRC(request);
        CustomLogger.customLogger(Level.INFO, "GRC LGP request assertion:");
        if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.productAssertion(result, expectedDataModel, warningsList); // product code assertion
            ParamAssertions.attrAssertions(result, expectedDataModel, RequestGraceLGP.setAssertList(), warningsList); // assertion for complex params
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestGraceLGP.setAssertList(), warningsList);
        }
    }
}
