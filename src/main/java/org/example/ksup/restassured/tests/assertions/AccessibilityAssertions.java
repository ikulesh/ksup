package org.example.ksup.restassured.tests.assertions;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;

import java.util.List;
import java.util.logging.Level;

import static org.example.ksup.restassured.Config.POSITIVE_ASSERT_LOGS;


public abstract class AccessibilityAssertions {
    public static boolean accessibilityAssertions(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel) {
        String expectedAccessibility = expectedDataModel.getParamsPIPC().get("PIPC000801");
        String accessible = "N";

        // Return early if resultSetRowList is null or empty
        if (resultSetRowList != null && !resultSetRowList.isEmpty()) {
            for (ResultSetRow row : resultSetRowList) {
                if (row.getFl8pck().equals(expectedDataModel.getFl8pck())) {
                    if (expectedDataModel.getFl1grp() == null) {
                        expectedDataModel.setFl1grp(row.getFl1grp());
                    }
                    if (row.getFl3prm().equals("PIPC000801") && row.getFl4val().equals("Y")) {
                        accessible = "Y";
                        break;
                    }
                }
            }
        }

        boolean nextStep = expectedAccessibility.equals("Y") && accessible.equals("Y");

        if (!expectedAccessibility.equals(accessible)) {
            CustomLogger.customLogger(Level.WARNING, "Wrong accessibility: PIPC000801 equals " + accessible + ", but should be " + expectedAccessibility);
        } else if (POSITIVE_ASSERT_LOGS) {
            CustomLogger.customLogger(Level.INFO, "Correct accessibility");
        }

        return nextStep;
    }
}
