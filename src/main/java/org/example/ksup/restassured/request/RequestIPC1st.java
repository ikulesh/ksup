package org.example.ksup.restassured.request;

import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

public class RequestIPC1st {
    public static List<ResultSetRow> requestForDifGCC(RequestModel requestModel, boolean isSecondCard) throws JAXBException {
        return Request.request("IPC", requestModel, setAttrList(isSecondCard), false, "W");
    }

    public static List<String> setAttrList(boolean isSecondCard) {
        List<String> attrList = new ArrayList<>();
        //attrList.add("PAPPTYPE");
        attrList.add("PIPC000001");
        attrList.add("PIPC000504");
        attrList.add("PIPC000604");
        attrList.add("PIPC000503");
        attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        attrList.add("PIPC000614");
        attrList.add("PIPC000615");
        attrList.add("PIPC000703");
        attrList.add("PIPC000003");
        attrList.add("PIPC000807");
        attrList.add("PIPC000808");
        attrList.add("PIPC000709");
        attrList.add("PIPC000809");
        attrList.add("PIPC000811");
        attrList.add("PIPC000608");
        attrList.add("PIPC000611");
        attrList.add("PIPC000505");
        attrList.add("PIPC000610");
        attrList.add("PIPC000804");
        attrList.add("PIPC000805");
        attrList.add("PIPC000806");
        attrList.add("PIPC000904");
        attrList.add("PIPC000905");

        if (!isSecondCard) {
            attrList.add("PIPC000209");
            attrList.add("PIPC000501");
            attrList.add("PIPC000618");
        } else {
            attrList.add("PIPC000219");
            attrList.add("PIPC000511");
            attrList.add("PIPC000628");
        }

        return attrList;
    }

    public static List<String> setAssertList(boolean isSecondCard) {
        List<String> attrList = new ArrayList<>();
        //attrList.add("PAPPTYPE");
        //attrList.add("PIPC000001");
        //attrList.add("PIPC000504");
        //attrList.add("PIPC000604");
        attrList.add("PIPC000503");
        attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        //attrList.add("PIPC000614");
        //attrList.add("PIPC000615");
        attrList.add("PIPC000703");
        attrList.add("PIPC000003");
        attrList.add("PIPC000807");
        //attrList.add("PIPC000808");
        attrList.add("PIPC000709");
        attrList.add("PIPC000809");
        attrList.add("PIPC000811");
        attrList.add("PIPC000608");
        attrList.add("PIPC000611");
        attrList.add("PIPC000505");
        attrList.add("PIPC000610");
        attrList.add("PIPC000804");
        attrList.add("PIPC000805");
        attrList.add("PIPC000806");
        //attrList.add("PIPC000904");
        //attrList.add("PIPC000905");

        if (!isSecondCard) {
            attrList.add("PIPC000209");
            attrList.add("PIPC000501");
            attrList.add("PIPC000618");
        } else {
            attrList.add("PIPC000219");
            attrList.add("PIPC000511");
            attrList.add("PIPC000628");
        }

        return attrList;
    }
}
