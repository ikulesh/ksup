package org.example.ksup.pojo.inprms;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Nested class of inParms
 */
@XmlRootElement
public class fllpfllst {
    @XmlElement(name = "recordSetRow")
    private RecordSetRow recordSetRow;

    /**
     * Method creates new instance of recordSetRow and sets fllpfl (client code)
     */
    public void setRecordSetRow(String fllpfl) {
        this.recordSetRow = new RecordSetRow();
        recordSetRow.setFllpfl(fllpfl);
    }
}