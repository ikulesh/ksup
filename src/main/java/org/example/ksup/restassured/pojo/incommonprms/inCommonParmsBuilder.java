package org.example.ksup.restassured.pojo.incommonprms;


import org.example.ksup.restassured.pojo.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class inCommonParmsBuilder {
    public static inCommonParms inCommonParmsBuilder(RequestModel requestModel) {
        inCommonParms inComPar = new inCommonParms();
        inComPar.setUserID("WSFL");
        inComPar.setBranchNumber("0000");
        inComPar.setExternalUserCode("WSFL");
        inComPar.setExternalSystemCode(requestModel.getExtSysCode());
        List<inCommonParmsExt> inCommonParmsExtList = new ArrayList<>();
        inCommonParmsExtList.add(new inCommonParmsExt("ConstantID", requestModel.getConstantID()));
        if (requestModel.getApplicationID() != null) {
            inCommonParmsExtList.add(new inCommonParmsExt("AppID", requestModel.getApplicationID()));
        }
        inComPar.setInCommonParmsExt(inCommonParmsExtList);
        return inComPar;
    }
}
