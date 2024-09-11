package org.example.ksup.restassured.pojo.inprms;

import java.util.List;

public class inParmsBuilder {
    public static inParms inParmsBuilder(boolean withoutRegAndChannel, String reqGroup, String fl8pck, String chancd, String regcd, String fl1grp, String fllpfl, String fl1pro, String riskLevel, String alg, List<String> attrlist) {
        inParms inPrm = new inParms();
        inPrm.setFl1grp(fl1grp);
        inPrm.setFl1pro(fl1pro);
        inPrm.setFl8sts("ACTIVE");
        inPrm.setPrmmod(1);
        inPrm.setFl5sts("ACTIVE");
        inPrm.setFl5tpr(reqGroup);
        inPrm.setHrymod("I");
        inPrm.setFl8pck(fl8pck);
        if (!withoutRegAndChannel) {
            inPrm.setAlg(alg);
            inPrm.setRegcd(regcd);
            inPrm.setChancd(chancd);
            inPrm.setPagesize(1000);
            inPrm.setPageindex(1);
        } else {
            inPrm.setTp6cd("PTECH");
            inPrm.setAlg("8");
        }
        attrlst attrlst = new attrlst();
        attrlst.addParam(attrlist, riskLevel);
        inPrm.setAttrlst(attrlst);

        fllpfllst fllpfllst = new fllpfllst();
        fllpfllst.setRecordSetRow(fllpfl);
        inPrm.setFllpfllst(fllpfllst);

        return inPrm;
    }
}
