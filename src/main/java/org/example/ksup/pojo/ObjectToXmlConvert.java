package org.example.ksup.pojo;


import javax.xml.bind.*;
import java.io.StringWriter;

/**
 * Class for converting.
 */
public class ObjectToXmlConvert {
    /**
     * Method converts RequestDataModel into String for sending request (body)
     */
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
