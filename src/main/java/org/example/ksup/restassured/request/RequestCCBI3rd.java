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

import static org.example.ksup.restassured.Config.CCBI_IS_NEED;


public class RequestCCBI3rd {
    public static List<ResultSetRow> requestCCBI(RequestModel requestModel) throws JAXBException {
        return Request.request("CCBI", requestModel, setAttrList(), false, "W");
    }

    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request #3
        attrList.add("PCC0001038");
        attrList.add("PCC0001034");
        attrList.add("PCC0001033");
        attrList.add("PCC0001138");
        attrList.add("PCC0001139");
        attrList.add("PCC0001057");
        attrList.add("PCC0001049");
        attrList.add("PCC0000605");
        attrList.add("PBI0000001");
        attrList.add("PCC0001066");
        attrList.add("PCC0001024");
        attrList.add("PCC0001050");
        attrList.add("PCC0002033");
        attrList.add("PCC0001151");
        attrList.add("PCC0002053");

        return attrList;
    }

    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        //Request #3
        attrList.add("PCC0000605");
        return attrList;
    }

    public static void execution(RequestModel request, ExpectedDataModel expectedDataModel, List<String> warningsList) throws JAXBException {
        if (CCBI_IS_NEED) {
            request.setPcc0000605(expectedDataModel.getParamsPIPC().get("PIPC000602"));
            if (request.getPcc0000605() == null || request.getPcc0000605().isEmpty()) {
                request.setPcc0000605(expectedDataModel.getParamsPIPC().get("PIPC000601"));
            }
            List<ResultSetRow> result = RequestCCBI3rd.requestCCBI(request);
            CustomLogger.customLogger(Level.INFO, "CCBI 3rd request assertion:");
            if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                ParamAssertions.paramAssertion(result, expectedDataModel, RequestCCBI3rd.setAssertList(), warningsList);
            }
        }
    }
}
