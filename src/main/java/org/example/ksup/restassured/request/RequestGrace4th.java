package org.example.ksup.restassured.request;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.tests.assertions.ParamAssertions;
import org.example.ksup.restassured.tests.assertions.SetPriceGrp;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RequestGrace4th {
    public static List<ResultSetRow> requestGRCOther(RequestModel requestModel) throws JAXBException {
        return Request.request("GRC", requestModel, setAttrList(), false,"8");
    }

    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request 4
        //attrList.add("PAPPTYPE");
        attrList.add("PCC0001056");
        attrList.add("PCC0002122");
        attrList.add("PCC0002022");
        attrList.add("PCC0002132");
        attrList.add("PCC0002032");
        attrList.add("PCC0002162");
        attrList.add("PCC0002124");
        attrList.add("PCC0002214");
        attrList.add("PCC0002234");
        attrList.add("PCC0002142");
        attrList.add("PCC0002114");
        attrList.add("PCC0002115");
        attrList.add("PCC0002116");
        attrList.add("PCC0002117");
        attrList.add("PCC0002134");
        attrList.add("PCC0002135");
        attrList.add("PCC0002118");
        attrList.add("PCC0002042");
        attrList.add("PCC0002143");
        attrList.add("PCC0002044");
        attrList.add("PCC0002053");
        attrList.add("PCC0002152");
        attrList.add("PCC0002151");
        attrList.add("PCC0002121");
        attrList.add("PCC0002133");
        attrList.add("PCC0002113");
        attrList.add("PCC0002161");
        attrList.add("PCC0002131");
        attrList.add("PCC0002123");
        attrList.add("PCC0002141");


        return attrList;
    }
    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        //Request 4
        attrList.add("PCC0001056");
        attrList.add("PCC0002122");
        attrList.add("PCC0002022");
        attrList.add("PCC0002132");
        attrList.add("PCC0002032");
        attrList.add("PCC0002162");
        attrList.add("PCC0002124");
        attrList.add("PCC0002214");
        attrList.add("PCC0002234");
        attrList.add("PCC0002142");
        attrList.add("PCC0002114");
        attrList.add("PCC0002115");
        attrList.add("PCC0002116");
        attrList.add("PCC0002117");
        attrList.add("PCC0002134");
        attrList.add("PCC0002135");
        attrList.add("PCC0002118");
        attrList.add("PCC0002042");
        attrList.add("PCC0002143");
        attrList.add("PCC0002044");
        //attrList.add("PCC0002053");
        attrList.add("PCC0002152");
        attrList.add("PCC0002151");
        attrList.add("PCC0002121");
        attrList.add("PCC0002133");
        attrList.add("PCC0002113");
        attrList.add("PCC0002161");
        attrList.add("PCC0002131");
        attrList.add("PCC0002123");
        attrList.add("PCC0002141");

        return attrList;
    }
    public static void execution(RequestModel request, ExpectedDataModel expectedDataModel) throws JAXBException {
        List<ResultSetRow> result = RequestGrace4th.requestGRCOther(request);
        CustomLogger.customLogger(Level.INFO, "GRC 4th request assertion:");
        if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.attrAssertions(result, expectedDataModel, RequestGrace4th.setAssertList());
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestGrace4th.setAssertList());
        }
        //сетим ценовую группу в ожидаемую модель данных
        SetPriceGrp.setPriceGrp(result, expectedDataModel);
    }
}
