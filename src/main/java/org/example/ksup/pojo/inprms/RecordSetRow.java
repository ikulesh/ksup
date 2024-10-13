package org.example.ksup.pojo.inprms;

import org.example.ksup.pojo.RequestModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Nested class of inParms
 */
@XmlRootElement(name = "recordSetRow")
public class RecordSetRow {
    @XmlElement(name = "prm")
    private String prm;
    @XmlElement(name = "val")
    private String val;
    @XmlElement(name = "valcond")
    private Integer valcond;
    @XmlElement(name = "prmmod")
    private Integer prmmod;
    @XmlElement(name = "fllpfl")
    private String fllpfl;

    public RecordSetRow() {
    }

    public void setFllpfl(String fllpfl) {
        this.fllpfl = fllpfl;
    }

    /**
     * Method sets recordSetRow
     *
     * @param prm          contains in ExpectedDataModel
     * @param requestModel instance of RequestModel
     * @param riskLevel    contains in ExpectedDataModel
     */
    public RecordSetRow(String prm, RequestModel requestModel, String riskLevel) {
        this.prm = prm;
        if ("PAPPTYPE".equals(prm)) {
            this.val = "CC";
            this.valcond = 1;
            this.prmmod = 1;
        } else if ("PIPC000004".equals(prm)) {
            this.val = requestModel.getCurrency();
            this.valcond = 1;
            this.prmmod = 2;
        } else if ("PIPC000801".equals(prm)) {
            this.val = "Y";
            this.valcond = 1;
            this.prmmod = 2;
        } else if ("PIPC000812".equals(prm)) {
            this.val = requestModel.getPipc000812();
            this.valcond = 1;
            this.prmmod = 2;
        } else if ("PCC0002053".equals(prm)) {
            this.val = riskLevel;
            this.valcond = 1;
            this.prmmod = 2;
        } else if ("PCC0000605".equals(prm)) {
            this.val = requestModel.getPcc0000605();
            this.valcond = 6;
            this.prmmod = 2;
        } else {
            this.prmmod = 2;
        }
    }
}
