package org.example.ksup.tests.assertions;

import org.example.ksup.log.CustomLogger;
import org.example.ksup.pojo.outparms.ExpectedDataModel;
import org.example.ksup.pojo.outparms.ResultSetRow;

import java.util.List;
import java.util.logging.Level;


public abstract class AccessibilityAssertion {
    /**
     * Accessibility of package
     *
     * @param expectedDataModel info for comparing
     * @param resultSetRowList  response
     * @param warningsList      wrong params
     * @return boolean value for next step
     */
    public static boolean accessibilityAssertion(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel, List<String> warningsList) {
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
            warningsList.add("PIPC000801");
        } else if (accessible.equals("N")) {
            CustomLogger.customLogger(Level.INFO, "Correct accessibility: package was closed");
        } else {
            CustomLogger.customLogger(Level.FINE, "Correct accessibility: package available");
        }

        return nextStep;
    }
}
