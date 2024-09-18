package org.example.ksup.restassured.request;

import io.opentelemetry.semconv.SemanticAttributes;
import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.example.ksup.restassured.tests.assertions.ParamAssertions;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Config.IS_SECOND_CARD;

public class RequestIPC1st {
    public static List<ResultSetRow> requestForDifGCC(RequestModel requestModel, boolean isSecondCard) throws JAXBException {
        return Request.request("IPC", requestModel, setAttrList(isSecondCard), false, "W");
    }

    public static List<String> setAttrList(boolean isSecondCard) {
        List<String> attrList = new ArrayList<>();
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
        attrList.add("PIPC000503");
        attrList.add("PIPC000502");
        attrList.add("PIPC000606");
        attrList.add("PIPC000609");
        attrList.add("PIPC000703");
        attrList.add("PIPC000003");
        attrList.add("PIPC000807");
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

    public static void execution(RequestModel request, ExpectedDataModel expectedDataModel, List<String> warningsList) throws JAXBException {
        //отправляем запрос для проверки основных параметров для первой карты
        List<ResultSetRow> result = requestForDifGCC(request, false);
        CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = false) assertion:");
        if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
            ParamAssertions.paramAssertion(result, expectedDataModel, RequestIPC1st.setAssertList(false), warningsList);
        }

        //отправляем запрос для проверки альтернативных параметров для второй карты
        if (IS_SECOND_CARD) {
            result = RequestIPC1st.requestForDifGCC(request, true);
            CustomLogger.customLogger(Level.INFO, "IPC request (isSecondCard = true) assertion:");
            if (ParamAssertions.responseIsNotEmpty(result, expectedDataModel, request)) {
                ParamAssertions.paramAssertion(result, expectedDataModel, RequestIPC1st.setAssertList(true), warningsList);
            }
        }
    }
}
