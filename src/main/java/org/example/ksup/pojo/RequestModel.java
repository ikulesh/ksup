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
    public void initializer(ExpectedDataModel expectedDataModel) {
        setExtSysCode(EXT_SYS_CODE);
        setConstantID(CONSTANT_ID);
        //setFl8pck(expectedDataModel.getFl8pck());
        setRegcd(expectedDataModel.getRegcd());
        setCurrency(expectedDataModel.getParamsPIPC().get("PIPC000004"));
        setPipc000812(expectedDataModel.getParamsPIPC().get("PIPC000812"));

    }

    /**
     * Method resets params for next iteration
     */
    public void reset() {
        //reset params
        setApplicationID(null);
        setFl1pro(null);
        setFl1grp(null);
    }
}
