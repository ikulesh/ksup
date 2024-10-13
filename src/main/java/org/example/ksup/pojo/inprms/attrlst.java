package org.example.ksup.pojo.inprms;

import org.example.ksup.pojo.RequestModel;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Nested class of inParms.
 */
@XmlRootElement
public class attrlst {
    @XmlElement(name = "recordSetRow")
    private List<RecordSetRow> recordSetRow;

    public attrlst() {
        this.recordSetRow = new ArrayList<>();
    }

    /**
     * Method adds elements inside String list
     *
     * @param requestModel instance of RequestModel
     * @param params       param list (e.g. PIPC000801), contains in ExpectedDataModel
     * @param riskLevel    contains in ExpectedDataModel
     */
    public void addParam(List<String> params, RequestModel requestModel, String riskLevel) {
        for (String param : params) {
            recordSetRow.add(new RecordSetRow(param, requestModel, riskLevel));
        }
    }
}
