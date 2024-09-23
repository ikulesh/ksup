package org.example.ksup.restassured.pojo.inprms;

import org.example.ksup.restassured.pojo.RequestModel;

import java.util.List;

/**
 * inParms builder.
 */
public class InParmsBuilder {
    /**
     * inParmsBuilder create instance of inParms
     *
     * @param withoutRegAndChannel true for technical request
     * @param reqGroup             fl5tpr
     * @param alg                  could be various (e.g. "8", "W")
     * @param attrlist             list of params, contains in ExpectedDataModel
     * @param requestModel         instance of RequestModel
     * @return inParms instance
     */
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
        attrlst.addParam(attrlist, requestModel, requestModel.getRiskLevel());
        inPrm.setAttrlst(attrlst);

        fllpfllst fllpfllst = new fllpfllst();
        fllpfllst.setRecordSetRow(requestModel.getFllpfl());
        inPrm.setFllpfllst(fllpfllst);

        return inPrm;
    }
}
