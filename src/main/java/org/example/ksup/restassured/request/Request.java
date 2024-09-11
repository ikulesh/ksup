package org.example.ksup.restassured.request;

import io.restassured.RestAssured;
import org.example.ksup.restassured.pojo.ObjectToXmlConvert;
import org.example.ksup.restassured.pojo.RequestModel;
import org.example.ksup.restassured.pojo.incommonprms.inCommonParms;
import org.example.ksup.restassured.pojo.incommonprms.inCommonParmsBuilder;
import org.example.ksup.restassured.pojo.inprms.inParms;
import org.example.ksup.restassured.pojo.inprms.inParmsBuilder;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import javax.xml.bind.JAXBException;
import java.util.List;

import static org.example.ksup.restassured.request.XMLStringToResultSetRow.parser;


public class Request {
    public static List<ResultSetRow> request(String reqGroup, RequestModel requestModel, List<String> attrList, boolean withoutRegAndChannel, String alg) throws JAXBException {
        inCommonParms inComParms = inCommonParmsBuilder.inCommonParmsBuilder(requestModel.getConstantID(), requestModel.getApplicationID(), requestModel.getExtSysCode());
        inParms inParmsObj = inParmsBuilder.inParmsBuilder(withoutRegAndChannel, reqGroup, alg, attrList, requestModel);

        String request = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "<soapenv:Body>\n" +
                "<ns1:WSProductCatalogPackageGetProdParamValues xmlns:ns1=\"http://WSProductCatalogPackage14.EQ.CS.ws.alfabank.ru\">\n" +
                ObjectToXmlConvert.convertObjectToXml(inComParms) + "\n" +
                ObjectToXmlConvert.convertObjectToXml(inParmsObj) + "\n" +
                "</ns1:WSProductCatalogPackageGetProdParamValues>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";

        String response = RestAssured
                .given().baseUri("http://esbwsint:80/CS/EQ/WSProductCatalogPackage14/SOAP")
                .header("Content-Type", "text/xml")
                .header("Accept", "text/xml")
                .body(request).log().all()
                .when().post()
                .then().log().all()
                .extract().asString();

        return parser(response);
    }
}
