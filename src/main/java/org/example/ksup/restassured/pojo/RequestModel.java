package org.example.ksup.restassured.pojo;

import lombok.Data;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;

import static org.example.ksup.restassured.Properties.CONSTANT_ID;
import static org.example.ksup.restassured.Properties.EXT_SYS_CODE;

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

    public void initializer(ExpectedDataModel expectedDataModel) {
        setExtSysCode(EXT_SYS_CODE);
        setConstantID(CONSTANT_ID);
        setFl8pck(expectedDataModel.getFl8pck());
        setRegcd(expectedDataModel.getRegcd());
        setCurrency(expectedDataModel.getParamsPIPC().get("PIPC000004"));
        setPipc000812(expectedDataModel.getParamsPIPC().get("PIPC000812"));
    }
}
