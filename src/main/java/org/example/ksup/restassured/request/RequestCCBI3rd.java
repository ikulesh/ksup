package org.example.ksup.restassured.request;

import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;


public class RequestCCBI3rd {
    public static List<ResultSetRow> requestCCBI(RequestModel requestModel) throws JAXBException {
        return Request.request("CCBI", requestModel, setAttrList(), false,"W");
    }

    public static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        //Request #3
        //attrList.add("PAPPTYPE");
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
    public static List<String> setAssertList(){
        List<String> attrList = new ArrayList<>();
        //Request #3
        //attrList.add("PAPPTYPE");
        attrList.add("PCC0000605");


        return attrList;
    }
}
