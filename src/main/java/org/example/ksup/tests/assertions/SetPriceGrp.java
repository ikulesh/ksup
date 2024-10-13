package org.example.ksup.tests.assertions;

import org.example.ksup.pojo.outparms.ExpectedDataModel;
import org.example.ksup.pojo.outparms.ResultSetRow;

import java.util.List;

/**
 * Setting price group for sending req
 */
public class SetPriceGrp {
    /**
     * @param resultSetRowList  response
     * @param expectedDataModel comparing info
     */
    public static void setPriceGrp(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel) {
        for (ResultSetRow row : resultSetRowList) {
            if (/*row.getFl1pro().equals(expectedDataModel.getFl1pro()) &&*/ row.getFl8pck().equals(expectedDataModel.getFl8pck())) {
                expectedDataModel.setFl1grp(row.getFl1grp());
                break;
            }
        }
    }
}
