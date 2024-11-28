package edu.usb.argosinstrumentation.export;

import edu.usb.argosinstrumentation.domain.report.ReportLine;
import java.util.List;
import java.util.Map;

public class ReportLineUtils {

    private ReportLineUtils() {}

    public static void populateStatements(
            List<Integer> statements, Map<Integer, ReportLine> reportLineMap) {
        for (Integer statementNumber : statements) {
            ReportLine lineReport =
                    ReportLine.builder().statementNumber(statementNumber).isCovered(false).build();
            reportLineMap.put(statementNumber, lineReport);
        }
    }

    public static void populateCoveredStatements(
            List<Integer> statements, Map<Integer, ReportLine> reportLineMap) {
        for (Integer statement : statements) {
            reportLineMap.get(statement).setCovered(true);
        }
    }
}
