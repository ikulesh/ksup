package org.example.ksup.request;

import org.example.ksup.pojo.RequestModel;
import org.example.ksup.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Old class (could be used, but this req has no sense)
 *
 * @deprecated
 */
public class RequestChooseCard {
    public static List<ResultSetRow> requestChooseCard(RequestModel requestModel) throws JAXBException {
        return Request.request(null, requestModel, setAttrList(), false, "O");
    }

    private static List<String> setAttrList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000813");
        attrList.add("PAPPTYPE");
        //ChooseCardRequest
        //accessibility
        //creditParams.add("PIPC000801");

        attrList.add("PIPC000002");
        attrList.add("PIPC000001");
        attrList.add("PCC0001046");
        attrList.add("PIPC000302");
        attrList.add("PIPC000007");
        attrList.add("PIPC000011");
        attrList.add("PIPC000303");
        attrList.add("PIPC000502");
        attrList.add("PIPC000503");
        attrList.add("PCC0002152");
        attrList.add("PCC0001056");
        attrList.add("PIPC000601");
        attrList.add("PIPC000602");
        attrList.add("PCC0001001");
        attrList.add("PIPC000801");
        attrList.add("PCC0002122");
        attrList.add("PCC0002022");
        attrList.add("PCC0002132");
        attrList.add("PCC0002032");
        attrList.add("PCC0001150");
        attrList.add("PIPC000608");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        attrList.add("PIPC000501");
        attrList.add("PCC0002121");
        attrList.add("PCC0002131");
        attrList.add("PCC0002151");
        attrList.add("PIPC000618");

        return attrList;
    }

    public static List<String> setAssertList() {
        List<String> attrList = new ArrayList<>();
        attrList.add("PIPC000302");
        attrList.add("PIPC000002");
        attrList.add("PIPC000801");
        attrList.add("PIPC000001");


        return attrList;
    }
}
