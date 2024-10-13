package org.example.ksup.pojo;

import lombok.Data;
import org.example.ksup.pojo.outparms.ExpectedDataModel;

import static org.example.ksup.Config.CONSTANT_ID;
import static org.example.ksup.Config.EXT_SYS_CODE;

/**
 * Class for sending request.
 */
@Data
public class RequestModel {
    private String fl8pck;
    private String constantID;
    private String applicationID;
    private String extSysCode;
    private String chancd;
    private String regcd;
    private String fl1grp;
    private String fl1pro;
    private String fllpfl;
    private String riskLevel;
    private String currency;
    private String pipc000812;
    private String pcc0000605;

    /**
     * Method initialize common part of all requests
     *
     * @param expectedDataModel data for sending
     */
    public void initialize(ExpectedDataModel expectedDataModel) {
        extSysCode = EXT_SYS_CODE;
        constantID = CONSTANT_ID;
        //setFl8pck(expectedDataModel.getFl8pck());
        regcd = expectedDataModel.getRegcd();
        currency = expectedDataModel.getParamsPIPC().get("PIPC000004");
        pipc000812 = expectedDataModel.getParamsPIPC().get("PIPC000812");
    }

    /**
     * Method resets params for next iteration
     */
    public void reset() {
        //reset params
        applicationID = null;
        fl1pro = null;
        fl1grp = null;
    }
}
