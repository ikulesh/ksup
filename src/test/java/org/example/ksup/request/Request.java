package org.example.ksup.request;

import io.restassured.RestAssured;
import org.example.ksup.log.CustomLogger;
import org.example.ksup.pojo.ObjectToXmlConvert;
import org.example.ksup.pojo.RequestModel;
import org.example.ksup.pojo.incommonprms.InCommonParms;
import org.example.ksup.pojo.inprms.InParms;
import org.example.ksup.pojo.outparms.ResultSetRow;
import org.example.ksup.pojo.incommonprms.InCommonParmsBuilder;
import org.example.ksup.pojo.inprms.InParmsBuilder;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.logging.Level;

/**
 * Sending request.
 */
public class Request {
    /**
     * Method build request body and sends request.
     *
     * @param requestModel         instance, which cointains main request info
     * @param alg                  attr of inParms, could be various
     * @param attrList             param list (different for every request)
     * @param reqGroup             request type (IPC, GRC, etc.)
     * @param withoutRegAndChannel param for technical requests
     * @return response as a list of ResultSetRow
     */
    public static List<ResultSetRow> request(String reqGroup, RequestModel requestModel, List<String> attrList, boolean withoutRegAndChannel, String alg) throws JAXBException {
        InCommonParms inComParms = InCommonParmsBuilder.inCommonParmsBuilder(requestModel);
        InParms inParmsObj = InParmsBuilder.inParmsBuilder(withoutRegAndChannel, reqGroup, alg, attrList, requestModel);

        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "<soapenv:Body>\n" +
                "<ns1:WSProductCatalogPackageGetProdParamValues xmlns:ns1=\"http://WSProductCatalogPackage14.EQ.CS.ws.alfabank.ru\">\n" +
                ObjectToXmlConvert.convertObjectToXml(inComParms) + "\n" +
                ObjectToXmlConvert.convertObjectToXml(inParmsObj) + "\n" +
                "</ns1:WSProductCatalogPackageGetProdParamValues>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String response;
        try {
            response = RestAssured
                    .given().baseUri("http://esbwsint:80/CS/EQ/WSProductCatalogPackage14/SOAP")
                    .header("Content-Type", "text/xml; charset=UTF-8")
                    .header("Accept", "text/xml")
                    .body(request).log().all()
                    .when().post()
                    .then().log().all()
                    .extract().asString();
        } catch (Exception e) {
            // Log and rethrow the exception for better error handling
            CustomLogger.customLogger(Level.SEVERE, "Error occurred during SOAP request: " + e.getMessage());
            throw e;
        }

        return XMLStringToResultSetRow.parser(response);
    }
}
