package org.example.ksup.restassured.request;

import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;


public class RequestGraceLGP {
    public static List<ResultSetRow> requestGRC(RequestModel requestModel) throws JAXBException {
        return Request.request("GRC", requestModel, setAttrList(), false,"8");
    }

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
}
