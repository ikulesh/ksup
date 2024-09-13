package org.example.ksup.restassured.tests.assertions;

import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import java.util.List;

public abstract class SetPriceGrp {
    public static void setPriceGrp(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel) {
        for (ResultSetRow row : resultSetRowList) {
            if (/*row.getFl1pro().equals(expectedDataModel.getFl1pro()) &&*/ row.getFl8pck().equals(expectedDataModel.getFl8pck())) {
                expectedDataModel.setFl1grp(row.getFl1grp());
                break;
            }
        }
    }
}
