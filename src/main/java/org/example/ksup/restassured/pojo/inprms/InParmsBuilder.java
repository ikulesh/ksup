package org.example.ksup.restassured.pojo.inprms;

import org.example.ksup.restassured.pojo.RequestModel;

import java.util.List;

public class InParmsBuilder {
    public static inParms inParmsBuilder(boolean withoutRegAndChannel, String reqGroup, String alg, List<String> attrlist, RequestModel requestModel) {
        inParms inPrm = new inParms();
        inPrm.setFl1grp(requestModel.getFl1grp());
        inPrm.setFl1pro(requestModel.getFl1pro());
        inPrm.setFl8sts("ACTIVE");
        inPrm.setPrmmod(1);
        inPrm.setFl5sts("ACTIVE");
        inPrm.setFl5tpr(reqGroup);
        inPrm.setHrymod("I");
        inPrm.setFl8pck(requestModel.getFl8pck());
        if (!withoutRegAndChannel) {
            inPrm.setAlg(alg);
            inPrm.setRegcd(requestModel.getRegcd());
            inPrm.setChancd(requestModel.getChancd());
            inPrm.setPagesize(1000);
            inPrm.setPageindex(1);
        } else {
            inPrm.setTp6cd("PTECH");
            inPrm.setAlg("8");
        }
        attrlst attrlst = new attrlst();
        attrlst.addParam(attrlist,requestModel, requestModel.getRiskLevel());
        inPrm.setAttrlst(attrlst);

        fllpfllst fllpfllst = new fllpfllst();
        fllpfllst.setRecordSetRow(requestModel.getFllpfl());
        inPrm.setFllpfllst(fllpfllst);

        return inPrm;
    }
}
