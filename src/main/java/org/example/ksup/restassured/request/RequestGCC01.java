package org.example.ksup.restassured.request;

import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class RequestGCC01 {
    public static List<ResultSetRow> requestGCC01(RequestModel requestModel) throws JAXBException {
        return Request.request(null, requestModel, setAttrList(), false,"W");
    }

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
    public static List<String> setAssertList(){
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
}
