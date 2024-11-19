package org.example.ksup.pojo.incommonprms;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * inCommonParmsExt is nested class of inCommonParms
 */
@Data
@XmlRootElement(name = "inCommonParmsExt")
public class InCommonParmsExt {
    private String name;
    private String value;

    public InCommonParmsExt(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public InCommonParmsExt() {
    }
}
