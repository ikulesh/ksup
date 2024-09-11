package org.example.ksup.restassured.pojo.incommonprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class inCommonParmsExt {
    private String name;
    private String value;
    public inCommonParmsExt(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public inCommonParmsExt() {
    }
}
