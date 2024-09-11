package org.example.ksup.restassured.pojo.inprms;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class fllpfllst {
    @XmlElement(name = "recordSetRow")
    private recordSetRow recordSetRow;

    public void setRecordSetRow(String fllpfl) {
        this.recordSetRow = new recordSetRow();
        recordSetRow.setFllpfl(fllpfl);
    }
}