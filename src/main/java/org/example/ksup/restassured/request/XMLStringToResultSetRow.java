package org.example.ksup.restassured.request;

import org.example.ksup.restassured.pojo.outparms.ResultSetRow;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLStringToResultSetRow {
    public static List<ResultSetRow> parser(String response) {

        Pattern fl8pckPattern = Pattern.compile("<fl8pck>(.*?)</fl8pck>");
        Pattern fl1proPattern = Pattern.compile("<fl1pro>(.*?)</fl1pro>");
        Pattern fl3prmPattern = Pattern.compile("<fl3prm>(.*?)</fl3prm>");
        Pattern fl4valPattern = Pattern.compile("<fl4val>(.*?)</fl4val>");
        Pattern fl1grpPattern = Pattern.compile("<fl1grp>(.*?)</fl1grp>");
        Pattern fl1pprPattern = Pattern.compile("<fl1ppr>(.*?)</fl1ppr>");
        Matcher fl8pckMatcher = fl8pckPattern.matcher(response);
        Matcher fl1proMatcher = fl1proPattern.matcher(response);
        Matcher fl3prmMatcher = fl3prmPattern.matcher(response);
        Matcher fl4valMatcher = fl4valPattern.matcher(response);
        Matcher fl1grpMatcher = fl1grpPattern.matcher(response);
        Matcher fl1pprMatcher = fl1pprPattern.matcher(response);

        List<ResultSetRow> rows = new ArrayList<>();


        while (fl8pckMatcher.find() && fl1proMatcher.find() && fl1grpMatcher.find() && fl3prmMatcher.find() && fl4valMatcher.find() && fl1pprMatcher.find()) {
            ResultSetRow currentRow = new ResultSetRow();
            currentRow.setFl8pck(fl8pckMatcher.group(1));
            currentRow.setFl1pro(fl1proMatcher.group(1));
            currentRow.setFl1grp(fl1grpMatcher.group(1));
            currentRow.setFl3prm(fl3prmMatcher.group(1));
            currentRow.setFl4val(fl4valMatcher.group(1));
            currentRow.setFl1ppr(fl1pprMatcher.group(1));
            rows.add(currentRow);
        }
        return rows;
    }
}
