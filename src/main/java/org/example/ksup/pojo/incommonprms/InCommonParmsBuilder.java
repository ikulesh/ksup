package org.example.ksup.pojo.incommonprms;


import org.example.ksup.pojo.RequestModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder of inCommonParms
 */
public class InCommonParmsBuilder {
    /**
     * @param requestModel instance of RequestModel object
     * @return instance of inCommonParms
     */
    public static InCommonParms inCommonParmsBuilder(RequestModel requestModel) {
        InCommonParms inComPar = new InCommonParms();
        inComPar.setUserID("WSFL");
        inComPar.setBranchNumber("0000");
        inComPar.setExternalUserCode("WSFL");
        inComPar.setExternalSystemCode(requestModel.getExtSysCode());
        List<InCommonParmsExt> inCommonParmsExtList = new ArrayList<>();
        inCommonParmsExtList.add(new InCommonParmsExt("ConstantID", requestModel.getConstantID()));
        if (requestModel.getApplicationID() != null) {
            inCommonParmsExtList.add(new InCommonParmsExt("AppID", requestModel.getApplicationID()));
        }
        inComPar.setInCommonParmsExt(inCommonParmsExtList);
        return inComPar;
    }
}
