package org.example.ksup.restassured.pojo;

import org.example.ksup.restassured.pojo.inprms.inParms;

import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.*;
import java.io.StringWriter;

public class ObjectToXmlConvert {

    public static String convertObjectToXml(Object object) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);

        return writer.toString();
    }
}
