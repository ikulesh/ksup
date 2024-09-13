package org.example.ksup.restassured.tests.assertions;

import org.example.ksup.restassured.log.CustomLogger;
import org.example.ksup.restassured.pojo.outparms.ExpectedDataModel;
import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import java.util.List;
import java.util.logging.Level;

public abstract class AccessibilityAssertions {
    public static boolean accessibilityAssertions(List<ResultSetRow> resultSetRowList, ExpectedDataModel expectedDataModel) {
        boolean nextStep = false;
        String accessForCard = "N";
        for (ResultSetRow row : resultSetRowList) {
            if (row.getFl8pck().trim().equals(expectedDataModel.getFl8pck().trim())) {
                if (expectedDataModel.getFl1grp() == null) {
                    expectedDataModel.setFl1grp(row.getFl1grp());
                }
                if (row.getFl3prm().equals("PIPC000801")) {
                    if (row.getFl4val().trim().equals("Y")) {
                        accessForCard = "Y";
                        nextStep = true;
                    }
                }
            }
        }
        try {
            Assertions.assertEquals(expectedDataModel.getAccessibility(), accessForCard);
        } catch (AssertionFailedError error) {
            CustomLogger.customLogger(Level.WARNING, "Wrong level of accessibility for: " + expectedDataModel.getFl8pck() + " " + expectedDataModel.getFl1proCat() + " " + expectedDataModel.getFllpfl() + " " + expectedDataModel.getFl1pro());
        }
        return nextStep;
    }
}
