package org.example.ksup.restassured.pojo.incommonprms;


import java.util.ArrayList;
import java.util.List;

public class inCommonParmsBuilder {
    public static inCommonParms inCommonParmsBuilder(String constantID, String appID, String extSysCode) {
        inCommonParms inComPar = new inCommonParms();
        inComPar.setUserID("WSFL");
        inComPar.setBranchNumber("0000");
        inComPar.setExternalUserCode("WSFL");
        inComPar.setExternalSystemCode(extSysCode);
        List<inCommonParmsExt> inCommonParmsExtList = new ArrayList<>();
        inCommonParmsExtList.add(new inCommonParmsExt("ConstantID", constantID));
        if (appID != null) {
            inCommonParmsExtList.add(new inCommonParmsExt("AppID", appID));
        }
        inComPar.setInCommonParmsExt(inCommonParmsExtList);
        return inComPar;
    }
}
